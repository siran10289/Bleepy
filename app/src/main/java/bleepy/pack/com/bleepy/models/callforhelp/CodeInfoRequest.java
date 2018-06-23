package bleepy.pack.com.bleepy.models.callforhelp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/24/2018.
 */
public class CodeInfoRequest {
    @SerializedName("code_id")
    @Expose
    private String codeId;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
}
