package bleepy.pack.com.bleepy.view.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.models.callforhelp.TeamsResponse;
import butterknife.BindView;
import butterknife.ButterKnife;


public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyViewHolder> implements Filterable {

    private List<TeamsResponse.Datum> mTeamsList;
    private List<TeamsResponse.Datum> mFilteredList;
    private List<TeamsResponse.Datum> mSelectedTeamsList=new ArrayList<>();
    private Activity context;
    public int selectedRadioButtonPosition = -1;
    CustomFilter filter;

    public TeamsAdapter(Activity context, List<TeamsResponse.Datum> locationsList){
        this.context=context;
        this.mTeamsList=locationsList;
        this.mFilteredList=locationsList;
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(mFilteredList,this);
        }
        return filter;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvTeamName)TextView tvTeamName;
        @BindView(R.id.tvSelect)AppCompatCheckedTextView tvSelect;
        @BindView(R.id.llTeam)LinearLayout llTeam;




        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teams_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TeamsResponse.Datum team=mTeamsList.get(position);
        if(team.isChecked()){
            holder.llTeam.setBackgroundColor(ContextCompat.getColor(context,R.color.black_translucent));
        }else{
            holder.llTeam.setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
        }
        holder.tvTeamName.setText(team.getTeamname());
        holder.llTeam.setTag(team);

         holder.llTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LinearLayout linearLayout = (LinearLayout) view;
                    TeamsResponse.Datum team = (TeamsResponse.Datum) linearLayout.getTag();
                    //team.setChecked(holder.tvSelect.isChecked());
                    if (team != null && team.isChecked()) {
                        mSelectedTeamsList.remove(team);
                        holder.llTeam.setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
                        mTeamsList.get(position).setChecked(false);

                    } else  {
                        mSelectedTeamsList.add(team);
                        mTeamsList.get(position).setChecked(true);
                        holder.llTeam.setBackgroundColor(ContextCompat.getColor(context,R.color.black_translucent));
                    }
                    notifyDataSetChanged();
                }catch (Exception e){
                    Log.e("Error",e.toString());
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mTeamsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class CustomFilter extends Filter{
        TeamsAdapter adapter;
        List<TeamsResponse.Datum> filterList;

        public CustomFilter(List<TeamsResponse.Datum> filterList,TeamsAdapter adapter)
        {
            this.adapter=adapter;
            this.filterList=filterList;
        }
        //FILTERING OCURS
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();
            //CHECK CONSTRAINT VALIDITY
            if(constraint != null && constraint.length() > 0)
            {
                //CHANGE TO UPPER
                constraint=constraint.toString().toUpperCase();

                ArrayList<TeamsResponse.Datum> mFilteredCategories=new ArrayList<>();
                for (int i=0;i<filterList.size();i++)
                {
                    //CHECK
                    if(filterList.get(i).getTeamname().toUpperCase().contains(constraint))
                    {

                        mFilteredCategories.add(filterList.get(i));
                    }
                }
                results.count=mFilteredCategories.size();
                results.values=mFilteredCategories;
            }else
            {

                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {


            if(results.count==0){
                adapter.mTeamsList = (List<TeamsResponse.Datum>) results.values;
                adapter.notifyDataSetChanged();
                //mSearchView.clearFocus();

            }else {
                adapter.mTeamsList = (List<TeamsResponse.Datum>) results.values;
                //REFRESH
                adapter.notifyDataSetChanged();
            }

        }

    }
    public List<TeamsResponse.Datum> getSelectedTeamsList(){
        return mSelectedTeamsList;
    }


}
