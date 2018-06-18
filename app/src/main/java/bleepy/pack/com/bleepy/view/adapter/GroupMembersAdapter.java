package bleepy.pack.com.bleepy.view.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoResponse;
import bleepy.pack.com.bleepy.models.myschedule.MyScheduleListResponse;
import bleepy.pack.com.bleepy.models.team.GroupMembersResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class GroupMembersAdapter extends RecyclerView.Adapter<GroupMembersAdapter.MyViewHolder> {

    private List<GroupMembersResponse.Datum> mGroupMembersList = new ArrayList<>();
    private Activity context;
    public int selectedRadioButtonPosition = -1;

    public GroupMembersAdapter(Activity context, List<GroupMembersResponse.Datum> groupMembersList){
        this.context=context;
        this.mGroupMembersList=groupMembersList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.viewHorizontal)View viewHorizontal;
        @BindView(R.id.tvGroupName)TextView tvGroupName;
        @BindView(R.id.tvTotalMembers)TextView tvTotalMembers;
        @BindView(R.id.ivMember1)CircleImageView ivMember1;
        @BindView(R.id.ivMember2)CircleImageView ivMember2;
        @BindView(R.id.ivMember3)CircleImageView ivMember3;
        @BindView(R.id.ivMember4)CircleImageView ivMember4;


        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_members,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        CircleImageView ivTeamMember = null;
        GroupMembersResponse.Datum group=mGroupMembersList.get(position);
        List<GroupMembersResponse.Member> memberList=group.getMembers();
        Random r = new Random();
        int red=r.nextInt(255 - 0 + 1)+0;
        int green=r.nextInt(255 - 0 + 1)+0;
        int blue=r.nextInt(255 - 0 + 1)+0;
        holder.tvGroupName.setText(group.getTeamname());
        holder.tvTotalMembers.setText(String.valueOf(group.getNoOfUsers()));
        holder.viewHorizontal.setBackgroundColor(Color.rgb(red,green,blue));
        if(memberList!=null&&memberList.size()>0){
            GroupMembersResponse.Member member;
            for(int i=0;i<memberList.size();i++) {
                member=memberList.get(i);
                if (i == 4) {
                    return;
                }
                switch (i){
                    case 0:
                        ivTeamMember=holder.ivMember1;
                        break;
                    case 1:
                        ivTeamMember=holder.ivMember2;
                        break;
                    case 2:
                        ivTeamMember=holder.ivMember3;
                        break;
                    case 3:
                        ivTeamMember=holder.ivMember4;
                        break;

                }
                setProfileInfo(ivTeamMember,member.getProfileImage());

            }
        }
    }

    @Override
    public int getItemCount() {
        return mGroupMembersList.size();
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
