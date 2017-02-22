package by.catalog.dao.factory;

import by.catalog.dao.NewsItemDAO;
import by.catalog.dao.exception.DAOException;
import by.catalog.dao.impl.SqlNewsItemDAO;

/**
 * Created by Volha_Hitskaya on 1/30/2017.
 */
public final class DAOFactory
{

    private static final DAOFactory instance = new DAOFactory();

    private final NewsItemDAO sqlNewsItemImpl;

    private DAOFactory()
    {
        NewsItemDAO sqlDAO = null;
        try
        {
            sqlDAO = new SqlNewsItemDAO();
        }
        catch (DAOException e)
        {
            sqlDAO = null;
        }
        finally
        {
            this.sqlNewsItemImpl = sqlDAO;
        }

    }

    public static DAOFactory getInstance()
    {

        return instance;
    }

    public NewsItemDAO getNewsItemDAO() throws DAOException
    {
        if (sqlNewsItemImpl == null)
        {
            throw new DAOException("DAO was not initialized");
        }
        return sqlNewsItemImpl;
    }
}
