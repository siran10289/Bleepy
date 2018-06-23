package bleepy.pack.com.bleepy.di.module;

import android.app.Activity;
import android.content.Context;

import bleepy.pack.com.bleepy.di.scope.PerActivity;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardContract;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardPresenterImpl;
import bleepy.pack.com.bleepy.view.base.BaseContract;
import bleepy.pack.com.bleepy.view.base.BasePresenterImpl;
import bleepy.pack.com.bleepy.view.signup.PackageInfoInteractor;
import dagger.Module;
import dagger.Provides;

/**
 * Created by siranjeevi on 9/6/17.
 */
@Module
@PerActivity
public class BaseModule extends ActivityModule {
    Activity activity;

    public BaseModule(Activity activity) {
        super(activity);
        this.activity=activity;
    }


}