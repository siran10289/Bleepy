package bleepy.pack.com.bleepy.models.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/13/2018.
 */
public class DashboardInfoRequest {
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("teamID")
    @Expose
    private String teamID;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }
}
