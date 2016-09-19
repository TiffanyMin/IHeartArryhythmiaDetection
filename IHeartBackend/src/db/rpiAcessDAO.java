package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Data.SerailData;

public class rpiAcessDAO {
    public Connection getConnection() {
        String url = "jdbc:mysql://192.168.0.38:3306/ECG_Raw?useSSL=true&verifyServerCertificate=false";
        String id ="SSLECGDoc";
        String pw = "";
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //return DriverManager.getConnection(url);
            return DriverManager.getConnection(url, id, pw);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void addData(SerailData data) throws SQLException {
        // TODO Auto-generated method stub
        String sql = "insert into SerailData values(?,?,?,?)";        // sql 쿼리
        PreparedStatement pstmt = this.getConnection().prepareStatement(sql);                          // prepareStatement에서 해당 sql을 미리 컴파일한다.
        pstmt.setString(1,  data.getUserID());
        pstmt.setTimestamp(2,  new java.sql.Timestamp(data.getDate().getTime()));
        pstmt.setDouble(3,  data.getData());
        pstmt.setInt(4,  data.getSampleNo());
        
        
        pstmt.executeUpdate();

    }
    
    public SerailData getDataByIndex(int sampleIndex) throws SQLException{
        String sql = "select * from SerailData where sampleNo = ?";
        PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
        pstmt.setInt(4, sampleIndex);
        
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            SerailData data = new SerailData(
                    rs.getString("userId"),
                    rs.getDate("date"),
                    rs.getLong("data"),
                    rs.getInt("sampleNo"));
            return data;
                    
        }
        return null;
        
        
    }
    
    public List<SerailData> getAllData() throws SQLException{
        String sql = "select * from SerailData;";
        Connection con = this.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<SerailData> returnList = new ArrayList<SerailData>();
        
        while(rs.next())
        {
            SerailData data = new SerailData(
                    rs.getString("userId"),
                    rs.getDate("date"),
                    rs.getLong("data"),
                    rs.getInt("sampleNo"));
            returnList.add(data);
        //System.out.println(rs.getString("data"));//or getString(1) for coloumn 1 etc
        }
        
        //ResultSet rs = pstmt.executeQuery();
        
        return returnList;
        
    }
    
}
