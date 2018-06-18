package bleepy.pack.com.bleepy.view.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.models.myschedule.MyScheduleListResponse;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MySchedulesAdapter extends RecyclerView.Adapter<MySchedulesAdapter.MyViewHolder> {

    private List<MyScheduleListResponse.Datum> mScheduleList;
    private Activity context;
    public int selectedRadioButtonPosition = -1;
    int[] androidColors;

    public MySchedulesAdapter(Activity context, List<MyScheduleListResponse.Datum> scheduleList){
        this.context=context;
        this.mScheduleList=scheduleList;
        androidColors = context.getResources().getIntArray(R.array.androidcolors);
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvStartTime)TextView tvStartTime;
        @BindView(R.id.tvEndTime)TextView tvEndTime;
        @BindView(R.id.viewRound)View viewRound;
        @BindView(R.id.viewRound1)View viewRound1;
        @BindView(R.id.viewHorizontal)View viewHorizontal;
        @BindView(R.id.viewHorizontal1)View viewHorizontal1;
        @BindView(R.id.llScheduleStartEnd)LinearLayout llScheduleStartEnd;
        @BindView(R.id.llScheduleStartEnd1)LinearLayout llScheduleStartEnd1;
        @BindView(R.id.tvScheduleStart)TextView tvScheduleStart;
        @BindView(R.id.tvScheduleEnd)TextView tvScheduleEnd;
        @BindView(R.id.view_vertical)View view_vertical;


        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MyScheduleListResponse.Datum pojo=mScheduleList.get(position);
        if(pojo!=null) {
            /*Random r = new Random();
            int red = r.nextInt(255 - 0 + 1) + 0;
            int green = r.nextInt(255 - 0 + 1) + 0;
            int blue = r.nextInt(255 - 0 + 1) + 0;*/

           // int randomAndroidColor = androidColors[position];
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            /*GradientDrawable roundDrawable1 = (GradientDrawable) holder.viewRound.getBackground();
            roundDrawable1.setColor(randomAndroidColor);
            GradientDrawable drawable1 = (GradientDrawable) holder.llScheduleStartEnd.getBackground();
            drawable1.setColor(randomAndroidColor);*/
            holder.viewRound.getBackground().setColorFilter(randomAndroidColor,PorterDuff.Mode.SRC_IN);
            holder.llScheduleStartEnd.getBackground().setColorFilter(randomAndroidColor, PorterDuff.Mode.SRC_IN);
            holder.viewHorizontal.getBackground().setColorFilter(randomAndroidColor,PorterDuff.Mode.SRC_IN);
            holder.view_vertical.setBackgroundColor(randomAndroidColor);

            int randomAndroidColor1 = androidColors[new Random().nextInt(androidColors.length)];
         /*   GradientDrawable roundDrawable2 = (GradientDrawable) holder.viewRound1.getBackground();
            roundDrawable2.setColor(randomAndroidColor1);*/

            holder.viewHorizontal1.getBackground().setColorFilter(randomAndroidColor1,PorterDuff.Mode.SRC_IN);
            holder.viewRound1.getBackground().setColorFilter(randomAndroidColor1,PorterDuff.Mode.SRC_IN);
            holder.llScheduleStartEnd1.getBackground().setColorFilter(randomAndroidColor1, PorterDuff.Mode.SRC_IN);


           /* GradientDrawable drawable2 = (GradientDrawable) holder.llScheduleStartEnd1.getBackground();
            drawable2.setColor(randomAndroidColor1);*/
            if (pojo.getTimings() != null) {
                String[] timings = pojo.getTimings().trim().split("\\-");
                final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");

                try {
                    holder.tvStartTime.setText(Convert24to12(timings[0]));
                    holder.tvEndTime.setText(Convert24to12(timings[1]));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            holder.tvScheduleStart.setText(pojo.getShifttype() + " " + "starts");
            holder.tvScheduleEnd.setText(pojo.getShifttype() + " " + "ends");
        }



    }

    @Override
    public int getItemCount() {
        return mScheduleList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public  String Convert24to12(String time)
    {
        String convertedTime ="";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
            Date date = parseFormat.parse(time);
            convertedTime=displayFormat.format(date);

        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
        //Output will be 10:23 PM
    }



}
