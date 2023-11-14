/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.employees;

import Algorithm.Date;
import controller.BaseAuthController;
import dal.AccountDBContext;
import dal.EmployeeDBContext;
import dal.TimekeepingDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Employee;
import valid.CheckValidate;


public class TimekeepingEmployeeController extends BaseAuthController {

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
        CheckValidate check = new CheckValidate();
        //get year month
        String raw_year = request.getParameter("year");
        String raw_month = request.getParameter("month");
        int year, month;
        if ((check.checkInteger(raw_month) &&  check.checkInteger(raw_year)) ||(raw_year == null && raw_month == null) ) {
            int yearNow = LocalDate.now().getYear();
            int monthNow = LocalDate.now().getMonthValue();
            if (raw_year == null && raw_month == null) {
                year = yearNow;
                month = monthNow;
            } else {
                year = Integer.parseInt(raw_year);
                month = Integer.parseInt(raw_month);
            }
            EmployeeDBContext db = new EmployeeDBContext();
            ArrayList<Employee> employees = db.getEmployees(year, month);
            ArrayList<Integer> listYear = new ArrayList();
            for (int i = 2019; i <= yearNow; i++) {
                listYear.add(i);
            }
            Date d = new Date();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date raw_today = new java.util.Date();
                String today = sdf.format(raw_today);
                request.setAttribute("today", today);
                ArrayList<String> dayOfMonth = d.getDayOfMonth(year, month);
                request.setAttribute("dayOfMonth", dayOfMonth);
                request.setAttribute("year", year);
                request.setAttribute("month", month);
                request.setAttribute("listyear", listYear);
                request.setAttribute("employees", employees);
                request.getRequestDispatcher("../view/employee/timekeeping.jsp").forward(request, response);
            } catch (ParseException ex) {
                Logger.getLogger(TimekeepingEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.getWriter().println("Please enter integer number!");
        }
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
        AccountDBContext adb = new AccountDBContext();
        Account account = (Account) request.getSession().getAttribute("account");
        int g_id = adb.getGroupAccount(account.getUsername());
        if (g_id == 1) {
            EmployeeDBContext edb = new EmployeeDBContext();
            ArrayList<Employee> employees = edb.getEmployees();
            int year = Integer.parseInt(request.getParameter("year"));
            int month = Integer.parseInt(request.getParameter("month"));
            TimekeepingDBContext tdb = new TimekeepingDBContext();
            for (Employee e : employees) {
                String[] listDate = request.getParameterValues("" + e.getId());
                if (listDate == null) {
                    tdb.deleteTimekeeping(year, month, e.getId());
                } else {
                    //xoa di
                    tdb.deleteTimekeeping(year, month, e.getId());
                    //insert lai
                    tdb.insertTimekeeping(listDate, e.getId());
                }
            }
            response.sendRedirect("timekeeping");
        } else {
            response.getWriter().println("access denied!");
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
