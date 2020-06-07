package source.meetforplaymobile.Models;

import com.google.gson.annotations.Expose;

public class RegisterResult {

    @Expose
    private int status;

    @Expose
    private String info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
