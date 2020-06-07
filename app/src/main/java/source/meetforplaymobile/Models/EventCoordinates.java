package source.meetforplaymobile.Models;

import com.google.gson.annotations.Expose;

public class EventCoordinates {
    @Expose
    int EventId ;
    @Expose
    double latitude;
    @Expose
    double longitude ;
   @Expose
    String eventName;
    @Expose
    String eventCategoryName;
    @Expose
    String icon;

    public EventCoordinates(int eventId, double latitude, double longitude, String eventName, String eventCategoryName, String icon) {
        EventId = eventId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.eventName = eventName;
        this.eventCategoryName = eventCategoryName;
        this.icon = icon;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventCategoryName() {
        return eventCategoryName;
    }

    public void setEventCategoryName(String eventCategoryName) {
        this.eventCategoryName = eventCategoryName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getEventId() {
        return EventId;
    }

    public void setEventId(int eventId) {
        EventId = eventId;
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
}
