package com.example.dynamicformdemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FieldsModel {
    @SerializedName("players")
    @Expose
    private List<Field> fields = null;

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}

