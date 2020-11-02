package com.divine.yang.httpcomponent.retrofit2;

/**
 * Author: Divine
 * CreateDate: 2020/11/2
 * Describe:
 */
public class GeneralException extends RuntimeException{
    private int Code;
    private String ErrorMessage;

    public GeneralException(int Code, String ErrorMessage) {
        this.Code = Code;
        this.ErrorMessage = ErrorMessage;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
