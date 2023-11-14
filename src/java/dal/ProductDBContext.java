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
import model.Order;
import model.OrderDetail;
import model.Product;


public class ProductDBContext extends DBContext{
    public ArrayList<Product> getProducts(){
        try {
            String sql = "select p_id,phase_motor,kw_motor,speed_minutes,country,price,quantity\n" +
                    "from Products";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while(rs.next()){
                Product p = new Product();
                p.setId(rs.getInt("p_id"));
                p.setPhase(rs.getInt("phase_motor"));
                p.setKw(rs.getFloat("kw_motor"));
                p.setSpeed(rs.getInt("speed_minutes"));
                p.setCountry(rs.getString("country"));
                p.setPrice(rs.getFloat("price"));
                p.setQuantity(rs.getInt("quantity"));
                products.add(p);
            }
            return products;
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void insertProduct(Product p) {
        try {
            String sql = "INSERT INTO [Products]\n" +
                    "           ([phase_motor]\n" +
                    "           ,[kw_motor]\n" +
                    "           ,[speed_minutes]\n" +
                    "           ,[country]\n" +
                    "           ,[price]\n" +
                    "           ,[quantity])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, p.getPhase());
            stm.setFloat(2, p.getKw());
            stm.setInt(3, p.getSpeed());
            stm.setString(4, p.getCountry());
            stm.setFloat(5, p.getPrice());
            stm.setInt(6, p.getQuantity());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Product getProduct(int id) {
         try {
            String sql = "select p_id,phase_motor,kw_motor,speed_minutes,country,price,quantity\n" +
                    "from Products \n"
                    + "where p_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                Product p = new Product();
                p.setId(rs.getInt("p_id"));
                p.setPhase(rs.getInt("phase_motor"));
                p.setKw(rs.getFloat("kw_motor"));
                p.setSpeed(rs.getInt("speed_minutes"));
                p.setCountry(rs.getString("country"));
                p.setPrice(rs.getFloat("price"));
                p.setQuantity(rs.getInt("quantity"));
                return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateProduct(Product p) {
        try {
            String sql = "UPDATE [Products]\n" +
                    "   SET [phase_motor] = ?\n" +
                    "      ,[kw_motor] = ?\n" +
                    "      ,[speed_minutes] = ?\n" +
                    "      ,[country] = ?\n" +
                    "      ,[price] = ?\n" +
                    "      ,[quantity] = ?\n" +
                    " WHERE p_id = ? ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, p.getPhase());
            stm.setFloat(2, p.getKw());
            stm.setInt(3, p.getSpeed());
            stm.setString(4, p.getCountry());
            stm.setFloat(5, p.getPrice());
            stm.setInt(6, p.getQuantity());
            stm.setInt(7, p.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateQuantityProduct(int id) {
        try {
            String sql = "UPDATE [Products]\n" +
                    "   SET [quantity] = 0\n" +
                    " WHERE p_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public ArrayList<Product> searchProducts(int phase, float kw, int speed) {
        try {
            String sql = "select p_id,phase_motor,kw_motor,speed_minutes,country,"
                    + "price,quantity from Products\n";
            if(phase != -1 || kw != -1 || speed != -1){
                sql += "WHERE\n";
            }
            //phase
            if(phase >=1 ){
                sql += "phase_motor = '"+phase+"'\n";
            }
            //kw
            if(phase >=1 && kw >0){
                sql += "AND kw_motor = '"+kw+"'\n";
            }else if(phase ==-1 && kw > 0){
                sql += "kw_motor = '"+kw+"'\n";
            }
            //speed
            if((phase >=1 || kw >0) && speed >0 ){
                sql += "AND speed_minutes = '"+speed+"'\n";
            }else if(phase == -1 && kw == -1 && speed >0){
                sql += "speed_minutes = '"+speed+"'\n";
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while(rs.next()){
                Product p = new Product();
                p.setId(rs.getInt("p_id"));
                p.setPhase(rs.getInt("phase_motor"));
                p.setKw(rs.getFloat("kw_motor"));
                p.setSpeed(rs.getInt("speed_minutes"));
                p.setCountry(rs.getString("country"));
                p.setPrice(rs.getFloat("price"));
                p.setQuantity(rs.getInt("quantity"));
                products.add(p);
            }
            return products;
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }
    public boolean checkProductValid(int id,int quantity){
        try {
            String sql = "select p_id from products\n" +
                    "where p_id = ? AND quantity >= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setInt(2, quantity);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public void updateQuantityProduct(Order order){
         String sql = "UPDATE [Products]\n" +
                    "   SET [quantity] = [quantity]-?\n" +
                    " WHERE p_id = ?";
            try {
            connection.setAutoCommit(false);
                for (OrderDetail o : order.getOrderDetails()) {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setInt(1, o.getQuantity());
                    stm.setInt(2, o.getProduct().getId());
                    stm.executeUpdate();
                }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        finally
        {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            //close connection
        }
    }
}
