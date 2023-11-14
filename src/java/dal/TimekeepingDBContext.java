/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.Timekeeping;


public class TimekeepingDBContext extends DBContext{
    
    public ArrayList<Timekeeping> getTimekeeping(int year,int month,int id){
        try {
            String sql = "select * from Timekeeping_Employees\n" +
                    "where MONTH(t_day) = ? AND YEAR(t_day)=? \n" +
                    "AND e_id = ? ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, month);
            stm.setInt(2, year);
            stm.setInt(3, id);
            ResultSet rs = stm.executeQuery();
            ArrayList<Timekeeping> timekeepings = new ArrayList<>();
            while(rs.next()){
                Timekeeping t = new Timekeeping();
                t.setDay(rs.getDate("t_day"));
                timekeepings.add(t);
            }
            return timekeepings;
        } catch (SQLException ex) {
            Logger.getLogger(TimekeepingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void insertTimekeeping(String[] listDate,int id){
        String sql ="INSERT INTO [Timekeeping_Employees]\n" +
                    "           ([t_day]\n" +
                    "           ,[e_id])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?)";
        try {
            connection.setAutoCommit(false);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(int i  = 0 ;i<listDate.length;i++){
                java.sql.Date sqlDate;
                Date d = null;
                try {
                    d = (Date)sdf.parse(listDate[i]);
                } catch (ParseException ex) {
                    Logger.getLogger(TimekeepingDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
                sqlDate = new java.sql.Date(d.getTime());
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1,sqlDate);
            stm.setInt(2,id);
            stm.executeUpdate();
            }
        connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(TimekeepingDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(TimekeepingDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        finally
        {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(TimekeepingDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            //close connection
        }
        
    }

    public void deleteTimekeeping(int year, int month,int id) {
        try {
            String sql = "DELETE FROM [Timekeeping_Employees]\n" +
                    "       where MONTH(t_day) = ? \n" +
                    "	   AND YEAR(t_day)=? \n" +
                    "       AND e_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, month);
            stm.setInt(2, year);
            stm.setInt(3, id);
            stm.executeUpdate();
                    } catch (SQLException ex) {
            Logger.getLogger(TimekeepingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
   
