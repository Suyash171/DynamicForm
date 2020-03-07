package com.example.dynamicformdemo;

import java.util.ArrayList;
import java.util.List;

public class ModelForm {

    private List<String> selectedCheckBoxes = new ArrayList<>();

    public ModelForm() {
    }

    public List<String> getSelectedCheckBoxes() {
        return selectedCheckBoxes;
    }

    public void setSelectedCheckBoxes(List<String> selectedCheckBoxes) {
        this.selectedCheckBoxes = selectedCheckBoxes;
    }
}
