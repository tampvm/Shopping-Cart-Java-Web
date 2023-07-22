<%-- 
    Document   : checkout
    Created on : 15-03-2023, 01:48:08
    Author     : minht
--%>

<%@page import="tampvm.tbl_order.Tbl_OrderDTO"%>
<%@page import="java.util.List"%>
<%@page import="tampvm.tbl_detail.Tbl_DetailDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check out</title>
    </head>
    <body>
        <h1> Order </h1>
         <%
            List<Tbl_OrderDTO> resultOrder = (List<Tbl_OrderDTO>)request.getAttribute("SEARCH_RESULT_ORDER");
            if (resultOrder != null) {
                %>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Date</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%                          
                            for (Tbl_OrderDTO dtoOrder : resultOrder) {
                                %>
                        <tr>
                            <td>
                                <%= dtoOrder.getId() %>
                            </td>
                            <td>
                                <%= dtoOrder.getDate() %>
                            </td>
                            <td>
                                <%= dtoOrder.getTotal() %>
                            </td>
                        </tr>
                        <%
                            }//end for dto in result
                        %>
                    </tbody>
                </table><br/>
         <%
            }
        %>
        
        <h1> Detail Items </h1>
        <%
            List<Tbl_DetailDTO> resultDetail = (List<Tbl_DetailDTO>)request.getAttribute("SEARCH_RESULT_DETAIL");
            if (resultDetail != null) {
                %>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Product ID</th>
                            <th>Order ID</th>
                            <th>Quanity</th>
                            <th>Price</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%                          
                            for (Tbl_DetailDTO dtoDetail : resultDetail) {
                                %>
                        <tr>
                            <td>
                                <%= dtoDetail.getId() %>
                            </td>
                            <td>
                                <%= dtoDetail.getSku() %>
                            </td>
                            <td>
                                <%= dtoDetail.getOrderId() %>
                            </td>
                            <td>
                                <%= dtoDetail.getQuanity() %>
                            </td>
                            <td>
                                <%= dtoDetail.getPrice() %>
                            </td>
                            <td>
                                <%= dtoDetail.getTotal() %>
                            </td>
                        </tr>
                        <%
                            }//end for dto in result
                        %>
                    </tbody>
                </table> <br/>
        <%
            }
        %>       
        <form action="DispatchServlet" method="POST">
            <input type="submit" value="Shopping" name="btAction" />
            <input type="submit" value="login" name="btAction" />
        </form>
        
    </body>
</html>
