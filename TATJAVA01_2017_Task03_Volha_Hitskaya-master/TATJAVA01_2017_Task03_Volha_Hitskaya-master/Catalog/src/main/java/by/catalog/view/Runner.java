package by.catalog.view;

import by.catalog.controller.Controller;
import by.catalog.dao.impl.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Volha_Hitskaya on 1/30/2017.
 */
public class Runner {
    public static void main(String[] args) {
//        ResultSet db_data = test.getDBData("SELECT * FROM `phone`");
//        try{
//        while (db_data.next())
//        {
//            System.out.println(db_data.getString("number"));
//        }
//        }catch(SQLException e){
//
//        }
        System.out.println(" Use commands:\n" +
                " - add category title additional_information - to add a news item to the Catalog \n" +
                "  -- for the category write film or book or disk \n" +
                "  -- use low line _ to write titles that consist of more than one word \n" +
                " - search [title=\"title\"] [date=\"date\"] - to search news items by title or by date or both by title and date \n" +
                "  -- for the date format use yyyy/mm/dd, any part of date can be used for search\n" +
                " - exit - to exit the application");
        Controller controller = new Controller();
        try (Scanner sc = new Scanner(System.in))
        {
            while (true)
            {
                String command = sc.nextLine();
                if(command.toLowerCase().equals("exit"))
                {
                    return;
                }
                String commandResult = controller.executeCommand(command);
                System.out.println(commandResult);
            }
        }
    }
}
