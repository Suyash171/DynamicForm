
package com.example.dynamicformdemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Field {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("minCharacters")
    @Expose
    private Integer minCharacters;
    @SerializedName("maxCharacters")
    @Expose
    private Integer maxCharacters;
    @SerializedName("min")
    @Expose
    private Integer min;
    @SerializedName("max")
    @Expose
    private Integer max;
    @SerializedName("defaultValue")
    @Expose
    private Boolean defaultValue;


    @SerializedName("emailvalidation")
    @Expose
    private Boolean emailvalidation;

    @SerializedName("componentType")
    @Expose
    private String componentType;


    @SerializedName("values")
    @Expose
    private List<Field> values = null;

    private String enteredValue;
    private Integer setSelectedRadioButton;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMinCharacters() {
        return minCharacters;
    }

    public void setMinCharacters(Integer minCharacters) {
        this.minCharacters = minCharacters;
    }

    public Integer getMaxCharacters() {
        return maxCharacters;
    }

    public void setMaxCharacters(Integer maxCharacters) {
        this.maxCharacters = maxCharacters;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<Field> getValues() {
        return values;
    }

    public void setValues(List<Field> values) {
        this.values = values;
    }

    public String getEnteredValue() {
        return enteredValue;
    }

    public void setEnteredValue(String enteredValue) {
        this.enteredValue = enteredValue;
    }

    public Boolean getEmailvalidation() {
        return emailvalidation;
    }

    public void setEmailvalidation(Boolean emailvalidation) {
        this.emailvalidation = emailvalidation;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public Integer getSetSelectedRadioButton() {
        return setSelectedRadioButton;
    }

    public void setSetSelectedRadioButton(Integer setSelectedRadioButton) {
        this.setSelectedRadioButton = setSelectedRadioButton;
    }

}
