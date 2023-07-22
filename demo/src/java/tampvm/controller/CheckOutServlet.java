/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tampvm.cart.CartObj;
import tampvm.tbl_detail.Tbl_DetailDAO;
import tampvm.tbl_detail.Tbl_DetailDTO;
import tampvm.tbl_order.Tbl_OrderDAO;
import tampvm.tbl_order.Tbl_OrderDTO;
import tampvm.tbl_product.Tbl_ProductDTO;

/**
 *
 * @author minht
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private final String CHECKOUT = "checkout.jsp";
    private final String INVALID = "errors.html";

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

        String url = INVALID;
        try {
            boolean itemResult = false;
            boolean orderResult = false;
            Date date = new Date();
            int countCheckout = 0;
            String orderId;
            HttpSession ID = request.getSession();

            

//            countCheckout=  (int)   ID.getAttribute("ID");
            
            //ID.getAttribute("ID");
            
            //System.out.println(ID.getAttribute("ID"));
            
            if (ID.getAttribute("ID") == null) {
                countCheckout = 1;
                orderId = "ID" + countCheckout;
                ID.setAttribute("ID", countCheckout);
            } else {
                int count1 = (int) ID.getAttribute("ID");               
                count1++;
                orderId = "ID" + count1;
                ID.setAttribute("ID", count1);
            }
            
            
            
//            System.out.println(ID.getAttribute("ID"));
            
                        

//            
//            Tbl_DetailDTO dtoDetail = new Tbl_DetailDTO(int, String, String, int, float, float);
            //1. Customer goes to cart place
            HttpSession session = request.getSession(false); //dùng false vì dữ liệu ở phía sv, session scope đã hết tg tồn tại, view thấy ở browser và browser ko tự refresh nên view vẫn còn đồ nên phải lấy false và check null
            if (session != null) {
                //2. Customer take cart
                CartObj cart = (CartObj) session.getAttribute("CART");
                if (cart != null) {
                    //3. Customer gets all items
                    Map<String, Tbl_ProductDTO> items = cart.getItems();
                    if (items != null) { //check items có tồn tại hay không 
                        Tbl_DetailDAO detailDao = new Tbl_DetailDAO();
                        Tbl_OrderDAO orderDao = new Tbl_OrderDAO();
                        int count = 0;
                        float totalPrice = 0;

                        for (String item : items.keySet()) {
                            int id = ++count;

                            String sku = item;
                            int quanity = items.get(item).getQuanity();
                            float price = items.get(item).getPrice();
                            float total = items.get(item).getQuanity() * items.get(item).getPrice();

                            //4. insert each items into DB -> new obj DAO, tạo DTO rồi call phương thức trong DAO để insert items (vì nếu thay đổi dữ liệu trên bảng thì dto sẽ thay đổi)
                            Tbl_DetailDTO dtoDetail = new Tbl_DetailDTO(id, sku, orderId, quanity, price, total);
                            itemResult = detailDao.insertItemsDetail(dtoDetail);

                            totalPrice += total;

                        }//end traverse Map base on key

                        //insert eech order into DB
                        Tbl_OrderDTO dtoOrder = new Tbl_OrderDTO(orderId, date, totalPrice);
                        orderResult = orderDao.insertItemsOrder(dtoOrder);

                        if (itemResult && orderResult) {//model set value cho reqObj gửi về cho Controller
                            //call DAO to search detail items and order for view render           
                            detailDao.searchItemsDetail();
                            List<Tbl_DetailDTO> resultSearch = detailDao.getOrderDetailList(); // 4.3 (thấy được kết quả của search thông qua list) store result to Scope (if any)                
                            request.setAttribute("SEARCH_RESULT_DETAIL", resultSearch); //nơi lưu trữ là scope, lưu trong attribute của scope

                            orderDao.searchOrder();
                            List<Tbl_OrderDTO> resultSearchOrder = orderDao.getOrderList();
                            request.setAttribute("SEARCH_RESULT_ORDER", resultSearchOrder);

                            url = CHECKOUT; //Send view and view render
                        }
                    }//end items have existed
                }//end cart has existed
            }//session has existed

            //5. remove cart
            session.removeAttribute("CART");
            //6. return shop.jsp or login

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        } finally {
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


