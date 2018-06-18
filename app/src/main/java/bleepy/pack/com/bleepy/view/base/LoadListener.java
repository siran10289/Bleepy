package bleepy.pack.com.bleepy.view.base;

/**
 * Created by siranjeevi on 9/6/17.
 */
public interface LoadListener<T> {
    void onSuccess(T responseBody);

    void onFailure(Throwable t);

    void onError(Object error);


}
