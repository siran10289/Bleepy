package bleepy.pack.com.bleepy.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/10/2018.
 */
public class WelcomeScreenRequest {
    @SerializedName("locationID")
    @Expose
    private String locationID;
    @SerializedName("appid")
    @Expose
    private String appid;

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
