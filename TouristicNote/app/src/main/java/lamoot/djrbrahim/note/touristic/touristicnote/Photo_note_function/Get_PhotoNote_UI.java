package lamoot.djrbrahim.note.touristic.touristicnote.Photo_note_function;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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

import Recycle_Adapter_gallery.CreateListImage;
import Recycle_Adapter_gallery.MyAdapter_gallery;
import lamoot.djrbrahim.note.touristic.touristicnote.R;
import lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function.Get_AllTypesNotes_activity_UI;

public class Get_PhotoNote_UI extends AppCompatActivity implements Get_PhotoNote_UI_itf {
    private int idVoyage;
    private GridLayoutManager lLayout;
    private Get_PhotoNote_presenterItr presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_gallery);
        idVoyage=getIntent().getIntExtra("SendidVoyage",0);
        Toast.makeText(this,idVoyage + "",Toast.LENGTH_SHORT).show();


        presenter=new Get_PhotoNote_presenter(this);
        this.configSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        idVoyage=getIntent().getIntExtra("SendidVoyage",0);
        presenter.getAllPhoto(idVoyage, "notesID");
    }

    public void takePic(View view) {
        Intent it=new Intent(this,Add_PhotoNote_UI.class);
        it.putExtra("idVoyage",idVoyage);
        startActivity(it);
    }


    @Override
    public void generateRecycleViewPic(ArrayList<CreateListImage> imageList){
        int resId = R.anim.layout_animation_from_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        lLayout = new GridLayoutManager(this, 4);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lLayout);
        MyAdapter_gallery adapter = new MyAdapter_gallery(imageList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void configSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_notesphotos);
        List<String> list = new ArrayList<String>();
        list.add("Date");
        list.add("Titre");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListenerNotesPhotos(this.presenter, idVoyage));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,Get_AllTypesNotes_activity_UI.class).putExtra("SendidVoyage",idVoyage));
        finish();
    }
}

class CustomOnItemSelectedListenerNotesPhotos implements AdapterView.OnItemSelectedListener
{
    private Get_PhotoNote_presenterItr presenter;
    private int idVoyage;
    public CustomOnItemSelectedListenerNotesPhotos(Get_PhotoNote_presenterItr presenter, int idVoyage) {
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
            presenter.getAllPhoto(idVoyage, "notesID");
        else if(item_selected.equals("Titre"))
            presenter.getAllPhoto(idVoyage, "titre");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}