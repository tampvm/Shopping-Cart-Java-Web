/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tampvm.registration.RegistrationDAO;
import tampvm.registration.RegistrationDTO;

/**
 *
 * @author minht
 */
@WebServlet(name = "SearchLoginServlet", urlPatterns = {"/SearchLoginServlet"})
public class SearchLastNameServlet extends HttpServlet {
    private final String SEARCH_PAGE = "search.html";
    private final String SEARCH_RESULT_PAGE = "search.jsp"; //đôi lúc tồn tại 2 trang jsp và html
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
        String url = SEARCH_RESULT_PAGE;
        
        String searchValue = request.getParameter("txtSearchValue");
        try  {
            if (!searchValue.trim().isEmpty()) {//parameter luôn là chuỗi rỗng
                //4. call DAO
                RegistrationDAO dao = new RegistrationDAO();// 4.1 new obj
                dao.searchLastName(searchValue); //4.2 call method
                
                List<RegistrationDTO> result = dao.getAccountList(); // 4.3 (thấy được kết quả của search thông qua list) store result to Scope (if any)
                url = SEARCH_RESULT_PAGE;
                request.setAttribute("SEARCH_RESULT", result); //nơi lưu trữ là scope, lưu trong attribute của scope
            } //end search value has inputter valid value
        } catch (/*ClassNotFoundException*/NamingException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
//            dùng sendredirect thì reqObj sẽ biến mất ko còn lưu trữ
            RequestDispatcher rd = request.getRequestDispatcher(url);// phải duy trì req obj 
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
