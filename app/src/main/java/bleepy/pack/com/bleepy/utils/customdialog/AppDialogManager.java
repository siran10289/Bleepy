package bleepy.pack.com.bleepy.utils.customdialog;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import bleepy.pack.com.bleepy.R;

import static bleepy.pack.com.bleepy.utils.Constants.ALERT_INTENT_CAMERA;
import static bleepy.pack.com.bleepy.utils.Constants.ALERT_INTENT_GALLERY;
import static bleepy.pack.com.bleepy.utils.Constants.ALERT_REMOVE;

public class AppDialogManager {
    static Dialog networkAlertDialog = null;

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


}
