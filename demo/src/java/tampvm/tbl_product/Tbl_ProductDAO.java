/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.tbl_product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tampvm.utils.DBHelpers;

/**
 *
 * @author minht
 */
public class Tbl_ProductDAO implements Serializable {
    private List<Tbl_ProductDTO> productList;

    public List<Tbl_ProductDTO> getProductList() {
        return productList;
    }
    
    
    public void searchProduct () throws SQLException, NamingException {
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        
        try {
            //1. connect DB
            con = DBHelpers.makeConnection(); //1. connect DB
            if (con != null) { //check con có tồn tại hay không, nếu có là đã kết nối thành công
                 //2. writing sql statement
                 String sql = "Select sku, name, price, quanity, status " 
                         + "From tbl_Product";
                 //3. create statement to set sql
                 stm = con.createStatement();
                 //4. execute statement to get result
                 rs = stm.executeQuery(sql);
                 //5. process result
                 while (rs.next()) {
                     String sku = rs.getString("sku");
                     String name = rs.getString("name");
                     float price = rs.getFloat("price");
                     int quanity = rs.getInt("quanity");
                     boolean status = rs.getBoolean("status");
                     //create DTO instance (obj), map thành phần vào DTO
                     Tbl_ProductDTO dto = new Tbl_ProductDTO(sku, name, price, quanity, status);
                     //add productlist, kiểm tra productList có tồn tại hay không, kiểm tra là ktra null -> từ đó build nó lại
                     if (this.productList == null) {
                         this.productList = new ArrayList<>();
                     }//end productList has NOT existed
                    //nếu productList != null thì add thêm vào
                     this.productList.add(dto);
                 } //end rs has more than one record                
             } // end if connnection is existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            
            if (stm != null) {
                stm.close();
            }
            
            if (con != null) {
                con.close();
            }
        }
        
    }
}
