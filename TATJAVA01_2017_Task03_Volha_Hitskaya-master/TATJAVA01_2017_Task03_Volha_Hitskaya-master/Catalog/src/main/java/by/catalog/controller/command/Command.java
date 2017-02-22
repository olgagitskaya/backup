package by.catalog.controller.command;

import by.catalog.bean.NewsItem;

/**
 * Created by Volha_Hitskaya on 1/30/2017.
 */
public interface Command {
    public String execute(String request);
}
