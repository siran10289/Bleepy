package bleepy.pack.com.bleepy.view.base;


import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import bleepy.pack.com.bleepy.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;


import static bleepy.pack.com.bleepy.utils.AppUtils.hasInternetConnection;
import static bleepy.pack.com.bleepy.utils.Constants.getDomainName;
import static java.net.HttpURLConnection.HTTP_OK;

public class UiCallback<T> implements Callback<T> {
    private Response<T> response;
    private T responseBody;
    private BaseView baseView;
    private Call<T> requestCall;
    private LoadListener<T> loadListener;
    private Context mContext;

    private boolean withProgress = true;

    public UiCallback(BaseView baseView, LoadListener<T> loadListener, boolean state) {
        this.baseView = baseView;
        this.loadListener = loadListener;
        this.withProgress = state;
        this.mContext= (Context) baseView;

    }
    public UiCallback(Activity activity,BaseView baseView, LoadListener<T> loadListener, boolean state) {
        this.baseView = baseView;
        this.loadListener = loadListener;
        this.withProgress = state;
        this.mContext= (Context) activity;

    }

    public void start(Call<T> call) {

        if(hasInternetConnection(mContext)) {
            requestCall = call;
            showProgress();
            requestCall.enqueue(this);
        }else{
            hideProgress();
            baseView.showNoNetworkDialog();
        }
    }

    private void showProgress() {
        if (baseView != null && withProgress) {
            baseView.showProgress();
        }
    }

    private void hideProgress() {
        if (baseView != null && withProgress) {
            baseView.hideProgress();
        }
    }

    private boolean isResponseCodeOk(Response<T> response) {
        return HTTP_OK == response.code() || HttpURLConnection.HTTP_CREATED == response.code();
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        try {
            Log.e("onResponse: ", "" + requestCall.request().url().toString() + " " + response.message() + " code " + response.code());

        if (loadListener != null) {
            if (response.isSuccessful()) {
                this.response = response;
                responseBody = response.body();
                Log.e("ResponseBody:",response.message().toString()+"-"+new Gson().toJson(responseBody).toString());
                hideProgress();
                loadListener.onSuccess(responseBody);
            } else {

                    Object error = null;
                    if (requestCall.request().url().toString().contains(getDomainName()))
                        error = GsonConverterFactory.create().responseBodyConverter(ApiError.class, ApiError.class.getAnnotations(), null).convert(response.errorBody());
                    else
                        error = GsonConverterFactory.create().responseBodyConverter(Object.class, Object.class.getAnnotations(), null).convert(response.errorBody());

                    loadListener.onError(error);

                if (baseView != null && withProgress) {
                    baseView.hideProgress();
                    baseView.showLoadErrorDialog();
                }
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception:",e.toString());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
       /* Log.i("onFailure", "onFailure: " + requestCall.request().url().toString());
        Log.i("onFailure", "onFailure: " + " " + t.getLocalizedMessage());
        Log.e("Error:", t.getLocalizedMessage());
        t.printStackTrace();*/
        Log.e("Error:", t.getLocalizedMessage());

        if (loadListener != null) {
            if (baseView == null) {
                loadListener.onFailure(t);
            } else if (baseView.isActivityActive()) {
                loadListener.onFailure(t);
                if (t instanceof TimeoutException || t instanceof SocketTimeoutException)
                    baseView.showErrorMessage(mContext.getString(R.string.timeout_error));
                else if (t instanceof UnknownHostException)
                    baseView.showErrorMessage(mContext.getString(R.string.no_network_connection_error));
                else if(t instanceof NoRouteToHostException || t instanceof UnknownHostException)
                    baseView.showErrorDialog(mContext.getString(R.string.server_error));
                else
                    baseView.showErrorDialog(mContext.getString(R.string.generic_error));
                if (withProgress) {
                    baseView.hideProgress();
                }
            }
        }
    }

}