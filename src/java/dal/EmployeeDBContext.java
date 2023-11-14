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
import model.Employee;


public class EmployeeDBContext extends DBContext{
    
    
    public void insertEmployee(Employee employee){
        try {
            String sql = "INSERT INTO [dbo].[Employees]\n" +
                        "           ([e_first_name]\n" +
                        "           ,[e_last_name]\n" +
                        "           ,[e_gender]\n" +
                        "           ,[e_date_of_birth]\n" +
                        "           ,[e_hire_date]\n" +
                        "           ,[e_salary]\n" +
                        "           ,[e_address]\n" +
                        "           ,[e_phone]\n" +
                        "           ,[e_mail])\n" +
                        "     VALUES\n" +
                        "           (?\n" +
                        "           ,?\n" +
                        "           ,?\n" +
                        "           ,?\n" +
                        "           ,?\n" +
                        "           ,?\n" +
                        "           ,?\n" +
                        "           ,?\n" +
                        "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, employee.getFirstname());
            stm.setString(2, employee.getLastname());
            stm.setBoolean(3, employee.isGender());
            stm.setDate(4, employee.getDob());
            stm.setDate(5, employee.getHiredate());
            stm.setInt(6, employee.getSalary());
            stm.setString(7, employee.getAddress());
            stm.setString(8, employee.getPhone());
            stm.setString(9, employee.getMail());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<Employee> getEmployees(int year,int month){
        try {
            TimekeepingDBContext db = new TimekeepingDBContext();
            String sql = "select e_id,e_first_name,e_last_name,e_gender,e_date_of_birth,"
                    + "e_hire_date,e_salary,e_address,e_phone,e_mail\n" +
                    "from Employees";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ArrayList<Employee> employees = new ArrayList<>();
            while(rs.next()){
                Employee e = new Employee();
                e.setId(rs.getInt("e_id"));
                e.setFirstname(rs.getString("e_first_name"));
                e.setLastname(rs.getString("e_last_name"));
                e.setGender(rs.getBoolean("e_gender"));
                e.setDob(rs.getDate("e_date_of_birth"));
                e.setHiredate(rs.getDate("e_hire_date"));
                e.setSalary(rs.getInt("e_salary"));
                e.setAddress(rs.getString("e_address"));
                e.setAddress(rs.getString("e_phone"));
                e.setAddress(rs.getString("e_mail"));
                e.setTimekeeping(db.getTimekeeping(year, month, rs.getInt("e_id")));
                employees.add(e);
            }
            return employees;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
     return null;   
    }
    public ArrayList<Employee> getEmployees(){
        AccountDBContext db = new AccountDBContext();
        try {
            String sql = "select e_id,e_first_name,e_last_name,e_gender,e_date_of_birth,"
                    + "e_hire_date,e_salary,e_address,e_phone,e_mail\n" +
                    "from Employees";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ArrayList<Employee> employees = new ArrayList<>();
            while(rs.next()){
                Employee e = new Employee();
                e.setId(rs.getInt("e_id"));
                e.setFirstname(rs.getString("e_first_name"));
                e.setLastname(rs.getString("e_last_name"));
                e.setGender(rs.getBoolean("e_gender"));
                e.setDob(rs.getDate("e_date_of_birth"));
                e.setHiredate(rs.getDate("e_hire_date"));
                e.setSalary(rs.getInt("e_salary"));
                e.setAddress(rs.getString("e_address"));
                e.setPhone(rs.getString("e_phone"));
                e.setMail(rs.getString("e_mail"));
                e.setAccount(db.getAccountById(rs.getInt("e_id")));
                employees.add(e);
            }
            return employees;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
     return null;   
    }

    public Employee getEmployeeById(int id) {
        try {
            String sql = "select e_id,e_first_name,e_last_name,e_gender,e_date_of_birth,e_hire_date\n" +
                    ",e_salary,e_address,e_phone,e_mail from employees\n" +
                    "where e_id = ? ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                Employee e = new Employee();
                e.setId(rs.getInt("e_id"));
                e.setFirstname(rs.getString("e_first_name"));
                e.setLastname(rs.getString("e_last_name"));
                e.setGender(rs.getBoolean("e_gender"));
                e.setDob(rs.getDate("e_date_of_birth"));
                e.setHiredate(rs.getDate("e_hire_date"));
                e.setSalary(rs.getInt("e_salary"));
                e.setAddress(rs.getString("e_address"));
                e.setPhone(rs.getString("e_phone"));
                e.setMail(rs.getString("e_mail"));
                return e;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateEmployee(Employee employee,int id) {
        try {
            String sql = "UPDATE [Employees]\n" +
                    "   SET [e_first_name] = ?\n" +
                    "      ,[e_last_name] = ?\n" +
                    "      ,[e_gender] = ?\n" +
                    "      ,[e_date_of_birth] = ?\n" +
                    "      ,[e_hire_date] = ?\n" +
                    "      ,[e_salary] = ?\n" +
                    "      ,[e_address] = ?\n" +
                    "      ,[e_phone] = ?\n" +
                    "      ,[e_mail] = ?\n" +
                    " WHERE e_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, employee.getFirstname());
            stm.setString(2, employee.getLastname());
            stm.setBoolean(3, employee.isGender());
            stm.setDate(4, employee.getDob());
            stm.setDate(5, employee.getHiredate());
            stm.setInt(6, employee.getSalary());
            stm.setString(7, employee.getAddress());
            stm.setString(8, employee.getPhone());
            stm.setString(9, employee.getMail());
            stm.setInt(10, id);
            stm.executeUpdate();
                    } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteEmployee(int id) {
        try {
            String sql = "DELETE FROM [Employees]\n" +
                    "      WHERE e_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
                    } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
