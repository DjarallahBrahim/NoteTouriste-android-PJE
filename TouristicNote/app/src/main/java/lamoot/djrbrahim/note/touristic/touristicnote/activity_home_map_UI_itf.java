package lamoot.djrbrahim.note.touristic.touristicnote;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import databaseTable.table.VoyageTable;

/**
 * Created by ASUS on 16/11/2017.
 */

public interface activity_home_map_UI_itf {
    void export_import(View view);

    void afficheVoyage(List<VoyageTable> voyageTableList);
    void moveToCurrentLocation(GoogleMap googleMap, LatLng currentLocation);
    void showSnackBar(String message, int color);
}
