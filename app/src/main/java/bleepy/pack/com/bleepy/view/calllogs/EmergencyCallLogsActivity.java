package bleepy.pack.com.bleepy.view.calllogs;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerDashboardComponent;
import bleepy.pack.com.bleepy.di.component.DashboardComponent;
import bleepy.pack.com.bleepy.di.module.DashboardModule;
import bleepy.pack.com.bleepy.models.common.CommonResponse;
import bleepy.pack.com.bleepy.models.emegencycalllog.CodeLogMembersResponse;
import bleepy.pack.com.bleepy.models.emegencycalllog.EmergencyCalllogResponse;
import bleepy.pack.com.bleepy.utils.customdialog.AppDialogManager;
import bleepy.pack.com.bleepy.utils.customdialog.DialogListener;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardActivity;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardContract;
import bleepy.pack.com.bleepy.view.Dashboard.MyScheduleActivity;
import bleepy.pack.com.bleepy.view.adapter.CodeLogMembersAdapter;
import bleepy.pack.com.bleepy.view.adapter.CodeRegisterAdapter;
import bleepy.pack.com.bleepy.view.adapter.EmergencyCallLogsAdapter;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class EmergencyCallLogsActivity extends BaseActivity implements DashboardContract.EmergencyCallLogView,
        CodeRegisterAdapter.ItemListener,DialogListener.EmergencyCodeLogListener{
    @Inject
    DashboardContract.Presenter mPresenter;
    DashboardComponent mDashboardComponent;
    @Inject
    PrefsManager mPrefsManager;
    @BindView(R.id.toolbar)Toolbar mToolBar;
    @BindView(R.id.toolbar_title)TextView toolbar_title;
    @BindView(R.id.rvEmergencyLogs)RecyclerView rvEmergencyLogs;
    @BindView(R.id.rbReturedCode)RadioButton rbReturedCode;
    @BindView(R.id.rbCodeRegistered)RadioButton rbCodeRegistered;
    @BindView(R.id.etSearchCodes)EditText etSearchCodes;
    EmergencyCallLogsAdapter mEmergencyCallLogsAdapter;
    CodeRegisterAdapter mCodeRegisterAdapter;
    CodeLogMembersAdapter mCodeLogMembersAdapter;
    String codeType="0";
    int deletablePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_call_logs);
        init();
    }
    @SuppressLint("SetTextI18n")
    private void init(){
        setSupportActionBar(mToolBar);
        setDisplayHomeUp();
        initComponent();
        toolbar_title.setText(R.string.title_code_logs);
        rvEmergencyLogs.setHasFixedSize(true);
        rvEmergencyLogs.setLayoutManager(new LinearLayoutManager(EmergencyCallLogsActivity.this));
        mPresenter.getEmergencyCallLogs("0",false);
        etSearchCodes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mPresenter.getEmergencyCallLogs(codeType,true);
                    return true;
                }
                return true;
            }
        });

    }

    private void initComponent() {
        this.mDashboardComponent = DaggerDashboardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .dashboardModule(new DashboardModule(this))
                .build();
        mDashboardComponent.inject(this);
        mPresenter.setEmergencyCallLogView(this);
    }
    @OnClick({R.id.rbReturedCode, R.id.rbCodeRegistered})
    public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.rbReturedCode:
                if (rbReturedCode.isChecked()) {
                    rbReturedCode.setTextColor(ContextCompat.getColor(EmergencyCallLogsActivity.this,R.color.white));
                    rbCodeRegistered.setTextColor(ContextCompat.getColor(EmergencyCallLogsActivity.this,R.color.light_lavendor_color));
                    codeType="0";
                    mPresenter.getEmergencyCallLogs(codeType,false);
                }
                break;
            case R.id.rbCodeRegistered:
                if (rbCodeRegistered.isChecked()) {
                    rbReturedCode.setTextColor(ContextCompat.getColor(EmergencyCallLogsActivity.this,R.color.light_lavendor_color));
                    rbCodeRegistered.setTextColor(ContextCompat.getColor(EmergencyCallLogsActivity.this,R.color.white));
                    codeType="1";
                    mPresenter.getEmergencyCallLogs(codeType,false);
                }
                break;

        }
    }

    @Override
    public void setEmergencyLogs(EmergencyCalllogResponse response) {
        if(response!=null&&response.getData()!=null&&response.getData().size()>0){
            switch (codeType){
                case "0":
                    mEmergencyCallLogsAdapter=new EmergencyCallLogsAdapter(EmergencyCallLogsActivity.this,response.getData());
                    rvEmergencyLogs.setAdapter(mEmergencyCallLogsAdapter);
                    break;
                case "1":
                    mCodeRegisterAdapter=new CodeRegisterAdapter(EmergencyCallLogsActivity.this,response.getData(),mPrefsManager,this);
                    rvEmergencyLogs.setAdapter(mCodeRegisterAdapter);
                    break;
            }
        }else{
            switch (codeType){
                case "0":
                    if(mEmergencyCallLogsAdapter!=null)mEmergencyCallLogsAdapter.clearList();
                    break;
                case "1":
                    if(mCodeRegisterAdapter!=null)mCodeRegisterAdapter.clearList();
                    break;
            }
        }
    }
    public void clearViews(){
        switch (codeType){
            /*case "0":
                if(mEmergencyCallLogsAdapter!=null)mEmergencyCallLogsAdapter.clearList();
                break;
            case "1":
                if(mCodeRegisterAdapter!=null)mCodeRegisterAdapter.clearList();
                break;*/
        }
    }


    @Override
    public void setDeleteCodeResponse(CommonResponse commonResponse) {
        if(mCodeRegisterAdapter!=null)mCodeRegisterAdapter.onDeleteItem(deletablePosition);
        showSuccessDialog("Cancellation confirmation",commonResponse.getMeta().getMessage());
    }

    @Override
    public void setCodeLogMembersList(CodeLogMembersResponse codeLogMembersList) {
        if(codeLogMembersList.getData()!=null&&codeLogMembersList.getData().size()>0){
            AppDialogManager.showCodeLogMembersDialog(EmergencyCallLogsActivity.this,this,codeLogMembersList.getData());
            //mCodeLogMembersAdapter=new CodeLogMembersAdapter(EmergencyCallLogsActivity.this,codeLogMembersList.getData());

        }
    }

    @Override
    public String getSearchableCodeID() {
        return etSearchCodes.getText().toString().trim();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mEmergencyCallLogsAdapter!=null)mEmergencyCallLogsAdapter.stopPlayer();
        if(mCodeRegisterAdapter!=null)mCodeRegisterAdapter.stopPlayer();

    }

    @Override
    public void onCodeDeleted(EmergencyCalllogResponse.Datum callLog, int position) {
        deletablePosition=position;
        mPresenter.onCodeDelete(callLog);
    }

    @Override
    public void onCodeInfoClicked(String codeID) {
        mPresenter.onCodeInfoClicked(codeID);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mBleepyApplication.setCurrentActivity(EmergencyCallLogsActivity.this);
    }


}
