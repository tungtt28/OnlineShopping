<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../css/list.css" rel="stylesheet" type="text/css"/>
        <script>
            function back(){
                window.location.href = "../product/search";
            }
        </script>
    </head>
    <body>
        <c:if test="${sessionScope.shoppingcart==null}">
            <p>don't have anything</p>
        </c:if>
            <c:if test="${sessionScope.shoppingcart!=null}">
        <table border="1px">
            <thead>
            <tr>
                <td>Id</td>
                <td>Phase</td>
                <td>Kw</td>
                <td>Speed</td>
                <td>Country</td>
                <td>Price</td>
                <td>Quantity</td>
                <td>Unitprice</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.shoppingcart.orderDetails}" var="o">
                <tr>
                    <td>${o.product.id}</td> 
                    <td>${o.product.phase}</td> 
                    <td>${o.product.kw}</td> 
                    <td>${o.product.speed}</td> 
                    <td>${o.product.country}</td> 
                    <td>${o.product.price}</td> 
                    <td>${o.quantity}</td> 
                    <td><fmt:formatNumber type="number" maxFractionDigits="3" value="${o.unitPrice}" /></td> 
                    
                </tr>
            </c:forEach>
                <tr>
                    <td colspan="7">Total</td>
                    <td class="total">
                        <fmt:formatNumber type="number" maxFractionDigits="3" value=" ${sessionScope.shoppingcart.total}" />
                    </td>
                </tr>
                </tbody>
        </table>
                    <div class="contact">
                <form action="shopping" method="POST">
                    <h3>Information Customer</h3>
                <br/>
                Name:<input type="text" name="name"><br/>
                Phone:<input type="text" name="phone"><br/>
                Address:<input type="text" name="address"><br/>
                <input type="button" value="Back" onclick="back()">
                    <input type="submit" value="Buy">
                </form>
                        </div>
                </c:if>
    </body>
</html>
