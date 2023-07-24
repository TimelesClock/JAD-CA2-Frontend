package util;


import java.io.FileInputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.servlet.ServletContext;

public class DatabaseUtil {

    public static Connection getConnection(ServletContext servletContext) {
    	try {
            String propertiesPath = servletContext.getRealPath("/WEB-INF/dbconfig.properties");
            Properties properties = new Properties();
            properties.load(new FileInputStream(propertiesPath));

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
