package bleepy.pack.com.bleepy.models.signin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/10/2018.
 */
public class ForgetCredentialsRequest {
    @SerializedName("emailid")
    @Expose
    private String emailid;

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }
}
