package com.atguigu5.blob;

import com.atguigu3.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author qcq
 * @create 2020/1/11-11:19 上午
 */
public class InsertTest {
    /*
   设置不允许自动提交
    */
    @Test
    public void testInsert3() {
        Connection connecton = null;
        PreparedStatement preparedStatement=null;
        long start=System.currentTimeMillis();
        try {
            connecton = JDBCUtils.getConnecton();
            connecton.setAutoCommit(false);//关闭自动提交
            String sql="insert into goods(name) values(?)";
            preparedStatement = connecton.prepareStatement(sql);
            for(int i=1;i<=1000000;i++){
                preparedStatement.setObject(1,"name_"+i);
                preparedStatement.addBatch();
                if(i%500==0){
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            connecton.commit();//提交
            long end=System.currentTimeMillis();
            System.out.println("花费的时间为："+(end-start));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connecton,preparedStatement);
        }

    }
    /*
    使用preparedstatement进行批量插入数据，addBatch(),executeBatch()401ms
     */
    @Test
    public void testInsert2() {
        Connection connecton = null;
        PreparedStatement preparedStatement=null;
        long start=System.currentTimeMillis();
        try {
            connecton = JDBCUtils.getConnecton();
            String sql="insert into goods(name) values(?)";
            preparedStatement = connecton.prepareStatement(sql);
            for(int i=1;i<=1000000;i++){
                preparedStatement.setObject(1,"name_"+i);
                preparedStatement.addBatch();
                if(i%500==0){
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            long end=System.currentTimeMillis();
            System.out.println("花费的时间为："+(end-start));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connecton,preparedStatement);
        }

    }
    /*
    使用preparedstatement进行批量插入数据3870ms
     */
    @Test
    public void testInsert1() {
        Connection connecton = null;
        PreparedStatement preparedStatement=null;
        long start=System.currentTimeMillis();
        try {
            connecton = JDBCUtils.getConnecton();
            String sql="insert into goods(name) values(?)";
            preparedStatement = connecton.prepareStatement(sql);
            for(int i=1;i<=20000;i++){
                preparedStatement.setObject(1,"name_"+i);
                preparedStatement.execute();
            }
            long end=System.currentTimeMillis();
            System.out.println("花费的时间为："+(end-start));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connecton,preparedStatement);
        }

    }
}
