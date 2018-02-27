package lamoot.djrbrahim.note.touristic.touristicnote.Export_Import_function;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import Permitssion.Utils;
import lamoot.djrbrahim.note.touristic.touristicnote.Audio_note_function.Add_AudioNote_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.R;
import lamoot.djrbrahim.note.touristic.touristicnote.activity_home_map_UI;

/**
 * Created by lamoot on 30/11/17.
 */

public class Export_Import_UI extends AppCompatActivity implements Export_Import_UI_itf{

    private int idVoyage;
    private Export_Import_presenteritf presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_import);

        idVoyage = getIntent().getIntExtra("idVoyage",0);
        presenter = new Export_Import_presenter(this);
        Utils.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //presenter = new Add_NoteText_presenter() ;
    }

     public void exportDB(View view)
     {
         if(presenter.exportDB("exportBDTEST")) {
             Toast.makeText(this, "EXPORTED", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(this, activity_home_map_UI.class));
         }
         else {
             Toast.makeText(this, "Not exported... :(", Toast.LENGTH_SHORT).show();
         }
     }

     public void importDB(View view)
     {
         if(presenter.importDB("exportBDTEST")) {
             Toast.makeText(this, "IMPORTED", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(this, activity_home_map_UI.class));
         }
         else {
             Toast.makeText(this, "Not exported... :(", Toast.LENGTH_SHORT).show();
         }


     }
}
