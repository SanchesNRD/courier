package by.epam.courierexchange.controller.command;

import by.epam.courierexchange.controller.command.impl.DefaultCommand;
import by.epam.courierexchange.controller.command.impl.LoginCommand;

import java.util.EnumMap;

import static by.epam.courierexchange.controller.command.CommandType.*;

public class CommandProvider {
    private static CommandProvider INSTANCE;
    private final EnumMap<CommandType, Command> commands = new EnumMap<CommandType, Command>(CommandType.class);

    private CommandProvider(){
        commands.put(LOGIN, new LoginCommand());
        commands.put(DEFAULT, new DefaultCommand());
    }

    public static CommandProvider getInstance(){
        if(INSTANCE==null){
            INSTANCE = new CommandProvider();
        }
        return INSTANCE;
    }

    public Command getCommand(String commandName){
        if(commandName == null){
            return commands.get(DEFAULT);
        }
        CommandType commandType;
        try{
            commandType = valueOf(commandName.toUpperCase());
        }catch (IllegalArgumentException e){
            commandType = DEFAULT;
        }
        return commands.get(commandType);
    }
}