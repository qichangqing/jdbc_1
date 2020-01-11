package com.atguigu5.blob;

import com.atguigu3.bean.Customer;
import com.atguigu3.util.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * @author qcq
 * @create 2020/1/11-3:37 上午
 */
public class BlobTest {
    @Test
    public void testInsert() {
        Connection connecton = null;
        PreparedStatement preparedStatement=null;
        FileInputStream fileInputStream=null;
        try {
            connecton = JDBCUtils.getConnecton();
            String sql="insert into customers(name,email,birth,photo) values(?,?,?,?)";
            preparedStatement = connecton.prepareStatement(sql);
            preparedStatement.setObject(1,"袁浩");
            preparedStatement.setObject(2,"yh@126.com");
            preparedStatement.setObject(3,"1997-01-03");
            fileInputStream=new FileInputStream(new File("girl.jpg"));
            preparedStatement.setBlob(4,fileInputStream);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(fileInputStream!=null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(connecton,preparedStatement);
        }
    }
    @Test
    public void testQuery() {
        Connection connecton = null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        InputStream binaryStream=null;
        FileOutputStream fileOutputStream=null;
        try {
            connecton = JDBCUtils.getConnecton();
            String sql="select id,name,email,birth,photo from customers where id=?";
            preparedStatement = connecton.prepareStatement(sql);
            preparedStatement.setObject(1,25);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Date date=resultSet.getDate("birth");
                int id=resultSet.getInt("id");
                Customer customer=new Customer(id,name,email,date);
                System.out.println(customer);
                Blob photo = resultSet.getBlob("photo");
                binaryStream = photo.getBinaryStream();
                byte[] buffer=new byte[1024];
                fileOutputStream=new FileOutputStream("zhangyuhao.jpg");
                int len=0;
                while((len=binaryStream.read(buffer))!=-1){
                    fileOutputStream.write(buffer,0,len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(fileOutputStream!=null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(binaryStream!=null)
                    binaryStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(connecton,preparedStatement,resultSet);
        }

    }
}
