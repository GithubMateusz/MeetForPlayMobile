package source.meetforplaymobile.Models;

import com.google.gson.annotations.Expose;

public class Status {
    @Expose
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
