package lamoot.djrbrahim.note.touristic.touristicnote.UnderTravel_function;

import android.support.v7.app.AppCompatActivity;


import databaseTable.AppManager;
import databaseTable.voyageManager.VoyageManagerItf;

/**
 * Created by lamoot on 07/12/17.
 */

public class Get_UnderTravel_presenter implements Get_UnderTravel_presenteritf{

    protected Get_UnderTravel_UI_itf view;
    protected VoyageManagerItf voyageManager;

    public Get_UnderTravel_presenter(Get_UnderTravel_UI view)
    {
        this.view = view;
        this.voyageManager = AppManager.instance.getVoyageManager();
    }

    @Override
    public void getAllSousVoyage(int idVoyage) {
        view.afficheSousVoyage(voyageManager.getUnderTravel(idVoyage));
    }
}
