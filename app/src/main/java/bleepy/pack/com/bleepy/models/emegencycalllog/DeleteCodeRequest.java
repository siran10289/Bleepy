package bleepy.pack.com.bleepy.models.emegencycalllog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/26/2018.
 */
public class DeleteCodeRequest {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("code_id")
    @Expose
    private String codeId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

}
