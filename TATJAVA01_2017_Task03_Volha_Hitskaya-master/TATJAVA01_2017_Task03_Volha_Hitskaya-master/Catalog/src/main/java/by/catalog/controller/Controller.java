package by.catalog.controller;

import by.catalog.bean.NewsItem;
import by.catalog.controller.command.Command;

import java.util.Date;

/**
 * Created by Volha_Hitskaya on 1/30/2017.
 */
public final class Controller {
    private final CommandProvider provider = new CommandProvider();

    public String executeCommand(String request) {
        String commandName = request.split(" ",2)[0];
        Command executionCommand = provider.getCommand(commandName);
        String response = executionCommand.execute(request);
        return response;
    }
}
