/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.employees;

import controller.BaseAuthController;
import dal.AccountDBContext;
import dal.EmployeeDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Employee;
import valid.CheckValidate;


public class InsertEmployeeController extends BaseAuthController {

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
        request.getRequestDispatcher("../view/employee/insert.jsp").forward(request, response);
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
        //information employee
        String raw_lastname = request.getParameter("lastname");
        String raw_firstname = request.getParameter("firstname");
        String raw_gender = request.getParameter("gender");
        String raw_dob = request.getParameter("dob");
        String raw_hdate = request.getParameter("hdate");
        String raw_salary = request.getParameter("salary");
        
        String raw_address = request.getParameter("address");
        String raw_phone = request.getParameter("phone");
        String raw_mail = request.getParameter("mail");

        //information account
        String raw_user = request.getParameter("user");
        String raw_pass = request.getParameter("pass");
        String raw_displayname = request.getParameter("displayname");

        //check validate 
        CheckValidate check = new CheckValidate();
        String notice = "";
        String lastname = null, firstname = null, address = null, phone = null, mail = null;
        boolean gender = false;
        Date dob = null, hdate = null;
        int salary =Integer.valueOf(raw_salary);

        String user = null, pass = null, displayname = null;
        boolean valid = true;
        if (check.checkString(raw_lastname)) {
            lastname = raw_lastname;
        } else {
            notice += "lastname, ";
            valid = false;
        }
        if (check.checkString(raw_firstname)) {
            firstname = raw_firstname;
        } else {
            notice += "firstname, ";
            valid = false;
        }
        if (check.checkGender(raw_gender)) {
            gender = raw_gender.equals("male");
        } else {
            notice += "gender, ";
            valid = false;
        }
        if (check.checkDateOfBirth(raw_dob)) {
            dob = Date.valueOf(raw_dob);
        } else {
            notice += "dob, ";
            valid = false;
        }
        if (check.checkHireDate(raw_hdate, raw_dob)) {
            hdate = Date.valueOf(raw_hdate);
        } else {
            notice += "hiredate, ";
            valid = false;
        }
        if (check.checkInteger(raw_salary)) {
            salary = Integer.parseInt(raw_salary);
        } else {
            notice += "salary, ";
            valid = false;
        }
        if (check.checkStringAndNumber(raw_address)) {
            address = raw_address;
        } else {
            notice += "address, ";
            valid = false;
        }
        if (check.checkPhone(raw_phone)) {
            phone = raw_phone;
        } else {
            notice += "phone, ";
            valid = false;
        }
        if (check.checkEmail(raw_mail)) {
            mail = raw_mail;
        } else {
            notice += "email, ";
            valid = false;
        }
        if (check.checkStringAndNumber(raw_user)) {
            user = raw_user;
        } else {
            notice += "user, ";
            valid = false;
        }
        if (check.checkStringAndNumber(raw_pass)) {
            pass = raw_pass;
        } else {
            notice += "password, ";
            valid = false;
        }
        if (check.checkStringAndNumber(raw_displayname)) {
            displayname = raw_displayname;
        } else {
            notice += "displayname, ";
            valid = false;
        }
        if (valid == true) {
            Employee employee = new Employee(lastname, firstname, gender, dob, hdate, salary, address, phone, mail);
            Account account = new Account(user, pass, displayname);
            EmployeeDBContext edb = new EmployeeDBContext();
            AccountDBContext adb = new AccountDBContext();

            //check user exist
            if (!adb.checkAccountExist(user)) {
                edb.insertEmployee(employee);
                adb.insertAccount(account);
                adb.insertGroupAccount(user);
                response.sendRedirect("list");
            } else {
                response.getWriter().println("Username is exist!");
            }

        } else {
            response.getWriter().println(notice.substring(0, notice.length() - 2) + " invalid");
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
