package Recycle_Adapter_gallery;

import android.graphics.Bitmap;

/**
 * Created by ASUS on 26/10/2017.
 */

public class CreateListImage {
    private String image_title;
    private Integer image_id;
    private Bitmap bitmap;
    private String image_path;
    private int idNotePhoto;
    private int idVoyage;

    private double latitude;
    private double longitude;


    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String android_version_name) {
        this.image_title = android_version_name;
    }

    public Integer getImage_ID() {
        return image_id;
    }

    public void setImage_ID(Integer android_image_url) {
        this.image_id = android_image_url;
    }

    public int getIdNotePhoto(){
        return this.idNotePhoto;
    }

    public void setIdNotePhoto(int idNotePhoto)
    {
        this.idNotePhoto = idNotePhoto;
    }


    public int getIdVoyage() {
        return idVoyage;
    }

    public void setIdVoyage(int idVoyage) {
        this.idVoyage = idVoyage;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
