/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author minht
 */

//hỗ trợ thằng khác chạy nên đặt tên helper
public class DBHelpers implements Serializable {
    public static Connection makeConnection() throws /*ClassNotFoundException,*/ SQLException, NamingException {
        //1. get current context
        Context context = new InitialContext();
        //2. get context Tomcat
        Context tomcat = (Context)context.lookup("java:comp/env");
        //3. look up DS
        DataSource ds = (DataSource)tomcat.lookup("DateSourceDS");
        //4. open connection
        Connection con = ds.getConnection();
        
        return con;
//        //1. Load Driver
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        //2. Create connection string để trỏ tới địa chỉ DB
//        String url = "jdbc:sqlserver://localhost:1433;databaseName=master;instanceName=FUKO";
//        //3. Open connection
//        Connection con = DriverManager.getConnection(url, "sa", "123456");
//        return con;
    }
}
