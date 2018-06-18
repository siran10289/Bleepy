package bleepy.pack.com.bleepy.di.component;




import bleepy.pack.com.bleepy.di.module.ActivityModule;
import bleepy.pack.com.bleepy.di.module.SignupModule;
import bleepy.pack.com.bleepy.di.scope.PerActivity;
import bleepy.pack.com.bleepy.view.activation.PersonalDeviceActivity;
import bleepy.pack.com.bleepy.view.activation.RegistrationKeyActivity;
import bleepy.pack.com.bleepy.view.signup.RegistrationActivity;
import bleepy.pack.com.bleepy.view.signup.SignupContract;
import bleepy.pack.com.bleepy.view.welcome.SplashScreenActivity;
import dagger.Component;

/**
 * Created by siranjeevi on 9/6/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, SignupModule.class})
public interface SignupComponent {

    void inject(RegistrationActivity registrationActivity);
    void inject(PersonalDeviceActivity personalDeviceActivity);
    void inject(RegistrationKeyActivity registrationKeyActivity);
    void inject(SplashScreenActivity splashScreenActivity);
    SignupContract.Presenter signupPresenter();
}
