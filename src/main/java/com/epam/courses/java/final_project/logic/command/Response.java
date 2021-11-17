package com.epam.courses.java.final_project.logic.command;

public class Response {

    Direction direction;
    String response;
    int errCode;

    public Response(Direction direction, String response) {
        this.direction = direction;
        this.response = response;
    }

    public Response(Direction direction, int errCode, String response) {  // Error case
        this.direction = direction;
        this.errCode = errCode;
        this.response = response;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getResponse() {
        return response;
    }

    public int getErrCode() {
        return errCode;
    }

    public enum Direction{
        Forward,
        Redirect,
        Error
    }

    @Override
    public String toString() {
        return "Response{" +
                "direction=" + direction.name() +
                ", response='" + response + '\'' +
                '}';
    }
}
