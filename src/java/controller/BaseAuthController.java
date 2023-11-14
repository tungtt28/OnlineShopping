/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.AccountDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

public abstract class BaseAuthController extends HttpServlet {

    private boolean isAuthenticated(HttpServletRequest request)
    {
        Account account = (Account) request.getSession().getAttribute("account");
       if(account == null)
            return false;
        else
        {
            String url = request.getServletPath();
            System.out.println("ádasdasd"+url);
            AccountDBContext db = new AccountDBContext();
            int permission = db.getPermission(account.getUsername(), url);
           return permission >0;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(isAuthenticated(request))
        {
            processGet(request, response);
        }
        else
        {
            response.getWriter().println("access denined!");
        }
    }
    protected abstract void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        if(isAuthenticated(request))
//        {
//            //business
//            processPost(request, response);
//        }
//        else
//        {
//            response.getWriter().println("access denined!");
//        }
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
    
    
//     <% AccountDBContext db = new AccountDBContext(); %>
//        <% if(db.getPermission(${sessionScope.account.username}, "http://localhost:9999/project/employee/list")>0){ %>
//            
//        <button id="btsingup">Log out</button>
//        
//        <% } %>

}
