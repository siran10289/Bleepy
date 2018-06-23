package bleepy.pack.com.bleepy.models.callforhelp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/23/2018.
 */
public class EmergencyAlertResponse {
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("data")
    @Expose
    private Data data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("code_respond_time")
        @Expose
        private String codeRespondTime;
        @SerializedName("user_status")
        @Expose
        private String userStatus;
        @SerializedName("rejected_reason")
        @Expose
        private String rejectedReason;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCodeRespondTime() {
            return codeRespondTime;
        }

        public void setCodeRespondTime(String codeRespondTime) {
            this.codeRespondTime = codeRespondTime;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getRejectedReason() {
            return rejectedReason;
        }

        public void setRejectedReason(String rejectedReason) {
            this.rejectedReason = rejectedReason;
        }

    }
    public class Meta {

        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("status_type")
        @Expose
        private Integer statusType;
        @SerializedName("message")
        @Expose
        private String message;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getStatusType() {
            return statusType;
        }

        public void setStatusType(Integer statusType) {
            this.statusType = statusType;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
