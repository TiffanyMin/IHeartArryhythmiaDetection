package db;

import java.sql.SQLException;
import java.util.Date;

import Data.SerailData;

public class test {
    
    
    public static void main(String[] args){
        SerailData rawData = new SerailData("1235", new Date(), 11.3 ,0);
        DataDAO dataDAO = new DataDAO();
        try{
            dataDAO.addData(rawData);
        } catch(SQLException e){ 
            e.printStackTrace();          
        }
    }
}