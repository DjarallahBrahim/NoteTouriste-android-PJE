package lamoot.djrbrahim.note.touristic.touristicnote.Photo_note_function;

import databaseTable.AppManager;
import databaseTable.notePhotoManager.NotePhotoManagerItf;
/**
 * Created by ASUS on 16/11/2017.
 */

public class Get_PhotoNote_presenter implements Get_PhotoNote_presenterItr {

    protected Get_PhotoNote_UI_itf view;
    protected NotePhotoManagerItf photoManager;

    Get_PhotoNote_presenter(Get_PhotoNote_UI view){
        this.view=view;
        photoManager= AppManager.instance.getNotePhotoManager();
    }

    @Override
    public void getAllPhoto(int idVoyage, String orderBy) {
        view.generateRecycleViewPic(photoManager.getAllPhoto(idVoyage,orderBy));
    }
}
