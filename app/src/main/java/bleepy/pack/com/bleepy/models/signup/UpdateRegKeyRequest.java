package bleepy.pack.com.bleepy.models.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/10/2018.
 */
public class UpdateRegKeyRequest {
    @SerializedName("servername")
    @Expose
    private String servername;
    @SerializedName("user_reg_key")
    @Expose
    private String registrationkey;
    @SerializedName("user_reg_type")
    @Expose
    private String userRegType;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public String getRegistrationkey() {
        return registrationkey;
    }

    public void setRegistrationkey(String registrationkey) {
        this.registrationkey = registrationkey;
    }

    public String getUserRegType() {
        return userRegType;
    }

    public void setUserRegType(String userRegType) {
        this.userRegType = userRegType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
