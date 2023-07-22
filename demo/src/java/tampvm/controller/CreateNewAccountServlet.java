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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tampvm.registration.RegistrationDAO;
import tampvm.registration.RegistrationDTO;
import tampvm.registration.RegistrationInsertError;

/**
 *
 * @author minht
 */
@WebServlet(name = "CreateNewAccountServlet", urlPatterns = {"/CreateNewAccountServlet"})
public class CreateNewAccountServlet extends HttpServlet {
    private final String LOGIN_PAGE = "login.html";
    private final String ERROR_PAGE = "createNewAccount.jsp"; //nếu đăng kí ko thành công chuyển qua trang jsp
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
        
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
        
        RegistrationInsertError errors = new RegistrationInsertError(); //đối tượng để đón nhận lỗi
        boolean foundErr = false; //biến để xác định có lỗi hay không
        String url = ERROR_PAGE;
        
        try {
            //1. Check all user errors, bắt hết tất cả lỗi người dùng
            if (username.trim().length() < 6 || username.trim().length() > 20) {
                foundErr = true;
                errors.setUsernameLengthErr("Username is required from 6 to 20 chars");
            }//lỗi được báo là hàng loạt ko phải là từng cái nên dùng if
            
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                foundErr = true;
                errors.setPasswordLengthErr("Password is required from 6 to 30 chars");
            } else if (!confirm.trim().equals(password.trim())) { //vì confirm được bắt lỗi khi password đúng nên dùng else
                foundErr = true;
                errors.setConfirmNotMatch("Confirm must match passwrod");
            }
            
            if (fullname.trim().length() < 2 || fullname.trim().length() > 50) {
                foundErr = true;
                errors.setFullNameLengthErr("Fullname is required from 2 to 50 chars");
            }
            
            //2. process
            //2.1 If errors occur, system display errors and log errors            
            if (foundErr) {//2.2 Otherwise, call Model - DAO
                //nếu có lỗi thì dùng Attribute để lưu trữ lỗi trong Scope sau đó mới truyền qua trang jsp, vì show ra cho user nên dùng reqScope
                request.setAttribute("INSERT_ERRORS", errors);                
            } else {
                //nếu ko có lỗi thì insert vào Database - call DAO (làm bên DAO)
                
                //tạo dto 
                RegistrationDTO dto = new RegistrationDTO(username, password, fullname, false); //vì nếu thay đổi dữ liệu trên bảng thì dto sẽ thay đổi
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.createAccount(dto);
                
                if (result) {
                    //nếu đăng kí thành công chuyển qua login page
                    url = LOGIN_PAGE;
                }//end if account is created
            }           
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("CreateNewAccountServlet _ SQL " + msg);
            if (msg.contains("duplicate")) { //dựa vào keyword trong msg để bắt lỗi
                errors.setUsernameIsExisted(username + " existed!!!");
                request.setAttribute("INSERT_ERRORS", errors); //lỗi đang ở tay mình phải set vào Attribute để lưu lỗi
                //sau đó qua trang jsp để show lỗi
            }
        } catch (NamingException ex) {
            log("CreateNewAccountServlet _ Naming" + ex.getMessage());
        } finally {
            //vì để xử lí lỗi (lỗi nằm trong attribute của scope) nên dùng reqDispatcher forward, dùng sendRedirect nó sẽ hủy requset(lỗi dc lưu trong attribute)
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
