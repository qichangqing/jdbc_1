package com.atguigu4.exer;

import com.atguigu3.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

/**
 * @author qcq
 * @create 2020/1/11-1:57 上午
 */
public class Exer2Test {
    public static void main(String[] args){
        //第一种
//        System.out.println("请输入要删除的考号：");
//        Scanner scanner=new Scanner(System.in);
//        String examId=scanner.next();
//        String sql="select FlowID flowID,Type type,IDCard idCard,ExamCard examCard,StudentName name,Location location,Grade grade from examstudent where ExamCard=?";
//        Student s = new Exer2Test().getInstance(Student.class, sql, examId);
//        if(s==null){
//            System.out.println("查无此人，请重新输入");
//        }else{
//            String sql1="delete from examstudent where ExamCard=?";
//            int deletecount = new Exer2Test().update(sql1, examId);
//            if(deletecount>0){
//                System.out.println("删除成功");
//            }
//        }
        //第二种
        System.out.println("请输入要删除的考号：");
        Scanner scanner=new Scanner(System.in);
        String examId=scanner.next();
        String sql1="delete from examstudent where ExamCard=?";
        int deletecount = new Exer2Test().update(sql1, examId);
        if(deletecount>0){
            System.out.println("删除成功");
        }else {
            System.out.println("查无此人，请重新输入");
        }

    }

//    public static void main(String[] args){
//        System.out.println("请输入查询类型：");
//        System.out.println("a.按考号查询");
//        System.out.println("b.按身份证号查询");
//        Scanner scanner=new Scanner(System.in);
//        String querytype=scanner.nextLine();
//        if("a".equalsIgnoreCase(querytype)){
//            System.out.println("请输入考号：");
//            String examCard=scanner.next();
//            String sql="select FlowID flowID,Type type,IDCard idCard,ExamCard examCard,StudentName name,Location location,Grade grade from examstudent where ExamCard=?";
//            Student student = new Exer2Test().getInstance(Student.class, sql, examCard);
//            if(student==null){
//                System.out.println("输入的准考证号有误！");
//            }else{
//                System.out.println(student);
//            }
//
//        }else if("b".equalsIgnoreCase(querytype)){
//            System.out.println("请输入身份证号：");
//            String idCard=scanner.next();
//            String sql="select FlowID flowID,Type type,IDCard idCard,ExamCard examCard,StudentName name,Location location,Grade grade from examstudent where IDCard=?";
//            Student student = new Exer2Test().getInstance(Student.class, sql, idCard);
//            if(student==null){
//                System.out.println("输入的身份证号有误！");
//            }else{
//                System.out.println(student);
//            }
//
//        }else {
//            System.out.println("查询类型输入错误，请重新输入。");
//        }
//    }

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
//    public static void main(String[] args){
//        Scanner scanner=new Scanner(System.in);
//        System.out.print("请输入考试类型：");
//        int type=scanner.nextInt();
//        System.out.print("请输入身份证号：");
//        String IDCard=scanner.next();
//        System.out.print("请输入考号：");
//        String ExamCard=scanner.next();
//        System.out.print("请输入姓名：");
//        String StudentName=scanner.next();
//        System.out.print("请输入地址：");
//        String Location=scanner.next();
//        System.out.print("请输入考试成绩：");
//        int Grade=scanner.nextInt();
//        String sql="insert into examstudent(type,idcard,examcard,studentname,location,grade) values(?,?,?,?,?,?)";
//        int res = new Exer2Test().update(sql, type,IDCard, ExamCard,StudentName,Location,Grade);
//        if(res>0){
//            System.out.println("插入成功");
//        }else {
//            System.out.println("插入失败");
//        }
//    }
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
