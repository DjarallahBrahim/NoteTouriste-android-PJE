package databaseTable.noteManager;

import java.util.ArrayList;

import Recycler_Adapter_notes.NoteObject;
import databaseTable.table.NotesTable;

/**
 * Created by romain on 12/11/17.
 */

public interface NoteManagerItf {

    //ArrayList<NoteObject> getAllNote(int voyageID);

    ArrayList<NoteObject> getAllNote(int voyageID, String orderBy);

    void createNote(String title, String description, int voyageID);

    void removeNoteById(int idNote);

    long count();

    NotesTable getNoteById(int idNote);

    void editNote(String title, String description, int idNote);

    void createNoteWithLocation(String titre, String description, int idVoyage, double latitude, double longitude);
}
