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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tampvm.registration.RegistrationDAO;
import tampvm.registration.RegistrationDTO;

/**
 *
 * @author minht
 */
public class LoginServlet extends HttpServlet {
    private final String SEARCH_PAGE = "search.jsp";
    private final String INVALID_PAGE = "invalid.html";

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

        //get parametterString button = request.getParameter("btAction"); //forward

        String url = INVALID_PAGE;
        String username = request.getParameter("txtUsername"); //forward
        String password = request.getParameter("txtPassword");//forward- Controller xu li dia chi reqobj duoc container gui den 
        
        try {
            //3. call DAO => new DAO obj & call method of DAO
            RegistrationDAO dao = new RegistrationDAO(); //new obj
//            boolean result = dao.checkLogin(username, password); //call method from that obj
            RegistrationDTO result = dao.checkLogin(username, password); //call method from that obj
            if (result != null) {
                url = SEARCH_PAGE; // controller đã send cho view và view render
                HttpSession session = request.getSession();//tạo session để lưu trữ user đã login vào bên phía server, login thành công phải lưu trữ thông tin người dùng bên phía sv + lưu bên client => dùng httpsession
                session.setAttribute("USERNAME", result);//luôn luôn lưu trữ nên phải tạo mới (True), hỗ trợ cho việc check role để phân biệt user có thể sd những tính năng gì, login là phải tạo session
                
                //get full name from user via DAO
                //session.setAttribute("FULLNAME", fullname);
                
                //store cookie (add cookie to client using resObj, viết cookie. được xảy ra khi đăng nhập thành công
                Cookie cookie = new Cookie(username, password);
                cookie.setMaxAge(60 * 3);
                response.addCookie(cookie);
                
                              
                
            } //4. send View
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (/*ClassNotFoundException*/NamingException ex) {
            ex.printStackTrace();
        } finally {
            //5. set values to resobj
            response.sendRedirect(url); //gửi cookie về phải dùng respone
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
            
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
