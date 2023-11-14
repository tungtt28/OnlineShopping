/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.products;

import controller.BaseAuthController;
import dal.AccountDBContext;
import dal.ProductDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Product;
import valid.CheckValidate;


public class SearchProductController extends BaseAuthController {

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
        ProductDBContext db = new ProductDBContext();
        ArrayList<Product> products = db.getProducts();
        request.setAttribute("products", products);
        Account account = (Account) request.getSession().getAttribute("account");
        AccountDBContext adb = new AccountDBContext();
        int groupAccount = adb.getGroupAccount(account.getUsername());
        request.setAttribute("groupAccount", groupAccount);
        request.getRequestDispatcher("../view/product/search.jsp").forward(request, response);
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
        CheckValidate check = new CheckValidate();
        String raw_phase = request.getParameter("phase");
        String raw_kw = request.getParameter("kw");
        String raw_speed = request.getParameter("speed");
        int phase = 0, speed = 0;
        float kw = 0;

        String notice = "";
        boolean valid = true;
        //phase
        if (check.checkPhaseMotor(raw_phase) == true || raw_phase.endsWith("-1")) {
            phase = Integer.parseInt(raw_phase);
        } else {
            notice += "phase, ";
            valid = false;
        }
        //kw
        if (raw_kw.equalsIgnoreCase("All")) {
            kw = -1;
        } else {
            if (check.checkKwMotor(raw_kw, phase)) {
                kw = Float.parseFloat(raw_kw);
            } else {
                notice += "kw, ";
                valid = false;
            }
        }
        //speed
        if (raw_speed.equalsIgnoreCase("All")) {
            speed = -1;
        } else {
            if (check.checkInteger(raw_speed)) {
                speed = Integer.parseInt(raw_speed);
            } else {
                notice += "speed, ";
                valid = false;
            }
        }
        //
        if (valid == false) {
            response.getWriter().println(notice.substring(0, notice.length() - 2) + " invalid");
        } else {
            
            ProductDBContext db = new ProductDBContext();
            ArrayList<Product> products = db.searchProducts(phase,kw,speed);
            
            Account account = (Account) request.getSession().getAttribute("account");
        AccountDBContext adb = new AccountDBContext();
        int groupAccount = adb.getGroupAccount(account.getUsername());
        request.setAttribute("groupAccount", groupAccount);
            request.setAttribute("searchPhase", phase);
            request.setAttribute("searchKw", kw);
            request.setAttribute("searchSpeed", speed);
            request.setAttribute("products", products);
            request.getRequestDispatcher("../view/product/search.jsp").forward(request, response);
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
