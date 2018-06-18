package bleepy.pack.com.bleepy.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import bleepy.pack.com.bleepy.apiservice.Api;
import bleepy.pack.com.bleepy.apiservice.RequestHeaderInterceptor;
import bleepy.pack.com.bleepy.di.scope.GsonRestAdapter;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static bleepy.pack.com.bleepy.utils.Constants.getDomainName;


@Module
public class NetworkModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Context context, PrefsManager prefsManager) {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new RequestHeaderInterceptor(prefsManager));
        setLoggingInterceptor(clientBuilder);
       // clientBuilder.connectionSpecs(Collections.singletonList(spec));
        clientBuilder.cache(getHttpCache(context));
        setTimeouts(clientBuilder);
        return clientBuilder.build();
    }

    private void setTimeouts(OkHttpClient.Builder clientBuilder) {
        clientBuilder.connectTimeout(60 * 2000, TimeUnit.MILLISECONDS);
        clientBuilder.readTimeout(60 * 2000, TimeUnit.MILLISECONDS);
    }

    private void setLoggingInterceptor(OkHttpClient.Builder clientBuilder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.interceptors().add(logging);
       // clientBuilder.addInterceptor(logging);
    }


    @NonNull
    private Cache getHttpCache(Context context) {
        final File httpCacheDir = new File(context.getCacheDir(), "http");
        final long httpCacheSize = 20 * 1024 * 1024; // 10 MiB
        return new Cache(httpCacheDir, httpCacheSize);
    }

   /* @Provides
    @Named("baseUrl")
    public String provideCustomBaseUrl() {
        return BuildConfig.BASE_URL;
    }*/

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(getDomainName())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @GsonRestAdapter
    public Retrofit provideGsonRestAdapter(OkHttpClient okHttpClient) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
        .baseUrl(getDomainName())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @GsonRestAdapter
    Api provideGsonApi(@GsonRestAdapter Retrofit retrofit) {
        return retrofit.create(Api.class);
    }


    @Provides
    @Singleton
    Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

}
