package lamoot.djrbrahim.note.touristic.touristicnote.Audio_note_function;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Permitssion.Utils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import lamoot.djrbrahim.note.touristic.touristicnote.R;

public class Add_AudioNote_UI extends AppCompatActivity implements Add_AudioNote_UI_Itf {

    private Add_audio_Presenter presenter;
    private String mCurrentPhotoPath;
    private EditText description,title;
    protected  int idVoyage=0;
    protected double[] location; // 0 -> latitude , 1 -> longitude


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_audio_note_activity);

        Utils.requestPermission(this, Manifest.permission.RECORD_AUDIO);
        Utils.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        idVoyage=getIntent().getIntExtra("idVoyage",0);
        presenter=new Add_audio_Presenter(this);
        location = presenter.myGetLastPosition();

    }





    public void record(View view) throws IOException {

        description=(EditText)findViewById(R.id.vocal_descritpion);
        title=(EditText)findViewById(R.id.vocal_title);

        List<EditText> editeList=new ArrayList<EditText>(){};
        editeList.add(title);
        editeList.add(description);
        if(Utils.FieldRempli(editeList)) {
            mCurrentPhotoPath = presenter.creatImageFile();
            presenter.creatAudio(mCurrentPhotoPath);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                presenter.creatVocalTableWithLocation(title.getText().toString(),description.getText().toString(),idVoyage,mCurrentPhotoPath, location[0], location[1]);
                // Great! User has recorded and saved the audio file
            } else if (resultCode == RESULT_CANCELED) {
                // Oops! User has canceled the recording
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,Get_AudioNote_UI.class).putExtra("SendidVoyage",idVoyage));
        finish();
    }

    @Override
    public void showDialogueSucc() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Good job!")
                .setContentText("Vocal save!")
                .show();
    }
}
