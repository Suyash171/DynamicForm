package com.example.dynamicformdemo;

import java.util.ArrayList;
import java.util.List;

public class ModelForm {

    private List<String> selectedCheckBoxes = new ArrayList<>();
    private ArrayList<Field> fielsModel = new ArrayList<>();

    public ModelForm() {
    }

    public List<String> getSelectedCheckBoxes() {
        return selectedCheckBoxes;
    }

    public void setSelectedCheckBoxes(List<String> selectedCheckBoxes) {
        this.selectedCheckBoxes = selectedCheckBoxes;
    }

    public ArrayList<Field> getFielsModel() {
        return fielsModel;
    }

    public void setFielsModel(ArrayList<Field> fielsModel) {
        this.fielsModel = fielsModel;
    }
}
