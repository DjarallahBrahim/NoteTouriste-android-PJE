package lamoot.djrbrahim.note.touristic.touristicnote.Audio_note_function;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import Recycle_Adapter_vocal.CreateListVocal;
import Recycle_Adapter_vocal.MyAdapter_vocal;
import lamoot.djrbrahim.note.touristic.touristicnote.R;
import lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function.Get_AllTypesNotes_activity_UI;

public class Get_AudioNote_UI extends AppCompatActivity implements Get_AudioNote_UI_itf {

    private int idVoyage;
    private Get_AudioNote_Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_audio);

        idVoyage=getIntent().getIntExtra("SendidVoyage",0);

        presenter = new Get_AudioNote_Presenter(this);

        this.configSpinner();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getAllAudioNote(idVoyage, "notesID");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,Get_AllTypesNotes_activity_UI.class).putExtra("SendidVoyage",idVoyage));
        finish();
    }

    public void addNewAudio(View view) {
        Intent it=new Intent(this,Add_AudioNote_UI.class);
        it.putExtra("idVoyage",idVoyage);
        startActivity(it);
    }

    @Override
    public void showAudioNote(ArrayList<CreateListVocal> audioList) {
        int resId = R.anim.layout_animation_from_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.audiogalory);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //get all audioNote from BD and use it in adapter
        MyAdapter_vocal adapter = new MyAdapter_vocal(audioList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void configSpinner()
    {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_notesvocales);
        List<String> list = new ArrayList<String>();
        list.add("Date");
        list.add("Titre");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListenerNotesVocales(this.presenter, idVoyage));
    }
}


class CustomOnItemSelectedListenerNotesVocales implements AdapterView.OnItemSelectedListener
{
    private Get_AudioNote_PresenterItf presenter;
    private int idVoyage;
    public CustomOnItemSelectedListenerNotesVocales(Get_AudioNote_PresenterItf presenter, int idVoyage) {
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
            presenter.getAllAudioNote(idVoyage, "notesID");
        else if(item_selected.equals("Titre"))
            presenter.getAllAudioNote(idVoyage, "titre");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}