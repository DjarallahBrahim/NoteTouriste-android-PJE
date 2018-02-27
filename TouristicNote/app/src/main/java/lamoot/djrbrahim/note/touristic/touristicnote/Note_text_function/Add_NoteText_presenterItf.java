package lamoot.djrbrahim.note.touristic.touristicnote.Note_text_function;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import databaseTable.table.NotesTable;

/**
 * Created by ASUS on 15/11/2017.
 */

public interface Add_NoteText_presenterItf {
    void creatVocalTable(String note, String description, int voyageID);

    NotesTable getNote(int idNote);

    void editNote(String title, String description, int idNote);

    double[] myGetLastPosition();

    void createNoteWithLocation(String s, String s1, int idVoyage, double latitude, double longitude);
}
