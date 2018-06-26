package bleepy.pack.com.bleepy.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/24/2018.
 */
public class UpdateFCMTokenResponse {
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

        @SerializedName("deviceid")
        @Expose
        private String deviceid;
        @SerializedName("appid")
        @Expose
        private String appid;
        @SerializedName("device_type")
        @Expose
        private String deviceType;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("staffID")
        @Expose
        private String staffID;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("bleepyID")
        @Expose
        private String bleepyID;
        @SerializedName("teamID")
        @Expose
        private String teamID;

        public String getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
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

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStaffID() {
            return staffID;
        }

        public void setStaffID(String staffID) {
            this.staffID = staffID;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getBleepyID() {
            return bleepyID;
        }

        public void setBleepyID(String bleepyID) {
            this.bleepyID = bleepyID;
        }

        public String getTeamID() {
            return teamID;
        }

        public void setTeamID(String teamID) {
            this.teamID = teamID;
        }

    }
}
