package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Data.SerailData;

public class DataDAO {
    public Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/ECG_Raw";
        String id ="root";
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
        String sql = "select * from USERS where sampleNo = ?";
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
}
