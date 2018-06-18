package bleepy.pack.com.bleepy.di.module;

import android.content.Context;


import javax.inject.Singleton;

import bleepy.pack.com.bleepy.apiservice.Api;
import bleepy.pack.com.bleepy.di.scope.GsonRestAdapter;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
import bleepy.pack.com.bleepy.interactor.ApiInteractorImpl;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.signup.PackageInfoInteractor;
import bleepy.pack.com.bleepy.view.signup.PackageInfoInteractorImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by siranjeevi on 7/6/18.
 */
@Module
public class PresentationModule {
    @Provides
    @Singleton
    public ApiInteractor driverInteractor(@GsonRestAdapter Api remoteService, PrefsManager prefsManager) {
        return new ApiInteractorImpl(remoteService, prefsManager);
    }
    @Provides
    @Singleton
    public PackageInfoInteractor mPackageInfoInteractor(Context context, PrefsManager prefsManager) {
        return new PackageInfoInteractorImpl(context, prefsManager);
    }

}
