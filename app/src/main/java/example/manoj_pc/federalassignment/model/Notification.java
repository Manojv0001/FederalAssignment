package example.manoj_pc.federalassignment.model;

/**
 * Created by MANOJ-PC on 4/3/2018.
 */

public class Notification {
    private String body;

    private String title;

    public String getBody ()
    {
        return body;
    }

    public void setBody (String body)
    {
        this.body = body;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [body = "+body+", title = "+title+"]";
    }
}

