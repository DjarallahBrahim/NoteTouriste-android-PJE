package Permitssion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;

import java.util.List;

import databaseTable.AppManager;
import lamoot.djrbrahim.note.touristic.touristicnote.activity_home_map_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.activity_home_map_UI_itf;

/**
 * Created by ASUS on 28/10/2017.
 */


public class Utils {
   public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;
    public static final int REQUEST_GPS=2;

    /* function to propose permission for user
    * @param activity
    * @param permission name
     */
    public static boolean requestPermission(Activity activity, String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return false;
        }
        return true;
    }
    /* function to activet GPS
     * @param Context
     */
	  public static void gpsTurnOn(Context context){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            ((Activity)context).startActivityForResult(intent,REQUEST_GPS);

           // context.startActivity(intent);
      }

    /*
        function to test if all field rempli
        @param List of EditText
     */
    public static boolean FieldRempli(List<EditText> listView){
        for(EditText text : listView){
            if(text.getText().length()<=1){
                text.setHintTextColor(Color.RED);
                text.setHint(text.getHint());
                return false;
            }
        }
        return true;
    }
}
