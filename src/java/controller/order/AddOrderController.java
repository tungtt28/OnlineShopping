/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.order;

import controller.BaseAuthController;
import dal.ProductDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;
import model.OrderDetail;
import model.Product;
import valid.CheckValidate;


public class AddOrderController extends BaseAuthController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDBContext db = new ProductDBContext();
        String raw_id = request.getParameter("id");
        String raw_quantity = request.getParameter("quantity");
        CheckValidate check = new CheckValidate();
        //check validate
        if (check.checkInteger(raw_id) && check.checkInteger(raw_quantity)) {
            int id = Integer.parseInt(raw_id);
            Product product = db.getProduct(id);
            int quantity = Integer.parseInt(raw_quantity);
            boolean checkProductValid = db.checkProductValid(id, quantity);
            if (checkProductValid == false) {
                response.getWriter().println("product invalid!");
            } else {
                //add to cart
                Order orders = (Order) request.getSession().getAttribute("shoppingcart");

                if (orders == null) {
                    orders = new Order();
                }
                boolean isExist = false;
                boolean checkQuantity = false;
                for (OrderDetail detail : orders.getOrderDetails()) {
                    //update quantity and unitprice
                    if (detail.getProduct().getId() == product.getId()
                            && detail.getQuantity() + quantity <= product.getQuantity()) {
                        detail.setQuantity(detail.getQuantity() + quantity);
                        detail.setUnitPrice(detail.getQuantity()*product.getPrice());
                        isExist = true;
                        break;
                        //check quantity buy more than quantity we have
                    } else if (detail.getProduct().getId() == product.getId()
                            && detail.getQuantity() + quantity > product.getQuantity()) {
                        checkQuantity = true;
                        break;
                    }
                }
                if (checkQuantity == true) {
                    response.getWriter().println("The quantity purchased is larger than the quantity in stock");
                } else {
                    if (isExist == false) {
                        OrderDetail detail = new OrderDetail();
                        detail.setOrder(orders);
                        detail.setProduct(product);
                        detail.setQuantity(quantity);
                        detail.setUnitPrice(quantity * product.getPrice());
                        orders.getOrderDetails().add(detail);
                    }
                    request.getSession().setAttribute("shoppingcart", orders);
                    response.sendRedirect("../product/search");
                }
            }
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
