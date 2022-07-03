package com.teaminvincible.ESchool.Output;

public class ErrorField {
    private String fieldName;
    private String objection;

    public ErrorField() {
    }

    public ErrorField(String fieldName, String objection) {
        this.fieldName = fieldName;
        this.objection = objection;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getObjection() {
        return objection;
    }

    public void setObjection(String objection) {
        this.objection = objection;
    }
}
