package databaseTable;

import android.Manifest;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import Permitssion.Utils;
import databaseTable.table.NotesPhotosVideosTable;
import databaseTable.table.NotesTable;
import databaseTable.table.NotesVocalesTable;
import databaseTable.table.VoyageTable;

/**
 * Created by lamoot on 12/10/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "voyage.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<VoyageTable, Integer> voyageDao;
    private Dao<NotesPhotosVideosTable, Integer> notesPhotosVideosDao;
    private Dao<NotesTable, Integer> NotesTableDao;
    private Dao<NotesVocalesTable, Integer> NotesVocalDao;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
    {
        try{
            TableUtils.createTable(connectionSource, VoyageTable.class);
            TableUtils.createTable(connectionSource, NotesTable.class);
            TableUtils.createTable(connectionSource, NotesPhotosVideosTable.class);
            TableUtils.createTable(connectionSource, NotesVocalesTable.class);
        } catch (java.sql.SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create databases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVer, int newVer)
    {
        try
        {
            TableUtils.dropTable(connectionSource, VoyageTable.class, true);
            TableUtils.dropTable(connectionSource, NotesTable.class, true);
            TableUtils.dropTable(connectionSource, NotesPhotosVideosTable.class, true);
            TableUtils.dropTable(connectionSource, NotesVocalesTable.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (java.sql.SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new version " + newVer, e);

        }

    }

    public Dao<VoyageTable, Integer> getVoyageDao() throws java.sql.SQLException {
        if(voyageDao == null)
        {
            voyageDao = getDao(VoyageTable.class);
        }
        return voyageDao;
    }

    public Dao<NotesPhotosVideosTable, Integer> getNotesPhotosVideosDao() throws java.sql.SQLException {
        if(notesPhotosVideosDao == null)
        {
            notesPhotosVideosDao = getDao(NotesPhotosVideosTable.class);
        }
        return notesPhotosVideosDao;
    }

    public Dao<NotesTable, Integer> getNotesTableDao() throws java.sql.SQLException {
        if(NotesTableDao == null)
        {
            NotesTableDao = getDao(NotesTable.class);
        }
        return NotesTableDao;
    }

    public Dao<NotesVocalesTable, Integer> getNotesVocalDao() throws java.sql.SQLException {
        if(NotesVocalDao == null)
        {
            NotesVocalDao = getDao(NotesVocalesTable.class);
        }
        return NotesVocalDao;
    }

    public boolean exportDatabase(File storageDir, String exportFileName) throws IOException {
        // Get directories
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        // Create files handles
        File newDb = new File(getWritableDatabase().getPath()); /* path of voyage.db */
        File exportedFile = new File(sd , exportFileName);
        if (newDb.exists()) {
            System.err.println("Export DB " + exportedFile.getCanonicalPath() + " --> " + exportFileName);
            FileUtils.copyFile(newDb, exportedFile);
            return true;
        }
        return false;
    }

    public boolean importDatabase(String exportFileName) throws IOException {
    // Close the SQLiteOpenHelper so it will commit the created empty
    // database to internal storage.
            close();
            File sd = Environment.getExternalStorageDirectory();
            File newDb = new File(sd, exportFileName);
            File oldDb = new File(getWritableDatabase().getPath());
            if (newDb.exists()) {
                System.err.println("Import DB " + oldDb.getCanonicalPath() + "<--" + exportFileName );
                FileUtils.copyFile(newDb, oldDb);
    // Access the copied database so SQLiteHelper will cache it and mark
    // it as created.
                getWritableDatabase();
                return true;
            }
            return false;
    }

}
