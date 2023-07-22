/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.tbl_detail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tampvm.utils.DBHelpers;

/**
 * @author minht
 */
public class Tbl_DetailDAO implements Serializable {
    public boolean insertItemsDetail(Tbl_DetailDTO dto) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            //1. conncect DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. create sql statement string
                String sql = "Insert Into tbl_Detail(sku, orderId, quanity, price, total) "
                        + "Values(?, ?, ?, ?, ?)";
                //3. create Statement to set SQL
                stm = con.prepareStatement(sql);
                //3.1 nạp parameter vào statement
//                stm.setInt(1, dto.getId());
                stm.setString(1, dto.getSku());
                stm.setString(2, dto.getOrderId());
                stm.setInt(3, dto.getQuanity());
                stm.setFloat(4, dto.getPrice());
                stm.setFloat(5, dto.getTotal());
                
                //4. Execute Query
                int row = stm.executeUpdate();
                
                //5.Process
                if (row > 0) {
                    return true;
                }
            }//end if connect success
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
        return false;
        
    }
    
    private List<Tbl_DetailDTO> orderDetailList;

    public List<Tbl_DetailDTO> getOrderDetailList() {
        return orderDetailList;
    }      

    public void searchItemsDetail () 
            throws SQLException, NamingException {
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        
        try {
            //1. connect DB
            con = DBHelpers.makeConnection(); //1. connect DB
            if (con != null) {//check con có tồn tại hay không, nếu có là đã kết nối thành công
                //2. writing sql statement
                String sql = "Select id, sku, orderId, quanity, price, total "
                        + "From tbl_Detail";
                //3. create statement to set sql
                stm = con.createStatement();
                //4. execute statement to get result
                rs = stm.executeQuery(sql);
                //5. process result
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String sku = rs.getString("sku");
                    String orderId = rs.getString("orderId");
                    int quanity = rs.getInt("quanity");
                    float price = rs.getFloat("price");
                    float total = rs.getFloat("total");

                    //create DTO instance (obj), map thành phần vào DTO
                    Tbl_DetailDTO dto = new Tbl_DetailDTO(id, sku, orderId, quanity, price, total);
                    //add orderDetailList, kiểm tra orderDetailList có tồn tại hay không, kiểm tra là ktra null -> từ đó build nó lại
                    if (this.orderDetailList == null) {
                        this.orderDetailList = new ArrayList<>();
                    }//end orderDetailList has NOT existed
                    //nếu orderDetailList != null thì add thêm vào
                    this.orderDetailList.add(dto);
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
