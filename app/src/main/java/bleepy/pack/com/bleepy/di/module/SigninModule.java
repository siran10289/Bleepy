package bleepy.pack.com.bleepy.di.module;

import android.app.Activity;
import android.content.Context;


import bleepy.pack.com.bleepy.di.scope.PerActivity;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.signin.SigninContract;
import bleepy.pack.com.bleepy.view.signin.SigninPresenterImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by siranjeevi on 9/6/17.
 */
@Module
@PerActivity
public class SigninModule extends ActivityModule {
    Activity activity;

    public SigninModule(Activity activity) {
        super(activity);
        this.activity=activity;
    }

    @Provides
    @PerActivity
    public SigninContract.Presenter signInPresenter(Context context, ApiInteractor driverInteractor, PrefsManager prefsManager) {
        return new SigninPresenterImpl(context,driverInteractor,prefsManager);
    }
}