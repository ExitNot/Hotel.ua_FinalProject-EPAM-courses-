package com.epam.courses.java.final_project.logic.command;

import com.epam.courses.java.final_project.logic.command.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.courses.java.final_project.util.CommandConstant.*;
import static com.epam.courses.java.final_project.util.Constant.LOG_TRACE;

import java.util.HashMap;
import java.util.Map;


/**
 * (can be said "Invoker" of command pattern) or strategy pattern
 * */
public class CommandContainer {

    private static final CommandContainer INSTANCE;
    private static final Map<String, Command> getCommands;
    private static final Map<String, Command> postCommands;
    private static final Command NOT_FOUND = new NotFoundCommand();

    private final Logger log = LogManager.getLogger(LOG_TRACE);

    static {
        INSTANCE = new CommandContainer();
        getCommands = new HashMap<>();
        postCommands = new HashMap<>();

//        GET commands
        getCommands.put(INDEX, new IndexCommand());
        getCommands.put(LOGOUT, new LogoutCommand());
        getCommands.put(AVAILABLE_ROOMS, new AvailableRoomsCommand());
        getCommands.put(SIGN_IN, new SignInCommand());
        getCommands.put(PROFILE, new ProfileCommand());
//        POST commands
        postCommands.put(SIGN_UP, new SignUpCommand());
        postCommands.put(DELETE_USER, new DeleteUserCommand());
        postCommands.put(BOOK_SPECIFIC_ROOM, new BookSpecificRoomCommand());
    }

    public static CommandContainer getInstance(){
        return INSTANCE;
    }

    public Command getCommandGet(String command){
        Command out = getCommands.get(command);
        if (out == null){
            return NOT_FOUND;
        }
        return out;
    }

    public Command getCommandPost(String command){
        Command out = postCommands.get(command);
        if (out == null){
            return NOT_FOUND;
        }
        return out;
    }
}
