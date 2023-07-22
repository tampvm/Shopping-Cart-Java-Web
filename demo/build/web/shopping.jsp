<%-- 
    Document   : shopping
    Created on : 07-03-2023, 23:02:32
    Author     : minht
--%>

<%@page import="tampvm.tbl_product.Tbl_ProductDTO"%>
<%@page import="java.util.List"%>
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
        <c:set var="result" value="${requestScope.SEARCH_RESULT_PRODUCT}"/>
        <c:if test="${not empty result}">
            <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Product ID</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Quanity</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                            <form action="DispatchServlet" method="POST">
                        <tr>
                            <td>
                                ${counter.count}
                            .</td>
                            <td>
                                ${dto.sku}
                                <input type="hidden" name="txtSku" value="${dto.sku}" />
                            </td>
                            <td>
                                ${dto.name}
                                <input type="hidden" name="txtName" value="${dto.name}" />
                            </td>
                            <td>
                                ${dto.price}
                            </td>
                            <td>
                                ${dto.quanity}
                            </td>
                            <td>
                                <input type="checkbox" name="chkItem" value="ON"
                                       <c:if test="${dto.status}">
                                            checked="checked"
                                        </c:if>   
                                           />
                                
                                
                            </td>
                            <td>
                                <input type="number" name="txtQuanity" value="0" max="${dto.quanity}>" min="0" >                                
                                <input type="submit" value="Add Book to Your Cart" name="btAction"
                            </td>
                            
                        </tr>                        
                        </form><br/>
                        </c:forEach>
                    </tbody>
            </table>
        </c:if>
        <a href="viewcart.jsp">View Your Cart</a>
<%--        <%
            List<Tbl_ProductDTO> result = (List<Tbl_ProductDTO>)request.getAttribute("SEARCH_RESULT_PRODUCT");
            if (result != null) {
                %>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Sku</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Quanity</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int count = 0;
                            for (Tbl_ProductDTO dto : result) {
                                %>
                    <form action="DispatchServlet" method="POST">
                        <tr>
                            <td>
                                <%= ++count %>
                            .</td>
                            <td>
                                <%= dto.getSku() %>
                                <input type="hidden" name="txtSku" value="<%= dto.getSku() %>" />
                            </td>
                            <td>
                                <%= dto.getName() %>
                                <input type="hidden" name="txtName" value="<%= dto.getName() %>" />
                            </td>
                            <td>
                                <%= dto.getPrice() %>
                            </td>
                            <td>
                                <%= dto.getQuanity() %>
                            </td>
                            <td>
                                <input type="checkbox" name="chkItem" value="ON" 
                                           <% 
                                            if (dto.isStatus()) {
                                                %>
                                                checked="checked"
                                           <%
                                            }//end item is true
                                           %>
                                           />
                                <%-- <%= dto.isStatus() %> -->          
                            </td>
                            <td>
                                <input type="text" name="txtQuanity" value=""  />
                                <input type="number" name="txtQuanity" value="0" max="<%= dto.getQuanity() %>" min="0" >                                
                                <input type="submit" value="Add Book to Your Cart" name="btAction"

                            </td>
                            
                        </tr>                        
                        </form><br/>
                        <%
                            }//end for dto in result
                        %>                       
                    </tbody>
                </table>
        <%
            }
        %>
        <a href="viewcart.jsp">View Your Cart</a>
            <input type="submit" value="Add Book to Your Cart" name="btAction" />
            <input type="submit" value="View Your Cart" name="btAction" />
        --%>
    </body>
</html>
