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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tampvm.cart.CartObj;

/**
 *
 * @author minht
 */
@WebServlet(name = "AddItemToCartServlet", urlPatterns = {"/AddItemToCartServlet"})
public class AddItemToCartServlet extends HttpServlet {
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
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        
        String urlRewriting = "DispatchServlet"
                            + "?btAction=Shopping";
        
        try {
            //1. Customer goes to cart place (cart place là Session trong server), tạo session scope
            HttpSession session = request.getSession(); //get True vì luôn luôn phải đi tìm vỏ hàng có chưa, nếu chưa có vỏ hàng thì sẽ tạo mới vỏ hàng, ko cần ghi True vì True mặc định
            //tạo vỏ CartObj
            //2. Customer take cart, cart là Atrribute
            CartObj cart = (CartObj)session.getAttribute("CART");
            if (cart == null) {//nếu cart chưa tồn tại thì new cho cái giỏ 
                cart = new CartObj();
            }
            //3. Customer drops item to cart
            String item = request.getParameter("txtSku"); //take items
            String quanityItem = request.getParameter("txtQuanity"); //take items. vì đang là string nên convert lại thành int để lấy số lượng
            
            int quanity = Integer.parseInt(quanityItem);
                            
            //ko cần kiểm tra khác null, vì param truyền vào là đã khác null
            cart.addItemToCart(item, quanity); //drop item to cart   
            session.setAttribute("CART", cart); //getAttribute là phải set lại Attribute trong scope   
            
            //4. Customer continuosly goes shopping
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        } finally {
            //call shopping page again using urlRewriting để tiếp tục mua hàng
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
