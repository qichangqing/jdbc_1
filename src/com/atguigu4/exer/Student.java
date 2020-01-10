package com.atguigu4.exer;

/**
 * @author qcq
 * @create 2020/1/11-2:31 上午
 */
public class Student {
    /*
    FlowID
    Type
    IDCard
    ExamCard
    StudentName
    Location
    Grade
     */
    private int flowID;
    private int type;
    private String idCard;
    private String examCard;
    private String name;
    private String location;
    private int grade;

    public Student() {
    }

    @Override
    public String toString() {
        return info();
    }

    private String info() {
        System.out.println("==================查询结果===================");
        return "流水号："+flowID+"\n考试类型："+type+"\n身份证号："+idCard+"\n考号："+examCard+"\n姓名："+name+"\n地址："+location+"\n";
    }

    public Student(int flowID, int type, String idCard, String examCard, String name, String location, int grade) {
        this.flowID = flowID;
        this.type = type;
        this.idCard = idCard;
        this.examCard = examCard;
        this.name = name;
        this.location = location;
        this.grade = grade;
    }

    public int getFlowID() {
        return flowID;
    }

    public void setFlowID(int flowID) {
        this.flowID = flowID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getExamCard() {
        return examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
