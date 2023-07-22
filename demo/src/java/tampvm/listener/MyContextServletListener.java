/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.listener;

import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import tampvm.utils.PropertiesFileHelper;

/**
 * Web application lifecycle listener.
 *
 * @author minht
 */
public class MyContextServletListener implements ServletContextListener {   
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String siteMapLocation = 
                context.getInitParameter(
                        "SITEMAP_PROPERTIES_FILE_LOCATION");
        Properties siteMapProperty = 
                PropertiesFileHelper.getProperties(context, 
                                                siteMapLocation);
        context.setAttribute("SITE_MAP", siteMapProperty);
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
    
    
    
    
//    //có hiệu lực khi được map trong web.xml
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
////        System.out.println("Application is deploying...........");
////        
////        ServletContext context = sce.getServletContext();
////        String siteMapsPath = context.getInitParameter("INIT_NAME");
////        //read file and load to map
////        Properties siteMaps = new Properties();
////        InputStream is = null;
////        
////        try {
////            is = context.getResourceAsStream(siteMapsPath);
////            siteMaps.load(is);
////            context.setAttribute("SITEMAPS", context);
////            
////        } finally {
////            if (is != null) {
////                try {
////                    is.close();  
////                } catch (IOException ex) {
////                    context.log("MyContextServletListener _ IO " + ex.getMessage());
////                }                
////            }
////        }
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        System.out.println("Application is destroying...........");
//    }

