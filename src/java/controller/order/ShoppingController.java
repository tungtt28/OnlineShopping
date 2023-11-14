/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.order;

import controller.BaseAuthController;
import dal.CustomerDBContext;
import dal.OrderDBContext;
import dal.ProductDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Customer;
import model.Order;
import valid.CheckValidate;


public class ShoppingController extends BaseAuthController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Order order = (Order)request.getSession().getAttribute("shoppingcart");
        request.getRequestDispatcher("../view/order/shopping.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
           String raw_name = request.getParameter("name");
        String raw_phone = request.getParameter("phone");
        String raw_address = request.getParameter("address");
        CheckValidate check = new CheckValidate();
        if(check.checkString(raw_name)||check.checkPhone(raw_phone)||check.checkStringAndNumber(raw_address)){
            //add order orderdetail customer to database
            Customer c = new Customer(raw_name, raw_phone, raw_address);
            CustomerDBContext cdb = new CustomerDBContext();
            OrderDBContext odb = new OrderDBContext();
            ProductDBContext pdb = new ProductDBContext();
            Account account = (Account)request.getSession().getAttribute("account");
            Order order = (Order)request.getSession().getAttribute("shoppingcart");
            
            cdb.insertCustomer(c);
            odb.insertOrder(account.getUsername());
            odb.insertOrderDetail(order);
            //update lai quantity cua product 
            pdb.updateQuantityProduct(order);
            request.getSession().setAttribute("shoppingcart", null);
            response.getWriter().println("Buy Successfull!!!");
            
        }else{
            response.getWriter().println("Information invalid!");
        }
       
    }
    
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
