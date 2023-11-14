<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../css/list.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <c:if test="${requestScope.detail.size()!=0}">
        <table border='1px'>
            <thead>
            <tr>
                <td>Phase</td>
                <td>Kw</td>
                <td>Speed</td>
                <td>Country</td>
                <td>Price</td>
                <td>Quantity</td>
                <td>UnitPrice</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.detail}" var="d">
                <tr>
                    <td>${d.product.phase}</td>
                    <td>${d.product.kw}</td>
                    <td>${d.product.speed}</td>
                    <td>${d.product.country}</td>
                    <td><fmt:formatNumber type="number" maxFractionDigits="3" value="${d.product.price}" /></td>
                    <td>${d.quantity}</td> 
                    <td><fmt:formatNumber type="number" maxFractionDigits="3" value="${d.quantity*d.product.price}" /></td>
                </tr>
            </c:forEach>
                </tbody>
        </table>
            </c:if>
        <c:if test="${requestScope.detail.size()==0}">
            No orders yet!
        </c:if>
    </body>
</html>
