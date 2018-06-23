package bleepy.pack.com.bleepy.utils.customdialog;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.models.callforhelp.LocationsResponse;
import bleepy.pack.com.bleepy.models.callforhelp.TeamsResponse;
import bleepy.pack.com.bleepy.models.common.EmergencyCode;
import bleepy.pack.com.bleepy.view.adapter.LocationsAdapter;
import bleepy.pack.com.bleepy.view.adapter.TeamsAdapter;
import bleepy.pack.com.bleepy.view.callforhelp.CallForHelpActivity;
import bleepy.pack.com.bleepy.view.team.GroupMembersActivity;

import static bleepy.pack.com.bleepy.utils.AppUtils.stringForTime;
import static bleepy.pack.com.bleepy.utils.Constants.ALERT_INTENT_CAMERA;
import static bleepy.pack.com.bleepy.utils.Constants.ALERT_INTENT_GALLERY;
import static bleepy.pack.com.bleepy.utils.Constants.ALERT_REMOVE;

public class AppDialogManager {
    static Dialog networkAlertDialog = null;
    static MediaPlayer mediaPlayer=new MediaPlayer();;
    private static final int SHOW_PROGRESS = 2;
    static AppCompatSeekBar seekBar;
    static TextView tvCurrentTime;
    public static Handler mHandler = new MessageHandler();
    public static Dialog confirmationDialog;

    public static void showAlertDialog(final Activity activity, String message,String type,boolean isCancelNeeded) {

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        //alertDialog.setTitle(activity.getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {

                    if (null != dialog)
                        dialog.dismiss();
                });
        if(isCancelNeeded) {
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    (dialog, which) -> {

                        if (null != dialog)
                            dialog.dismiss();
                    });
        }
        alertDialog.show();
    }


    public static void showNetWorkStatusAlertDialog(final Activity activity, Object object) {
        final DialogListener.NetworkDialogListener mCallback;
        try {
            mCallback = (DialogListener.NetworkDialogListener) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(object.toString() + " must implement DialogListener");
        }
        if (networkAlertDialog == null) {
            networkAlertDialog = new Dialog(activity);
            networkAlertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            networkAlertDialog.setCancelable(false);
            networkAlertDialog.setContentView(R.layout.dialog_network_info);
            networkAlertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            Button btnOK = (Button) networkAlertDialog.findViewById(R.id.btn_dialog_ok);
            Button btnSettings = (Button) networkAlertDialog.findViewById(R.id.btn_dialog_setting);

            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    networkAlertDialog.cancel();
                    networkAlertDialog = null;
                    mCallback.onOKPressed();
                }
            });
            btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    networkAlertDialog.cancel();
                    networkAlertDialog = null;
                    mCallback.onSettingsPressed();
                }
            });


            networkAlertDialog.show();
        }
    }

    public static void photoPickerDialog(final Activity activity, Object object) {
        final Dialog addPhotoDialog;
        final DialogListener.AlertDialogListener mCallback;
        try {
            mCallback = (DialogListener.AlertDialogListener) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(object.toString() + " must implement DialogListener");
        }
        addPhotoDialog = new Dialog(activity);
        addPhotoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addPhotoDialog.setContentView(R.layout.dialog_photo_chooser);
        addPhotoDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        addPhotoDialog.setCanceledOnTouchOutside(true);
        addPhotoDialog.setCancelable(true);

        addPhotoDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        addPhotoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wlmp = addPhotoDialog.getWindow().getAttributes();
        wlmp.gravity = Gravity.BOTTOM;

        LinearLayout llGallery = (LinearLayout) addPhotoDialog.findViewById(R.id.llGallery);
        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhotoDialog.dismiss();
                mCallback.onOKPressed(ALERT_INTENT_GALLERY);
            }
        });

        LinearLayout llCamera = (LinearLayout) addPhotoDialog.findViewById(R.id.llCamera);
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhotoDialog.dismiss();
                mCallback.onOKPressed(ALERT_INTENT_CAMERA);
            }
        });

        LinearLayout llRemove = (LinearLayout) addPhotoDialog.findViewById(R.id.llRemove);
        llRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhotoDialog.dismiss();
                mCallback.onOKPressed(ALERT_REMOVE);
            }
        });
        addPhotoDialog.show();
    }

    public static void showDeviceTypeDialog(final Activity activity, Object object) {
        final DialogListener.SelectDeviceLisenter mCallback;
        try {
            mCallback = (DialogListener.SelectDeviceLisenter) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(object.toString() + " must implement DialogListener");
        }

        Dialog deviceTypeDialog = new Dialog(activity);
        deviceTypeDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        deviceTypeDialog.setCancelable(false);
        deviceTypeDialog.setContentView(R.layout.dialog_select_device_type);
        deviceTypeDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        CardView cvPersonalDevice = (CardView) deviceTypeDialog.findViewById(R.id.cvPersonalDevice);
        CardView cvSharedDevice = (CardView) deviceTypeDialog.findViewById(R.id.cvSharedDevice);
        ImageView ivClose=deviceTypeDialog.findViewById(R.id.ivClose);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deviceTypeDialog.cancel();
            }
        });
        cvPersonalDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceTypeDialog.cancel();
                mCallback.personalDeviceSelected();
            }
        });
        cvSharedDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deviceTypeDialog.cancel();
                mCallback.shardDeviceSelected();
            }
        });


        deviceTypeDialog.show();
    }
    public static void showLocationSearchDialog(final Activity activity, Object object, List<LocationsResponse.Datum> locationList)  {
        final DialogListener.CallForHelpListener mCallback;
        LocationsAdapter locationsAdapter;
        try {
            mCallback = (DialogListener.CallForHelpListener) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(object.toString() + " must implement DialogListener");
        }
        Dialog locationSelectDialog = new Dialog(activity);
        locationSelectDialog.getWindow().setBackgroundDrawableResource(R.color.black_translucent);
        locationSelectDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        locationSelectDialog.setCancelable(true);
        locationSelectDialog.setContentView(R.layout.dialog_select_locations);
        locationSelectDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        EditText etSearchLocations=locationSelectDialog.findViewById(R.id.etSearchLocations);
        RecyclerView rvLocations=locationSelectDialog.findViewById(R.id.rvLocations);
        rvLocations.setHasFixedSize(true);
        rvLocations.setLayoutManager(new LinearLayoutManager(activity));
        locationsAdapter=new LocationsAdapter(activity,locationList, locationSelectDialog,(LocationsAdapter.ItemListener) activity);
        rvLocations.setAdapter(locationsAdapter);

        etSearchLocations.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                locationsAdapter.getFilter().filter(s.toString().trim());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        locationSelectDialog.show();
    }
    public static void showTeamSearchDialog(final Activity activity, Object object, List<TeamsResponse.Datum> locationList) {
        final DialogListener.CallForHelpListener mCallback;
        TeamsAdapter teamsAdapter;
        try {
            mCallback = (DialogListener.CallForHelpListener) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(object.toString() + " must implement DialogListener");
        }
        Dialog locationSelectDialog = new Dialog(activity);
        locationSelectDialog.getWindow().setBackgroundDrawableResource(R.color.black_translucent);
        locationSelectDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        locationSelectDialog.setCancelable(true);
        locationSelectDialog.setContentView(R.layout.dialog_select_teams);
        locationSelectDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        EditText etSearchTeams=locationSelectDialog.findViewById(R.id.etSearchTeams);
        Button btnSubmit=locationSelectDialog.findViewById(R.id.btnSubmit);
        RecyclerView rvTeams=locationSelectDialog.findViewById(R.id.rvTeams);
        rvTeams.setHasFixedSize(true);
        rvTeams.setLayoutManager(new LinearLayoutManager(activity));
        teamsAdapter=new TeamsAdapter(activity,locationList);
        rvTeams.setAdapter(teamsAdapter);
        etSearchTeams.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                teamsAdapter.getFilter().filter(s.toString().trim());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationSelectDialog.dismiss();
                mCallback.onTeamsSelected(teamsAdapter.getSelectedTeamsList());
            }
        });

        locationSelectDialog.show();
    }
    static int duration;
    static int amoungToupdate;
    static  Runnable runnable;
    public static void showWaitingAcceptanceDialog(final Activity activity, Object object, EmergencyCode emergencyCode) {
        final DialogListener.BaseListener mCallback;
        try {
            mCallback = (DialogListener.BaseListener) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(object.toString() + " must implement DialogListener");
        }

        Dialog dialog = new Dialog(activity);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_waiting_acceptance);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView tvDescription=dialog.findViewById(R.id.tv_dialog_description);
        TextView tvResponders=dialog.findViewById(R.id.tvResponders);
        TextView tvLocation=dialog.findViewById(R.id.tvLocation);
        Button btnAccept=dialog.findViewById(R.id.btnAccept);
        Button btnReject=dialog.findViewById(R.id.btnReject);
        ImageView ivPlay=dialog.findViewById(R.id.ivPlay);
        seekBar=dialog.findViewById(R.id.seekBar);
        tvCurrentTime=dialog.findViewById(R.id.tvCurrentTime);
        LinearLayout llAudioPlayer=dialog.findViewById(R.id.llAudioPlayer);
        tvDescription.setText(emergencyCode.getDescription()+"");
        tvLocation.setText(emergencyCode.getLocation()+"");
        if(emergencyCode.getResponseCount()!=null) {
            tvResponders.setText("(" + emergencyCode.getResponseCount() + ")R");
        }else{
            tvResponders.setVisibility(View.GONE);
        }
        if(emergencyCode.getVoiceData()!=null) {
            try {
                llAudioPlayer.setVisibility(View.VISIBLE);
                mediaPlayer.setDataSource(emergencyCode.getVoiceData());
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                         duration = mediaPlayer.getDuration();
                         amoungToupdate = duration / 100;
                         seekBar.setMax(duration);
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        seekBar.setProgress(0);
                    }
                });

            } catch (Exception e) {

            }
            ivPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mediaPlayer.isPlaying()) {
                        ivPlay.setImageDrawable(ContextCompat.getDrawable(activity,R.drawable.ic_play));
                        mHandler.removeMessages(SHOW_PROGRESS);
                        if(mediaPlayer!=null)mediaPlayer.pause();


                    }else{
                        ivPlay.setImageDrawable(ContextCompat.getDrawable(activity,R.drawable.ic_pause));
                        mediaPlayer.start();
                        mHandler.sendEmptyMessage(SHOW_PROGRESS);
                    }
                }
            });

        }else{
            llAudioPlayer.setVisibility(View.GONE);
        }
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               mCallback.onAcceptClicked(emergencyCode);
            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mCallback.onRejectClicked();
            }
        });

        dialog.show();
    }

    public static void showRejectionReasonDialog(final Activity activity, Object object) {
        final DialogListener.BaseListener mCallback;
        try {
            mCallback = (DialogListener.BaseListener) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(object.toString() + " must implement DialogListener");
        }

        Dialog rejectionReasonDialog = new Dialog(activity);
        rejectionReasonDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        rejectionReasonDialog.setCancelable(true);
        rejectionReasonDialog.setContentView(R.layout.dialog_rejection_reason);
        rejectionReasonDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Button btnAccept=rejectionReasonDialog.findViewById(R.id.btnAccept);
        Button btnCancel=rejectionReasonDialog.findViewById(R.id.btnCancel);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rejectionReasonDialog.show();
    }
    public static void showConfirmationDialog(final Activity activity, Object object) {
        final DialogListener.BaseListener mCallback;
        try {
            mCallback = (DialogListener.BaseListener) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(object.toString() + " must implement DialogListener");
        }

        confirmationDialog = new Dialog(activity);
        confirmationDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        confirmationDialog.setCancelable(true);
        confirmationDialog.setContentView(R.layout.dialog_confirmation);
        confirmationDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Button btnAccept=confirmationDialog.findViewById(R.id.btnAccept);
        Button btnCancel=confirmationDialog.findViewById(R.id.btnCancel);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialog.dismiss();
                mCallback.confirmClicked();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialog.dismiss();
               mCallback.CancelClicked();
            }
        });

        confirmationDialog.show();
    }
    private static class MessageHandler extends Handler {


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
    private static int setProgress(){
        int position = mediaPlayer.getCurrentPosition();
        seekBar.setProgress(position);
        tvCurrentTime.setText(stringForTime(position));
        return position;

    }



}
