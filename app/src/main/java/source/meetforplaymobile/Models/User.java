package source.meetforplaymobile.Models;

import com.google.gson.annotations.Expose;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class User {

    @Expose
    private String email;

    @Expose
    private int userId;

    @Expose
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = hashPassword(password);
    }

    private static String byteArrayToString(byte[] bytes) {
        StringBuilder buffer = new StringBuilder();
        for (byte b : bytes) {
            buffer.append(String.format(Locale.getDefault(), "%02x", b));
        }
        return buffer.toString();
    }

    private static String hashPassword(String clearString)
    {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(clearString.getBytes("UTF-8"));
            return byteArrayToString(messageDigest.digest());
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return null;
        }
    }
}
