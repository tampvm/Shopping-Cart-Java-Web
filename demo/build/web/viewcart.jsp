<%-- 
    Document   : viewcart
    Created on : 01-03-2023, 13:50:18
    Author     : minht
--%>

<%@page import="tampvm.tbl_product.Tbl_ProductDTO"%>
<%@page import="java.util.Map"%>
<%@page import="tampvm.cart.CartObj"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Book Store</h1>
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:if test="${not empty cart}">
            <c:set var="items" value="${cart.items}"/>
            <c:if test="${not empty items}">
                <form action="DispatchServlet">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Name</th>
                                <th>Quanity</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:url var="addbookLink" value="DispatchServlet">
                                <c:param name="btAction" value="Shopping"/>    
                            </c:url>
                            <c:forEach var="key" items="${items.keySet()}" varStatus="counter">
                                <tr>
                                    <td> ${counter.count} .</td>
                                    <td> ${key} </td>
                                    <td> ${items.get(key).quanity} </td>
                                    <td> <input type="checkbox" name="chkItem" value="${key}" /> </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="3">
                                    <a href="${addbookLink}">Add more Books to Your Cart</a>
                                </td>
                                <td>
                                    <input type="number" name="txtQuanity" value="0" max="" min="0">
                                    <input type="submit" value="Remove Select Items" name="btAction" /> <!-- nam ngoai`for -->
                                </td>
                            </tr>                                
                        </tbody>
                    </table>
                    <input type="submit" value="Check out" name="btAction" />
                </form>
            </c:if>
            <c:if test="${empty items}">
                <h2>
                    No Cart is existed!!!
                </h2>
            </c:if>
        </c:if>
        <c:if test="${empty cart}">
            <h2>
                No Cart is existed!!!
            </h2>
        </c:if>

        
        
        
        
<%--        <% 
            //1. Customer goes to cart place/ session đã được lấy vì có implicit obj hỗ trợ cho trang jsp
            if (session != null) {
                //2. Customer take cart
                CartObj cart = (CartObj)session.getAttribute("CART");
                if (cart != null) { //check đồ trong vỏ có tồn tại hay không, nếu có thì show ra view
                    //3. Customer gets all item
                    Map<String, Tbl_ProductDTO>  items = cart.getItems(); //item co kieu du lieu la map
                    if (items != null) {
                        //4.Customer review item
                        %> 
                        <form action="DispatchServlet">
                            <table border="1">
                                <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Name</th>
                                    <th>Quanity</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <% 
                                    int count = 0;
                                    String urlRewriting = "DispatchServlet" //gọi lại trang shopping thông qua DispatchServlet
                                            + "?btAction=Shopping"; 
                                    for (String key : items.keySet()) {
                                        %> 
                                <tr>
                                    <td>
                                        <%= ++count %>
                                    .</td>
                                    <td>
                                        <%= key %>
                                    </td>
                                    <td>
                                        <%= items.get(key).getQuanity() %>                                       
                                    </td>
                                    <td>                                        
                                        <input type="checkbox" name="chkItem" 
                                               value="<%= key %>" />
                                    </td>
                                </tr>
                                <%
                                    }//end traverse Map base on key, lấy từng đồ ra vỏ đồ
                                %>
                                <tr>
                                    <td colspan="3">
                                        <a href="<%= urlRewriting %>">Add more Books to Your Cart</a>
                                    </td>
                                    <td>
                                        <input type="number" name="txtQuanity" value="0" max="" min="0">
                                        <input type="submit" value="Remove Select Items" name="btAction" /> <!-- nam ngoai`for -->
                                    </td>
                                </tr>                                
                            </tbody>
                            </table>
                            <input type="submit" value="Check out" name="btAction" />
                        </form>
        <%
                        return;
                    }//item have existed
                }//cart has existed
            }//session has existed
        %>         
        <h2>
            No Cart is existed!!!
        </h2> --%>
    </body>
</html>
