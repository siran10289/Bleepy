package bleepy.pack.com.bleepy.di.module;

import android.app.Activity;
import android.content.Context;
import bleepy.pack.com.bleepy.di.scope.PerActivity;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.signup.PackageInfoInteractor;
import bleepy.pack.com.bleepy.view.signup.SignupContract;
import bleepy.pack.com.bleepy.view.signup.SignupPresenterImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by siranjeevi on 9/6/17.
 */
@Module
@PerActivity
public class SignupModule extends ActivityModule {
    Activity activity;

    public SignupModule(Activity activity) {
        super(activity);
        this.activity=activity;
    }

    @Provides
    @PerActivity
    public SignupContract.Presenter signupPresenter(Context context, ApiInteractor driverInteractor, PrefsManager prefsManager, PackageInfoInteractor mPackageInfoInteractor) {
        return new SignupPresenterImpl(context,driverInteractor,prefsManager,mPackageInfoInteractor);
    }
}