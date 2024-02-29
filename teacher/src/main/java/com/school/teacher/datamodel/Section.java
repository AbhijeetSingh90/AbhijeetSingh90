package com.school.teacher.datamodel;

import java.util.List;

public class Section {

    private String name;
    private String displayName;
    private List<Section> sections;
    private Boolean disableValidation;
    private String errorMessage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        if (displayName == null) {
            if (name == null) {
                return "";
            }
            return name;
        }
        return displayName;
    }

    public boolean isLeafSection() {
        return sections == null || sections.isEmpty();
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void setDisableValidation(Boolean disableValidation) {
        this.disableValidation = disableValidation;
    }

    public boolean isValidationDisabled() {
        return (disableValidation != null && disableValidation);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "Section{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", sections=" + sections +
                ", disableValidation=" + disableValidation +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
