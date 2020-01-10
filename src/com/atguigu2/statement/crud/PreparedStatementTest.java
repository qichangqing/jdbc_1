package com.atguigu2.statement.crud;

import com.atguigu3.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

/**
 * @author qcq
 * @create 2020/1/11-12:32 上午
 */
public class PreparedStatementTest {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入用户名：");
        String user = scanner.next();
        System.out.print("请输入密码：");
        String password=scanner.next();
        String sql="select user,password from user_table where user=? and password=?";
        User user1 = new PreparedStatementTest().getInstance(User.class,sql,user,password);
        if(user1==null){
            System.out.println("用户名或密码错误！");
        }else{
            System.out.println("登陆成功！");
        }
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
