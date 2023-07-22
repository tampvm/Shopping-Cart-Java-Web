/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tampvm.registration.RegistrationDAO;
import tampvm.registration.RegistrationDTO;

/**
 *
 * @author minht
 */
@WebServlet(name = "TriggerAppServlet", urlPatterns = {"/TriggerAppServlet"})
public class TriggerAppServlet extends HttpServlet {
    private final String LOGIN_PAGE = "login.html";
    private final String SEARCH_PAGE = "search.jsp";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        //đọc cookies
        try {
           //1. get all cookies (read) from request, cookies được lấy là 1 list nên kiểu dữ liệu là mảng
           Cookie[] cookies = request.getCookies();
           if (cookies != null) { //get cookie là lấy cookie đã tồn tại, nếu cookie ko tồn tại thì chuyển về trang login
               //2. get Last Cookie
               Cookie lastCookies = cookies[cookies.length-1];
               //3. get username and password from cặp name value được lưu trong cookie
               String username = lastCookies.getName();
               String password = lastCookies.getValue();
               //4. call DAO to check authentication, kiểm tra username password có tồn tại trong DB không
               RegistrationDAO dao = new RegistrationDAO();
               RegistrationDTO result = dao.checkLogin(username, password);
               if (result != null) {
                   url = SEARCH_PAGE; //sau khi đăng nhập thành công, thì hiện welcome + search.jsp, vì welcome hiển thị tên người thay đổi nên xài search động
               }//end user is authenticated
               
               /*//2. traverse all cookies to check authentication, 
               for (Cookie cookie : cookies) {
                   //3. get username and password from cặp name value   được lưu trong cookie
                   String username = cookie.getName();
                   String password = cookie.getValue();
                   //4. call DAO to check authentication, kiểm tra username password có tồn tại trong DB không
                   RegistrationDAO dao = new RegistrationDAO();
                   boolean result = dao.checkLogin(username, password);
                   if (result) {
                       url = SEARCH_PAGE;
                       //break; //ngắt vòng for
                   }//end user is authenticated
               }//end for traverse cookies*/
               
           }//end cookies had existed
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        } finally {
            response.sendRedirect(url); //ko cần duy trì, bởi vì cookie lưu ở bên phía client  
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
