package com.epam.courses.java.final_project.logic.command;

// custom pair for response
public class Response {

    Direction direction;
    String response;

    public Response(Direction direction, String response) {
        this.direction = direction;
        this.response = response;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getResponse() {
        return response;
    }

    public enum Direction{
        Forward,
        Redirect
    }

    @Override
    public String toString() {
        return "Response{" +
                "direction=" + direction.name() +
                ", response='" + response + '\'' +
                '}';
    }
}
