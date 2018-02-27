package lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import lamoot.djrbrahim.note.touristic.touristicnote.Audio_note_function.Get_AudioNote_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.Note_text_function.Get_NoteText_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.Photo_note_function.Get_PhotoNote_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.R;
import lamoot.djrbrahim.note.touristic.touristicnote.UnderTravel_function.Get_UnderTravel_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.activity_home_map_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.show_notes_map.activity_show_notes_map_UI;

/**
 * Created by romain on 25/11/17.
 */

public class Get_AllTypesNotes_activity_UI extends AppCompatActivity implements Get_AllTypesNotes_activity_UI_itf {

    private int idVoyage;
    private boolean underVoyage;

    private String titleVoyage;
    private String descrpVoyage;

    private Get_AllTypesNotes_activity_presenteritf presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_get__memoris);

        TextView titleVoyageTV = (TextView)findViewById(R.id.titleVoyage);
        TextView descrpVoyageTV = (TextView)findViewById(R.id.descrpVoyage);


        /* Get idVoyage */
        this.idVoyage = getIntent().getIntExtra("SendidVoyage",0);
        /* New presenter */
        this.presenter = new Get_AllTypesNotes_activity_presenter(this.idVoyage);

        /* Get title and description and display it */
        this.titleVoyage = presenter.getTitleVoyage();
        this.descrpVoyage = presenter.getDescriptionVoyage();

        titleVoyageTV.setText(titleVoyage);
        descrpVoyageTV.setText(descrpVoyage);

        underVoyage=getIntent().getBooleanExtra("sousVoyage",false);



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getPicture(View view) {
        idVoyage=getIntent().getIntExtra("SendidVoyage",0);
        Intent i=new Intent(this,Get_PhotoNote_UI.class);
        i.putExtra("SendidVoyage",idVoyage);
        startActivity(i);
    }

    @Override
    public void getAudio(View view) {
        idVoyage=getIntent().getIntExtra("SendidVoyage",0);
        Intent i=new Intent(this,Get_AudioNote_UI.class);
        i.putExtra("SendidVoyage",idVoyage);
        startActivity(i);
    }

    @Override
    public void getNote(View view) {
        idVoyage=getIntent().getIntExtra("SendidVoyage",0);
        Intent i = new Intent(this,Get_NoteText_UI.class);
        i.putExtra("SendidVoyage",idVoyage);
        startActivity(i);
    }

    @Override
    public void getUnderTravel(View view)
    {
        idVoyage = getIntent().getIntExtra("SendidVoyage", 0);
        Intent i = new Intent(this,Get_UnderTravel_UI.class);
        i.putExtra("SendidVoyage",idVoyage);
        startActivity(i);
    }

    public void getShowNotesMap(View view)
    {
        idVoyage = getIntent().getIntExtra("SendidVoyage",0);
        Intent i = new Intent(this, activity_show_notes_map_UI.class);
        i.putExtra("SendidVoyage", idVoyage);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,activity_home_map_UI.class));
        finish();
    }

}
