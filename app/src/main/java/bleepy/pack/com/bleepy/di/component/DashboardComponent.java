package bleepy.pack.com.bleepy.di.component;



import bleepy.pack.com.bleepy.di.module.ActivityModule;
import bleepy.pack.com.bleepy.di.module.DashboardModule;
import bleepy.pack.com.bleepy.di.module.SigninModule;
import bleepy.pack.com.bleepy.di.scope.PerActivity;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardActivity;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardContract;
import bleepy.pack.com.bleepy.view.Dashboard.EditProfileFragment;
import bleepy.pack.com.bleepy.view.Dashboard.MyScheduleActivity;
import bleepy.pack.com.bleepy.view.Dashboard.SettingsFragment;
import bleepy.pack.com.bleepy.view.callforhelp.CallForHelpActivity;
import bleepy.pack.com.bleepy.view.signin.ForgetCredentialsActivity;
import bleepy.pack.com.bleepy.view.signin.SigninActivity;
import bleepy.pack.com.bleepy.view.signin.SigninContract;
import bleepy.pack.com.bleepy.view.team.GroupMembersActivity;
import dagger.Component;

/**
 * Created by siranjeevi on 9/6/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, DashboardModule.class})
public interface DashboardComponent {

    void inject(DashboardActivity dashboardActivity);
    void inject(MyScheduleActivity myScheduleActivity);
    void inject(GroupMembersActivity groupMembersActivity);
    void inject(CallForHelpActivity callForHelpActivity);
    void inject(EditProfileFragment editProfileFragment);
    void inject(SettingsFragment settingsFragment);
    DashboardContract.Presenter dashBoardPresenter();
}
