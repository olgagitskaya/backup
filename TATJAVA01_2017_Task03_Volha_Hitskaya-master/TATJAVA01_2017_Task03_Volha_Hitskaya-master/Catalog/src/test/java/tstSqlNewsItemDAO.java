import by.catalog.bean.NewsItem;
import by.catalog.dao.NewsItemDAO;
import by.catalog.dao.exception.DAOException;
import by.catalog.dao.factory.DAOFactory;
import by.catalog.dao.manager.ConnectionPool;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Created by user on 05.02.2017.
 */
public class tstSqlNewsItemDAO
{
    DAOFactory daoObjectFactory;
    NewsItemDAO newsItemDAO;

    @Before
    public void setUp() throws Exception
    {
        this.daoObjectFactory = DAOFactory.getInstance();
        this.newsItemDAO = daoObjectFactory.getNewsItemDAO();
    }

    @After
    public void tearDown() throws Exception
    {
        this.daoObjectFactory = null;
        this.newsItemDAO = null;
    }


    @AfterClass
    public static void clear() throws Exception {
        ConnectionPool.getInstance().dispose();
    }

    @Test
    //check is made using preloaded data (DataSource.xlm)
    public void getNewsItemsByTitlePositive() throws DAOException
    {
        ArrayList<NewsItem> result = null;
        //get full title
        result = this.newsItemDAO.getNewsItemsByTitleAndDate("Meet_Joe_Black", "");
        assertTrue("Incorrect number of news items", result.size()==2);
        assertTrue("Wrong title", result.get(0).getTitle().equals("Meet_Joe_Black"));
        assertTrue("Wrong title", result.get(1).getTitle().equals("Meet_Joe_Black"));

        //get partial title
        result = this.newsItemDAO.getNewsItemsByTitleAndDate("Pride", "");
        assertTrue("Incorrect number of news items", result.size()==1);
        assertTrue("Wrong title", result.get(0).getTitle().contains("Pride"));
    }

    @Test
    //check is made using preloaded data (DataSource.xlm)
    public void getNewsItemsByDatePositive() throws DAOException
    {
        ArrayList<NewsItem> result = null;
        //get full date
        result = this.newsItemDAO.getNewsItemsByTitleAndDate("", "1998/11/15");
        assertEquals("Incorrect number of news items", 1, result.size());
        assertTrue("Wrong date", result.get(0).getDate().equals("1998/11/15"));

        //get partial date
        result = this.newsItemDAO.getNewsItemsByTitleAndDate("", "1998");
        assertEquals("Incorrect number of news items",3, result.size());
        assertTrue("Wrong date", result.get(0).getDate().contains("1998"));
        assertTrue("Wrong date", result.get(1).getDate().contains("1998"));
        assertTrue("Wrong date", result.get(2).getDate().contains("1998"));
    }

    @Test
    //check is made using preloaded data (DataSource.xlm)
    public void getNewsItemsByTitleAndDatePositive() throws DAOException
    {
        ArrayList<NewsItem> result = null;
        //get partial title and date
        result = this.newsItemDAO.getNewsItemsByTitleAndDate("Meet", "11/14");
        assertTrue("Incorrect number of news items", result.size()==1);
        assertTrue("Wrong title", result.get(0).getTitle().contains("Meet"));
        assertTrue("Wrong date", result.get(0).getDate().contains("11/14"));
    }

    @Test
    public void getNewsItemsByTitleAndDateNegative() throws DAOException
    {
        ArrayList<NewsItem> result = null;
        result = this.newsItemDAO.getNewsItemsByTitleAndDate("gufhtkr", "");
        assertTrue("Item should not be found",result.size()==0);
        result = this.newsItemDAO.getNewsItemsByTitleAndDate("", "6889065");
        assertTrue("Item should not be found", result.size()==0);
        result = this.newsItemDAO.getNewsItemsByTitleAndDate("gufhtkr", "6889065");
        assertTrue("Item should not be found", result.size()==0);
    }

    @Test
    public void addNewsItemPositive() throws DAOException
    {
        String category = "film";
        String title = "Forrest_Gump";
        String date = "2017/02/05";
        String additionalInfo = "directed by Robert Zemeckis";
        NewsItem newsItem = new NewsItem(category,title,date,additionalInfo);
        boolean result = this.newsItemDAO.addNewsItem(newsItem);
        assertTrue(result);
        ArrayList<NewsItem> getItemResult = this.newsItemDAO.getNewsItemsByTitleAndDate("Forrest_Gump", "2017/02/05");
        assertTrue("Wrong title", getItemResult.get(0).getTitle().equals("Forrest_Gump"));
        assertTrue("Wrong date", getItemResult.get(0).getDate().equals("2017/02/05"));
    }

    @Test
    public void addNewsItemNegative() throws DAOException
    {
        NewsItem newsItem = new NewsItem("film","","2070/02/05","directed by Robert Zemeckis");
        boolean result = this.newsItemDAO.addNewsItem(newsItem);
        assertFalse(result);
        ArrayList<NewsItem> getItemResult = this.newsItemDAO.getNewsItemsByTitleAndDate("", "2070/02/05");
        assertTrue("Item should not be found", getItemResult.size() == 0);

        newsItem = new NewsItem("","Tugkho","2080/03/14","directed by Robert Zemeckis");
        result = this.newsItemDAO.addNewsItem(newsItem);
        assertFalse(result);
        getItemResult = this.newsItemDAO.getNewsItemsByTitleAndDate("Tugkho", "2080/03/14");
        assertTrue("Item should not be found", getItemResult.size() == 0);
    }

}
