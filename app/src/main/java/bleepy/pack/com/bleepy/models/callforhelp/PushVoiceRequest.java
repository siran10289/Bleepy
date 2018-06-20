package bleepy.pack.com.bleepy.models.callforhelp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Siranjeevi Rengarajan on 6/20/2018.
 */
public class PushVoiceRequest {

    @SerializedName("voice_record")
    @Expose
    private String voiceRecord;

    public String getVoiceRecord() {
        return voiceRecord;
    }

    public void setVoiceRecord(String voiceRecord) {
        this.voiceRecord = voiceRecord;
    }
}
