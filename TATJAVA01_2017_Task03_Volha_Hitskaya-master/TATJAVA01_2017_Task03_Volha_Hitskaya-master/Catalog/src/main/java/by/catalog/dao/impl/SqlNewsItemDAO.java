package by.catalog.dao.impl;

import by.catalog.bean.Category;
import by.catalog.bean.NewsItem;
import by.catalog.dao.NewsItemDAO;
import by.catalog.dao.exception.DAOException;
import by.catalog.dao.manager.ConnectionPool;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public boolean addNewsItem(NewsItem newsItem) throws DAOException
    {
        if (newsItem.getTitle().isEmpty()|| newsItem.getDate().isEmpty()||newsItem.getCategory().isEmpty())
        {
            return false;
        }
        try
        {
            Connection con = this.connectionPool.takeConnection();
            String sql = "INSERT INTO catalog.news(title, category, date, additional_info) VALUES (?, ?, ?, ?)";
            Category category = Category.valueOf(newsItem.getCategory().toUpperCase());
            String dateStr = newsItem.getDate();
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date date = format.parse(dateStr);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newsItem.getTitle());
            ps.setInt(2, category.ordinal());
            ps.setDate(3, sqlDate);
            ps.setString(4, newsItem.getAdditionalinfo());
            int countRows = ps.executeUpdate();
            if (countRows > 0)
            {
                return true;
            }
            connectionPool.closeConnection(con, ps);
        }
        catch (Exception e)
        {
            throw new DAOException("Information addition error", e);
        }

        return false;
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
            ps.setString(1, "%" + title + "%");
            ps.setString(2, "%" + date.replace('/', '-') + "%");
            ResultSet rs = ps.executeQuery();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            while (rs.next())
            {
                Category category = Category.values()[rs.getInt("category")];
                String categoryStr = category.toString();
                String titleStr = rs.getString("title");
                String dateStr = dateFormat.format(rs.getDate("date"));
                String additional_info = rs.getString("additional_info");
                NewsItem newsItem = new NewsItem(categoryStr, titleStr, dateStr, additional_info);
                news.add(newsItem);
            }
            connectionPool.closeConnection(con,ps,rs);
        }
        catch (Exception e)
        {
            throw new DAOException("News can not be found", e);
        }
        return news;
    }
}





