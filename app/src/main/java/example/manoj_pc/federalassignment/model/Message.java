package example.manoj_pc.federalassignment.model;

/**
 * Created by MANOJ-PC on 4/3/2018.
 */

public class Message {
    private String token;

    private Notification notification;

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public Notification getNotification ()
    {
        return notification;
    }

    public void setNotification (Notification notification)
    {
        this.notification = notification;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [token = "+token+", notification = "+notification+"]";
    }
}

