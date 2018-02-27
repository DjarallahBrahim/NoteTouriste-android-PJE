package lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

import Permitssion.Utils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import lamoot.djrbrahim.note.touristic.touristicnote.Photo_note_function.Add_PhotoNote_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.R;

/**
 * Created by romain on 25/11/17.
 */

public class Add_Voyage_UI extends AppCompatActivity implements Add_Voyage_UI_itf{

    protected EditText titre;
    protected EditText description;
    protected double latitude;
    protected double longitude;

    protected int idVoyageParent;
    protected boolean underVoyage;

    protected Add_Voyage_presenteritf presenter;

    protected int PLACE_PICKER_REQUEST=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_action);

        this.presenter = new Add_Voyage_presenter(this);

        idVoyageParent = getIntent().getIntExtra("idVoyage",-1);
        underVoyage=getIntent().getBooleanExtra("sousVoyage",false);
        this.latitude = getIntent().getDoubleExtra("latitude",0);
        this.longitude = getIntent().getDoubleExtra("longitude",0);

        if(underVoyage){
            findViewById(R.id.underTravel).setVisibility(View.VISIBLE);
        }
        if(latitude==0 || longitude==0){
            findViewById(R.id.locationplus).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void saveTravel(View view) {
        titre = (EditText)findViewById(R.id.title_travel_editText);
        description = (EditText)findViewById(R.id.travel_description_editText);

        List<EditText> editeList=new ArrayList<EditText>(){};
        editeList.add(titre);
        editeList.add(description);
        if(Utils.FieldRempli(editeList)) {
            Intent it = new Intent(this, Get_AllTypesNotes_activity_UI.class);

            if(idVoyageParent == -1) {
                int idVoyage = presenter.saveTravel(titre.getText().toString(), description.getText().toString(), this.latitude, this.longitude);
                //it.putExtra("idVoyage", idVoyage);
                it.putExtra("SendidVoyage", idVoyage);

            }
            else
            {
                int idVoyage = presenter.saveUnderTravel(titre.getText().toString(), description.getText().toString(), this.latitude, this.longitude, idVoyageParent);
                //it.putExtra("idVoyage", idVoyage);
                it.putExtra("SendidVoyage", idVoyage);
                it.putExtra("sousVoyage",true);

                //it.putExtra("idVoyageParent", idVoyageParent);
            }
            //it.putExtra("titleVoyage", titre.getText().toString());
            //it.putExtra("descrpVoyage", description.getText().toString());
            startActivity(it);
        }
    }

    public void getLocationYnderVoyage(View view) {
        /* Get permissions */
        Utils.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(presenter.permissionCheker()&&presenter.checkGPS(this)) {
            try {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      if(requestCode ==PLACE_PICKER_REQUEST && resultCode==RESULT_OK){
            Place place = PlacePicker.getPlace(this, data);
            if (place!=null){
                this.latitude = place.getLatLng().latitude;
                this.longitude = place.getLatLng().longitude;
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Location")
                        .setContentText("We get your location")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }else{
                //PLACE IS NULL
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Location")
                        .setContentText("We can not " +
                                "get your location")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        }
    }
}
