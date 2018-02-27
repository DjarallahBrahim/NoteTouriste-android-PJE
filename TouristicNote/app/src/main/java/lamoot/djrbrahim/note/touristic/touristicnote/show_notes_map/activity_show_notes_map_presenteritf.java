package lamoot.djrbrahim.note.touristic.touristicnote.show_notes_map;

import java.util.ArrayList;

import Recycle_Adapter_gallery.CreateListImage;
import Recycle_Adapter_vocal.CreateListVocal;
import Recycler_Adapter_notes.NoteObject;

/**
 * Created by romain on 20/12/17.
 */

public interface activity_show_notes_map_presenteritf {

    ArrayList<NoteObject> getAllNotesEcrites(int idVoyage);

    ArrayList<CreateListImage> getAllNotesPhotos(int idVoyage);

    ArrayList<CreateListVocal> getAllNotesVocales(int idVoyage);
}
