package source.meetforplaymobile.Models;

import com.google.gson.annotations.Expose;

public class User {

    @Expose
    private String email;

    @Expose
    private int userId;

    public int getId()
    {
        return userId;
    }

    public void setId(int userId)
    {
        this.userId = userId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
