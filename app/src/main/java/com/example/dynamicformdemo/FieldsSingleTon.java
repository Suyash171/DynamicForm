package com.example.dynamicformdemo;

import java.util.ArrayList;

public class FieldsSingleTon {
    private static final FieldsSingleTon ourInstance = new FieldsSingleTon();

    public static FieldsSingleTon getInstance() {
        return ourInstance;
    }

    private FieldsSingleTon() {
    }

    private ArrayList<Field> fieldArrayList  = new ArrayList<>();

    public ArrayList<Field> getFieldArrayList() {
        return fieldArrayList;
    }

    public void setFieldArrayList(ArrayList<Field> fieldArrayList) {
        this.fieldArrayList = fieldArrayList;
    }
}
