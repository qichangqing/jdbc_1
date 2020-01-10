package com.atguigu3.util;

import com.atguigu1.connection.ConnectionTest;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author qcq
 * @create 2020/1/10-7:10 下午
 */
public class JDBCUtils {
    public static Connection getConnecton()throws Exception{
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(in);
        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String url=properties.getProperty("url");
        String driverClass=properties.getProperty("driverClass");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
    public static void closeResource(Connection connection, PreparedStatement statement){
        try {
            if(connection!=null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(statement!=null){
                statement.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet){
        try {
            if(connection!=null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(statement!=null){
                statement.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(resultSet!=null)
                resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
