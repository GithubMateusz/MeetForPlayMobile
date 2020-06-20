package source.meetforplaymobile.Models;

public class AddEvent {
    private String evetnName;
    private String objectName;
    private String eventCategoryName;
    private String minPersonToStart;
    private String maxPersonToStart;
    private int userId;
    private String latittude;
    private String longitude;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
