<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="../css/insertUpdate.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
        <form id="contact"  action="update" method="POST">
            <h3>Update Employee</h3>
    <h4>Hello ${sessionScope.account.displayname}</h4>
            <input hidden="" type="text" value="${requestScope.employee.id}" name="id">
            Firstname:<input type="text" name="firstname" value="${requestScope.employee.firstname}"><br/>
            Lastname:<input type="text" name="lastname" value="${requestScope.employee.lastname}" ><br/>
            Male<input 
                <c:if test="${(requestScope.employee.gender==true)}">
                    <c:out value="checked=checked"></c:out>
                    </c:if>
                type="radio" name="gender" value="male"  >
            Female<input 
                <c:if test="${(requestScope.employee.gender==false)}">
                    <c:out value="checked=checked"></c:out>
                    </c:if>
                type="radio" name="gender" value="female"><br/>
            Dob:<input type="date" name="dob" value="${requestScope.employee.dob}"><br/>
            HireDate:<input type="date" name="hdate" value="${requestScope.employee.hiredate}"><br/>
            Salary:<input type="text" name="salary" value="${requestScope.employee.salary}"><br/>
            Address:<input type="text" name="address" value="${requestScope.employee.address}"><br/>
            Phone:<input type="text" name="phone" value="${requestScope.employee.phone}"><br/>
            Mail:<input type="text" name="mail" value="${requestScope.employee.mail}"><br/>
            AccountUser:${requestScope.account.username}<input hidden="" type="text" name="user" value="${requestScope.account.username}"><br/>
            AccountPass:<input type="text" name="pass" value="${requestScope.account.password}"><br/>
            DisplayName:<input type="text" name="displayname" value="${requestScope.account.displayname}"><br/>
            <input type="reset" value="Reset">
            <input type="submit" value="Update">
        </form>
            </div>
    </body>
</html>
