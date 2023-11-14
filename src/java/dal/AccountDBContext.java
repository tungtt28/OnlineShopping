/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;


public class AccountDBContext extends DBContext{
   public void insertAccount(Account account) {
           String sql_new_employee = "select top 1 e_id from Employees \n" +
                   "order by e_id DESC";
           String sql_insert_account = "INSERT INTO [Account]\n" +
                   "           ([username]\n" +
                   "           ,[password]\n" +
                   "           ,[displayname]\n" +
                   "           ,[e_id])\n" +
                   "     VALUES\n" +
                   "           (?\n" +
                   "           ,?\n" +
                   "           ,?\n" +
                   "           ,?\n)";

      try {
            connection.setAutoCommit(false);
            PreparedStatement stm_new_employee = connection.prepareStatement(sql_new_employee);
               ResultSet rs = stm_new_employee.executeQuery();
               int e_id = 0;
            if(rs.next()){
                e_id=rs.getInt("e_id");
            }
            PreparedStatement stm_insert_account = connection.prepareStatement(sql_insert_account);
            stm_insert_account.setString(1, account.getUsername());
            stm_insert_account.setString(2, account.getPassword());
            stm_insert_account.setString(3, account.getDisplayname());
            stm_insert_account.setInt(4, e_id);
            stm_insert_account.executeUpdate();
            
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        finally
        {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            //close connection
        }
   }
   public Account getAccountById(int id){
       try {
           String sql = "select username,password,displayname,e_id from Account\n" +
                        "where e_id  = ?";
           PreparedStatement stm  = connection.prepareStatement(sql);
           stm.setInt(1, id);
           ResultSet rs = stm.executeQuery();
           if(rs.next()){
             Account a = new Account();
             a.setUsername(rs.getString("username"));
             a.setPassword(rs.getString("password"));
             a.setDisplayname(rs.getString("displayname"));
             a.setEid(rs.getInt("e_id"));
             return a;
           }
       } catch (SQLException ex) {
           Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }
   public Account getAccount(String user , String pass){
       try {
           String sql = "SELECT username,password,displayname,e_id FROM Account \n"
                   + "WHERE username = ? and password = ?";
           PreparedStatement stm  = connection.prepareStatement(sql);
           stm.setString(1, user);
           stm.setString(2, pass);
           ResultSet rs = stm.executeQuery();
           if(rs.next()){
             Account a = new Account();
             a.setUsername(rs.getString("username"));
             a.setPassword(rs.getString("password"));
             a.setDisplayname(rs.getString("displayname"));
             a.setEid(rs.getInt("e_id"));
             return a;
           }
       } catch (SQLException ex) {
           Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }
   public int getEIdByUser(String user){
       try {
           String sql = "SELECT username,password,displayname,e_id FROM Account \n"
                   + "WHERE username = ? ";
           PreparedStatement stm  = connection.prepareStatement(sql);
           stm.setString(1, user);
           ResultSet rs = stm.executeQuery();
           if(rs.next()){
             return rs.getInt("e_id");
           }
       } catch (SQLException ex) {
           Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
       }
       return -1;
   }
   public int getPermission(String username, String url)
    {
        try {
            String sql = "SELECT COUNT(*) as Total FROM \n" +
"            Account a INNER JOIN Group_Account ga ON a.username = ga.username\n" +
"           		  INNER JOIN [Group] g ON g.g_id = ga.g_id\n" +
"            		  INNER JOIN Group_Feature gf ON gf.g_id = g.g_id\n" +
"            		  INNER JOIN Feature f ON f.f_id = gf.f_id\n" +
"					  WHERE a.username = ? AND f.url = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, url);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
            {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void updateAccount(Account account, String user) {
       try {
           String sql = "UPDATE [Account]\n" +
                   "   SET [password] = ?\n" +
                   "      ,[displayname] = ?\n" +
                   " WHERE e_id = ?";
           PreparedStatement stm = connection.prepareStatement(sql);
           stm.setString(1, account.getPassword());
           stm.setString(2, account.getDisplayname());
           stm.setInt(3, getEIdByUser(user));
           stm.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    public void updateEidAccount(int id) {
        try {
            String sql = "UPDATE [Account]\n" +
                    "   SET [e_id] = NULL\n" +
                    " WHERE username = ?";
            Account account = getAccountById(id);
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, account.getUsername());
            stm.executeUpdate();
                    } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  public boolean checkAccountExist(String user){
       try {
           String sql = "SELECT username,password,displayname,e_id FROM Account \n"
                   + "WHERE username = ?";
           PreparedStatement stm  = connection.prepareStatement(sql);
           stm.setString(1, user);
           ResultSet rs = stm.executeQuery();
           if(rs.next()){
             return true;
           }
       } catch (SQLException ex) {
           Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
       }
       return false;
   }
  public void insertGroupAccount(String username){
       try {
           String sql = "INSERT INTO [Group_Account]\n" +
                   "           ([username]\n" +
                   "           ,[g_id])\n" +
                   "     VALUES\n" +
                   "           (?\n" +
                   "           ,2)";
           PreparedStatement stm = connection.prepareStatement(sql);
           stm.setString(1, username);
           stm.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
       }
  }

    public void deleteGroupAccount(int id) {
       try {
           Account account = getAccountById(id);
           String sql = "DELETE FROM [Group_Account]\n" +
                   "WHERE username = ?";
           PreparedStatement stm = connection.prepareStatement(sql);
           stm.setString(1, account.getUsername());
           stm.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    public int getGroupAccount(String username){
       try {
           String sql = "select g_id from Group_Account\n" +
                   "where username = ?";
           PreparedStatement stm = connection.prepareStatement(sql);
           stm.setString(1, username);
           ResultSet rs = stm.executeQuery();
           if(rs.next()){
               return rs.getInt("g_id");
           }
                   } catch (SQLException ex) {
           Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
       }
       return -1;
    }
}
