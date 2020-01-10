package com.atguigu1.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author qcq
 * @create 2020/1/10-3:29 上午
 */
public class ConnectionTest {
    @Test
    public void testConnection1()throws Exception{
        Driver driver=new com.mysql.jdbc.Driver();
        String url ="jdbc:mysql://localhost:3306/test";
        Properties info=new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");
        Connection connection=driver.connect(url,info);
        System.out.println(connection);

    }
    @Test
    public void testConnecton2() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class clazz= Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        String url="jdbc:mysql://localhost:3306/test";
        Properties info=new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }
    @Test
    public void testConnection3() throws Exception {
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver=(Driver) clazz.newInstance();
        String user="root";
        String password="root";
        String url="jdbc:mysql://localhost:3306/test";
        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
    @Test
    public void testConnection4() throws Exception {
        String user="root";
        String password="root";
        String url="jdbc:mysql://localhost:3306/test";
        Class.forName("com.mysql.jdbc.Driver");
//        Driver driver=(Driver) clazz.newInstance();
//        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
    @Test
    public void testConnection5() throws Exception {
        InputStream in = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(in);
        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String url=properties.getProperty("url");
        String driverClass=properties.getProperty("driverClass");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

}
