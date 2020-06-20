package source.meetforplaymobile.Models;

import com.google.gson.annotations.Expose;

public class EventAllInfo {
    @Expose
    private String eventName;

    @Expose
    private String eventDate;

    @Expose
    private String objectName;

    @Expose
    private String eventCategoryName;

    @Expose
    private double latitude;

    @Expose
    private double longitude ;

    @Expose
    private String icon;

    @Expose
    private int maxPerson;

    @Expose
    private int takenSpots;

    @Expose
    private int takesPart;

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

    public String getEventCategoryName() {
        return eventCategoryName;
    }

    public void setEventCategoryName(String eventCategoryName) {
        this.eventCategoryName = eventCategoryName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public int getTakesPart() {
        return takesPart;
    }

    public void setTakesPart(int takesPart) {
        this.takesPart = takesPart;
    }
}
