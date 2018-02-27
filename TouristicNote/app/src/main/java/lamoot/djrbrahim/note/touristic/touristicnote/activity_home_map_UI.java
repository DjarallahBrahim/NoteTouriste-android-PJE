package lamoot.djrbrahim.note.touristic.touristicnote;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import Permitssion.Utils;
import databaseTable.table.VoyageTable;
import Recycle_Adapter_voyage.MyAdapter_Voyage;
import lamoot.djrbrahim.note.touristic.touristicnote.Export_Import_function.Export_Import_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function.Add_Voyage_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function.Get_AllTypesNotes_activity_UI;

public class activity_home_map_UI extends AppCompatActivity implements activity_home_map_UI_itf, OnMapReadyCallback {
    private SlidingUpPanelLayout mLayout;
    private activity_home_map_presenterItf presenter;
    private GoogleMap googleMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maptravel);
        //init SlidingUpPanelLayout of activity home !
        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        if (mLayout.getAnchorPoint() == 1.0f) {
            mLayout.setAnchorPoint(0.7f);
            mLayout.setPanelState(PanelState.ANCHORED);
        }
        mLayout.setFadeOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(PanelState.COLLAPSED);
            }
        });

        //presenter=new activity_home_map_presenter(activity_home_map_UI.this);
        presenter=new activity_home_map_presenter(activity_home_map_UI.this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo, menu);
        MenuItem item = menu.findItem(R.id.action_toggle);
        if (mLayout != null) {
            if (mLayout.getPanelState() == PanelState.HIDDEN) {
                item.setTitle(R.string.action_show);
            } else {
                item.setTitle(R.string.action_hide);
            }
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_toggle: {
                if (mLayout != null) {
                    if (mLayout.getPanelState() != PanelState.HIDDEN) {
                        mLayout.setPanelState(PanelState.HIDDEN);
                        item.setTitle(R.string.action_show);
                    } else {
                        mLayout.setPanelState(PanelState.COLLAPSED);
                        item.setTitle(R.string.action_hide);
                    }
                }
                return true;
            }
            case R.id.action_anchor: {
                if (mLayout != null) {
                    if (mLayout.getAnchorPoint() == 1.0f) {
                        mLayout.setAnchorPoint(0.7f);
                        mLayout.setPanelState(PanelState.ANCHORED);
                        item.setTitle(R.string.action_anchor_disable);
                    } else {
                        mLayout.setAnchorPoint(1.0f);
                        mLayout.setPanelState(PanelState.COLLAPSED);
                        item.setTitle(R.string.action_anchor_enable);
                    }
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        inisMapFragment();
        /* Get permissions */
        Utils.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        /* Get location */
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public void inisMapFragment(){
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        //presenter.inisMapFragment(mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setUpMap();
        presenter.myGetLastPosition(googleMap, mFusedLocationClient,mLayout);

        //Show all the travel
        presenter.get_AllVoyage_bd();
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(),Get_AllTypesNotes_activity_UI.class);
                int uid = Integer.parseInt(marker.getSnippet());
                marker.setSnippet("");
                intent.putExtra("SendidVoyage",uid);
                startActivity(intent);
                return false;
            }
        });
    }

    //Add new travel !
    public void addTravel(View view) {
        if(presenter.getLatitude()==0) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Location")
                    .setContentText("Do you want to save your location ?")
                    .setConfirmText("Yes!")
                    .setCancelText("No")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            presenter.checkGPS(activity_home_map_UI.this);
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent i = new Intent(activity_home_map_UI.this, Add_Voyage_UI.class);
                            startActivity(i);
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }else{
            Intent i = new Intent(activity_home_map_UI.this, Add_Voyage_UI.class);
            i.putExtra("latitude", presenter.getLatitude());
            i.putExtra("longitude", presenter.getLongitude());
            startActivity(i);
        }

    }

    @Override
    public void export_import(View view)
    {
        startActivity(new Intent(this, Export_Import_UI.class));

    }

    @Override
    public void afficheVoyage( List<VoyageTable> voyageTableList) {
        for (VoyageTable voyage : voyageTableList) {
            LatLng myPosition = new LatLng(voyage.getLatitude(), voyage.getLongitude());
            if(myPosition.latitude!=0) {
                MarkerOptions options = new MarkerOptions();
                options.snippet(String.valueOf(voyage.getVoyageID()))
                        .position(myPosition);
                googleMap.addMarker(options);
            }
        }
        /* Liaison entre localisation et son voyage */
        int resId = R.anim.layout_animation_from_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);

        mRecyclerView = (RecyclerView) findViewById(R.id.rec1);

        mRecyclerView.setLayoutAnimation(animation);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        MyAdapter_Voyage mAdapter = new MyAdapter_Voyage(voyageTableList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utils.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }else{
                }
            }
        }
    }

    public void setUpMap() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Utils.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        //googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void moveToCurrentLocation(GoogleMap googleMap, LatLng currentLocation) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 5));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
    }

    @Override
    public void showSnackBar(String message, int color) {
        Snackbar snackbar = Snackbar
                .make(mLayout, message, Snackbar.LENGTH_LONG)
                .setActionTextColor(color);
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onMapReady(googleMap);
    }
}
