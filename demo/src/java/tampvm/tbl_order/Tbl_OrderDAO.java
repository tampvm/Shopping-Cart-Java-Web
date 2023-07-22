/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.tbl_order;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
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
public class Tbl_OrderDAO implements Serializable {
    public boolean insertItemsOrder(Tbl_OrderDTO dto) 
            throws SQLException, NamingException {
        
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            //1. conncect DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. create sql statement string
                String sql = "Insert Into tbl_Order(id, date, total) "
                        + "Values(?, CURRENT_TIMESTAMP, ?)";
                //3. create Statement to set SQL
                stm = con.prepareStatement(sql);
                //3.1 nạp parameter vào statement
                stm.setString(1, dto.getId());                
                stm.setFloat(2, dto.getTotal());

                
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
    
    private List<Tbl_OrderDTO> orderList;

    public List<Tbl_OrderDTO> getOrderList() {
        return orderList;
    }

    public void searchOrder () 
            throws SQLException, NamingException {
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        
        try {
            //1. connect DB
            con = DBHelpers.makeConnection(); //1. connect DB
            if (con != null) { //check con có tồn tại hay không, nếu có là đã kết nối thành công
                //2. writing sql statement
                String sql = "Select id, date, total "
                        + "From tbl_Order";
                //3. create statement to set sql
                stm = con.createStatement();
                //4. execute statement to get result
                rs = stm.executeQuery(sql);
                //5. process result
                while (rs.next()) {
                    String id = rs.getString("id");
                    Date date = rs.getDate("date");
                    float total = rs.getFloat("total");

                    //create DTO instance (obj), map thành phần vào DTO
                    Tbl_OrderDTO dto = new Tbl_OrderDTO(id, date, total);
                    //add orderList, kiểm tra orderList có tồn tại hay không, kiểm tra là ktra null -> từ đó build nó lại
                    if (this.orderList == null) {
                        this.orderList = new ArrayList<>();
                    }//end orderList has NOT existed
                    //nếu orderList != null thì add thêm vào
                    this.orderList.add(dto);
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
    
    public boolean updateOrderList (String id, float total) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelpers.makeConnection(); //1. connect DB
            
            if (con != null) {
                //2. create sql statement
                String sql = "Update tbl_Order "
                        + "Set total = ? "
                        + "Where id = ?";
                //3. create statement to set sql
                stm = con.prepareStatement(sql);
                //3.1 nạp parameter vào ? trong statement
                stm.setFloat(1, total);
                stm.setString(2, id);
                //4. execute
                int effectiveRows = stm.executeUpdate();
                //5. process result
                if (effectiveRows > 0) {
                    result = true; //set value cho req obj
                }
            }//end if connection is existed     
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    } 
}
