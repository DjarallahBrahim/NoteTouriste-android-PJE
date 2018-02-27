package lamoot.djrbrahim.note.touristic.touristicnote.Note_text_function;

import databaseTable.AppManager;
import databaseTable.noteManager.NoteManagerItf;

/**
 * Created by ASUS on 15/11/2017.
 */

public class Get_NoteText_presenter implements Get_NoteText_presenterItf {

    protected Get_NoteText_UI_itf view;

    protected NoteManagerItf noteManager;

    public Get_NoteText_presenter(Get_NoteText_UI view) {

        this.view=view;
        noteManager=AppManager.instance.getNoteManager();
    }

    @Override
    public void getAllTextNote(int voyageID, String orderBy) {
        view.showTextNote(noteManager.getAllNote(voyageID, orderBy));
    }
}
