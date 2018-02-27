package databaseTable.noteVocalesManager;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import Recycle_Adapter_vocal.CreateListVocal;
import databaseTable.DatabaseHelper;
import databaseTable.table.NotesVocalesTable;

/**
 * Created by romain on 12/11/17.
 */

public class NoteVocalesManagerWithDB implements NoteVocalesManagerItf {

    protected Context context;

    protected DatabaseHelper db;

    public NoteVocalesManagerWithDB(Context context)
    {
        this.context = context;

        db = new DatabaseHelper(context);
    }

    @Override
    public ArrayList<CreateListVocal> getAllVocales(int voyageID, String orderBy) {
        ArrayList<CreateListVocal> notesVocalesList = new ArrayList<CreateListVocal>();
        try{
            Dao<NotesVocalesTable, Integer> vocalDao = this.db.getNotesVocalDao();
            Iterator<NotesVocalesTable> it = vocalDao.query(vocalDao.queryBuilder().orderBy(orderBy, true).where().eq("voyageID", voyageID).prepare()).iterator();
            while(it.hasNext()) {
                NotesVocalesTable mVocalesVideosTable = it.next();

                CreateListVocal myNote = new CreateListVocal();
                myNote.setIdVoyage(voyageID);
                myNote.setTitle(mVocalesVideosTable.getTitre());
                myNote.setVocal_path(mVocalesVideosTable.getFileLink());
                myNote.setLatitude(mVocalesVideosTable.getLatitude());
                myNote.setLongitude(mVocalesVideosTable.getLongitude());

                /* DECODESIMPLEBITMAPFROMFILE A METTRE DANS PRESENTER ?????!!!! */
                myNote.setIdNoteVocal(mVocalesVideosTable.getNotesID());
                notesVocalesList.add(myNote);
            }
            return notesVocalesList;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createVocale(String titre, String description, int voyageID, String fileLink) {
        try {

            NotesVocalesTable myNote = new NotesVocalesTable(titre, description, voyageID, fileLink);
            this.db.getNotesVocalDao().create(myNote);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeVocaleById(int idNoteVocale) {
        try{
            this.db.getNotesVocalDao().deleteById(idNoteVocale);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void createVocaleWithLocation(String titre, String description, int voyageID, String fileLink, double latitude, double longitude) {
        try {

            NotesVocalesTable myNote = new NotesVocalesTable(titre, description, voyageID, fileLink, latitude, longitude);
            this.db.getNotesVocalDao().create(myNote);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
