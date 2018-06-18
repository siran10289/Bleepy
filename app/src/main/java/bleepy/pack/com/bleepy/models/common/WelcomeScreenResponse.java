package bleepy.pack.com.bleepy.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Siranjeevi Rengarajan on 6/10/2018.
 */
public class WelcomeScreenResponse {
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("data")
    @Expose
    private List<Screen> data = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Screen> getData() {
        return data;
    }

    public void setData(List<Screen> data) {
        this.data = data;
    }
    public class Screen {

        @SerializedName("locationid")
        @Expose
        private String locationid;
        @SerializedName("screen_label")
        @Expose
        private String screenLabel;
        @SerializedName("screen_description")
        @Expose
        private String screenDescription;
        @SerializedName("Imagepath")
        @Expose
        private String imagepath;

        public String getLocationid() {
            return locationid;
        }

        public void setLocationid(String locationid) {
            this.locationid = locationid;
        }

        public String getScreenLabel() {
            return screenLabel;
        }

        public void setScreenLabel(String screenLabel) {
            this.screenLabel = screenLabel;
        }

        public String getScreenDescription() {
            return screenDescription;
        }

        public void setScreenDescription(String screenDescription) {
            this.screenDescription = screenDescription;
        }

        public String getImagepath() {
            return imagepath;
        }

        public void setImagepath(String imagepath) {
            this.imagepath = imagepath;
        }

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

}
