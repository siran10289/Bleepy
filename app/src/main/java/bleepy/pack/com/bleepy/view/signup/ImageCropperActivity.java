

package bleepy.pack.com.bleepy.view.signup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;


import com.isseiaoki.simplecropview.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;


import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.utils.Constants;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

import static bleepy.pack.com.bleepy.utils.Constants.KEY_IMAGE_BYTE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_IMAGE_URI;


public class ImageCropperActivity extends BaseActivity {

    @BindView(R.id.cropImageView)CropImageView cropImageView;
    @BindView(R.id.BTN_Cancel)Button btnCancel;
    @BindView(R.id.BTN_Rotate)Button btnRotate;
    @BindView(R.id.BTN_Crop)Button btnCrop;
    private String isFrom_Camera_Gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_cropper);
        init();

    }

    private void init() {
        cropImageView.setCropMode(CropImageView.CropMode.CIRCLE);
        isFrom_Camera_Gallery = getIntent().getExtras().getString("FLAG");
        Uri selectedImageUri = Uri.parse(getIntent().getExtras().getString(KEY_IMAGE_URI));
        Log.e("URI:",selectedImageUri.toString());
        try {
            if (isFrom_Camera_Gallery.equalsIgnoreCase(Constants.ALERT_INTENT_CAMERA)) {
                    cropImageView.setImageBitmap(decodeUri(selectedImageUri,200));

            } else {

                cropImageView.setImageBitmap(decodeUri(selectedImageUri,200));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.BTN_Crop)
    public void onCropButtonClicked(){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            cropImageView.getCroppedBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
            Intent returnIntent = new Intent();
            returnIntent.putExtra(KEY_IMAGE_BYTE, stream.toByteArray());
            setResult(Constants.CROPING_CODE, returnIntent);
            finish();
            overridePendingTransition(R.anim.animation_left_right_enter, R.anim.animation_left_right_exit);
        }catch (Exception e){
            Log.e("Exception:",e.toString());
        }
    }
    @OnClick(R.id.BTN_Rotate)
    public void onRotateButtonClicked(){
        cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);

    }
    @OnClick(R.id.BTN_Cancel)
    public void onCancelButtonClicked(){
       /* ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cropImageView.getImageBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        Intent returnIntent = new Intent();
        returnIntent.putExtra(KEY_IMAGE_BYTE,stream.toByteArray());
        setResult(Constants.CROPING_CODE, returnIntent);*/
        finish();
        overridePendingTransition(R.anim.animation_left_right_enter,R.anim.animation_left_right_exit);

    }
    private Bitmap decodeUri(Uri uri, final int requiredSize) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ImageCropperActivity.this.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while(true) {
            if(width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri), null, o2);
    }
    private void getDropboxIMGSize(Uri uri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void setError(int resID, String message) {

    }
}