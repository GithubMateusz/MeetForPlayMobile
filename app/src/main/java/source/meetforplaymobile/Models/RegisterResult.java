package source.meetforplaymobile.Models;

import com.google.gson.annotations.Expose;

public class RegisterResult {

    @Expose
    private boolean status;

    @Expose
    private String info;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
