<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../css/timekeeping.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
            <!--search following year month-->
            <form action="timekeeping" method="GET">
                <!--option year -->
                Year:<select name="year">
                <c:forEach items="${requestScope.listyear}" var="year" >
                    <option 
                        ${(requestScope.year==year)?"Selected=\"Selected\"":""}
                        value="${year}">${year}</option>
                </c:forEach>
            </select> 
            <!--option Month: -->
            Month:<select name="month">
                <c:forTokens items="1,2,3,4,5,6,7,8,9,10,11,12" delims="," var="month">
                    <option
                        ${(requestScope.month==month)?"Selected=\"Selected\"":""}
                        value="${month}"><c:out value="${month}"/></option> 
                </c:forTokens>
            </select> 
            <input type="submit" value="Search">
        </form>
        <!--save check box-->
        <form action="timekeeping" method="POST">
            <input hidden="" type="text" value="${requestScope.year}" name="year">
        <input hidden="" type="text" value="${requestScope.month}" name="month" >
            <input type="submit" value="save">
            <table border="1px">
                <thead>
                <tr>
                    <td></td>
                    <!--display employees-->
                    <c:forEach items="${requestScope.employees}" var="e">
                        <td>
                            ${e.id}:
                            ${e.firstname}
                            ${e.lastname}
                        </td>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <!--display day of month-->
                <c:forEach items="${requestScope.dayOfMonth}" var="d" >
                    <tr><td 
                            <c:if test="${d==requestScope.today}">
                                style="color: crimson"
                            </c:if>
                            >${d}</td> 
                        <c:forEach items="${requestScope.employees}" var="e">
                            <!--hidden if employeee.hiredate<day-->
                            <!--and checked if had in database-->
                            <td><input 
                                    <c:if test="${d < e.hiredate}">
                                        <c:out value="hidden=\"hidden\""/>
                                    </c:if>
                                    <c:forEach items="${e.timekeeping}" var="t">
                                        <c:if test="${t.day == d}">
                                        <c:out value="checked=\"checked\""/>
                                        </c:if>
                                    </c:forEach>
                                    type="checkbox" name="${e.id}" value="${d}"></td>
                            </c:forEach>
                    </tr>
                </c:forEach>
                    <!--display salary-->
                    <tr>
                        <td>Salary</td>
                        <c:forEach items="${requestScope.employees}" var="e">
                            <c:set var="salary" value="${(e.salary/requestScope.dayOfMonth.size()*e.timekeeping.size())}" />
                            <td>
            <fmt:formatNumber type="number" maxFractionDigits="3" value="${salary}" />
                        </c:forEach>
                    </tr>
                    </tbody>
            </table>
            <br/>
        </form>
    </body>
</html>
