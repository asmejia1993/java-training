package com.asmejia93.articles.exception;

import java.util.Date;

public class ApiError {
    private int status;
    private String msg;
    private Date date;

    public ApiError(int value, String msg, Date date) {
        this.status = value;
        this.msg = msg;
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
