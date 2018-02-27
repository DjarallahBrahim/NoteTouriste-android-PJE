package lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import Permitssion.Utils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import databaseTable.AppManager;
import databaseTable.table.VoyageTable;
import databaseTable.voyageManager.VoyageManagerItf;

/**
 * Created by romain on 25/11/17.
 */

public class Add_Voyage_presenter implements Add_Voyage_presenteritf {

    protected Add_Voyage_UI_itf view;
    protected VoyageManagerItf voyageManager;
    protected Context context;

    public Add_Voyage_presenter(Add_Voyage_UI view)
    {
        this.view = view;
        this.context=view.getApplicationContext();
        this.voyageManager = AppManager.instance.getVoyageManager();
    }

    @Override
    public int saveTravel(String titre, String description, double latitude, double longitude) {
        VoyageTable newVoyage=new VoyageTable();
        newVoyage.setTitre(titre);
        newVoyage.setDescription(description);
        newVoyage.setLatitude(latitude);
        newVoyage.setLongitude(longitude);

        //create voyageTable + get id
        int idVoyage = voyageManager.createVoyage(newVoyage);

        return idVoyage;
    }

    @Override
    public int saveUnderTravel(String titre, String description, double latitude, double longitude, int voyageParentID)
    {
        VoyageTable newVoyage=new VoyageTable();
        newVoyage.setTitre(titre);
        newVoyage.setDescription(description);
        newVoyage.setLatitude(latitude);
        newVoyage.setLongitude(longitude);
        newVoyage.setVoyageParentID(voyageParentID);

        //create voyageTable + get id
        int idVoyage = voyageManager.createVoyage(newVoyage);

        return idVoyage;
    }

    @Override
    public void locationUderTravel() {

    }
    @Override
    public boolean permissionCheker(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        else return true;
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
}
