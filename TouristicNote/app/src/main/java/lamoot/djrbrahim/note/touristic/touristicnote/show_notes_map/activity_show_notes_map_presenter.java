package lamoot.djrbrahim.note.touristic.touristicnote.show_notes_map;

import java.util.ArrayList;

import Recycle_Adapter_gallery.CreateListImage;
import Recycle_Adapter_vocal.CreateListVocal;
import Recycler_Adapter_notes.NoteObject;
import databaseTable.AppManager;
import databaseTable.noteManager.NoteManagerItf;
import databaseTable.notePhotoManager.NotePhotoManagerItf;
import databaseTable.noteVocalesManager.NoteVocalesManagerItf;

/**
 * Created by romain on 20/12/17.
 */

public class activity_show_notes_map_presenter implements activity_show_notes_map_presenteritf {

    private NoteManagerItf noteManager;
    private NotePhotoManagerItf notePhotoManager;
    private NoteVocalesManagerItf noteVocalesManager;

    public activity_show_notes_map_presenter()
    {
        this.noteManager = AppManager.instance.getNoteManager();
        this.notePhotoManager = AppManager.instance.getNotePhotoManager();
        this.noteVocalesManager = AppManager.instance.getNoteVocalesManager();
    }

    @Override
    public ArrayList<NoteObject> getAllNotesEcrites(int idVoyage) {
        return noteManager.getAllNote(idVoyage, "notesID");
    }

    @Override
    public ArrayList<CreateListImage> getAllNotesPhotos(int idVoyage)
    {
        return notePhotoManager.getAllPhoto(idVoyage, "notesID");
    }

    @Override
    public ArrayList<CreateListVocal> getAllNotesVocales(int idVoyage) {
        return noteVocalesManager.getAllVocales(idVoyage, "notesID");
    }


}
