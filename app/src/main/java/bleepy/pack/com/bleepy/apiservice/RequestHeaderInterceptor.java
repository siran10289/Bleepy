package bleepy.pack.com.bleepy.apiservice;



import java.io.IOException;

import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by siran on 6/6/17.
 */
public class RequestHeaderInterceptor implements Interceptor {

    private PrefsManager prefsManager;

    public RequestHeaderInterceptor(PrefsManager prefsManager) {
        this.prefsManager = prefsManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        String accessToken = prefsManager.getKeyValueFromPrefsByKey("Access token");

        Request original = chain.request();
        String url = original.url().url().toString();

        Request.Builder builder = original.newBuilder().header("Accept-Language", "en-US");
        builder.addHeader("Content-type", "application/json");


        Request request = builder.method(original.method(), original.body())
                        .build();
        return chain.proceed(request);
    }


}
