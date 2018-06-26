package bleepy.pack.com.bleepy.view.team;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerDashboardComponent;
import bleepy.pack.com.bleepy.di.component.DashboardComponent;
import bleepy.pack.com.bleepy.di.module.DashboardModule;
import bleepy.pack.com.bleepy.models.team.GroupMembersResponse;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardActivity;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardContract;
import bleepy.pack.com.bleepy.view.Dashboard.MyScheduleActivity;
import bleepy.pack.com.bleepy.view.adapter.GroupMembersAdapter;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import butterknife.BindView;

public class GroupMembersActivity extends BaseActivity implements DashboardContract.GroupMembersView{
    @Inject
    DashboardContract.Presenter mPresenter;
    DashboardComponent mDashboardComponent;
    @BindView(R.id.toolbar)Toolbar mToolBar;
    @BindView(R.id.toolbar_title)TextView toolbar_title;
    @BindView(R.id.rvTeamMembers)RecyclerView rvTeamMembers;
    GroupMembersAdapter mGroupMembersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);
        init();

    }
    private void init(){
        initComponent();
        setSupportActionBar(mToolBar);
        setDisplayHomeUp();
        toolbar_title.setText(R.string.groups);
        rvTeamMembers.setHasFixedSize(true);
        rvTeamMembers.setLayoutManager(new LinearLayoutManager(GroupMembersActivity.this));
        mPresenter.getTeamMembers();
    }
    private void initComponent() {
        this.mDashboardComponent = DaggerDashboardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .dashboardModule(new DashboardModule(this))
                .build();
        mDashboardComponent.inject(this);
        mPresenter.setGroupMembersView(this);
    }

    /*@Override
    public String getTeamID() {
        return "3";
    }*/

    @Override
    public void setTeamMembers(GroupMembersResponse groupMembersResponse) {
        mGroupMembersAdapter=new GroupMembersAdapter(GroupMembersActivity.this,groupMembersResponse.getData());
        rvTeamMembers.setAdapter(mGroupMembersAdapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        mBleepyApplication.setCurrentActivity(GroupMembersActivity.this);
    }
}
