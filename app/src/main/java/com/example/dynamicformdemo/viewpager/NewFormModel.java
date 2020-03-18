package com.example.dynamicformdemo.viewpager;

import com.example.dynamicformdemo.Field;

import java.util.ArrayList;
import java.util.List;

public class NewFormModel {
    private List<Field> parentList = new ArrayList<>();

    public List<Field> getParentList() {
        return parentList;
    }

    public void setParentList(List<Field> parentList) {
        this.parentList = parentList;
    }

}
