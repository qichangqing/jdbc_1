package com.atguigu3.preparestatement.crud;

import com.atguigu3.bean.Order;
import com.atguigu3.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;

/**
 * @author qcq
 * @create 2020/1/10-10:28 下午
 */
public class OrderForQuery {
    @Test
    public void testQueryOrder(){
        String sql="select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id=?";
        Order order = queryOrder(sql, 1);
        System.out.println(order);
    }
    public Order queryOrder(String sql,Object ...args) {
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
                Order order=new Order();
                for(int i=0;i<columnCount;i++){
                    Object object = resultSet.getObject(i + 1);
//                    String columnName=metaData.getColumnName(i+1);
                    String columnLabel=metaData.getColumnLabel(i+1);
                    Field field=order.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(order,object);
                }
                return order;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally{

            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
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
            String sql="select order_id,order_name,order_date from `order` where order_id=?";
            preparedStatement = connecton.prepareStatement(sql);
            preparedStatement.setObject(1,1);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int id=resultSet.getInt(1);
                String name=resultSet.getString(2);
                Date date=resultSet.getDate(3);
                Order order=new Order(id,name,date);
                System.out.println(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,preparedStatement,resultSet);
        }

    }
}
