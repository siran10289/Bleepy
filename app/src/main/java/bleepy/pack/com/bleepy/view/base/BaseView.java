package bleepy.pack.com.bleepy.view.base;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;

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
    public void showErrorMessage(String message);
    public void showErrorMessage(String message, CoordinatorLayout coordinatorLayout);

    void setError(int resID, String message);

    void showNoNetworkDialog();

}
