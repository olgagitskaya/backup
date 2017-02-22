package by.catalog.dao.manager;

/**
 * Created by user on 12.02.2017.
 */
public class ConnectionPoolException extends Exception
{
    private static final long serialVersionUID = 1L;
    public ConnectionPoolException(String message, Exception e){
        super(message, e);
    }
}