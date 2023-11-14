<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "dal.AccountDBContext" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="../css/list.css" rel="stylesheet" type="text/css"/>
        <link href="../img/cart.png" rel="stylesheet" type="text/css"/>
        <script>
//        function getQuantity(id,quantity){
//            var deleteQuantity = prompt("Enter the quantity you want to delete");
//            if(deleteQuantity>quantity){
//                window.location.href = "search";
//                alert("The quantity you want to delete is more than the quantity in stock");
//            }else if(deleteQuantity==null){
//                window.location.href = "search";
//                alert("Please enter quantity!");
//            }else if(deleteQuantity.trim().length==0){
//                window.location.href = "search";
//                alert("Please enter quantity!");
//            }else{
//                window.location.href = "delete?id="+id+"&quantity="+deleteQuantity;
//            }
//        }
        function getQuantity(id){
            var result = confirm("Are you sure?");
            if(result==true){
                window.location.href = "delete?id="+id;
            }
        }
        function checkQuantity(id,quantity){
            var quantityId = "quantity"+id;
            var quantityBuy = document.getElementById(quantityId).value;
            if(quantityBuy<=quantity && quantityBuy >0){
                return true;
            }else{
                alert("The quantity invalid");
                return false;
            }
        }
    </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
    </head>
    <body>
        <div class="header">
        Hello: ${sessionScope.account.username} 
        <div
        <c:if test="${requestScope.groupAccount!=1}">
                            hidden=""
                        </c:if> >
        <a href="../employee/list">
                     <button id="btlistemployee">List Employee</button>
                 </a>
        </div>
        <a href="../order/shopping" class="buy">
                        <img src="../img/cart.png" alt="cart" style="width: 30px">
                        </a>
        <form action="search" method="POST">
            Phase:<select name="phase">
                <option
                    ${(requestScope.searchPhase==-1)?"selected=\"selected\"":""}
                    value="-1">All</option>
                <option
                    ${(requestScope.searchPhase==1)?"selected=\"selected\"":""}
                    value="1">1</option>
                <option 
                    ${(requestScope.searchPhase==3)?"selected=\"selected\"":""}
                    value="3">3</option>
            </select>
            Kw:<input size="5" type="text" list="listkw" 
                      placeholder="Enter"  name="kw"
                      <c:if test="${requestScope.searchKw!=null && requestScope.searchKw!=-1}">
                          value ="${requestScope.searchKw}"
                      </c:if>
                          <c:if test="${requestScope.searchKw==null || requestScope.searchKw==-1 }">
                              value="All"
                      </c:if>
                      />
            <datalist id="listkw">
                <c:forTokens items="0.125,0.15,0.37,0.5,0.75,1.1,1.5,2.2,2.5,3,3.7,4,4.5,5,7.5,11,15,17,18.5,22,24,25,28,30,33,37,40,45,55,75,90,100,110,132,160,220" delims=","  var="i">
                    <option
                        
                        value="${i}">${i}Kw</option>
                             </c:forTokens>
            </datalist>
            Speed:<input type="text" name="speed" size="3" placeholder="Enter" 
                         <c:if test="${requestScope.searchSpeed!=null && requestScope.searchSpeed!=-1 }">
                          value ="${requestScope.searchSpeed}"
                      </c:if>
                          <c:if test="${requestScope.searchSpeed==null || requestScope.searchSpeed==-1}">
                              value="All"
                      </c:if>
                         >
            <input type="submit" value="Search" >
            
        </form>
                    
                </div>    
        <table border="1px">
            <thead>
            <tr>
                <td>Phase</td>
                <td>Kw</td>
                <td>Speed</td>
                <td>Country</td>
                <td>Price</td>
                <td>Quantity</td>
                <td
                    <c:if test="${requestScope.groupAccount!=1}">
                            hidden=""
                        </c:if>
                    ><a href="insert">Insert</a></td>
                <td>Quantity Purchased</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.products}" var="p">
                <c:if test="${p.quantity!=0}">
                <tr>
                    <td>${p.phase}</td>
                    <td>${p.kw}</td>
                    <td>${p.speed}</td>
                    <td>${p.country}</td>
                    <td><fmt:formatNumber type="number" maxFractionDigits="3" value="${p.price}" /></td>
                    <td>${p.quantity}</td>
                    <td 
                        <c:if test="${requestScope.groupAccount!=1}">
                            hidden=""
                        </c:if>
                        >
                        <a href="update?id=${p.id}">Update</a>
                        <a href="#" onclick="getQuantity(${p.id})">Delete</a>
                    </td>
                    <td>
                        <form action="../order/add" method="">
                            <input hidden="" name="id" value="${p.id}">
                            <input
                                <c:out value="id=quantity${p.id}"></c:out> 
                                type="text" name="quantity" size="1">
                            <input type="submit" value="Buy" onclick="return checkQuantity(${p.id},${p.quantity})">
                        </form>
                    </td>
                </tr>
                </c:if>
            </c:forEach>
                </tbody>
        </table>
    </body>
    
</html>
