package by.catalog.dao.impl;

import by.catalog.bean.Category;
import by.catalog.bean.NewsItem;
import by.catalog.dao.NewsItemDAO;
import by.catalog.dao.exception.DAOException;
import by.catalog.dao.manager.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by user on 14.02.2017.
 */

public class SqlNewsItemDAO implements NewsItemDAO
{
    ConnectionPool connectionPool;

    public SqlNewsItemDAO() throws DAOException
    {
        connectionPool = ConnectionPool.getInstance();
        try
        {
            connectionPool.initPoolData();
        }
        catch (Exception e)
        {
            throw new DAOException("Connection pool initialization failed");
        }
    }

    public boolean addNewsItem(NewsItem newsItem)
    {

        return true;
    }

    public ArrayList<NewsItem> getNewsItemsByTitleAndDate(String title, String date) throws DAOException
    {
        ArrayList<NewsItem> news = new ArrayList<>();

        try
        {
            Connection con = this.connectionPool.takeConnection();
            String sql = "SELECT * FROM CATALOG.NEWS WHERE title LIKE ? AND date LIKE ?";
            //use PreparedStatement to prevent sql injection
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,"%" + title + "%");
            ps.setString(2,"%" + date + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Category category = Category.values()[rs.getInt("category")];
                String categoryStr = category.toString();
                String titleStr = rs.getString("title");
                String dateStr = rs.getString("date");
                String additional_info = rs.getString("additional_info");
                //System.out.println(rs.getString(1) + " " + rs.getInt(2) + " " + rs.getString(3)+ " " +rs.getString(4));
                NewsItem newsItem = new NewsItem(categoryStr, titleStr, dateStr, additional_info);
                news.add(newsItem);
            }
        }
        catch(Exception e)
        {
            throw new DAOException("News can not be found", e);
        }
        return news;
    }
}





