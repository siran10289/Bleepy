
package bleepy.pack.com.bleepy.models.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetTeamMembersRequest {

    @SerializedName("uid")
    @Expose
    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
