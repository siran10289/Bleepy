package bleepy.pack.com.bleepy.models.emegencycalllog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/25/2018.
 */
public class EmergencyCalllogRequest {
    @SerializedName("code_type")
    @Expose
    private String codeType;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("flag")
    @Expose
    private String flag;

    @SerializedName("code_id")
    @Expose
    private String codeId;



    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
}
