package bleepy.pack.com.bleepy.view.signup;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.utils.AppUtils;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import okhttp3.MediaType;

/**
 * Created by Siranjeevi on 19/07/17.
 */
public class PackageInfoInteractorImpl implements PackageInfoInteractor {

    private static final MediaType IMAGE_MEDIA_TYPE = MediaType.parse("image/*");
    private static final String IMAGE_FILE_NAME = "photo%d.jpg";
    private static final int IN_SAMPLE_SIZE = 5;

    public static final String[] CAMERA_GALLERY_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Context context;
    private Uri photoUri;
    private File photoBitmapFile;
    private ImageProcessingListener imageProcessingListener;
    private Bitmap photoBitmap;

    public PackageInfoInteractorImpl(Context context, PrefsManager mPrefsmanger) {
        this.context = context;

    }
    @Override
    public void getPermissions(final PermissionGrantedListener permissionGrantedListener) {
        Dexter.checkPermissions(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.getGrantedPermissionResponses().isEmpty()) {
                    permissionGrantedListener.onPermissionDenied();
                } else {
                    permissionGrantedListener.onPermissionGranted();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }, CAMERA_GALLERY_PERMISSIONS);
    }


    @Override
    public boolean checkForCameraPermission() {
        return AppUtils.hasPermissions(context,CAMERA_GALLERY_PERMISSIONS);
    }

    @Override
    public Intent getPhotoCaptureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = createImageFile();
        if (imageFile != null) {
           // photoUri = Uri.fromFile(imageFile);
            photoUri= FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", imageFile);
        }

        if (photoUri != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        } else {
            Log.i("", "dispatchTakePictureIntent: " + "Uri is null");
        }
        return takePictureIntent;
    }

    @Override
    public Intent getPhotoGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return intent;
    }

    @Override
    public void removeTempFiles() {
        if (photoBitmapFile != null) {
            photoBitmapFile.delete();
            photoBitmapFile = null;
        }
    }

    @Override
    public void handleCapturedPhoto(ImageProcessingListener processingListener) {

        /*imageProcessingListener = processingListener;
        imageProcessingListener.onProcessingStart();
        photoBitmap = resizeCapturedPhoto();
        photoBitmapFile = createFileFromBitmap(photoBitmap);
        imageProcessingListener.onProcessingComplete();*/
        imageProcessingListener = processingListener;
        imageProcessingListener.onProcessingComplete(photoUri.toString());

    }
    @Override
    public void handleGalleryPhoto(Uri uri, ImageProcessingListener processingListener) {

        photoUri = uri;
        handleCapturedPhoto(processingListener);
    }

    private File createFileFromBitmap(Bitmap source) {
        File file = new File(context.getFilesDir(), String.format(Locale.US, IMAGE_FILE_NAME, System.currentTimeMillis()));
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            source.compress(Bitmap.CompressFormat.PNG, 0, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                source.recycle();
                source = null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private File createImageFile() {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/"+context.getString(R.string.app_name));
        storageDir.mkdirs();
        File imageFile = null;
        try {
            imageFile = File.createTempFile("temp", ".jpg", storageDir);

        } catch (IOException e) {
            Log.e("Exception:",e.toString());
        }
        return imageFile;
    }


    private Bitmap resizeCapturedPhoto() {
        InputStream input = null;
        try {
            input = context.getContentResolver().openInputStream(photoUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = IN_SAMPLE_SIZE;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bmOptions);
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public File getPhotoFile() {
        return photoBitmapFile;
    }

    @Override
    public String getBitmapImg() {
    // get the base 64 string
        String imgString = Base64.encodeToString(getBytesFromBitmap(photoBitmap), Base64.NO_WRAP);
        return imgString;
    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
}
