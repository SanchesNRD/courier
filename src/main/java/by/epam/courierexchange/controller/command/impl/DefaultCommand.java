package by.epam.courierexchange.controller.command.impl;

import by.epam.courierexchange.controller.command.Command;
import by.epam.courierexchange.controller.command.CommandResult;
import static by.epam.courierexchange.controller.command.CommandResult.ResponseType.*;
import by.epam.courierexchange.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.INDEX, REDIRECT);
    }
}
