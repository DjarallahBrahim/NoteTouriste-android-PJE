package lamoot.djrbrahim.note.touristic.touristicnote;

import android.content.Context;
import android.widget.ProgressBar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/**
 * Created by ASUS on 16/11/2017.
 */

public interface activity_home_map_presenterItf {

    void get_AllVoyage_bd();
    void myGetLastPosition(GoogleMap googleMap, FusedLocationProviderClient mFusedLocationClient, SlidingUpPanelLayout mLayout);
    double getLatitude();
    double getLongitude();
    boolean checkGPS(final Context context);
}
