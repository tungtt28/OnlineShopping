<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <h3>Insert Product</h3>
        <h4>Hello ${sessionScope.account.displayname}</h4>
            Phase:<input type="text" name="phase">   <br/>  
            Kw:<input size="5" type="text" list="listkw" 
                       name="kw"
                      />
            <datalist id="listkw">
                <c:forTokens items="0.125,0.15,0.37,0.5,0.75,1.1,1.5,2.2,2.5,3,3.7,4,4.5,5,7.5,11,15,17,18.5,22,24,25,28,30,33,37,40,45,55,75,90,100,110,132,160,220" delims=","  var="i">
                    <option
                        value="${i}">${i}Kw</option>
                             </c:forTokens>
            </datalist>
            Speed:<input type="text" name="speed">   <br/>  
            Country:<input type="text" name="country">  <br/>   
            Price:<input type="text" name="price">    <br/> 
            Quantity:<input type="text" name="quantity">    <br/> 
             <input type="reset" value="Reset">
            <input type="submit" value="Insert">
        </form>
        </div>
    </body>
</html>
