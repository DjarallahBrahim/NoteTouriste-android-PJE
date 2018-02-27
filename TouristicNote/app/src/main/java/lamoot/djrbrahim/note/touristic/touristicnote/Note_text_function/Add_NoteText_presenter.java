package lamoot.djrbrahim.note.touristic.touristicnote.Note_text_function;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import databaseTable.AppManager;
import databaseTable.table.NotesTable;

/**
 * Created by ASUS on 15/11/2017.
 */

public class Add_NoteText_presenter implements Add_NoteText_presenterItf {

    private FusedLocationProviderClient mFusedLocationClient;
    private Context context;
    private Add_NoteText_UI_itf view;

    public Add_NoteText_presenter(Add_NoteText_UI view)
    {
        this.view = view;
        this.context = view.getApplicationContext();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }


    @Override
    public void creatVocalTable(String note, String description, int voyageID) {
        AppManager.instance.getNoteManager().createNote(note, description, voyageID);
    }

    @Override
    public NotesTable getNote(int idNote)
    {
        return AppManager.instance.getNoteManager().getNoteById(idNote);
    }

    @Override
    public void editNote(String title, String description, int idNote) {
        AppManager.instance.getNoteManager().editNote(title, description, idNote);
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
    public void createNoteWithLocation(String titre, String description, int idVoyage, double latitude, double longitude) {
        AppManager.instance.getNoteManager().createNoteWithLocation(titre, description, idVoyage, latitude, longitude);

    }
}
