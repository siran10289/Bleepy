package bleepy.pack.com.bleepy.view.base;

import android.content.Intent;

/**
 * Created by siranjeevi on 9/6/17.
 */
public interface BaseView {
    void showLoadErrorDialog();

    boolean isActivityActive();

    void hideProgress();

    void showProgress();

    void openActivityForResult(Intent intent, int resultCode);

    void setResultAndCloseActivity(Intent extraIntent);

    void showErrorDialog(String errorMsg);

    void showErrorDialog(int resID);

    void showListenerDialog(String errorMsg);

    void showErrorMessage(String message);

    void setError(int resID, String message);

    void showNoNetworkDialog();

}
