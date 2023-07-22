<%-- 
    Document   : search
    Created on : 18-02-2023, 13:15:38
    Author     : minht
--%>

<%--<%@page import="tampvm.registration.RegistrationDTO"%>
<%@page import="java.util.List"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>        
        <c:if test="${empty sessionScope.USERNAME}">
            <c:redirect url="login.html"/>
        </c:if>
        <c:if test="${not empty sessionScope.USERNAME}">
            <font color="red">       
                Welcome, ${sessionScope.USERNAME.fullName} <!-- luôn luôn phải show fullname ko phải username -->     
            </font>
        </c:if>
               
        <%--<font color="red">
            Welcome, ${sessionScope.USERNAME.fullName} <!-- luôn luôn phải show fullname ko phải username -->
        </font>--%>
        <h1>Search Page</h1>
        <form action="DispatchServlet">
            Search value <input type="text" name="txtSearchValue"          
                                value="${param.txtSearchValue}" /><br/> 
            <input type="submit" value="Search" name="btAction" />
            <input type="submit" value="Logout" name="btAction" />
        </form>

        <c:set var="searchValue" value="${param.txtSearchValue}"/>
        <c:if test="${not empty searchValue}"> <%-- được phép search khi giá tr5i search khác null --%>
            <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                            <!-- 4. result -->
                        <form action="DispatchServlet" method="POST">                       
                            <tr>
                                <td>
                                    ${counter.count} <!-- 8. counter -->
                                    .</td>
                                <td>
                                    ${dto.username} <!-- 5. result.username / 6. dt.username / 7. dto.usename / -->
                                    <input type="hidden" name="txtUsername" <%-- copy name ở update servlet--%>
                                           value="${dto.username}" />
                                </td>
                                <td>
                                    <%--${dto.password}--%>
                                    <input type="text" name="txtPassword" <%-- copy name ở update servlet--%>
                                           value="${dto.password}" />
                                </td>
                                <td>
                                    ${dto.fullName}
                                </td>
                                <td>
                                    <input type="checkbox" name="chkAdmin" value="ON"
                                           <c:if test="${dto.role}">
                                               checked="checked"
                                           </c:if>
                                           />
                                </td>
                                <td>
                                    <c:url var="deleteLink" value="DispatchServlet"> <%-- kĩ thuật url related actions giống url rewriting trong chức năng delete --%>
                                        <c:param name="btAction" value="delete"/> <%-- phía trước param đầu tiên tự chuyển thành dấu ?, param thứ 2 tự động chuyển thành dấu &, tự động thêm dấu bằng vào sau mỗi param --%>
                                        <c:param name="pk" value="${dto.username}"/>
                                        <c:param name="lastSearchValue" value="${searchValue}"/>
                                    </c:url>
                                    <a href="${deleteLink}">Delete</a>
                                </td>
                                <td>
                                    <input type="submit" value="Update" name="btAction" />
                                    <input type="hidden" name="lastSearchValue" 
                                           value="${searchValue}" />
                                </td>
                                    
                            </tr>
                        </form>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty result}">
                <h2>
                    No record is matched!!!
                </h2>
            </c:if>
        </c:if>
        <%--    <c:set var="cookies" value="${requestScope.Cookies}"/>
            <c:if test="${not empty cookies}">
                <c:set var="username" value=""/>
                <c:forEach var="cookie" items="${cookies}">
                    <c:set var="temp" value="${cookie.name}"/>
                    <c:if test="${not temp == 'JSESSIONID'}">
                        username=${temp}
                    </c:if>
                </c:forEach>
                   
            </c:if>--%>
        
        <%--<% 
            if (session.getAttribute("USERNAME") == null) {//trước khi vào trang search luôn luôn check lại session lưu trữ user khi login vào, = null -> login (tránh trường hợp logout mà back lại trang)
                response.sendRedirect("login.html");
            } else {
        %>
        <font color="red">
        <% 
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {//1. cookie phải tồn tại, cookie phải được thông qua login thì mới được add vào, còn nếu dùng url thì ko có cookie
//                Cookie lastCookie = cookies[cookies.length - 1];
//                String username = lastCookie.getName();
                String username = "";
                for (Cookie cookie : cookies) {
                    String temp = cookie.getName();
                    if (!temp.equals("JSESSIONID")) {
                        username = temp;
                    }
                }
                %>
                

            Welcome, <%= username %>
                            <%        
            }//end cookies are existed
        }
        %>
        </font>
        
        
        <h1>Search Page</h1>
        <form action="DispatchServlet">
            Search value <input type="text" name="txtSearchValue"          
                                value="<%= request.getParameter("txtSearchValue") %>" /><br/>  <!-- 1. xác định scripting element dùng - 2. xác định implicit Obj --> <!-- giá trị người dùng nhập còn tồn tại, reqObj còn tồn tại do Dispatcher forward -->
            <input type="submit" value="Search" name="btAction" />
            <input type="submit" value="Logout" name="btAction" />
        </form><br/>
        <!-- luôn luôn check giá trị người dùng nhập trên các parameter phải khác null -->
        <% 
            //check nếu = null -> load lại search 
            String searchValue = request.getParameter("txtSearchValue");

            if (searchValue != null) {
                List<RegistrationDTO> result = (List<RegistrationDTO>)request.getAttribute("SEARCH_RESULT"); //do kết quả search đang đặt trong SearchLastNameSv, có kiểu dữ liệu là List<RegistrationDTO>, cần ép kiểu tường minh các attribute, tên lấy từ SearchLNSvl
                if (result != null) {    //kiểm tra khác null
                    %>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Full name</th>
                                <th>Role</th>
                                <th>Delete</th>
                                <th>Update</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int count = 0;                      //Quy tắc viết url rewriting:
                                for (RegistrationDTO dto : result) {//ko tồn tại dấu %20, phải có dấu ? và 2 dấu &. giữa các dấu = tuyệt đối ko có khoảng trắng, và khoảng trắng dc thể hiện bằng %20%
                                    String urlRewriting = "DispatchServlet"
                                            + "?btAction=delete"
                                            + "&pk=" + dto.getUsername() //đưa primary key (pk) cho DB để xóa
                                            + "&lastSearchValue=" + searchValue; //sau khi xóa thì load(cập nhật) lại trang search, nhắc cho server nhớ user đã làm gì
                                    %>
                        <form action="DispatchServlet" method="POST">                       
                                    <tr> <!-- lấy giá trị của biến nên dùng expression -->
                                <td>
                                    <%= ++count %> 
                                .</td>
                                <td>
                                    <%= dto.getUsername() %>
                                    <input type="hidden" name="txtUsername" 
                                           value="<%= dto.getUsername() %>" />
                                </td>
                                <td>
                                    <input type="text" name="txtPassword" 
                                           value="<%= dto.getPassword() %>" />
                                </td>
                                <td>
                                    <%= dto.getFullName() %>
                                </td>
                                <td>
                                    <input type="checkbox" name="chkAdmin" value="ON" 
                                           <% 
                                            if (dto.isRole()) {
                                                %>
                                                checked="checked"
                                           <%
                                            }//end admin role is true
                                           %>
                                           />
                                </td>
                                <td>
                                    <a href="<%= urlRewriting %>">Delete</a>
                                </td>
                                <td>
                                    <input type="submit" value="Update" name="btAction" />
                                    <input type="hidden" name="lastSearchValue" value="<%= searchValue %>" />
                                </td>                           
                            </tr>
                        </form>   
                            <%
                                }//end for dto in result
                            %>
                        </tbody>
                    </table>
        <%
                } else { 
                    %>
                    <h2>
                        No record is matched!!!
                    </h2>    
        <%
                }
            }//end if search Value has valid         
        %>--%>  
    </body>
</html>
