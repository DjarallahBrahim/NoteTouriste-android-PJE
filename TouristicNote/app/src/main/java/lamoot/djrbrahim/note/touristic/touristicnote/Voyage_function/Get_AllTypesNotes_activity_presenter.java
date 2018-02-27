package lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function;

import databaseTable.AppManager;
import databaseTable.voyageManager.VoyageManagerItf;

/**
 * Created by romain on 25/11/17.
 */

public class Get_AllTypesNotes_activity_presenter implements Get_AllTypesNotes_activity_presenteritf {

    private VoyageManagerItf voyageManager;
    private int idVoyage;

    public Get_AllTypesNotes_activity_presenter(int idVoyage)
    {
        this.voyageManager = AppManager.instance.getVoyageManager();
        this.idVoyage = idVoyage;
    }

    @Override
    public String getTitleVoyage() {
        return voyageManager.getVoyageById(idVoyage).getTitre();
    }

    @Override
    public String getDescriptionVoyage() {
        return voyageManager.getVoyageById(idVoyage).getDescription();
    }
}
