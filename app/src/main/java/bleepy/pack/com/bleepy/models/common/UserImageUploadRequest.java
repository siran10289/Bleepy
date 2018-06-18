package bleepy.pack.com.bleepy.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/9/2018.
 */
public class UserImageUploadRequest {
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
