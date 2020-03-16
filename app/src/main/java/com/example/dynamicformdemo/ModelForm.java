package com.example.dynamicformdemo;

import java.util.ArrayList;
import java.util.List;

//Here use observables
public class ModelForm {

    private ArrayList<Field> fielsModel = new ArrayList<>();

    public ModelForm() {
    }

    public ArrayList<Field> getFielsModel() {
        return fielsModel;
    }

    public void setFielsModel(ArrayList<Field> fielsModel) {
        this.fielsModel = fielsModel;
    }
}
