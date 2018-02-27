package lamoot.djrbrahim.note.touristic.touristicnote;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import Permitssion.Utils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import databaseTable.AppManager;
import databaseTable.voyageManager.VoyageManagerItf;

/**
 * Created by ASUS on 16/11/2017.
 */

public class activity_home_map_presenter implements activity_home_map_presenterItf {
    protected activity_home_map_UI_itf view;
    protected VoyageManagerItf voyageManagerItf;
    private Context context;
    protected double latitude;
    protected double longitude;


    activity_home_map_presenter(activity_home_map_UI view) {
        this.view = view;
        context = view.getApplicationContext();
        voyageManagerItf = AppManager.instance.getVoyageManager();
    }

    @Override
    public void get_AllVoyage_bd() {
        view.afficheVoyage(voyageManagerItf.getAllVoyageParents());
    }

    @Override
    public void myGetLastPosition(final GoogleMap googleMap, FusedLocationProviderClient mFusedLocationClient, final SlidingUpPanelLayout mLayout) {
        //test perm
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        Task<Location> task = mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    setLatitudeLongitude(lat,lon);
                    LatLng loc=new LatLng(lat,lon);
                    view.moveToCurrentLocation(googleMap,loc);
                    view.showSnackBar("we Get Your location",Color.BLUE);
                } else {
                    view.showSnackBar("we CAN'T get your location",Color.RED);
                }
            }
        });
    }


    public void setLatitudeLongitude(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return this.latitude;
    }

    public double getLongitude()
    {
        return this.longitude;
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
        else return false;
    }

}
