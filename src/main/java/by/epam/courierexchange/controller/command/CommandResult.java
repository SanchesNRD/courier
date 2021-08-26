package by.epam.courierexchange.controller.command;

public class CommandResult {
    public enum ResponseType{
        FORWARD,
        REDIRECT
    }

    private final ResponseType responseType;
    private final String pagePath;


    public CommandResult(String pagePath, ResponseType responseType){
        this.pagePath = pagePath;
        this.responseType = responseType;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getPagePath() {
        return pagePath;
    }
}
