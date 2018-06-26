package bleepy.pack.com.bleepy.view.adapter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.models.callforhelp.LocationsResponse;
import bleepy.pack.com.bleepy.models.emegencycalllog.EmergencyCalllogResponse;
import bleepy.pack.com.bleepy.utils.Constants;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import static bleepy.pack.com.bleepy.utils.AppUtils.stringForTime;


public class CodeRegisterAdapter extends RecyclerView.Adapter<CodeRegisterAdapter.MyViewHolder> implements Handler.Callback {
    PrefsManager mPrefsManager;
    private List<EmergencyCalllogResponse.Datum> mLogList;
    private Activity context;
    public int selectedRadioButtonPosition = -1;
    private static final int MSG_UPDATE_SEEK_BAR = 1845;
    private MediaPlayer mediaPlayer;
    private Handler uiUpdateHandler;
    private int playingPosition;
    private CodeRegisterAdapter.MyViewHolder playingHolder;
    private ItemListener mItemListener;

    public CodeRegisterAdapter(Activity context, List<EmergencyCalllogResponse.Datum> logList,PrefsManager prefsManager,ItemListener mItemListener){
        this.context=context;
        this.mLogList=logList;
        this.playingPosition = -1;
        this.uiUpdateHandler = new Handler(this);
        this.mPrefsManager=prefsManager;
        this.mItemListener=mItemListener;
    }
    public void clearList(){
        mLogList.clear();
        notifyDataSetChanged();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_UPDATE_SEEK_BAR: {
                playingHolder.tvCurrentTime.setText(stringForTime(mediaPlayer.getCurrentPosition())+"/"+stringForTime(mediaPlayer.getDuration()));
                playingHolder.sbProgress.setProgress(mediaPlayer.getCurrentPosition());
                uiUpdateHandler.sendEmptyMessageDelayed(MSG_UPDATE_SEEK_BAR, 100);
                return true;
            }
        }
        return false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

        @BindView(R.id.tvDescrption)TextView tvDescrption;
        @BindView(R.id.seekBar)AppCompatSeekBar sbProgress;
        @BindView(R.id.ivPlay)ImageView ivPlayPause;
        @BindView(R.id.tvCurrentTime)TextView tvCurrentTime;
        @BindView(R.id.tvAudioFileName)TextView tvAudioFileName;
        @BindView(R.id.tvCodeID)TextView tvCodeID;
        @BindView(R.id.llAudioPlayer)LinearLayout llAudioPlayer;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivPlayPause.setOnClickListener(this);
            sbProgress.setOnSeekBarChangeListener(this);
        }
        @Override
        public void onClick(View v) {
            if (getAdapterPosition() == playingPosition) {
                // toggle between play/pause of audio
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
                updatePlayingView();
            } else {
                // start another audio playback
                playingPosition = getAdapterPosition();
                if (mediaPlayer != null) {
                    if (null != playingHolder) {
                        updateNonPlayingView(playingHolder);
                    }
                    mediaPlayer.release();
                }
                playingHolder = this;
                startMediaPlayer(this,mLogList.get(playingPosition).getCodeVoicePath());
            }

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mediaPlayer.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
    @Override
    public CodeRegisterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_code_register,parent,false);
        return new CodeRegisterAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CodeRegisterAdapter.MyViewHolder holder, final int position) {
        if (position == playingPosition) {
            playingHolder = holder;
            // this view holder corresponds to the currently playing audio cell
            // update its view to show playing progress
            updatePlayingView();
        } else {
            // and this one corresponds to non playing
            updateNonPlayingView(holder);
        }
        EmergencyCalllogResponse.Datum callLog=mLogList.get(position);
        if(callLog.getDescription()!=null)holder.tvDescrption.setText(callLog.getDescription());

        if(callLog.getCodeVoicePath()==null){
            holder.llAudioPlayer.setVisibility(View.GONE);
        }
        if(callLog.getCodeStatus().equalsIgnoreCase("1")) {
            if(callLog.getCode()!=null)holder.tvCodeID.setText("Code # "+callLog.getCode());
            holder.tvCodeID.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getDrawable(R.drawable.info_icon), null);
        }else if(callLog.getCodeStatus().equalsIgnoreCase("0")){
            if(callLog.getCode()!=null)holder.tvCodeID.setText("Code # "+callLog.getCode()+" (Under processing)");
        }
        if(callLog.getUserId().equalsIgnoreCase(String.valueOf(mPrefsManager.getIntKeyValueFromPrefsByKey(Constants.KEY_USERID)))){
            if(callLog.getCodeStatus().equalsIgnoreCase("0")) {
                holder.tvCodeID.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getDrawable(R.drawable.close_icon_sm_circle), null);
            }
        }


        holder.tvCodeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               switch (callLog.getCodeStatus()){
                   case "0":
                      if(callLog.getUserId().equalsIgnoreCase(String.valueOf(mPrefsManager.getIntKeyValueFromPrefsByKey(Constants.KEY_USERID)))){
                          mItemListener.onCodeDeleted(callLog,position);
                      }
                       break;
                   case "1":
                   case "2":
                   case "3":
                   case "4":
                       mItemListener.onCodeInfoClicked(callLog.getCode());
                       break;
               }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mLogList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void updatePlayingView() {
        playingHolder.tvCurrentTime.setText(stringForTime(mediaPlayer.getDuration()));
        playingHolder.sbProgress.setMax(mediaPlayer.getDuration());
        playingHolder.sbProgress.setProgress(mediaPlayer.getCurrentPosition());
        playingHolder.sbProgress.setEnabled(true);
        if (mediaPlayer.isPlaying()) {
            uiUpdateHandler.sendEmptyMessageDelayed(MSG_UPDATE_SEEK_BAR, 100);
            playingHolder.ivPlayPause.setImageResource(R.drawable.ic_pause);
        } else {
            uiUpdateHandler.removeMessages(MSG_UPDATE_SEEK_BAR);
            playingHolder.ivPlayPause.setImageResource(R.drawable.ic_play);
        }
    }
    public void stopPlayer() {
        if (null != mediaPlayer) {
            releaseMediaPlayer();
        }
    }
    public void onDeleteItem(int position){
        mLogList.remove(position);
        notifyDataSetChanged();
    }
    private void startMediaPlayer(CodeRegisterAdapter.MyViewHolder holder, String dataSource) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(dataSource);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    updatePlayingView();

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
            }
        });
        //duration = mediaPlayer.getDuration();
        // holder.sbProgress.setMax((int) duration);
        mediaPlayer.start();


    }

    private void releaseMediaPlayer() {
        if (null != playingHolder) {
            updateNonPlayingView(playingHolder);
        }
        mediaPlayer.release();
        mediaPlayer = null;
        playingPosition = -1;
    }
    private void updateNonPlayingView(CodeRegisterAdapter.MyViewHolder holder) {
        if (holder == playingHolder) {
            uiUpdateHandler.removeMessages(MSG_UPDATE_SEEK_BAR);
        }
        if(mediaPlayer!=null)holder.tvCurrentTime.setText(stringForTime(mediaPlayer.getDuration()));
        holder.sbProgress.setEnabled(false);
        holder.sbProgress.setProgress(0);
        holder.ivPlayPause.setImageResource(R.drawable.ic_play);
    }

    public interface ItemListener {
        void onCodeDeleted(EmergencyCalllogResponse.Datum callLog, int position);
        void onCodeInfoClicked(String codeID);
    }





}
