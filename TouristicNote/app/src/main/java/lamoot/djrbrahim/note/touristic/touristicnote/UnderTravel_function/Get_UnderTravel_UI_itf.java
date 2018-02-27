package lamoot.djrbrahim.note.touristic.touristicnote.UnderTravel_function;

import android.view.View;

import java.util.List;

import databaseTable.table.VoyageTable;

/**
 * Created by lamoot on 07/12/17.
 */

public interface Get_UnderTravel_UI_itf {

    void afficheSousVoyage(List<VoyageTable> voyageTableList);

    void addSousVoyage(View view);
}
