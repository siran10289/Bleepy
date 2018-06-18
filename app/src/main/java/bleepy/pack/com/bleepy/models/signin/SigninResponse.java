package bleepy.pack.com.bleepy.models.signin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Siranjeevi Rengarajan on 6/8/2018.
 */
public class SigninResponse {
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

    public class Datum {

        @SerializedName("screen_label")
        @Expose
        private String screenLabel;
        @SerializedName("screen_description")
        @Expose
        private String screenDescription;
        @SerializedName("Imagepath")
        @Expose
        private String imagepath;

        public String getScreenLabel() {
            return screenLabel;
        }

        public void setScreenLabel(String screenLabel) {
            this.screenLabel = screenLabel;
        }

        public String getScreenDescription() {
            return screenDescription;
        }

        public void setScreenDescription(String screenDescription) {
            this.screenDescription = screenDescription;
        }

        public String getImagepath() {
            return imagepath;
        }

        public void setImagepath(String imagepath) {
            this.imagepath = imagepath;
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
    public class Data {

        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email ")
        @Expose
        private String email;
        @SerializedName("appid")
        @Expose
        private String appid;
        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("groupid")
        @Expose
        private String groupid;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("user_profile_pic")
        @Expose
        private String userProfilePic;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("staffID")
        @Expose
        private String staffID;
        @SerializedName("bleepyID")
        @Expose
        private String bleepyID;
        @SerializedName("device_type")
        @Expose
        private String deviceType;
        @SerializedName("teamID")
        @Expose
        private String teamID;
        @SerializedName("user_reg_key")
        @Expose
        private String userRegKey;
        @SerializedName("emergency_attended")
        @Expose
        private String emergencyAttended;
        @SerializedName("warning_accepted")
        @Expose
        private String warningAccepted;
        @SerializedName("important_message")
        @Expose
        private String importantMessage;
        @SerializedName("user_status")
        @Expose
        private int user_status;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getUserProfilePic() {
            return userProfilePic;
        }

        public void setUserProfilePic(String userProfilePic) {
            this.userProfilePic = userProfilePic;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getStaffID() {
            return staffID;
        }

        public void setStaffID(String staffID) {
            this.staffID = staffID;
        }

        public String getBleepyID() {
            return bleepyID;
        }

        public void setBleepyID(String bleepyID) {
            this.bleepyID = bleepyID;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getTeamID() {
            return teamID;
        }

        public void setTeamID(String teamID) {
            this.teamID = teamID;
        }

        public String getUserRegKey() {
            return userRegKey;
        }

        public void setUserRegKey(String userRegKey) {
            this.userRegKey = userRegKey;
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

        public int getUserStatus() {
            return user_status;
        }

        public void setUserStatus(int status_type) {
            this.user_status = status_type;
        }
    }
}
