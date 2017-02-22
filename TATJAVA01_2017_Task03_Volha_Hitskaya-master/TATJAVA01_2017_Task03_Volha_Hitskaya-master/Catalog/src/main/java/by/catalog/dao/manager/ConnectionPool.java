package by.catalog.dao.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;



/**
 * Created by user on 12.02.2017.
 */
public final class ConnectionPool
{
    private static ConnectionPool instance = new ConnectionPool();
    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private ConnectionPool()
    {
        try{
            DBResourceManager dbResourseManager = DBResourceManager.getInstance();
            this.driverName = dbResourseManager.getValue(DBParameter.DB_DRIVER);
            this.url = dbResourseManager.getValue(DBParameter.DB_URL);
            this.user = dbResourseManager.getValue(DBParameter.DB_USER);
            this.password = dbResourseManager.getValue(DBParameter.DB_PASSWORD);
            this.poolSize = Integer.parseInt(dbResourseManager.getValue(DBParameter.DB_POLL_SIZE));
        }
        catch (NumberFormatException e)
        {
            poolSize = 5;
        }
    }
    public static ConnectionPool getInstance()
    {
        return instance;
    }
    public void initPoolData() throws ConnectionPoolException
    {
        Locale.setDefault(Locale.ENGLISH);
        try
        {
            Class.forName(driverName);
            givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
            connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
            for (int i = 0; i < poolSize; i++)
            {
                Connection connection = DriverManager.getConnection(url,
                        user,
                        password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            }
        }
        catch (SQLException e)
        {
            throw new ConnectionPoolException("SQLException in ConnectionPool", e);
        }
        catch (ClassNotFoundException e)
        {
            throw new ConnectionPoolException(
                    "Can't find database driver class", e);
        }
    }

    public void dispose()
    {
        clearConnectionQueue();
    }

    private void clearConnectionQueue()
    {
        try
        {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
        }
        catch (SQLException e)
        {
// logger.log(Level.ERROR, "Error closing the connection.", e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException
    {
        Connection connection = null;
        try
        {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        }
        catch (InterruptedException e)
        {
            throw new ConnectionPoolException(
                    "Error connecting to the data source.", e);
        }
        return connection;
    }

    public void closeConnection(Connection con, Statement st, ResultSet rs)throws SQLException
    {
        try
        {
            con.close();
        }
        catch (SQLException e)
        {
// logger.log(Level.ERROR, "Connection isn't return to the pool. ");
            if (!givenAwayConQueue.remove(con))
            {
                throw new SQLException("Error deleting connection from the given away connections pool.");
            }
            if (!connectionQueue.offer(con))
            {
                throw new SQLException("Error allocating connection in the pool.");
            }
        }
        try
        {
            rs.close();
        }
        catch (SQLException e)
        {
// logger.log(Level.ERROR, "ResultSet isn't closed.");
        }
        try
        {
            st.close();
        }
        catch (SQLException e)
        {
// logger.log(Level.ERROR, "Statement isn't closed.");
        }
    }

    public void closeConnection(Connection con, Statement st) throws SQLException
    {
        try
        {
            con.close();
        }
        catch (SQLException e)
        {
// logger.log(Level.ERROR, "Connection isn't return to the pool. ");
            if (!givenAwayConQueue.remove(con))
            {
                throw new SQLException("Error deleting connection from the given away connections pool.");
            }
            if (!connectionQueue.offer(con))
            {
                throw new SQLException("Error allocating connection in the pool.");
            }
        }
        try
        {
            st.close();
        }
        catch (SQLException e)
        {
// logger.log(Level.ERROR, "Statement isn't closed.");
        }
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue)
            throws SQLException
    {
        Connection connection;
        while ((connection = queue.poll()) != null)
        {
            if (!connection.getAutoCommit())
            {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }
}
