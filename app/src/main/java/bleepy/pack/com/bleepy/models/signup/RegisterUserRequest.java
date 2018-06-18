package bleepy.pack.com.bleepy.models.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/9/2018.
 */
public class RegisterUserRequest {
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("emailid")
    @Expose
    private String emailid;
    @SerializedName("bleepId")
    @Expose
    private String bleepId;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("staffID")
    @Expose
    private String staffId;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("Rtype")
    @Expose
    private String rtype;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("user_profile_pic")
    @Expose
    private String userProfilePic;
    @SerializedName("appid")
    @Expose
    private String fcmToken;






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

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getBleepId() {
        return bleepId;
    }

    public void setBleepId(String bleepId) {
        this.bleepId = bleepId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
