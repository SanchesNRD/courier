package by.epam.courierexchange.controller.command.impl.go;

import by.epam.courierexchange.controller.command.Command;
import by.epam.courierexchange.controller.command.CommandResult;
import by.epam.courierexchange.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

import static by.epam.courierexchange.controller.command.CommandResult.ResponseType.FORWARD;

public class GoToUserProfile implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.USER_PROFILE_PAGE, FORWARD);
    }
}
