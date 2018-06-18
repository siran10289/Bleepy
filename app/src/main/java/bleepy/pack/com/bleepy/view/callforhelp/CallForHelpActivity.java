package bleepy.pack.com.bleepy.view.callforhelp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.devlomi.record_view.OnBasketAnimationEnd;
import com.devlomi.record_view.OnRecordListener;
import com.devlomi.record_view.RecordButton;
import com.devlomi.record_view.RecordView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import bleepy.pack.com.bleepy.MainActivity;
import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static bleepy.pack.com.bleepy.utils.AppUtils.collapse;
import static bleepy.pack.com.bleepy.utils.AppUtils.createVideoFile;
import static bleepy.pack.com.bleepy.utils.AppUtils.expand;
import static bleepy.pack.com.bleepy.utils.AppUtils.stringForTime;
import static bleepy.pack.com.bleepy.utils.Constants.AUDIO_PATH;

public class CallForHelpActivity extends BaseActivity implements MediaPlayer.OnCompletionListener {
    @BindView(R.id.record_button)RecordButton mRecordButton;
    @BindView(R.id.record_view)RecordView mRecordView;
    @BindView(R.id.toolbar)Toolbar mToolBar;
    @BindView(R.id.toolbar_title)TextView toolbar_title;
    @BindView(R.id.ivPausePlay)ImageView ivPausePlay;
    @BindView(R.id.sbAudioPlay)AppCompatSeekBar sbAudioPlay;
    @BindView(R.id.sbVolumeControl)AppCompatSeekBar sbVolumeControl;
    @BindView(R.id.tvCurrentTime)TextView tvCurrentTime;
    @BindView(R.id.tvReminingTime)TextView tvReminingTime;
    @BindView(R.id.llPalyerView)LinearLayout llPalyerView;
    @BindView(R.id.tvAudioFileName)TextView tvAudioFileName;
    private static final int SHOW_PROGRESS = 2;
    private MediaRecorder myAudioRecorder;
    private String outputFile;
    private File recordedFile,recordingFile;
    MediaPlayer mediaPlayer;
    public static final int RequestPermissionCode = 1;
    public Handler mHandler = new MessageHandler();
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_for_help);
        init();
    }
    @SuppressLint("SetTextI18n")
    private void init(){
        setSupportActionBar(mToolBar);
        setDisplayHomeUp();
        toolbar_title.setText(R.string.call_for_help);
        if(checkPermission()) {
            mRecordButton.setRecordView(mRecordView);
            initRcordingView();
            initMediaRecorder();

        }else{
            requestPermission();
        }

    }
    private void initMediaRecorder(){
        recordingFile = createVideoFile("AudioRecord");

        /*outputFile  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bleepy/recording.3gp";
        File file=new File(outputFile);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();

        }*/
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(recordingFile.getAbsolutePath());

    }

    private void initRcordingView(){
        //mRecordButton.setListenForRecord(true);
        mRecordView.setCancelBounds(130);
        mRecordView.setSmallMicColor(Color.parseColor("#c2185b"));
        mRecordView.setLessThanSecondAllowed(false);
        mRecordView.setSoundEnabled(true);
        mRecordView.setSlideToCancelText("Slide To Cancel");
        mRecordView.setCustomSounds(R.raw.record_start, R.raw.record_finished, 0);
        mRecordView.setOnRecordListener(new OnRecordListener() {
            @Override
            public void onStart() {
                if(checkPermission()) {
                    if(mediaPlayer!=null){
                        if(recordedFile!=null&&recordedFile.exists()){
                            recordedFile.delete();
                        }
                        collapse(llPalyerView);
                        mediaPlayer.reset();
                        initMediaRecorder();
                    }
                    startRecording();
                    Toast.makeText(CallForHelpActivity.this, "OnStartRecord", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancel() {
                Log.d("RecordView", "onCancel");
                Toast.makeText(CallForHelpActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                stopRecording();
                if(recordingFile.exists()){
                    recordingFile.delete();
                }
            }

            @Override
            public void onFinish(long recordTime) {
                String time = getHumanTimeText(recordTime);
                Toast.makeText(CallForHelpActivity.this, "onFinishRecord - Recorded Time is: " + time, Toast.LENGTH_SHORT).show();
                recordedFile=recordingFile;
                stopRecording();
                setRecordedAudio();
            }

            @Override
            public void onLessThanSecond() {
                Toast.makeText(CallForHelpActivity.this, "OnLessThanSecond", Toast.LENGTH_SHORT).show();
                Log.d("RecordView", "onLessThanSecond");
            }
        });


        mRecordView.setOnBasketAnimationEndListener(new OnBasketAnimationEnd() {
            @Override
            public void onAnimationEnd() {
                Log.d("RecordView", "Basket Animation Finished");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_callfor_help, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menuDial:

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @OnClick({R.id.ivPausePlay,R.id.tvAudioFileName})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.ivPausePlay:
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                     mediaPlayer.pause();
                     mHandler.removeMessages(SHOW_PROGRESS);
                     ivPausePlay.setImageDrawable(ContextCompat.getDrawable(CallForHelpActivity.this,R.drawable.ic_play));
                }else{
                    if(recordedFile!=null&&mediaPlayer!=null) {
                        mediaPlayer.start();
                        ivPausePlay.setImageDrawable(ContextCompat.getDrawable(CallForHelpActivity.this, R.drawable.ic_pause));
                        mHandler.sendEmptyMessage(SHOW_PROGRESS);
                    }
                    /*CallForHelpActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if(mediaPlayer != null){
                                int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                                sbAudioPlay.setProgress(mCurrentPosition);
                            }
                            mHandler.postDelayed(this, 1000);
                        }
                    });*/
                }

                break;

            case R.id.tvAudioFileName:
                if(recordedFile!=null&&recordedFile.exists()){
                    if(recordedFile.delete()){
                        collapse(llPalyerView);
                        Toast.makeText(CallForHelpActivity.this, "Recorded audio deleted..", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void startRecording(){
        try {
            if(myAudioRecorder!=null) {
                myAudioRecorder.prepare();
                myAudioRecorder.start();
            }
        }  catch (Exception e) {
            Log.e("Exception:",e.toString());
            File dir = new File(AUDIO_PATH);
            try {
                FileUtils.deleteDirectory(dir);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void stopRecording(){
        try {
            if (myAudioRecorder != null) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;

            }
        }catch (Exception e){
            Log.e("Exception:",e.toString());
            File dir = new File(AUDIO_PATH);
            try {
                FileUtils.deleteDirectory(dir);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
    private void setRecordedAudio(){
        if(mediaPlayer==null) {
            initMediaPlayer();
        }
        try {
            Log.e("Path:",recordedFile.getAbsolutePath().toString());
            mediaPlayer.setDataSource(recordedFile.getAbsolutePath().toString());
            mediaPlayer.prepare();
            sbAudioPlay.setMax(mediaPlayer.getDuration());
            tvAudioFileName.setText(recordedFile.getName());
            expand(llPalyerView);
        } catch (Exception e) {
            // make something
        }
    }
    private void initMediaPlayer(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        sbVolumeControl.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbVolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbAudioPlay.setOnSeekBarChangeListener(mSeekListener);


    }
    private String getHumanTimeText(long milliseconds) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(CallForHelpActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        init();
                    } else {
                        requestPermission();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(mp!=null){
            tvCurrentTime.setText(stringForTime(0));
            tvReminingTime.setText(stringForTime(mp.getDuration()));
            mediaPlayer.pause();
            sbAudioPlay.setProgress(0);
            ivPausePlay.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_play));
        }

    }

    private  class MessageHandler extends Handler {


        @Override
        public void handleMessage(Message msg) {

            if (mediaPlayer==null) {
                return;
            }

            int pos;
            switch (msg.what) {

                case SHOW_PROGRESS:
                    pos= setProgress();
                    if (mediaPlayer.isPlaying()) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 10 - (pos % 10));
                    }
                    break;
            }
        }
    }
    private int setProgress(){
        int position = mediaPlayer.getCurrentPosition();
        sbAudioPlay.setProgress(position);
        tvCurrentTime.setText(stringForTime(position));
        tvReminingTime.setText(stringForTime(mediaPlayer.getDuration()-position));
        return position;

    }
    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar bar) {
            mHandler.removeMessages(SHOW_PROGRESS);

        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (mediaPlayer == null) {
                return;
            }
            if(!mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(progress);
            }

            /*if (mCurrentTime != null)
                mCurrentTime.setText(stringForTime((int) progress));*/
        }

        public void onStopTrackingTouch(SeekBar bar) {

            setProgress();
            mHandler.sendEmptyMessage(SHOW_PROGRESS);
        }
    };

}
