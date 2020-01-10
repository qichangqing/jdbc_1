package com.atguigu3.preparestatement.crud;

import com.atguigu3.bean.Customer;
import com.atguigu3.bean.Order;
import com.atguigu3.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qcq
 * @create 2020/1/10-11:46 下午
 */
public class PreparedStatementQueryTest {
    @Test
    public void testGetList(){
        String sql="select id,name,email,birth from customers";
        List<Customer> customerList = getList(Customer.class, sql);
        customerList.forEach(System.out::println);
        String sql1="select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id<?";
        List<Order> orderList = getList(Order.class, sql1, 4);
        orderList.forEach(System.out::println);
    }
    public <T> List<T> getList(Class<T> clazz, String sql, Object ...args){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            List<T> list=new ArrayList<T>();
            connection = JDBCUtils.getConnecton();
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()){
                T t = clazz.newInstance();
                for(int i=0;i<columnCount;i++){
                    Object object = resultSet.getObject(i + 1);
//                    String columnName=metaData.getColumnName(i+1);
                    String columnLabel=metaData.getColumnLabel(i+1);
                    Field field=t.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,object);
                }
                list.add(t);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }
        return null;
    }
    @Test
    public void testGetInstance(){
        String sql="select id,name,email,birth from customers where id=?";
        Customer customer = getInstance(Customer.class, sql, 1);
        System.out.println(customer);
        String sql1="select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id=?";
        Order order = getInstance(Order.class, sql1, 1);
        System.out.println(order);
    }
    public <T> T getInstance(Class<T> clazz,String sql,Object ...args){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection = JDBCUtils.getConnecton();
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            if(resultSet.next()){
                T t = clazz.newInstance();
                for(int i=0;i<columnCount;i++){
                    Object object = resultSet.getObject(i + 1);
//                    String columnName=metaData.getColumnName(i+1);
                    String columnLabel=metaData.getColumnLabel(i+1);
                    Field field=t.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,object);
                }
                return t;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally{

            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }
        return null;
    }
}
