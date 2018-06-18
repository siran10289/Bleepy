package bleepy.pack.com.bleepy.view.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by siranjeevi on 09/07/16.
 */
public class ApiError {
    @SerializedName("status_info")
    @Expose
    private String statusInfo;

    @SerializedName("status_code")
    private String statusCode;

    /**
     *
     * @return
     * The statusInfo
     */
    public String getStatusInfo() {
        return statusInfo;
    }

    /**
     *
     * @param statusInfo
     * The status_info
     */
    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }


    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
