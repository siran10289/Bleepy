package bleepy.pack.com.bleepy.models.callforhelp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/24/2018.
 */
public class CodeConfirmationRequest {
    @SerializedName("code_status")
    @Expose
    private String codeStatus;
    @SerializedName("code_id")
    @Expose
    private String codeId;
    @SerializedName("time_to_attend")
    @Expose
    private String timeToAttend;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(String codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getTimeToAttend() {
        return timeToAttend;
    }

    public void setTimeToAttend(String timeToAttend) {
        this.timeToAttend = timeToAttend;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
