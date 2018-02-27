package databaseTable.voyageManager;

import java.util.List;

import databaseTable.table.VoyageTable;

/**
 * Created by romain on 12/11/17.
 */

public interface VoyageManagerItf {

    List<VoyageTable> getAllVoyage();

    int createVoyage(VoyageTable voyagetab);

    void removeVoyageById(int id);

    VoyageTable getVoyageById(int id);

    List<VoyageTable> getUnderTravel(int idVoyage);

    List<VoyageTable> getAllVoyageParents();
}
