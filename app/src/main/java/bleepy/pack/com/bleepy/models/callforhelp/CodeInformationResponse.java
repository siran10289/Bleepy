package bleepy.pack.com.bleepy.models.callforhelp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/24/2018.
 */
public class CodeInformationResponse {
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

    public class Data {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("code_status")
        @Expose
        private String codeStatus;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("code_voice_path")
        @Expose
        private String codeVoicePath;
        @SerializedName("locationID")
        @Expose
        private String locationID;
        @SerializedName("locationname")
        @Expose
        private String locationname;
        @SerializedName("teamID")
        @Expose
        private String teamID;
        @SerializedName("code_created_on_date")
        @Expose
        private String codeCreatedOnDate;
        @SerializedName("code_created_on_time")
        @Expose
        private String codeCreatedOnTime;
        @SerializedName("code_respond_count")
        @Expose
        private Integer codeRespondCount;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeStatus() {
            return codeStatus;
        }

        public void setCodeStatus(String codeStatus) {
            this.codeStatus = codeStatus;
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

        public String getLocationname() {
            return locationname;
        }

        public void setLocationname(String locationname) {
            this.locationname = locationname;
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

        public Integer getCodeRespondCount() {
            return codeRespondCount;
        }

        public void setCodeRespondCount(Integer codeRespondCount) {
            this.codeRespondCount = codeRespondCount;
        }

    }
}
