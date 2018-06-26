package bleepy.pack.com.bleepy.view.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.models.emegencycalllog.CodeLogMembersResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class CodeLogMembersAdapter extends RecyclerView.Adapter<CodeLogMembersAdapter.MyViewHolder> {

    private List<CodeLogMembersResponse.Datum> mCodeLogMembersList = new ArrayList<>();
    private Activity context;
    public int selectedRadioButtonPosition = -1;

    public CodeLogMembersAdapter(Activity context, List<CodeLogMembersResponse.Datum> groupMembersList){
        this.context=context;
        this.mCodeLogMembersList=groupMembersList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvPersonName)TextView tvPersonName;
        @BindView(R.id.ivProfileImage)CircleImageView ivProfileImage;
        @BindView(R.id.tvDesingnation)TextView tvDesignation;
        @BindView(R.id.tvCodeAcceptStatus)TextView tvCodeAcceptStatus;
        @BindView(R.id.ivUserStatus)ImageView ivUserStatus;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_memeber,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        CodeLogMembersResponse.Datum member=mCodeLogMembersList.get(position);
        switch (member.getCodeStatus()){
            case "0":
                holder.tvCodeAcceptStatus.setText("Rejected");
                holder.tvCodeAcceptStatus.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                break;
            case "1":
                holder.tvCodeAcceptStatus.setText("Accepted");
                holder.tvCodeAcceptStatus.setTextColor(ContextCompat.getColor(context,R.color.inner_green));
                break;
        }
        switch (member.getUserStatus()){
            case "0":
                holder.ivUserStatus.getBackground().setColorFilter(ContextCompat.getColor(context,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                break;
            case "1":
                holder.ivUserStatus.getBackground().setColorFilter(ContextCompat.getColor(context,R.color.inner_green), PorterDuff.Mode.SRC_IN);
                break;
        }
        if(member.getUsername()!=null)holder.tvPersonName.setText(member.getUsername());
        if(member.getDesignation()!=null)holder.tvDesignation.setText(member.getDesignation());
        setProfileInfo(holder.ivProfileImage,member.getProfilePath());
    }

    @Override
    public int getItemCount() {
        return mCodeLogMembersList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    private void setProfileInfo(CircleImageView ivProfileImage,String imageUrl){
            if (imageUrl != null) {
                Log.e("ImageURl:",imageUrl);
                Glide.with(context).load(imageUrl)
                        .asBitmap()
                        .placeholder(R.drawable.profile_placeholder)
                        .centerCrop()
                        .into(new BitmapImageViewTarget(ivProfileImage) {
                            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                ivProfileImage.setImageDrawable(circularBitmapDrawable);

                            }
                        });
            }

    }



}
