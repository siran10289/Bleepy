package bleepy.pack.com.bleepy.view.Dashboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerDashboardComponent;
import bleepy.pack.com.bleepy.di.component.DashboardComponent;
import bleepy.pack.com.bleepy.di.module.DashboardModule;
import bleepy.pack.com.bleepy.models.myschedule.MyScheduleListResponse;
import bleepy.pack.com.bleepy.view.adapter.MySchedulesAdapter;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import butterknife.BindView;

public class MyScheduleActivity extends BaseActivity implements DashboardContract.MyScheduleView {
    @Inject
    DashboardContract.Presenter mPresenter;
    DashboardComponent mDashboardComponent;
    @BindView(R.id.toolbar)Toolbar mToolBar;
    @BindView(R.id.toolbar_title)TextView toolbar_title;
    @BindView(R.id.collapsibleCalendarView)CollapsibleCalendar collapsibleCalendarView;
    @BindView(R.id.rvMySchedule)RecyclerView rvMySchedule;
    @BindView(R.id.tvNoEvents)TextView tvNoEvents;
    String selectedDate;
    MySchedulesAdapter mMySchedulesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init(){
        initComponent();
        setSupportActionBar(mToolBar);
        setDisplayHomeUp();
        toolbar_title.setText(R.string.my_scedule);
        initCalenderView();
        rvMySchedule.setHasFixedSize(true);
        rvMySchedule.setLayoutManager(new LinearLayoutManager(MyScheduleActivity.this));
        Day day = collapsibleCalendarView.getSelectedDay();
        selectedDate= day.getYear()+"-"+(day.getMonth())+"-"+day.getDay();
        mPresenter.getMySchedules();
    }
    private void initComponent() {
        this.mDashboardComponent = DaggerDashboardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .dashboardModule(new DashboardModule(this))
                .build();
        mDashboardComponent.inject(this);
        mPresenter.setMyScheduleView(this);
    }
    private void initCalenderView(){
        /*CollapsibleCalendar collapsibleCalendar = findViewById(R.id.collapsibleCalendarView);
        Calendar today=new GregorianCalendar();
        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH));
        today.add(Calendar.DATE,1);
        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH), Color.BLUE);*/



        collapsibleCalendarView.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {

                Day day = collapsibleCalendarView.getSelectedDay();
                selectedDate= day.getYear()+"-"+(day.getMonth()+1)+"-"+day.getDay();
                mPresenter.getMySchedules();

            }

            @Override
            public void onItemClick(View v) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int position) {

            }
        });

    }

    @Override
    public String getDate() {
        return selectedDate;
    }

    @Override
    public void setMyScheduleResponse(MyScheduleListResponse myScheduleResponse) {
        if(myScheduleResponse.getData()!=null&&myScheduleResponse.getData().size()>0) {
            rvMySchedule.setVisibility(View.VISIBLE);
            tvNoEvents.setVisibility(View.GONE);
            mMySchedulesAdapter = new MySchedulesAdapter(MyScheduleActivity.this, myScheduleResponse.getData());
            rvMySchedule.setAdapter(mMySchedulesAdapter);
        }else{
            rvMySchedule.setVisibility(View.GONE);
            tvNoEvents.setVisibility(View.VISIBLE);
        }

    }
}
