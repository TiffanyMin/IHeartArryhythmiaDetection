package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.fazecast.jSerialComm.SerialPort;

import Data.SerailData;
import db.DataDAO;

public class ArduinoSensorGraphWorking {
	
	static SerialPort chosenPort;
	static int x = 0;
	static String userID;
	
	public static void main(String[] args) {
		
		// create and configure the window
		JFrame window = new JFrame();
		window.setTitle("Sensor Graph GUI");
		window.setSize(600, 400);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Text box with user ID
        JTextField textField = new JTextField("user ID");
        //userID = textField.getText();
		
		// create a drop-down box and connect button, then place them at the top of the window
		JComboBox<String> portList = new JComboBox<String>();
		JButton connectButton = new JButton("Connect");
		JPanel topPanel = new JPanel();
		topPanel.add(portList);
		topPanel.add(connectButton);
		topPanel.add(textField);
		window.add(topPanel, BorderLayout.NORTH);
		
		// populate the drop-down box
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());
		
		// create the line graph
		XYSeries series = new XYSeries("ECG(V)");
		XYSeriesCollection dataset = new XYSeriesCollection(series);
		JFreeChart chart = ChartFactory.createXYLineChart("ECG(V)", "Time (seconds)", "mVolts", dataset);
		window.add(new ChartPanel(chart), BorderLayout.CENTER);
		
		XYPlot plot = chart.getXYPlot();
		NumberAxis domainAxis=(NumberAxis) plot.getDomainAxis();
		NumberAxis rangeAxis=(NumberAxis) plot.getRangeAxis();
		rangeAxis.setAutoRangeIncludesZero(false);
		//rangeAxis.setRange(250, 500);
		rangeAxis.setAutoRange(true);
		//Domain axis would show data of 10000 samples for a time
		domainAxis.setFixedAutoRange(1000);  // 10000 samples
        //domainAxis.setVerticalTickLabels(true);
		// configure the connect button and use another thread to listen for data
		connectButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0) {
				if(connectButton.getText().equals("Connect")) {
					// attempt to connect to the serial port
					chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if(chosenPort.openPort()) {
						connectButton.setText("Disconnect");
						portList.setEnabled(false);
					}
					
					//Get userID
	                userID = textField.getText();
					
					// create a new thread that listens for incoming text and populates the graph
					Thread thread = new Thread(){
						@Override public void run() {
							Scanner scanner = new Scanner(chosenPort.getInputStream());
							while(scanner.hasNextLine()) {
							    
								try {
								    
								    
									String line = scanner.nextLine();
									float number = Float.parseFloat(line);
									series.add(x++, number);
									
									//Put raw data into mysql
									///java.util.Date utilDate = new java.util.Date();
								    ///java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	                                SerailData rawData = new SerailData(userID, new Date(), number ,x);
	                                DataDAO dataDAO = new DataDAO();
	                                try{
	                                    dataDAO.addData(rawData);
	                                } catch(SQLException e){ 
	                                }
	                                
	                                //Try to delay??
									window.repaint();
									
									//Thread.sleep(10);
								} catch(Exception e) {}
							}
							scanner.close();
						}
					};
					thread.start();
				} else {
					// disconnect from the serial port
					chosenPort.closePort();
					portList.setEnabled(true);
					connectButton.setText("Connect");
					series.clear();
					x = 0;
				}
			}
		});
		
		// show the window
		window.setVisible(true);
	}

}