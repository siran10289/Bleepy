package bleepy.pack.com.bleepy.models.callforhelp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/23/2018.
 */
public class EmergencyAlertAcceptRequest {

    @SerializedName("user_status")
    @Expose
    private String userStatus;
    @SerializedName("code_id")
    @Expose
    private String codeId;
    @SerializedName("code_respond_time")
    @Expose
    private String codeRespondTime;
    @SerializedName("rejected_reason")
    @Expose
    private String rejectedReason;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeRespondTime() {
        return codeRespondTime;
    }

    public void setCodeRespondTime(String codeRespondTime) {
        this.codeRespondTime = codeRespondTime;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
