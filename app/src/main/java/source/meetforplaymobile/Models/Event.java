package source.meetforplaymobile.Models;

import com.google.gson.annotations.Expose;

public class Event {


    @Expose
    private int status;

    private String evetnName;
    private  String objectName;
    private String eventCategoryName;
    private String minPersonToStart;
    private String maxPersonToStart;
    private String userId;
    private  String latittude ;
    private  String longitude ;
    private String eventDate;

    public String getLatittude() {
        return latittude;
    }

    public void setLatittude(String latittude) {
        this.latittude = latittude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventCategoryName() {
        return eventCategoryName;
    }

    public void setEventCategoryName(String eventCategoryName) {
        this.eventCategoryName = eventCategoryName;
    }

    public Event(String evetnName, String objectName, String minPersonToStar, String maxPersonToStar, String userId , String eventCategoryName,   String latittude,String longitude
    ,String eventDate) {
        this.evetnName = evetnName;
        this.objectName = objectName;
        this.minPersonToStart = minPersonToStar;
        this.maxPersonToStart = maxPersonToStar;
        this.userId = userId;
        this.eventCategoryName = eventCategoryName;
        this.latittude = latittude;
        this.longitude = longitude;
        this.eventDate = eventDate;

    }

    public Event() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEvetnName() {
        return evetnName;
    }

    public void setEvetnName(String evetnName) {
        this.evetnName = evetnName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getMinPersonToStart() {
        return minPersonToStart;
    }

    public void setMinPersonToStart(String minPersonToStart) {
        this.minPersonToStart = minPersonToStart;
    }

    public String getMaxPersonToStart() {
        return maxPersonToStart;
    }

    public void setMaxPersonToStart(String maxPersonToStart) {
        this.maxPersonToStart = maxPersonToStart;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
