/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tampvm.utils.DBHelpers;

/**
 *
 * @author minht
 */
public class RegistrationDAO implements Serializable {

//    public boolean checkLogin(String username, String password)
//            throws SQLException, /*ClassNotFoundException*/ NamingException {
    public RegistrationDTO checkLogin(String username, String password)
            throws SQLException, /*ClassNotFoundException*/ NamingException {
        Connection con = null;
        PreparedStatement stm = null; //câu truy vấn động hoặc có tham số
        ResultSet rs = null; //select thi2 du2ng resultset
//        boolean result = false;
        RegistrationDTO result = null;
        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();     //bắt 2 lỗi: sqlexp và classni

            if (con != null) {
                //2. create SQL statement
                String sql = "Select lastname, isAdmin "
                        + "From Registration "
                        + "Where username = ? And password = ?";
                //3. create statement to set sql (2,3 -> tạo ra câu truy vấn trong DB)
                stm = con.prepareStatement(sql); //prepareSta dùng để nạp string vào PreStatement (động)
                stm.setString(1, username); //cung cấp giá trị cho tham số (?) trc khi thực hiện truy vấn
                stm.setString(2, password); //có format: setXXX, XXX là kiểu dữ liệu, tham số đầu tiên có vị trí là 1 
                //4. execute statement Obj to get result (thực hiện câu truy vấn được tạo ra)
                rs = stm.executeQuery();
                //5. process result (check dữ liệu login được truy vấn ra có tồn tại hay không, nếu có return true)
                if (rs.next()) {
//                    return result = true;// set value cho req obj
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    
                     result = new RegistrationDTO(username, null, fullName, role);
                }
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
//        return result; //check login ko tồn tại thì trả về false      
        return result;
    }

    private List<RegistrationDTO> accountList;

    public List<RegistrationDTO> getAccountList() {
        return accountList;
    }

    public void searchLastName(String searchValue)
            throws SQLException, /*ClassNotFoundException*/ NamingException {
        Connection con = null;
        PreparedStatement stm = null; //câu truy vấn động hoặc có tham số
        ResultSet rs = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();     //bắt 2 lỗi: sqlexp và classni

            if (con != null) { //check con có tồn tại hay không, nếu có là đã kết nối thành công
                //2. create SQL statement
                String sql = "Select username, password, lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname Like ?";
                //3. create statement to set sql (2,3 -> tạo ra câu truy vấn trong DB)
                stm = con.prepareStatement(sql); //prepareSta dùng để nạp string vào PreStatement (động)
                stm.setString(1, "%" + searchValue + "%");
                //4. execute statement Obj to get result (thực hiện câu truy vấn được tạo ra)
                rs = stm.executeQuery();
                //5. process result (check dữ liệu login được truy vấn ra có tồn tại hay không, nếu có return true)
//                - truy vấn nhiều dòng nên dùng while
                while (rs.next()) {
                    //get field/column , lấy thứ tự cột nếu cột swap cột với nhau thì sai
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String lastname = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    //create DTO instance (obj), map thành phần vào DTO
                    RegistrationDTO dto = new RegistrationDTO(username, password, lastname, role);
                    //add acc list, kiểm tra accList có tồn tại hay không, kiểm tra là ktra null -> từ đó build nó lại
                    if (this.accountList == null) {
                        this.accountList = new ArrayList<>();
                    }//end accList has NOT existed
                    //nếu accList != null thì add thêm vào
                    this.accountList.add(dto);
                } //end rs has more than one record        
            }  // end if connnection is existed  
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
    
    public boolean deleteAcccount(String pk)
            throws SQLException, /*ClassNotFoundException*/ NamingException {
        Connection con = null;
        PreparedStatement stm = null; //câu truy vấn động hoặc có tham số
        boolean result = false;
        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();     //bắt 2 lỗi: sqlexp và classni

            if (con != null) {
                //2. create SQL statement
                String sql = "Delete From Registration "
                        + "Where username = ?";
                //3. create statement to set sql (2,3 -> tạo ra câu truy vấn trong DB)
                stm = con.prepareStatement(sql); //prepareSta dùng để nạp string vào PreStatement (động)
                stm.setString(1, pk); //cung cấp giá trị cho tham số (?) trc khi thực hiện truy vấn
            
                //4. execute (khi thực hiện câu lệnh delete, update, insert thì kết quả trả về số dòng(rows))
                int effectiveRows = stm.executeUpdate();
                //5. process result 
                if (effectiveRows > 0) {
                    result = true;// set value cho req obj
                }
            } // end if connnection is existed           
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result; //trả giá trị    
    }
    
    public boolean updateAccount (String username, String password, boolean role) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelpers.makeConnection(); //1. connect DB
            
            if (con != null) {
                //2. create sql statement
                String sql = "Update Registration "
                        + "Set password = ?, isAdmin = ? "
                        + "Where username = ?";
                //3. create statement to set sql
                stm = con.prepareStatement(sql);
                //3.1 nạp parameter vào ? trong statement
                stm.setString(1, password);
                stm.setBoolean(2, role);
                stm.setString(3, username);
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
    
    public boolean createAccount(RegistrationDTO dto) 
            throws SQLException, NamingException {
        //đối với trạng thái tìm obj thì luôn luôn kiểm tra nó có bằng null hay không
        if (dto == null) {
            return false;
        }//end dto is not existed
        
        Connection con = null;
        PreparedStatement stm = null;
                
        try {
            //1. connect DB
            con = DBHelpers.makeConnection();
            
            if (con != null) {
                //2. create sql statement string
                String sql = "Insert Into Registration("
                        + "username, password, lastname, isAdmin"
                        + ") Values("
                        + "?, ?, ?, ?"
                        + ")";
                
            
                //3. create Statement to set SQL
                stm = con.prepareStatement(sql);
                //3.1 nạp parameter vào statement
                stm.setString(1, dto.getUsername());
                stm.setString(2, dto.getPassword());
                stm.setString(3, dto.getFullName());
                stm.setBoolean(4, dto.isRole());
            
                //4. Execute Query
                int row = stm.executeUpdate();
                
                //5.Process
                if (row > 0) {
                    return true;
                }
            }//end if connect is success
            
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
}    
        