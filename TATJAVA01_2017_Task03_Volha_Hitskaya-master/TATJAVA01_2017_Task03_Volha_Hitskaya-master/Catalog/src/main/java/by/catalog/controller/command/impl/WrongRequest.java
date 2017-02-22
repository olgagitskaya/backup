package by.catalog.controller.command.impl;

import by.catalog.bean.NewsItem;
import by.catalog.controller.command.Command;

/**
 * Created by Volha_Hitskaya on 1/30/2017.
 */
public class WrongRequest implements Command
{
    @Override
    public String execute(String request)
    {
        return "Wrong request";
    }
}
