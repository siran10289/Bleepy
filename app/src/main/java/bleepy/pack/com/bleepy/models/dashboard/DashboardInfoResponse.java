package bleepy.pack.com.bleepy.models.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/13/2018.
 */
public class DashboardInfoResponse {
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

        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("emergency_attended")
        @Expose
        private String emergencyAttended;
        @SerializedName("warning_accepted")
        @Expose
        private String warningAccepted;
        @SerializedName("important_message")
        @Expose
        private String importantMessage;
        @SerializedName("designation")
        @Expose
        private String designation;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getEmergencyAttended() {
            return emergencyAttended;
        }

        public void setEmergencyAttended(String emergencyAttended) {
            this.emergencyAttended = emergencyAttended;
        }

        public String getWarningAccepted() {
            return warningAccepted;
        }

        public void setWarningAccepted(String warningAccepted) {
            this.warningAccepted = warningAccepted;
        }

        public String getImportantMessage() {
            return importantMessage;
        }

        public void setImportantMessage(String importantMessage) {
            this.importantMessage = importantMessage;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }
    }
}