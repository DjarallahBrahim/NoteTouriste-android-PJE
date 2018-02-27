package lamoot.djrbrahim.note.touristic.touristicnote.Audio_note_function;

import android.content.Context;

import databaseTable.AppManager;
import databaseTable.noteVocalesManager.NoteVocalesManagerItf;

/**
 * Created by ASUS on 15/11/2017.
 */

public class Get_AudioNote_Presenter implements Get_AudioNote_PresenterItf {

    protected Get_AudioNote_UI_itf view;

    protected NoteVocalesManagerItf vocalManager;

    protected Context context;

    public Get_AudioNote_Presenter(Get_AudioNote_UI view) {
        this.view=view;
        context=view.getApplicationContext();
        vocalManager= AppManager.instance.getNoteVocalesManager();
    }

    @Override
    public void getAllAudioNote(int voyageID, String orderBy) {
        view.showAudioNote(vocalManager.getAllVocales(voyageID, orderBy));
    }



}
