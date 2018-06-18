package bleepy.pack.com.bleepy.view.base;

import android.content.Intent;

/**
 * Created by siranjeevi on 9/6/17.
 */
public interface BasePresenter {
    void setView(BaseView view);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
