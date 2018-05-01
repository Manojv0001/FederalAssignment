package example.manoj_pc.federalassignment.model;

/**
 * Created by MANOJ-PC on 4/3/2018.
 */

public class MyPojo{
    private Message message;

    public Message getMessage ()
    {
        return message;
    }

    public void setMessage (Message message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+"]";
    }

}
