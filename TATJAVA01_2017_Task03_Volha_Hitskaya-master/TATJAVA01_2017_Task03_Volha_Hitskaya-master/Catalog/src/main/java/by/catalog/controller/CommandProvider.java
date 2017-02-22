package by.catalog.controller;

import by.catalog.controller.command.Command;
import by.catalog.controller.command.CommandName;
import by.catalog.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Volha_Hitskaya on 1/30/2017.
 */
final class CommandProvider
{
    private final Map<String, Command> commandList = new HashMap<String,Command>();

    CommandProvider()
    {
        commandList.put(CommandName.ADD_NEWS_ITEM, new AddNewsItem());
        commandList.put(CommandName.SEARCH, new Search());
        commandList.put(CommandName.WRONG_REQUEST, new WrongRequest());
    }

    Command getCommand(String name)
    {
        String commandName = name.toUpperCase();
        if (commandList.containsKey(commandName))
        {
            return commandList.get(commandName);
        }
        return commandList.get(CommandName.WRONG_REQUEST);
    }
}
