package bleepy.pack.com.bleepy.di.module;

import android.app.Activity;
import android.content.Context;

import bleepy.pack.com.bleepy.di.scope.PerActivity;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardContract;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardPresenterImpl;
import bleepy.pack.com.bleepy.view.signin.SigninContract;
import bleepy.pack.com.bleepy.view.signin.SigninPresenterImpl;
import bleepy.pack.com.bleepy.view.signup.PackageInfoInteractor;
import dagger.Module;
import dagger.Provides;

/**
 * Created by siranjeevi on 9/6/17.
 */
@Module
@PerActivity
public class DashboardModule extends ActivityModule {
    Activity activity;

    public DashboardModule(Activity activity) {
        super(activity);
        this.activity=activity;
    }

    @Provides
    @PerActivity
    public DashboardContract.Presenter dasBoardPresenter(Context context, ApiInteractor driverInteractor, PrefsManager prefsManager,PackageInfoInteractor mPackageInfoInteractor) {
        return new DashboardPresenterImpl(activity,context,driverInteractor,prefsManager,mPackageInfoInteractor);
    }
}