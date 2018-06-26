package bleepy.pack.com.bleepy.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/24/2018.
 */
public class UpdateFCMTokenInfoRequest {

    @SerializedName("deviceid")
    @Expose
    private String deviceid;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("appid")
    @Expose
    private String appid;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("device_type")
    @Expose
    private String deviceType;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
