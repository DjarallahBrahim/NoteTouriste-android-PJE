package lamoot.djrbrahim.note.touristic.touristicnote.Export_Import_function;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.provider.ContactsContract;

import java.io.File;
import java.io.IOException;

import Permitssion.Utils;
import databaseTable.DatabaseHelper;

/**
 * Created by lamoot on 30/11/17.
 */

class Export_Import_presenter implements Export_Import_presenteritf {

    protected Export_Import_UI_itf view;
    protected Context context;

    public Export_Import_presenter(Export_Import_UI view)
    {
        this.view = view;
        this.context=view.getApplicationContext();
    }

    @Override
    public boolean exportDB(String exportFileName) {
        DatabaseHelper db = new DatabaseHelper(this.context);
        try {
            File storageFile = this.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

            return db.exportDatabase(storageFile, exportFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean importDB(String importFileName) {
        DatabaseHelper db = new DatabaseHelper(this.context);
        try {
            return db.importDatabase(importFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
