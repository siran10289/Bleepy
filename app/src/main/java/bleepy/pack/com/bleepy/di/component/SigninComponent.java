package bleepy.pack.com.bleepy.di.component;



import bleepy.pack.com.bleepy.di.module.ActivityModule;
import bleepy.pack.com.bleepy.di.module.SigninModule;
import bleepy.pack.com.bleepy.di.scope.PerActivity;
import bleepy.pack.com.bleepy.view.signin.ForgetCredentialsActivity;
import bleepy.pack.com.bleepy.view.signin.ForgetPasswordActivity;
import bleepy.pack.com.bleepy.view.signin.SigninActivity;
import bleepy.pack.com.bleepy.view.signin.SigninContract;
import dagger.Component;

/**
 * Created by siranjeevi on 9/6/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, SigninModule.class})
public interface SigninComponent {

    void inject(SigninActivity signinActivity);
    void inject(ForgetCredentialsActivity forgetPasswordActivity);
    SigninContract.Presenter signinPresenter();
}
