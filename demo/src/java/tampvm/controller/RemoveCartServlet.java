/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tampvm.cart.CartObj;
import tampvm.tbl_product.Tbl_ProductDTO;

/**
 *
 * @author minht
 */
@WebServlet(name = "RemoveCartServlet", urlPatterns = {"/RemoveCartServlet"})
public class RemoveCartServlet extends HttpServlet {

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
        try  {
            //1. Customer goes to cart place
            HttpSession session = request.getSession(false); //dùng false vì dữ liệu ở phía sv, session scope đã hết tg tồn tại, view thấy ở browser và browser ko tự refresh nên view vẫn còn đồ nên phải lấy false và check null            
            if (session != null) {
                //2. Customer take cart
                CartObj cart = (CartObj)session.getAttribute("CART");
                if (cart != null) {
                    //3. Customer gets all items
                    Map<String, Tbl_ProductDTO> items = cart.getItems();
                    if (items != null) { //check items có tồn tại hay ko 
                        //4. Customer chooses items để xóa
                        String[] selectItem = request.getParameterValues("chkItem"); //vì param lấy về là 1 mảng nên dùng getParameterValues
                        String selectQuanity = request.getParameter("txtQuanity");

                        int quanity = Integer.parseInt(selectQuanity);
                        if (selectItem != null) { //chống trường hợp ko chọn gì cả mà nhấn remove
                            //5. Customer removes items from cart
                            for (String item : selectItem) {                                
                                cart.removeItemCart(item, quanity);
                            }//end removed
                            session.setAttribute("CART", cart);
                        }//end items had choiced
                    }//end items have existed
                }//end cart has existed
            }//session has existed
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
        } finally {
            //call ViewCart feature again using urlRewriting 
            String urlRewriting = "DispatchServlet"
                    + "?btAction=View Your Cart";
            response.sendRedirect(urlRewriting);
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
