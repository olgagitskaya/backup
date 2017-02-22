package by.catalog.controller.command.impl;

import by.catalog.bean.NewsItem;
import by.catalog.controller.command.Command;
import by.catalog.service.CatalogService;
import by.catalog.service.exception.ServiceException;
import by.catalog.service.factory.ServiceFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Volha_Hitskaya on 2/1/2017.
 */

public class Search implements Command
{
    String response = null;

    @Override
    public String execute(String request)
    {
        try
        {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            CatalogService catalogService = serviceFactory.getCatalogService();
            String titleCriteria = getCriteria(request, "title");
            String dateCriteria = getCriteria(request, "date");
            ArrayList<NewsItem> result = catalogService.searchByTitleAndDate(titleCriteria, dateCriteria);
            if (result != null && result.size() > 0)
            {
                response = "Result: \n";
                for (NewsItem item : result)
                {
                    response += item.toString() + "\n";
                }
                return response.trim();
            }

            return "Not Found";
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    public String getCriteria(String request, String filter)
    {
        Pattern p = Pattern.compile(".*" + filter + "=\\\"([^\\\"]*)\\\".*");
        Matcher m = p.matcher(request);
        String result = "";
        if (m.find())
        {
            result = m.group(1);
        }
        return result;
    }
}
