package databaseTable.noteManager;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import Recycler_Adapter_notes.NoteObject;
import databaseTable.DatabaseHelper;
import databaseTable.table.NotesTable;

/**
 * Created by romain on 12/11/17.
 */

public class NoteManagerWithDB implements NoteManagerItf {

    protected Context context;

    protected DatabaseHelper db;

    public NoteManagerWithDB(Context context)
    {
        this.context = context;

        db = new DatabaseHelper(context);
    }

    @Override
    public ArrayList<NoteObject> getAllNote(int voyageID, String orderBy) {
        ArrayList<NoteObject> notesList = new ArrayList<NoteObject>();
        try{
            Dao<NotesTable, Integer> notesDao = this.db.getNotesTableDao();
            Iterator<NotesTable> it = notesDao.query(notesDao.queryBuilder().orderBy(orderBy, true).where().eq("voyageID", voyageID).prepare()).iterator();
            while(it.hasNext()) {
                NotesTable mNotesTable = it.next();

                NoteObject myNote = new NoteObject();
                myNote.setTitle(mNotesTable.getTitre());
                myNote.setDescription(mNotesTable.getDescription());
                myNote.setIdVoyage(mNotesTable.getVoyageID());
                myNote.setIdNote(mNotesTable.getNotesID());
                myNote.setLatitude(mNotesTable.getLatitude());
                myNote.setLongitude(mNotesTable.getLongitude());

                notesList.add(myNote);
            }
            return notesList;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createNote(String title, String description, int voyageID) {
        try {

            NotesTable myNote = new NotesTable(title, description, voyageID);
            this.db.getNotesTableDao().create(myNote);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeNoteById(int idNote) {
        try{
            this.db.getNotesTableDao().deleteById(idNote);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public long count() {
        try {
            return db.getNotesTableDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public NotesTable getNoteById(int idNote) {
        try{
            Dao<NotesTable, Integer> noteDao = this.db.getNotesTableDao();
            Iterator<NotesTable> it = noteDao.query(noteDao.queryBuilder().where().eq("notesID", idNote).prepare()).iterator();

            while(it.hasNext())
            {
                NotesTable myNote = it.next();
                return myNote;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void editNote(String title, String description, int idNote) {
        try {
            Dao<NotesTable, Integer> noteDao = this.db.getNotesTableDao();
            UpdateBuilder<NotesTable, Integer> updateBuilder = noteDao.updateBuilder();
            updateBuilder.where().eq("notesID", idNote);
            updateBuilder.updateColumnValue("titre", title );
            updateBuilder.updateColumnValue("description" , description);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createNoteWithLocation(String titre, String description, int idVoyage, double latitude, double longitude) {
        try {

            NotesTable myNote = new NotesTable(titre, description, idVoyage, latitude, longitude);
            this.db.getNotesTableDao().create(myNote);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
