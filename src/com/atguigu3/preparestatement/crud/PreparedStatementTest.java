package com.atguigu3.preparestatement.crud;

import com.atguigu3.bean.Customer;
import com.atguigu3.util.JDBCUtils;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author qcq
 * @create 2020/1/10-6:38 下午
 */
public class PreparedStatementTest {

    @Test
    public void deleteTest(){
//        String sql="delete from customers where id=?";
//        update(sql,3);
        String sql="update `order` set order_name=? where order_id=?";
        update(sql,"DD",2);
    }
    public void update(String sql,Object ...args){
        Connection connecton = null;
        PreparedStatement preparedStatement=null;
        try {
            //获取链接
            connecton = JDBCUtils.getConnecton();
            //预编译sql
            preparedStatement = connecton.prepareStatement(sql);
            //填充占位符
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            //执行修改
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            JDBCUtils.closeResource(connecton,preparedStatement);
        }
    }
    @Test
    public void updateTest() {
        Connection connecton = null;
        PreparedStatement preparedStatement=null;
        try {
            connecton = JDBCUtils.getConnecton();
            String sql="update customers set name=? where id=?";
            preparedStatement = connecton.prepareStatement(sql);
            preparedStatement.setObject(1,"莫扎特");
            preparedStatement.setObject(2,18);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,preparedStatement);
        }

    }
    @Test
    public void preparedStatementTest()throws Exception{
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(is);
        String url=properties.getProperty("url");
        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String driverClass=properties.getProperty("driverClass");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        String sql="insert into customers(name,email,birth) values(?,?,?)";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,"哪吒");
        preparedStatement.setString(2,"nezha@gmail.com");
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("1000-01-01");
        preparedStatement.setDate(3,new java.sql.Date(date.getTime()));
        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
