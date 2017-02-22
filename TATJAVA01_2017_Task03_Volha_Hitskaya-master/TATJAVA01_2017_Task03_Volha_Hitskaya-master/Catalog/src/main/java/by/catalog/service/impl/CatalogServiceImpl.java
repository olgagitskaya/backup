package by.catalog.service.impl;

import by.catalog.bean.NewsItem;
import by.catalog.dao.NewsItemDAO;
import by.catalog.dao.exception.DAOException;
import by.catalog.dao.factory.DAOFactory;
import by.catalog.service.CatalogService;
import by.catalog.service.exception.ServiceException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Volha_Hitskaya on 1/30/2017.
 */
public class CatalogServiceImpl implements CatalogService
{
    @Override
    public NewsItem addNewsItem(String category, String title, String additionalInfo) throws ServiceException
    {
        try
        {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            NewsItemDAO newsItemDAO = daoObjectFactory.getNewsItemDAO();
            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            NewsItem newsItem = new NewsItem(category.toLowerCase(), title, dateFormat.format(currentDate), additionalInfo);
            newsItemDAO.addNewsItem(newsItem);
            return newsItem;
        }
        catch (DAOException e)
        {
            throw new ServiceException(e);
        }
    }

    @Override
    public ArrayList<NewsItem> searchByTitleAndDate(String title, String date) throws ServiceException
    {
        try
        {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            NewsItemDAO newsItemDAO = daoObjectFactory.getNewsItemDAO();
            ArrayList<NewsItem> result = newsItemDAO.getNewsItemsByTitleAndDate(title, date);
            return result;
        }
        catch (DAOException e)
        {
            throw new ServiceException(e);
        }
    }
}
