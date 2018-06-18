package bleepy.pack.com.bleepy.utils.customdialog;


import java.util.List;

/**
 * Created by Siranjeevi on 7/6/18.
 */
public interface DialogListener {

    public interface AlertDialogListener{
        void onOKPressed(String status);
        void onCancelPressed();
    }
    public interface NetworkDialogListener{
        void onOKPressed();
        void onSettingsPressed();
    }
    public interface SelectDeviceLisenter{
        void personalDeviceSelected();
        void shardDeviceSelected();
    }



}
