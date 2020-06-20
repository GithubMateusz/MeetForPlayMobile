package source.meetforplaymobile.Models;

import com.google.gson.annotations.Expose;

public class EventBasicInfo {
    @Expose
    private String eventName;

    @Expose
    private String eventDate;

    @Expose
    private String objectName;

    @Expose
    private String categoryName;

    @Expose
    private String icon;

    @Expose
    private int maxPerson;

    @Expose
    private int takenSpots;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }

    public int getTakenSpots() {
        return takenSpots;
    }

    public void setTakenSpots(int takenSpots) {
        this.takenSpots = takenSpots;
    }
}
