<%-- 
    Document   : createNewAccount
    Created on : 14-03-2023, 05:03:08
    Author     : minht
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <%-- muốn sd jstl thì phải import thư viện trước --%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Account</title>
    </head>
    <body>
        <h1>Create Account</h1>
        <form action="DispatchServlet" method="POST">
            <!--lỗi đang được đặt ở reqScope, (muốn lấy 1 property ở bên trong obj phải qua 2 giai đoạn: ktra obj có khác null ko, đốitượng.property có khác null ko)-->
            <c:set var="erros" value="${requestScope.INSERT_ERRORS}"/> 
            
            Username <input type="text" name="txtUsername" value="${param.txtUsername}" size="20"/> (e.g 6 - 20 chars)<br/>
            <c:if test="${not empty erros.usernameLengthErr}">
                <font color="red">
                    ${erros.usernameLengthErr}
                </font><br/>
            </c:if>
            <c:if test="${not empty erros.usernameIsExisted}">
                <font color="red">
                    ${erros.usernameIsExisted}
                </font><br/>
            </c:if>
                
            Password <input type="password" name="txtPassword" value="" size="30"/> (e.g 6 - 30 chars)<br/> <%-- vì lí do bảo mật nên ko hiển thị--%>
            <c:if test="${not empty erros.passwordLengthErr}">
                <font color="red">
                    ${erros.passwordLengthErr}
                </font><br/>
            </c:if>
                
            Confirm <input type="password" name="txtConfirm" value="" size="30"/><br/>
            <c:if test="${not empty erros.confirmNotMatch}">
                <font color="red">
                    ${erros.confirmNotMatch}
                </font><br/>
            </c:if>
                
            Full name <input type="text" name="txtFullname" value="${param.txtFullname}" size="50"/> (e.g 2 - 50 chars) <br/>
            <c:if test="${not empty erros.fullNameLengthErr}">
                <font color="red">
                    ${erros.fullNameLengthErr}
                </font><br/>
            </c:if>
            <input type="submit" value="Create New Account" name="btAction" />
            <input type="reset" value="Reset" />
        </form>
    </body>
</html>
