package databaseTable.noteVocalesManager;

import java.util.ArrayList;

import Recycle_Adapter_vocal.CreateListVocal;

/**
 * Created by romain on 12/11/17.
 */

public interface NoteVocalesManagerItf {

    ArrayList<CreateListVocal> getAllVocales(int voyageID, String orderBy);

    void createVocale(String titre, String description, int voyageID, String fileLink);

    void removeVocaleById(int idNoteVocale);

    void createVocaleWithLocation(String titre, String description, int voyageID, String fileLink, double latitude, double longitude);
}
