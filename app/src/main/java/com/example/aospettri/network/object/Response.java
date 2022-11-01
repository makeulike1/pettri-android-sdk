package com.example.aospettri.network.object;

public class Response {

    private     Integer     status;

    private     String      message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response(Integer status, String message){
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
