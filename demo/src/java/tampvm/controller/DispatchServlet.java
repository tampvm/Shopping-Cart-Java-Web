/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author minht
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"}) //anotation
public class DispatchServlet extends HttpServlet {
    private final String LOGIN_PAGE = "login.html"; // lấy url Pattern
    private final String LOGIN_CONTROLLER = "LoginServlet"; // lấy url Pattern
    private final String SEARCH_LASTNAME_CONTROLLER = "SearchLoginServlet"; // lấy url Pattern
    private final String DELETE_ACCOUNT_CONTROLLER = "DeleteAccountServlet";
    private final String UPDATE_ACCOUNT_CONTROLLER = "UpdateServlet";
    private final String TRIGGER_APP_CONTROLLER = "TriggerAppServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String ADD_ITEMS_TO_CART_CONTROLLER = "AddItemToCartServlet";
    private final String VIEW_CART_PAGE = "viewcart.jsp";
    private final String REMOVE_ITEM_CART_CONTROLER = "RemoveCartServlet";
    private final String SEARCH_PRODUCT_CONTROLLER = "SearchProductServlet";
    private final String CHECK_OUT_CONTROLLER = "CheckOutServlet";
    private final String CREATE_NEW_ACCOUNT_CONTROLLER = "CreateNewAccountServlet";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) //anotation
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = LOGIN_PAGE;
        //which button did user click?
        String button = request.getParameter("btAction");
        
        
        try  {
           if (button == null) { 
                //bất kể thời điểm nào, khi có req gửi từ client đến server, 
                //ta luôn phải kiểm tra coi req đó có gửi cookie lên hay không.
                //transfer to login page
                url = TRIGGER_APP_CONTROLLER;
           } else if (button.equals("Login")) {
               url = LOGIN_CONTROLLER;
           } else if (button.equals("Search")) {
               url = SEARCH_LASTNAME_CONTROLLER;
           } else if (button.equals("delete")) {
               url = DELETE_ACCOUNT_CONTROLLER;
           } else if (button.equals("Update")) {
               url = UPDATE_ACCOUNT_CONTROLLER;
           } else if (button.equals("Logout")) {
               url = LOGOUT_CONTROLLER;
           } else if (button.equals("Add Book to Your Cart")) {
               url = ADD_ITEMS_TO_CART_CONTROLLER;
           } else if (button.equals("View Your Cart")) {
               url = VIEW_CART_PAGE;
           } else if (button.equals("Remove Select Items")) {
               url = REMOVE_ITEM_CART_CONTROLER;
           } else if (button.equals("Shopping")) {
               url = SEARCH_PRODUCT_CONTROLLER;
           } else if (button.equals("Check out")) {
               url = CHECK_OUT_CONTROLLER;
           } else if (button.equals("Create New Account")) {
               url = CREATE_NEW_ACCOUNT_CONTROLLER;
           }     
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }
//    để tạo 1 tính năng mới trong mô hình MVC:
//    1. tạo giao diện, view (action là url parttern của Dispatcher Servlet)
//    2. Map tính năng mới vào dispatcher
//    3. tạo servlet của chức năng đó
//    4. call DAO

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
