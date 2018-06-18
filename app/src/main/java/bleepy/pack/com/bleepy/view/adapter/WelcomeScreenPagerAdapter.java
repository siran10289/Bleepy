package bleepy.pack.com.bleepy.view.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import java.util.List;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.models.common.WelcomeScreenResponse;


public class WelcomeScreenPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<WelcomeScreenResponse.Screen> mScreenList;
    private LayoutInflater mLayoutInflater;
    private View itemView;
    private WelcomeScreenResponse.Screen mScreen;


    public WelcomeScreenPagerAdapter(Context context, List<WelcomeScreenResponse.Screen> screenList) {
        this.mContext = context;
        this.mScreenList=screenList;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        itemView = mLayoutInflater.inflate(R.layout.item_welcome_screen, container, false);
        mScreen=mScreenList.get(position);
        TextView tvTitle=itemView.findViewById(R.id.tvTitle);
        TextView tvDescrption=itemView.findViewById(R.id.tvDescrption);
        ImageView ivImage=itemView.findViewById(R.id.ivImage);
        tvTitle.setText(mScreen.getScreenLabel());
        tvDescrption.setText(mScreen.getScreenDescription());
        Glide.with(mContext).load(mScreen.getImagepath())
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.bg_login)
                .into(new BitmapImageViewTarget(ivImage) {
                    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                    @Override
                    protected void setResource(Bitmap resource) {
                        ivImage.setImageBitmap(resource);
                    }
                });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mScreenList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}