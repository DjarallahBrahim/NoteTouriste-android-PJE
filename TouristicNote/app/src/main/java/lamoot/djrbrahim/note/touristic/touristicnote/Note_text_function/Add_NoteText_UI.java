package lamoot.djrbrahim.note.touristic.touristicnote.Note_text_function;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import Permitssion.Utils;
import databaseTable.table.NotesTable;
import lamoot.djrbrahim.note.touristic.touristicnote.R;

public class Add_NoteText_UI extends AppCompatActivity implements Add_NoteText_UI_itf{

    protected EditText title, description;
    protected  int idVoyage = 0;
    protected Add_NoteText_presenterItf presenter;
    protected int idNote = -1;
    protected double[] location; // 0 -> latitude , 1 -> longitude

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        idVoyage = getIntent().getIntExtra("idVoyage",0);
        idNote = getIntent().getIntExtra("idNote", -1);
        presenter=new  Add_NoteText_presenter(this) ;

        if(idNote != -1){
            NotesTable note = presenter.getNote(idNote);
            Toast.makeText(this,note.getTitre(),Toast.LENGTH_SHORT).show();
            title = (EditText)findViewById(R.id.title_new_note_editText);
            description = (EditText)findViewById(R.id.new_note_editText);
            title.setText(note.getTitre());
            description.setText(note.getDescription());

        }
        //Toast.makeText(this,idNote + "",Toast.LENGTH_SHORT).show();
        this.location = presenter.myGetLastPosition();

    }

    public void save(View view) {
        Toast.makeText(this,"Save clicked",Toast.LENGTH_SHORT).show();
        title = (EditText)findViewById(R.id.title_new_note_editText);
        description = (EditText)findViewById(R.id.new_note_editText);
        List<EditText> editeList=new ArrayList<EditText>(){};
        editeList.add(title);
        editeList.add(description);
        if(Utils.FieldRempli(editeList) && idNote == -1) {
            //presenter.creatVocalTable(title.getText().toString(), description.getText().toString(), idVoyage);
            presenter.createNoteWithLocation(title.getText().toString(), description.getText().toString(), idVoyage, location[0], location[1]);

            //Intent i = new Intent(this, Get_NoteText_UI.class);
            //i.putExtra("SendidVoyage", idVoyage);
            //startActivity(i);
            finish();
        }
        else
        {
            presenter.editNote(title.getText().toString(), description.getText().toString(), idNote);
            finish();
        }
    }
}
