package lamoot.djrbrahim.note.touristic.touristicnote.show_notes_map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import Permitssion.Utils;
import Recycle_Adapter_gallery.CreateListImage;
import Recycle_Adapter_vocal.CreateListVocal;
import Recycler_Adapter_notes.NoteObject;
import databaseTable.AppManager;
import lamoot.djrbrahim.note.touristic.touristicnote.Audio_note_function.Get_AudioNote_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.Note_text_function.Add_NoteText_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.Photo_note_function.Add_PhotoNote_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.R;

/**
 * Created by romain on 20/12/17.
 */

public class activity_show_notes_map_UI extends AppCompatActivity implements OnMapReadyCallback, activity_show_notes_map_UI_itf {


    private GoogleMap googleMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private int idVoyage;
    protected activity_show_notes_map_presenteritf presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        idVoyage = getIntent().getIntExtra("SendidVoyage",0);
        presenter = new activity_show_notes_map_presenter();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setUpMap();

        ArrayList<NoteObject> notesList = presenter.getAllNotesEcrites(idVoyage);
        ArrayList<CreateListImage> notesPhotosList = presenter.getAllNotesPhotos(idVoyage);
        ArrayList<CreateListVocal> notesVocalesList = presenter.getAllNotesVocales(idVoyage);


        for (NoteObject note : notesList) {
            LatLng myPosition = new LatLng(note.getLatitude(), note.getLongitude());
            if(myPosition.latitude!=0) {
                MarkerOptions options = new MarkerOptions();
                options.snippet(String.valueOf(note.getIdNote()))
                        .position(myPosition);
                googleMap.addMarker(options).setTag("notesEcrites");
            }
        }

        for(CreateListImage note : notesPhotosList)
        {
            Log.e("show_note_map", ""+note.getLatitude());
            Log.e("show_note_map", ""+note.getLongitude());
            LatLng myPosition = new LatLng(note.getLatitude(), note.getLongitude());
            if(myPosition.latitude!=0) {
                MarkerOptions options = new MarkerOptions();
                options.snippet(String.valueOf(note.getIdNotePhoto()))
                        .position(myPosition);
                googleMap.addMarker(options).setTag("notesPhotos");
            }
        }

        for(CreateListVocal note : notesVocalesList)
        {
            LatLng myPosition = new LatLng(note.getLatitude(), note.getLongitude());
            if(myPosition.latitude!=0) {
                MarkerOptions options = new MarkerOptions();
                options.snippet(String.valueOf(note.getIdNoteVocal()))
                        .position(myPosition);
                googleMap.addMarker(options).setTag("notesVocales");
            }
        }



        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = null;
                if(marker.getTag().equals("notesEcrites"))
                    intent = new Intent(getApplicationContext(),Add_NoteText_UI.class);
                else if(marker.getTag().equals("notesPhotos"))
                    intent = new Intent(getApplicationContext(),Add_PhotoNote_UI.class);
                else if(marker.getTag().equals("notesVocales"))
                    intent = new Intent(getApplicationContext(),Get_AudioNote_UI.class);

                int id = Integer.parseInt(marker.getSnippet());
                marker.setSnippet("");
                intent.putExtra("idNote",id);
                intent.putExtra("idVoyage", idVoyage);
                intent.putExtra("SendidVoyage", idVoyage);
                startActivity(intent);
                return false;
            }
        });
    }

    public void setUpMap() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Utils.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }
}
