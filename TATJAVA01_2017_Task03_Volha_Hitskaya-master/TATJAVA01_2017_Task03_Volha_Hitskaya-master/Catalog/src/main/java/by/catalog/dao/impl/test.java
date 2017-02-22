package by.catalog.dao.impl;

import java.sql.*;

/**
 * Created by user on 08.02.2017.
 */
public class test
{
    public static ResultSet getDBData(String query)
    {
        Statement statement;
        Connection connect;
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection("jdbc:mysql://localhost/phonebook?user=root&password=qwerty11&useUnicode=true&characterEncoding=UTF-8&characterSetResults=utf8&connectionCollation=utf8_general_ci");
            statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;
        }
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }

        System.out.println("null on getDBData()!");
        return null;

    }
}
