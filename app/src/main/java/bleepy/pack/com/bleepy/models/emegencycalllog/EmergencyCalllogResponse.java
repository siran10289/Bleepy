package bleepy.pack.com.bleepy.models.emegencycalllog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Siranjeevi Rengarajan on 6/25/2018.
 */
public class EmergencyCalllogResponse {
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
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
    public class Datum {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("code_voice_path")
        @Expose
        private String codeVoicePath;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("locationid")
        @Expose
        private String locationid;
        @SerializedName("locationname")
        @Expose
        private Object locationname;
        @SerializedName("teamID")
        @Expose
        private String teamID;
        @SerializedName("teamname")
        @Expose
        private Object teamname;
        @SerializedName("code_created_on_date")
        @Expose
        private String codeCreatedOnDate;
        @SerializedName("code_created_on_time")
        @Expose
        private String codeCreatedOnTime;
        @SerializedName("code_status")
        @Expose
        private String codeStatus;
        @SerializedName("responders")
        @Expose
        private Integer responders;
        @SerializedName("code_accepted")
        @Expose
        private Integer codeAccepted;
        @SerializedName("code_rejected")
        @Expose
        private Integer codeRejected;
        @SerializedName("rejected_reason")
        @Expose
        private String rejectedReason;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getLocationid() {
            return locationid;
        }

        public void setLocationid(String locationid) {
            this.locationid = locationid;
        }

        public Object getLocationname() {
            return locationname;
        }

        public void setLocationname(Object locationname) {
            this.locationname = locationname;
        }

        public String getTeamID() {
            return teamID;
        }

        public void setTeamID(String teamID) {
            this.teamID = teamID;
        }

        public Object getTeamname() {
            return teamname;
        }

        public void setTeamname(Object teamname) {
            this.teamname = teamname;
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

        public String getCodeStatus() {
            return codeStatus;
        }

        public void setCodeStatus(String codeStatus) {
            this.codeStatus = codeStatus;
        }

        public Integer getResponders() {
            return responders;
        }

        public void setResponders(Integer responders) {
            this.responders = responders;
        }

        public Integer getCodeAccepted() {
            return codeAccepted;
        }

        public void setCodeAccepted(Integer codeAccepted) {
            this.codeAccepted = codeAccepted;
        }

        public Integer getCodeRejected() {
            return codeRejected;
        }

        public void setCodeRejected(Integer codeRejected) {
            this.codeRejected = codeRejected;
        }

        public String getRejectedReason() {
            return rejectedReason;
        }

        public void setRejectedReason(String rejectedReason) {
            this.rejectedReason = rejectedReason;
        }

    }
}
