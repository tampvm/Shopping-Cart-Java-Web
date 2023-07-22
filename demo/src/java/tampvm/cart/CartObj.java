/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.cart;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import tampvm.tbl_product.Tbl_ProductDAO;
import tampvm.tbl_product.Tbl_ProductDTO;

/**
 *
 * @author minht
 */
public class CartObj implements Serializable {
//    private Map<String, Integer> items; //vỏ chứa nhiều items, có cặp name, quanity và chống trùng tên => dùng kiểu dữ liệu Map
    private Map<String, Tbl_ProductDTO> items;
    
//    public Map<String, Integer> getItems() {//vỏ là bỏ từng món vào chứ ko phải lấy toàn bộ đồ trong vỏ khác rồi bỏ vô nên dùng get, ko tồn tại set
//        return items;
//    }    
    public Map<String, Tbl_ProductDTO> getItems() {
        return items;
    }
        
    private List<Tbl_ProductDTO> listProduct;
    Tbl_ProductDAO dao = new Tbl_ProductDAO();
    
    public boolean addItemToCart(String id, int quanity) throws SQLException, NamingException {//bỏ đồ vào trong vỏ, thay thế phương thức set
        boolean result = false;
        //1. check đối số có hợp lệ hay không (id khác null), kiểm tra item có tồn tại hay ko 
        if (id == null) {
            return result;
        }
        
        if (id.trim().isEmpty()) {
            return result;
        }
        
        if (quanity <= 0) {
            return result;
        }
        
        //2. nếu item đã tồn tại trong shop, check existed items đã tồn tại trong cái vỏ hay chưa
        if (this.items == null) { //chưa tồn tại trong vỏ thì tạo mới
            this.items = new HashMap<>();
        }
        
        //3. check existed item, nếu item đã tồn tại thì check id để tăng số lượng items đó lên        
        if (this.items.containsKey(id)) {
            //4. increase quanity
            int currentQuanity = this.items.get(id).getQuanity();
            quanity = quanity + currentQuanity;
        }//end item has existed  
        
        
        
        //thông tin all sản phẩm có trong shop
        if (listProduct == null) {
            dao.searchProduct();
            listProduct = dao.getProductList();
        }                
        
        //update lại sản phẩm trong cart(id, quanity) + lấy thông tin của sản phẩm đã update để checkout
        for (Tbl_ProductDTO dto : listProduct) {
            if (dto.getSku().equals(id)) {                                
                dto.setQuanity(quanity);
                //5. update cart
                this.items.put(id, dto);
                result = true;
            }
        }
                
//        //5. update cart
//        this.items.put(id, dto);
//        result = true;
        
        return result;
    }
    
    public boolean removeItemCart(String id, int quanity) throws SQLException, NamingException {
        boolean result = false;
        
        //1. check đối số có hợp lệ hay không, kiểm tra item có tồn tại hay ko 
        if (id == null) {
            return result;
        }
        
        if (id.trim().isEmpty()) {
            return result;
        }
        
        if (quanity <= 0) {
            return result;
        }
        
        //2. check existed items        
        if (this.items == null) {
            return result;
        }       
        
        //3. check existed item 
        if (!this.items.containsKey(id)) {
            return result;
        }
        
        //4. decrease quanity
        int currentQuanity = this.items.get(id).getQuanity();
        
        if (currentQuanity < quanity) {
            return result;
        }
        
        if (currentQuanity >= quanity) {
            quanity = currentQuanity - quanity;
        }//drecreasing when larger
        
        
        //thông tin all sản phẩm có trong shop
        if (listProduct == null) {
            dao.searchProduct();
            listProduct = dao.getProductList();
        }
        
        //update lại sản phẩm trong cart(id, quanity) + lấy thông tin của sản phẩm đã update để checkout
        for (Tbl_ProductDTO dto : listProduct) {
            if (dto.getSku().equals(id)) {
                dto.setQuanity(quanity);
                //5. update cart
                if (quanity == 0) {
                    this.items.remove(id);
                    if (this.items.isEmpty()) {
                        this.items = null;
                    }
                } else {
                    this.items.put(id, dto);
                }
                result = true;
            }
        }   
        
        return result;  
    }
}