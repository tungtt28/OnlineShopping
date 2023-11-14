/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.products;

import controller.BaseAuthController;
import dal.ProductDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import valid.CheckValidate;


public class InsertProductController extends BaseAuthController {

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
        request.getRequestDispatcher("../view/product/insert.jsp").forward(request, response);
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
        String raw_phase = request.getParameter("phase");
        String raw_kw = request.getParameter("kw");
        String raw_speed = request.getParameter("speed");
        String raw_country = request.getParameter("country");
        String raw_price = request.getParameter("price");
        String raw_quantity = request.getParameter("quantity");

        int phase = 0, speed = 0, quantity = 0;
        float kw = 0, price = 0;
        String country = null, notice = "";
        boolean valid = true;
        //validate
        CheckValidate check = new CheckValidate();
        //check phase motor
        if (check.checkPhaseMotor(raw_phase)) {
            phase = Integer.parseInt(raw_phase);
        } else {
            notice += "phase, ";
            valid = false;
        }
        //check kw 
        if(check.checkKwMotor(raw_kw, phase)){
            kw = Float.parseFloat(raw_kw);
        }else {
                notice += "kw, ";
                valid = false;
            }
        //check speed
        if (check.checkInteger(raw_speed)) {
            speed = Integer.parseInt(raw_speed);
        } else {
            notice += "speed, ";
            valid = false;
        }
        //check country
        if (check.checkString(raw_country)) {
            country = raw_country;
        } else {
            notice += "country, ";
            valid = false;
        }
        //check price
        if (check.checkFloat(raw_price)) {
            price = Float.parseFloat(raw_price);
        } else {
            notice += "price, ";
            valid = false;
        }
        //check quantity
        if(check.checkInteger(raw_quantity)){
            quantity = Integer.parseInt(raw_quantity);
        }else{
            notice += "quantity, ";
            valid = false;
        }
        if(valid == false){
            response.getWriter().println(notice.substring(0, notice.length()-2)+" invalid");
        }else{
            Product p = new Product(phase, kw, speed, country, price, quantity);
            ProductDBContext db = new ProductDBContext();
            db.insertProduct(p);
        //    response.getWriter().println("insert successful!");
            response.sendRedirect("search");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
         String raw_phase = request.getParameter("phase");
        String raw_kw = request.getParameter("kw");
        String raw_speed = request.getParameter("speed");
        String raw_country = request.getParameter("country");
        String raw_price = request.getParameter("price");
        String raw_quantity = request.getParameter("quantity");

        int phase = 0, speed = 0, quantity = 0;
        float kw = 0, price = 0;
        String country = null, notice = "";
        boolean valid = true;
        //validate
        CheckValidate check = new CheckValidate();
        //check phase motor
        if (check.checkPhaseMotor(raw_phase)) {
            phase = Integer.parseInt(raw_phase);
        } else {
            notice += "phase, ";
            valid = false;
        }
        //check kw 
        if(check.checkKwMotor(raw_kw, phase)){
            kw = Float.parseFloat(raw_kw);
        }else {
                notice += "kw, ";
                valid = false;
            }
        //check speed
        if (check.checkInteger(raw_speed)) {
            speed = Integer.parseInt(raw_speed);
        } else {
            notice += "speed, ";
            valid = false;
        }
        //check country
        if (check.checkString(raw_country)) {
            country = raw_country;
        } else {
            notice += "country, ";
            valid = false;
        }
        //check price
        if (check.checkFloat(raw_price)) {
            price = Float.parseFloat(raw_price);
        } else {
            notice += "price, ";
            valid = false;
        }
        //check quantity
        if(check.checkInteger(raw_quantity)){
            quantity = Integer.parseInt(raw_quantity);
        }else{
            notice += "quantity, ";
            valid = false;
        }
        if(valid == false){
            response.getWriter().println(notice.substring(0, notice.length()-2)+" invalid");
        }else{
            Product p = new Product(phase, kw, speed, country, price, quantity);
            ProductDBContext db = new ProductDBContext();
            db.insertProduct(p);
        //    response.getWriter().println("insert successful!");
            response.sendRedirect("search");
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
