package com.example.ticketbooking.dto;

public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private String timestamp;

    public ApiResponse() {}

    public ApiResponse(int status, String message, T data, String timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public String getTimestamp() { return timestamp; }

    public void setStatus(int status) { this.status = status; }
    public void setMessage(String message) { this.message = message; }
    public void setData(T data) { this.data = data; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}