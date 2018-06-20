package bleepy.pack.com.bleepy.models.callforhelp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/21/2018.
 */
public class CodeCreationResponse {
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

        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("code_voice_path")
        @Expose
        private String codeVoicePath;
        @SerializedName("teamid")
        @Expose
        private String teamid;
        @SerializedName("locationid")
        @Expose
        private String locationid;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("code_status")
        @Expose
        private String codeStatus;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeVoicePath() {
            return codeVoicePath;
        }

        public void setCodeVoicePath(String codeVoicePath) {
            this.codeVoicePath = codeVoicePath;
        }

        public String getTeamid() {
            return teamid;
        }

        public void setTeamid(String teamid) {
            this.teamid = teamid;
        }

        public String getLocationid() {
            return locationid;
        }

        public void setLocationid(String locationid) {
            this.locationid = locationid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCodeStatus() {
            return codeStatus;
        }

        public void setCodeStatus(String codeStatus) {
            this.codeStatus = codeStatus;
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
