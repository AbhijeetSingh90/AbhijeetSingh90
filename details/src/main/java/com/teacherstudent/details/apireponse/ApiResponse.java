package com.teacherstudent.details.apireponse;

public class ApiResponse<S> {

    private String message;

    private Object dtos;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDtos() {
        return dtos;
    }

    public void setDtos(Object dtos) {
        this.dtos = dtos;
    }

    public ApiResponse(String message, Object dtos) {
        this.message = message;
        this.dtos = dtos;
    }
}
