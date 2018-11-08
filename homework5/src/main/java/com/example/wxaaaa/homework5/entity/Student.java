package com.example.wxaaaa.homework5.entity;

public class Student {
    public String id;
    public String name;
    public String age;
    public String describe;

    public Student(String name, String age, String describe){
        this.id = String.valueOf(System.currentTimeMillis() + age);
        this.name = name;
        this.age = age;
        this.describe = describe;
    }

}
