package databaseTable.notePhotoManager;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import Permitssion.DecodeBitmapFromFile;
import Recycle_Adapter_gallery.CreateListImage;
import databaseTable.DatabaseHelper;
import databaseTable.table.NotesPhotosVideosTable;

/**
 * Created by romain on 12/11/17.
 */

public class NotePhotoManagerWithDB implements NotePhotoManagerItf {

    protected Context context;

    protected DatabaseHelper db;

    public NotePhotoManagerWithDB(Context context)
    {
        this.context = context;

        db = new DatabaseHelper(context);
    }

    @Override
    public ArrayList<CreateListImage> getAllPhoto(int voyageID, String orderBy) {
        ArrayList<CreateListImage> notesPhotosList = new ArrayList<CreateListImage>();
        try{
            Dao<NotesPhotosVideosTable, Integer> imageDao = this.db.getNotesPhotosVideosDao();
            Iterator<NotesPhotosVideosTable> it = imageDao.query(imageDao.queryBuilder().orderBy(orderBy, true).where().eq("voyageID", voyageID).prepare()).iterator();
            while(it.hasNext()) {
                NotesPhotosVideosTable mNotesPhotosVideosTable = it.next();


                CreateListImage myNote = new CreateListImage();
                myNote.setImage_ID(voyageID);
                myNote.setImage_title(mNotesPhotosVideosTable.getTitre());
                myNote.setImage_path(mNotesPhotosVideosTable.getFileLink());
                /* DECODESIMPLEBITMAPFROMFILE A METTRE DANS PRESENTER ?????!!!! */
                myNote.setBitmap(DecodeBitmapFromFile.decodeSampledBitmapFromFile(mNotesPhotosVideosTable.getFileLink(),500,300));
                myNote.setIdNotePhoto(mNotesPhotosVideosTable.getNotesID());
                myNote.setLatitude(mNotesPhotosVideosTable.getLatitude());
                myNote.setLongitude(mNotesPhotosVideosTable.getLongitude());
                notesPhotosList.add(myNote);
            }
            return notesPhotosList;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createPhoto(String title, String description, int voyageID, String fileLink, String tags, String ville, String direction) {
        try {

            NotesPhotosVideosTable myNote = new NotesPhotosVideosTable(title, description, voyageID, fileLink, tags, ville, direction);
            this.db.getNotesPhotosVideosDao().create(myNote);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removePhotoById(int idNotePhoto) {
        try{
            this.db.getNotesPhotosVideosDao().deleteById(idNotePhoto);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public NotesPhotosVideosTable getNoteById(int idNote) {
        try{
            Dao<NotesPhotosVideosTable, Integer> noteDao = this.db.getNotesPhotosVideosDao();
            Iterator<NotesPhotosVideosTable> it = noteDao.query(noteDao.queryBuilder().where().eq("notesID", idNote).prepare()).iterator();

            while(it.hasNext())
            {
                NotesPhotosVideosTable myNote = it.next();
                return myNote;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void editNote(String title, String description, String ville, String tags, int idNote) {
        try {
            Dao<NotesPhotosVideosTable, Integer> noteDao = this.db.getNotesPhotosVideosDao();
            UpdateBuilder<NotesPhotosVideosTable, Integer> updateBuilder = noteDao.updateBuilder();
            updateBuilder.where().eq("notesID", idNote);
            updateBuilder.updateColumnValue("titre", title );
            updateBuilder.updateColumnValue("description" , description);
            updateBuilder.updateColumnValue("ville" , ville);
            updateBuilder.updateColumnValue("tags" , tags);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createPhotoWithLocation(String titre, String description, int voyageID, String fileLink, String tags, String ville, String direction, double latitude, double longitude) {
        try {

            NotesPhotosVideosTable myNote = new NotesPhotosVideosTable(titre, description, voyageID, fileLink, tags, ville, direction, latitude, longitude);
            this.db.getNotesPhotosVideosDao().create(myNote);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
