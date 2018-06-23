package bleepy.pack.com.bleepy.models.common;

/**
 * Created by Siranjeevi Rengarajan on 6/22/2018.
 */
public class EmergencyCode {
    String codeID;
    String description;
    String codeCreatedDate;
    String voiceData;
    String responseCount;
    String location;

    public String getCodeID() {
        return codeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeCreatedDate() {
        return codeCreatedDate;
    }

    public void setCodeCreatedDate(String codeCreatedDate) {
        this.codeCreatedDate = codeCreatedDate;
    }

    public String getVoiceData() {
        return voiceData;
    }

    public void setVoiceData(String voiceData) {
        this.voiceData = voiceData;
    }

    public String getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(String responseCount) {
        this.responseCount = responseCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
