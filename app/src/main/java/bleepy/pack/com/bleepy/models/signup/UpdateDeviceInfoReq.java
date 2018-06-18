package bleepy.pack.com.bleepy.models.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/9/2018.
 */
public class UpdateDeviceInfoReq {
    @SerializedName("devicetype")
    @Expose
    private String devicetype;
    @SerializedName("appid")
    @Expose
    private String appid;

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getFCMToken() {
        return appid;
    }

    public void setFCMToken(String token) {
        this.appid = token;
    }
}
