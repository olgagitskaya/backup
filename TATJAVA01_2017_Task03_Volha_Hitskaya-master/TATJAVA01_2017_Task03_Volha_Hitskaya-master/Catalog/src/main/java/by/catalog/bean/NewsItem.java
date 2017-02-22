package by.catalog.bean;

/**
 * Created by Volha_Hitskaya on 1/30/2017.
 */
public class NewsItem {
    private String category;
    private String title;
    private String date;
    private String additionalinfo;

    public NewsItem(String category, String title, String date, String additionalinfo) {
        this.category = category;
        this.title = title;
        this.date = date;
        this.additionalinfo = additionalinfo;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getDate(){ return this.date; }

    public String getCategory() {return this.category;}

    public String getAdditionalinfo() {return this.additionalinfo;}

    public String toString()
    {
        return this.category + " " +this.title+ " " +this.date+ " " +this.additionalinfo;
    }


}
