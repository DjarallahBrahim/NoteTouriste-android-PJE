package lamoot.djrbrahim.note.touristic.touristicnote.Photo_note_function;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Permitssion.Utils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import databaseTable.AppManager;
import databaseTable.notePhotoManager.NotePhotoManagerItf;
import databaseTable.table.NotesPhotosVideosTable;

/**
 * Created by ASUS on 16/11/2017.
 */

public class Add_PhotoNote_presenter implements Add_PhotoNote_presenterItf {

    protected Add_PhotoNote_UI_itf view;
    protected NotePhotoManagerItf photoManager;
    protected Context context;
    protected String mCurrentPhotoPath;
    private FusedLocationProviderClient mFusedLocationClient;


    Add_PhotoNote_presenter(Add_PhotoNote_UI view){
        this.view=view;
        context=view.getApplicationContext();
        photoManager= AppManager.instance.getNotePhotoManager();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

    }


    @Override
    public File createImageFile() throws IOException {
        //creation lien de l'image
        String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName="JPEG_"+timeStamp+"_";

        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);


        File image=File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );


        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void creatPhotoNote(String title, String description, int voyageID, String fileLink, String tags, String ville, String direction) {
        photoManager.createPhoto(title,description,voyageID,fileLink,tags,ville,direction);
    }



    @Override
    public void galleryAddPic(ImageView mImageView) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);

        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        view.showPic(bitmap);
    }

    @Override
    public void getLocation(Activity ac , final int PLACE_PICKER_REQUEST) {

    }

    @Override
    public boolean checkGPS(final Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("GPS is OFF")
                    .setContentText("Do you want to turn it ON ?")
                    .setConfirmText("Yes,i want !")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Utils.gpsTurnOn(context);
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
            return false;

        }
        return true;
    }
@Override
    public boolean permissionCheker(){
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return false;
    }
    else return true;
}

    public NotesPhotosVideosTable getNote(int idNote) {
        return AppManager.instance.getNotePhotoManager().getNoteById(idNote);
    }


    public void showBitmap(ImageView mImageView, String currentPath)
    {
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;


        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPath, bmOptions);
        view.showPic(bitmap);
    }

    public void editNote(String title, String description, String ville, String tags, int idNote) {
        AppManager.instance.getNotePhotoManager().editNote(title, description, ville, tags, idNote);

    }

    @Override
    public double[] myGetLastPosition() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        final double location_result[] = new double[3];
        Task<Location> task = mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    location_result[0] = location.getLatitude();
                    location_result[1] = location.getLongitude();

                    Toast.makeText(context,"We get location",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context,"can't get location",Toast.LENGTH_SHORT).show();

                }
            }
        });
        return location_result;
    }

    @Override
    public void creatPhotoNoteWithLocation(String titre, String description, int voyageID, String fileLink, String ville, String tags, String direction, double latitude, double longitude) {
        photoManager.createPhotoWithLocation(titre,description,voyageID,fileLink,tags,ville,direction, latitude, longitude);

    }
}

