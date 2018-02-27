package databaseTable.notePhotoManager;

import java.util.ArrayList;

import Recycle_Adapter_gallery.CreateListImage;
import databaseTable.table.NotesPhotosVideosTable;

/**
 * Created by romain on 12/11/17.
 */

public interface NotePhotoManagerItf {

    ArrayList<CreateListImage> getAllPhoto(int voyageID, String orderBy);

    void createPhoto(String title, String description, int voyageID, String fileLink, String tags, String ville, String direction);

    void removePhotoById(int idNotePhoto);;

    NotesPhotosVideosTable getNoteById(int idNote);

    void editNote(String title, String description, String ville, String tags, int idNote);

    void createPhotoWithLocation(String titre, String description, int voyageID, String fileLink, String tags, String ville, String direction, double latitude, double longitude);
}
