package com.atguigu4.exer;

import com.atguigu3.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 * @author qcq
 * @create 2020/1/11-1:25 上午
 */
public class Exer1Test {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入姓名：");
        String name=scanner.nextLine();
        System.out.print("请输入邮箱：");
        String email=scanner.nextLine();
        System.out.println("请输入生日：");
        String birth=scanner.nextLine();
        String sql="insert into customers(name,email,birth) values(?,?,?)";
        int res = new Exer1Test().update(sql, name,email, birth);
        if(res>0){
            System.out.println("插入成功");
        }else {
            System.out.println("插入失败");
        }
    }
    public int update(String sql,Object ...args){
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
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            JDBCUtils.closeResource(connecton,preparedStatement);
        }
        return 0;
    }
}
