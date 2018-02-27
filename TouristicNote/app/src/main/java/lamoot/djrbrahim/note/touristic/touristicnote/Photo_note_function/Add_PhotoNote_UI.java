package lamoot.djrbrahim.note.touristic.touristicnote.Photo_note_function;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Permitssion.Utils;
import databaseTable.table.NotesPhotosVideosTable;
import lamoot.djrbrahim.note.touristic.touristicnote.R;

public class Add_PhotoNote_UI extends AppCompatActivity implements Add_PhotoNote_UI_itf,GoogleApiClient.OnConnectionFailedListener {

    protected String mCurrentPhotoPath;
    protected int REQUEST_TAKE_PHOTO=2;
    protected int PLACE_PICKER_REQUEST=1;
    protected EditText title,desc,ville,tags;
    protected TextView direction;
    protected  int idVoyage=0;
    protected int idNote = -1;
    protected Add_PhotoNote_presenter presenter;
    protected double[]location; // 0 -> latitude , 1 -> longitude

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_picture);


        idVoyage=getIntent().getIntExtra("idVoyage",0);
        idNote = getIntent().getIntExtra("idNote", -1);
        presenter=new Add_PhotoNote_presenter(this);
        location = presenter.myGetLastPosition();

        if(idNote != -1){
            NotesPhotosVideosTable note = presenter.getNote(idNote);
            title = (EditText)findViewById(R.id.title_pic);
            desc = (EditText)findViewById(R.id.description_picture_editText);
            ville = (EditText) findViewById(R.id.ville_pic);
            tags = (EditText) findViewById(R.id.tag_picture_editText);

            title.setText(note.getTitre());
            desc.setText(note.getDescription());
            ville.setText(note.getVille());
            tags.setText(note.getTags());
            mCurrentPhotoPath = note.getFileLink();

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && idNote != -1) {
            this.importPic(presenter.getNote(idNote));
        }

    }

    public void take_Pic(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            //creation de fichier pour la photo
            File photoFile=null;
            try{
                photoFile = presenter.createImageFile();
                mCurrentPhotoPath=photoFile.getAbsolutePath();
                if(photoFile!=null){
                    Uri photoURI= FileProvider.getUriForFile(this,"First_provider_Pic",photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                    startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
                }
            }catch(IOException ex){

            }

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_TAKE_PHOTO && resultCode==RESULT_OK){
            ImageView mImageView = (ImageView) findViewById(R.id.picPreview);
            presenter.galleryAddPic(mImageView);
        }else if(requestCode ==PLACE_PICKER_REQUEST){
            Place place = PlacePicker.getPlace(this, data);
            if (place!=null){
                direction=(TextView)findViewById(R.id.direction_picture_editText);
                direction.setText(place.getAddress().toString());
                LatLng loct=place.getLatLng();
                location[0]=loct.latitude;
                location[1]=loct.longitude;
            }else{
                //PLACE IS NULL
            }
        }
    }
    @Override
     public void showPic(Bitmap bitmap) {
        ImageView mImageView = (ImageView) findViewById(R.id.picPreview);
        mImageView.setImageBitmap(bitmap);
    }

    public void importPic(NotesPhotosVideosTable note)
    {
        ImageView mImageView = (ImageView) findViewById(R.id.picPreview);
        presenter.showBitmap(mImageView, note.getFileLink());
    }

    public void save(View view) {
        title=(EditText)findViewById(R.id.title_pic);
        desc=(EditText)findViewById(R.id.description_picture_editText);
        ville=(EditText)findViewById(R.id.ville_pic);
        direction=(TextView)findViewById(R.id.direction_picture_editText);
        tags=(EditText)findViewById(R.id.tag_picture_editText);

        List<EditText> editeList=new ArrayList<EditText>(){};
        editeList.add(title);
        editeList.add(desc);
        editeList.add(ville);
        editeList.add(tags);

        if(idNote != -1)
        {
            presenter.editNote(title.getText().toString(), desc.getText().toString(), ville.getText().toString(), tags.getText().toString(), idNote);
            finish();
        }

        if(Utils.FieldRempli(editeList)) {
            presenter.creatPhotoNoteWithLocation(title.getText().toString(), desc.getText().toString(), idVoyage, mCurrentPhotoPath, tags.getText().toString(), ville.getText().toString(), direction.getText().toString(), location[0], location[1]);

            Log.e("Add_PhotoNote_UI", ""+location[0]);
            Log.e("Add_PhotoNote_UI", ""+location[1]);

            finish();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,Get_PhotoNote_UI.class).putExtra("SendidVoyage",idVoyage));
        finish();
    }

    public void getLocation(View view) {
        /* Get permissions */
        Utils.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(presenter.permissionCheker()&&presenter.checkGPS(Add_PhotoNote_UI.this)) {
            try {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                startActivityForResult(builder.build(Add_PhotoNote_UI.this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
