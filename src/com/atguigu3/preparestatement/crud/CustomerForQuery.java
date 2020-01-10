package com.atguigu3.preparestatement.crud;

import com.atguigu3.bean.Customer;
import com.atguigu3.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;

/**
 * @author qcq
 * @create 2020/1/10-8:36 下午
 */
public class CustomerForQuery {
    @Test
    public void testQueryForCustomers(){
        String sql="select id,name,email,birth from customers where id=?";
        Customer customer = queryForCustomers(sql, 13);
        System.out.println(customer);
    }
    public Customer queryForCustomers(String sql,Object ...args){
        Connection connecton = null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connecton = JDBCUtils.getConnecton();
            preparedStatement=connecton.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
           resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            if(resultSet.next()){
                Customer customer=new Customer();
                for(int j=0;j<columnCount;j++){
                    Object columnValue=resultSet.getObject(j+1);
                    String columnName=metaData.getColumnName(j+1);
                    Field field=customer.getClass().getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(customer,columnValue);
                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,preparedStatement,resultSet);
        }
        return null;
    }
    @Test
    public void testQuery1(){
        Connection connecton = null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connecton = JDBCUtils.getConnecton();
            String sql="select id,name,email,birth from customers where id=?";
            preparedStatement = connecton.prepareStatement(sql);
            preparedStatement.setObject(1,1);
            resultSet = preparedStatement.executeQuery();
            Customer customer=new Customer();
            if(resultSet.next()){
                int id=resultSet.getInt(1);
                String name=resultSet.getString(2);
                String email=resultSet.getString(3);
                Date birth=resultSet.getDate(4);
                customer.setBirth(birth);
                customer.setEmail(email);
                customer.setId(id);
                customer.setName(name);
            }
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,preparedStatement,resultSet);
        }
    }
}
