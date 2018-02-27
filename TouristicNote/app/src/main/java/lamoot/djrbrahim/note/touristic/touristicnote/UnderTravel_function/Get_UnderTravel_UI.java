package lamoot.djrbrahim.note.touristic.touristicnote.UnderTravel_function;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import Recycle_Adapter_voyage.MyAdapter_Voyage;
import databaseTable.table.VoyageTable;
import lamoot.djrbrahim.note.touristic.touristicnote.R;
import lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function.Add_Voyage_UI;

/**
 * Created by lamoot on 07/12/17.
 */

public class Get_UnderTravel_UI extends AppCompatActivity implements Get_UnderTravel_UI_itf{

    private Get_UnderTravel_presenteritf presenter;
    RecyclerView mRecyclerView;

    private int voyageParentID;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undervoyage_gallery);
        presenter = new Get_UnderTravel_presenter(this);

        voyageParentID=getIntent().getIntExtra("SendidVoyage",0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        voyageParentID=getIntent().getIntExtra("SendidVoyage",0);
        presenter.getAllSousVoyage(voyageParentID);
    }

    @Override
    public void afficheSousVoyage(List<VoyageTable> voyageTableList) {
        /*for (VoyageTable voyage : voyageTableList) {
            LatLng myPosition = new LatLng(voyage.getLatitude(), voyage.getLongitude());
            if(myPosition.latitude!=0) {
                MarkerOptions options = new MarkerOptions();
                options.snippet(String.valueOf(voyage.getVoyageID()))
                        .position(myPosition);
                googleMap.addMarker(options);
            }
        }*/
        mRecyclerView = (RecyclerView) findViewById(R.id.undervoyage_gallery);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        MyAdapter_Voyage mAdapter = new MyAdapter_Voyage(voyageTableList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void addSousVoyage(View view)
    {
        startActivity(new Intent(this, Add_Voyage_UI.class).putExtra("idVoyage",voyageParentID).putExtra("sousVoyage",true));
    }
}
