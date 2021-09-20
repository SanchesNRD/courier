package by.epam.courierexchange.controller.command.impl.go;

import by.epam.courierexchange.controller.command.Command;
import by.epam.courierexchange.controller.command.CommandResult;
import by.epam.courierexchange.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

import static by.epam.courierexchange.controller.command.CommandResult.ResponseType.*;

public class GoToSignUpCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.SIGN_UP_PAGE, FORWARD);
    }
}
