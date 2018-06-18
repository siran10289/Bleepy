package bleepy.pack.com.bleepy.models.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/16/2018.
 */
public class ChangePasswordRequest {
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("cupassword")
    @Expose
    private String cupassword;
    @SerializedName("newpassword")
    @Expose
    private String newpassword;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getCupassword() {
        return cupassword;
    }

    public void setCupassword(String cupassword) {
        this.cupassword = cupassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
