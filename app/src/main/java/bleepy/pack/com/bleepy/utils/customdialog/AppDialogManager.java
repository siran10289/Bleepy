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
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.models.callforhelp.LocationsResponse;
import bleepy.pack.com.bleepy.models.callforhelp.TeamsResponse;
import bleepy.pack.com.bleepy.models.common.EmergencyCode;
import bleepy.pack.com.bleepy.models.emegencycalllog.CodeLogMembersResponse;
import bleepy.pack.com.bleepy.utils.AppUtils;
import bleepy.pack.com.bleepy.view.adapter.CodeLogMembersAdapter;
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
    static MediaPlayer mediaPlayer;
    private static final int SHOW_PROGRESS = 2;
    static AppCompatSeekBar seekBar;
    static TextView tvCurrentTime;
    public static Handler mHandler = new MessageHandler();
    public static Dialog confirmationDialog;
    public static Dialog emergencyAlertDialog;


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
        if(emergencyAlertDialog!=null&&emergencyAlertDialog.isShowing()){
            emergencyAlertDialog.dismiss();
        }

        emergencyAlertDialog = new Dialog(activity);
        emergencyAlertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        emergencyAlertDialog.setCancelable(false);
        emergencyAlertDialog.setContentView(R.layout.dialog_waiting_acceptance);
        emergencyAlertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView tvDescription=emergencyAlertDialog.findViewById(R.id.tv_dialog_description);
        TextView tvResponders=emergencyAlertDialog.findViewById(R.id.tvResponders);
        TextView tvLocation=emergencyAlertDialog.findViewById(R.id.tvLocation);
        Button btnAccept=emergencyAlertDialog.findViewById(R.id.btnAccept);
        Button btnReject=emergencyAlertDialog.findViewById(R.id.btnReject);
        ImageView ivPlay=emergencyAlertDialog.findViewById(R.id.ivPlay);
        seekBar=emergencyAlertDialog.findViewById(R.id.seekBar);
        tvCurrentTime=emergencyAlertDialog.findViewById(R.id.tvCurrentTime);
        LinearLayout llAudioPlayer=emergencyAlertDialog.findViewById(R.id.llAudioPlayer);
        tvDescription.setText(emergencyCode.getDescription()+"");
        tvLocation.setText(emergencyCode.getLocation()+"");
        if(mediaPlayer==null){
            mediaPlayer=new MediaPlayer();
        }
        if(emergencyCode.getResponseCount()!=null) {
            tvResponders.setText("(" + emergencyCode.getResponseCount() + ")R");
        }else{
            tvResponders.setText("(" + 0 + ")R");
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
                        ivPlay.setImageDrawable(ContextCompat.getDrawable(activity,R.drawable.ic_play));
                    }
                });
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                });

            } catch (Exception e) {

            }
            ivPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mediaPlayer.isPlaying()) {
                        ivPlay.setImageDrawable(ContextCompat.getDrawable(activity,R.drawable.ic_play));
                        if(mediaPlayer!=null)mediaPlayer.pause();
                        mHandler.removeMessages(SHOW_PROGRESS);


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
                //dialog.dismiss();
                mHandler.removeMessages(SHOW_PROGRESS);
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer=null;
                emergencyAlertDialog.dismiss();
                mCallback.onAcceptClicked(emergencyCode);
            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeMessages(SHOW_PROGRESS);
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer=null;
                emergencyAlertDialog.dismiss();
               mCallback.onRejectClicked(emergencyCode);
            }
        });

        emergencyAlertDialog.show();
    }

    public static void showRejectionReasonDialog(final Activity activity, Object object,EmergencyCode emergencyCode) {
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
        Button btnOK=rejectionReasonDialog.findViewById(R.id.btnOK);
        Button btnCancel=rejectionReasonDialog.findViewById(R.id.btnCancel);
        EditText etRejectionReason=rejectionReasonDialog.findViewById(R.id.etRejectionReason);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectionReasonDialog.dismiss();
                mCallback.onRejectReasonOKClicked(etRejectionReason.getText().toString().trim());
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               rejectionReasonDialog.dismiss();
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
        if(!AppUtils.isAppIsInBackground(activity) ){
            confirmationDialog.show();
        }
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
    public static void showCustomDialog(final Activity activity, String title,String message) {


        Dialog rejectionReasonDialog = new Dialog(activity);
        rejectionReasonDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        rejectionReasonDialog.setCancelable(true);
        rejectionReasonDialog.setContentView(R.layout.dialog_common);
        rejectionReasonDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Button btnOK=rejectionReasonDialog.findViewById(R.id.btnOK);
        TextView tvTitle=rejectionReasonDialog.findViewById(R.id.tvTitle);
        TextView tvMessage=rejectionReasonDialog.findViewById(R.id.tvMessage);
        tvTitle.setText(title);
        tvMessage.setText(message);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectionReasonDialog.dismiss();

            }
        });


        rejectionReasonDialog.show();
    }
    public static void showCodeLogMembersDialog(final Activity activity, Object object, List<CodeLogMembersResponse.Datum> membersList) {
        final DialogListener.EmergencyCodeLogListener mCallback;
        CodeLogMembersAdapter teamsAdapter;
        try {
            mCallback = (DialogListener.EmergencyCodeLogListener) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(object.toString() + " must implement DialogListener");
        }
        Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawableResource(R.color.black_translucent);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_code_info);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        Button btnCancelCode=dialog.findViewById(R.id.btnCancelCode);
        TextView tvCodeID=dialog.findViewById(R.id.tvCodeID);
        tvCodeID.setText(membersList.get(0).getTeamname()+"");
        RecyclerView rvTeamMembers=dialog.findViewById(R.id.rvTeamMembers);
        rvTeamMembers.setHasFixedSize(true);
        rvTeamMembers.setLayoutManager(new LinearLayoutManager(activity));
        teamsAdapter=new CodeLogMembersAdapter(activity,membersList);
        rvTeamMembers.setAdapter(teamsAdapter);

        btnCancelCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvCodeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



}
