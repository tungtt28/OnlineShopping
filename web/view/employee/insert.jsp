<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../css/insertUpdate.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">  
    <form id="contact" action="insert" method="POST">
        <h3>Insert Employee</h3>
    <h4>Hello ${sessionScope.account.displayname}</h4>
            Firstname:<input type="text" name="firstname"><br/>
            Lastname:<input type="text" name="lastname"><br/>
            Male<input type="radio" name="gender" value="male">
            Female<input type="radio" name="gender" value="female"><br/>
            Dob:<input type="date" name="dob"><br/>
            HireDate:<input type="date" name="hdate"><br/>
            Salary:<input type="text" name="salary" value="500000"><br/>
            Address:<input type="text" name="address"><br/>
            Phone:<input type="text" name="phone"><br/>
            Mail:<input type="text" name="mail"><br/>
            AccountUser:<input type="text" name="user"><br/>
            AccountPass:<input type="text" name="pass"><br/>
            DisplayName:<input type="text" name="displayname"><br/>
            <input type="reset" value="Reset">
            <input type="submit" value="Insert" onclick="ValidateEmail()">
        </form>
</div>
    </body>
</html>
