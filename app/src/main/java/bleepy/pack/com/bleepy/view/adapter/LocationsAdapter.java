package bleepy.pack.com.bleepy.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.models.callforhelp.LocationsResponse;
import bleepy.pack.com.bleepy.models.team.GroupMembersResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.MyViewHolder> implements Filterable {

    private List<LocationsResponse.Datum> mLocationsList;
    private List<LocationsResponse.Datum> mFilteredList;
    private Activity context;
    public int selectedRadioButtonPosition = -1;
    CustomFilter filter;
    private ItemListener mItemListener;
    Dialog locationSelectDialog;

    public LocationsAdapter(Activity context, List<LocationsResponse.Datum> locationsList, Dialog locationSelectDialog,ItemListener mListener){
        this.context=context;
        this.mLocationsList=locationsList;
        this.mFilteredList=locationsList;
        this.mItemListener=mListener;
        this.locationSelectDialog=locationSelectDialog;
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

        @BindView(R.id.tvLocationName)TextView tvLocationName;
        @BindView(R.id.tvSelect)TextView tvSelect;
        @BindView(R.id.llLocations)LinearLayout llLocations;



        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_locaitons_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        LocationsResponse.Datum location=mLocationsList.get(position);
        holder.tvLocationName.setText(location.getLocationname());
        holder.llLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(locationSelectDialog!=null&&locationSelectDialog.isShowing()){
                    locationSelectDialog.dismiss();
                }
                mItemListener.onLocationSelected(location,position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mLocationsList.size();
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
        LocationsAdapter adapter;
        List<LocationsResponse.Datum> filterList;

        public CustomFilter(List<LocationsResponse.Datum> filterList,LocationsAdapter adapter)
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

                ArrayList<LocationsResponse.Datum> mFilteredCategories=new ArrayList<>();
                for (int i=0;i<filterList.size();i++)
                {
                    //CHECK
                    if(filterList.get(i).getLocationname().toUpperCase().contains(constraint))
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
                adapter.mLocationsList = (List<LocationsResponse.Datum>) results.values;
                adapter.notifyDataSetChanged();
                //mSearchView.clearFocus();

            }else {
                adapter.mLocationsList = (List<LocationsResponse.Datum>) results.values;
                //REFRESH
                adapter.notifyDataSetChanged();
            }

        }

    }
    public interface ItemListener {
        void onLocationSelected(LocationsResponse.Datum location, int position);
    }


}
