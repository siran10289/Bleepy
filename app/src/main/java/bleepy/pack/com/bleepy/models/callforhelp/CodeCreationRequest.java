package bleepy.pack.com.bleepy.models.callforhelp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/21/2018.
 */
public class CodeCreationRequest {
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("code_voice_path")
    @Expose
    private String codeVoicePath;
    @SerializedName("locationID")
    @Expose
    private String locationID;
    @SerializedName("teamID")
    @Expose
    private String teamID;
    @SerializedName("code_created_on_date")
    @Expose
    private String codeCreatedOnDate;
    @SerializedName("code_created_on_time")
    @Expose
    private String codeCreatedOnTime;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeVoicePath() {
        return codeVoicePath;
    }

    public void setCodeVoicePath(String codeVoicePath) {
        this.codeVoicePath = codeVoicePath;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getCodeCreatedOnDate() {
        return codeCreatedOnDate;
    }

    public void setCodeCreatedOnDate(String codeCreatedOnDate) {
        this.codeCreatedOnDate = codeCreatedOnDate;
    }

    public String getCodeCreatedOnTime() {
        return codeCreatedOnTime;
    }

    public void setCodeCreatedOnTime(String codeCreatedOnTime) {
        this.codeCreatedOnTime = codeCreatedOnTime;
    }

}
