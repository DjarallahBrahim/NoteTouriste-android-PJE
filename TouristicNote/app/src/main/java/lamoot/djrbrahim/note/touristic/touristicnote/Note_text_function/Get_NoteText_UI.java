package lamoot.djrbrahim.note.touristic.touristicnote.Note_text_function;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import Recycler_Adapter_notes.MyAdapter_notes;
import Recycler_Adapter_notes.NoteObject;
import lamoot.djrbrahim.note.touristic.touristicnote.R;
import lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function.Get_AllTypesNotes_activity_UI;

/**
 * Created by romain on 01/11/17.
 */

public class Get_NoteText_UI extends AppCompatActivity implements Get_NoteText_UI_itf {



    private int idVoyage;

    private RecyclerView mRecyclerView;
    private MyAdapter_notes mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Get_NoteText_presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_gallery);

        presenter=new Get_NoteText_presenter(this);
        idVoyage = getIntent().getIntExtra("SendidVoyage",-1);

        this.configSpinner();

    }


    @Override
    protected void onResume() {
        super.onResume();
        idVoyage = getIntent().getIntExtra("SendidVoyage",-1);

        presenter.getAllTextNote(idVoyage, "notesID");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this,"BACK ID = " + String.valueOf(idVoyage),Toast.LENGTH_SHORT).show();

        Intent it = new Intent(this,Get_AllTypesNotes_activity_UI.class);
        it.putExtra("SendidVoyage", idVoyage);

        startActivity(it);
        finish();
    }

    @Override
    public void showTextNote(ArrayList<NoteObject> NoteList) {
        int resId = R.anim.layout_animation_from_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.notes_list_view);
        mRecyclerView.setLayoutAnimation(animation);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter_notes(NoteList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void addNewNote(View view)
    {
        Intent it=new Intent(this,Add_NoteText_UI.class);
        it.putExtra("idVoyage",idVoyage);
        Toast.makeText(this,String.valueOf(idVoyage),Toast.LENGTH_SHORT).show();
        startActivity(it);
    }

    public void configSpinner()
    {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_notetext);
        List<String> list = new ArrayList<String>();
        list.add("Date");
        list.add("Titre");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this.presenter, idVoyage));
    }
}

class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener
{
    private Get_NoteText_presenterItf presenter;
    private int idVoyage;
    public CustomOnItemSelectedListener(Get_NoteText_presenter presenter, int idVoyage) {
        this.presenter = presenter;
        this.idVoyage = idVoyage;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
        String item_selected = parent.getItemAtPosition(position).toString();
        if(item_selected.equals("Date"))
            presenter.getAllTextNote(idVoyage, "notesID");
        else if(item_selected.equals("Titre"))
            presenter.getAllTextNote(idVoyage, "titre");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
