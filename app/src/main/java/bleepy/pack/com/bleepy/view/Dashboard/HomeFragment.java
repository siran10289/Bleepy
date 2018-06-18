package bleepy.pack.com.bleepy.view.Dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String dashBoardInfoJson;
    DashboardInfoResponse mDashboardInfoResponse;
    View mRootView;
    @BindView(R.id.tvEmcycount)TextView tvEmcycount;
    @BindView(R.id.tvWarningsCount)TextView tvWarningsCount;
    @BindView(R.id.tvMessage)TextView tvMessage;
    @BindView(R.id.ivProfileImage)CircleImageView ivProfileImage;

    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dashBoardInfoJson = getArguments().getString(ARG_PARAM1);
            mDashboardInfoResponse=new Gson().fromJson(dashBoardInfoJson,DashboardInfoResponse.class);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }
    private void init(){
        tvEmcycount.setText(mDashboardInfoResponse.getData().getEmergencyAttended()+"");
        tvWarningsCount.setText(mDashboardInfoResponse.getData().getWarningAccepted()+"");
        tvMessage.setText(mDashboardInfoResponse.getData().getImportantMessage()+"");
        setProfileInfo(mDashboardInfoResponse);
    }
    private void setProfileInfo(DashboardInfoResponse dashboardInfoResponse){
        if(dashboardInfoResponse.getData()!=null&&dashboardInfoResponse.getData().getProfileImage()!=null) {
            String imageUrl = dashboardInfoResponse.getData().getProfileImage();

            if (imageUrl != null) {
                Log.e("ImageURl:",imageUrl);
                Glide.with(getActivity()).load(imageUrl)
                        .asBitmap()
                        .placeholder(R.drawable.profile_placeholder)
                        .centerCrop()
                        .into(new BitmapImageViewTarget(ivProfileImage) {
                            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                ivProfileImage.setImageDrawable(circularBitmapDrawable);

                            }
                        });
            }
        }
    }
}
