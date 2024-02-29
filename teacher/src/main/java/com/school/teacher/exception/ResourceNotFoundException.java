package com.school.teacher.exception;

public class ResourceNotFoundException extends RuntimeException{

    String resourceName;
    String feildName;
    String feildValue;

    public ResourceNotFoundException(String resourceName, String feildName, String userId) {
        super(String.format("%s Not Found With %s :%s ",resourceName,feildName,userId));
        this.resourceName = resourceName;
        this.feildName = feildName;
        this.feildValue = userId;
    }

}
