package lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function;

import android.content.Context;

/**
 * Created by romain on 25/11/17.
 */

public interface Add_Voyage_presenteritf {

    int saveTravel(String titre, String description, double latitude, double longitude);

    int saveUnderTravel(String titre, String description, double latitude, double longitude, int voyageParentID);

    void locationUderTravel();

    boolean permissionCheker();

    boolean checkGPS(Context context);
}
