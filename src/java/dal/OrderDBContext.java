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
import model.Customer;
import model.Order;
import model.OrderDetail;
import model.Product;


public class OrderDBContext extends DBContext {

    public void insertOrder(String user) {
        String sql = "INSERT INTO [Order]\n"
                + "           ([c_id]\n"
                + "           ,[DateOrdered]\n"
                + "           ,[username])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,GETDATE()\n"
                + "           ,?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            CustomerDBContext db = new CustomerDBContext();
            int e_id = db.getLastCustomer();
            stm.setInt(1, e_id);
            stm.setString(2, user);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getLastOrder() {
        try {
            String sql = "select top 1 o_id from [Order]\n"
                    + "order by o_id DESC";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("o_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public ArrayList<Order> getOrders() {
        try {
            String sql = "select o_id,c_id,DateOrdered,username from [Order]";
            ArrayList<Order> orders = new ArrayList<>();
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            CustomerDBContext db = new CustomerDBContext();
            while (rs.next()) {
                Customer customer = db.getCustomer(rs.getInt("c_id"));
                Order o = new Order();
                o.setId(rs.getInt("o_id"));
                o.setCustomerName(customer.getName());
                o.setUsername(rs.getString("username"));
                o.setDateOrder(rs.getDate("DateOrdered"));
                o.setOrderDetails(getOrdersDetail(rs.getInt("o_id")));
                orders.add(o);
            }
            return orders;
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public Order getOrder(int id) {
        try {
            String sql = "select o_id,c_id,DateOrdered,username from [Order]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            CustomerDBContext db = new CustomerDBContext();
            if (rs.next()) {
                Customer customer = db.getCustomer(rs.getInt("c_id"));
                Order o = new Order();
                o.setId(rs.getInt("o_id"));
                o.setCustomerName(customer.getName());
                o.setUsername(rs.getString("username"));
                o.setDateOrder(rs.getDate("DateOrdered"));
                o.setOrderDetails(getOrdersDetail(rs.getInt("o_id")));
                return o;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<OrderDetail> getOrdersDetail(int o_id) {
        try {
            String sql = "select o_id,p_id,Quantity from Order_Detail\n"
                    + "where o_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, o_id);
            ResultSet rs = stm.executeQuery();
            ArrayList<OrderDetail> orderDetails = new ArrayList<>();
            ProductDBContext db = new ProductDBContext();
            while (rs.next()) {
                Product product = db.getProduct(rs.getInt("p_id"));
                
                OrderDetail od = new OrderDetail();
                od.setQuantity(rs.getInt("Quantity"));
                od.setUnitPrice(product.getPrice()*rs.getInt("Quantity"));
                od.setProduct(product);
                orderDetails.add(od);
            }
            return orderDetails;
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void insertOrderDetail(Order order) {
        String sql = "INSERT INTO [Order_Detail]\n"
                + "           ([o_id]\n"
                + "           ,[p_id]\n"
                + "           ,[Quantity])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        try {
            connection.setAutoCommit(false);
            int o_id = getLastOrder();
            for (OrderDetail o : order.getOrderDetails()) {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(1, o_id);
                stm.setInt(2, o.getProduct().getId());
                stm.setInt(3, o.getQuantity());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            //close connection
        }
    }
}
