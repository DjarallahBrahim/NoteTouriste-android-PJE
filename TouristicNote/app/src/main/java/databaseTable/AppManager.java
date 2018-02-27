package databaseTable;

import android.app.Application;
import android.util.Log;

import databaseTable.noteManager.NoteManagerItf;
import databaseTable.noteManager.NoteManagerWithDB;
import databaseTable.notePhotoManager.NotePhotoManagerItf;
import databaseTable.notePhotoManager.NotePhotoManagerWithDB;
import databaseTable.noteVocalesManager.NoteVocalesManagerItf;
import databaseTable.noteVocalesManager.NoteVocalesManagerWithDB;
import databaseTable.voyageManager.VoyageManagerItf;
import databaseTable.voyageManager.VoyageManagerWithDB;

/**
 * Created by romain on 12/11/17.
 */

public class AppManager extends Application {

    static public AppManager instance;

    protected VoyageManagerItf voyageManager;

    protected NoteManagerItf noteManager;

    protected NotePhotoManagerItf notePhotoManager;

    protected NoteVocalesManagerItf noteVocalesManager;

    /**
     *
     * @return
     */
    public AppManager getInstance() {
        return instance;
    }

    /**
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println(" AppManager created !!");
        Log.e("AppManager", "AppManager created !! :)");
        // init instance

        init();

    }

    private void init() {
        //noteManager = new NoteManager(this);
        voyageManager = new VoyageManagerWithDB(this);
        noteManager = new NoteManagerWithDB(this);
        notePhotoManager = new NotePhotoManagerWithDB(this);
        noteVocalesManager = new NoteVocalesManagerWithDB(this);


        instance = this;
    }

    public NoteManagerItf getNoteManager() {

        return noteManager;
    }

    public VoyageManagerItf getVoyageManager()
    {
        return voyageManager;
    }

    public NotePhotoManagerItf getNotePhotoManager()
    {
        return notePhotoManager;
    }

    public NoteVocalesManagerItf getNoteVocalesManager()
    {
        return noteVocalesManager;
    }

}

