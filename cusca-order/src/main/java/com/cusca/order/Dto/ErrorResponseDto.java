package com.cusca.order.Dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorResponseDto {

    private String message;
    private int statusCode;
    private String timestamp;

    public ErrorResponseDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = getFormattedTimestamp();
    }
    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}