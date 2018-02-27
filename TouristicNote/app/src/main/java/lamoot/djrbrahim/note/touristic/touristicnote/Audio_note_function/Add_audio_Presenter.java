package lamoot.djrbrahim.note.touristic.touristicnote.Audio_note_function;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import databaseTable.AppManager;
import databaseTable.noteVocalesManager.NoteVocalesManagerItf;
import lamoot.djrbrahim.note.touristic.touristicnote.R;

/**
 * Created by ASUS on 15/11/2017.
 */

public class Add_audio_Presenter implements Add_audio_PresenterItf{

    protected Add_AudioNote_UI_Itf view;

    protected NoteVocalesManagerItf vocalManager;

    protected Context context;
    private FusedLocationProviderClient mFusedLocationClient;


    public Add_audio_Presenter(Add_AudioNote_UI view) {
        this.view = view ;
        this.context=view.getApplicationContext();
        vocalManager= AppManager.instance.getNoteVocalesManager();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

    }


    @Override
    public void creatVocalTable(String titre, String description, int voyageID, String fileLink) {
        vocalManager.createVocale(titre,description,voyageID,fileLink);

        view.showDialogueSucc();
    }

    @Override
    public String creatImageFile() throws IOException {
        //creation lien de l'image

        String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName="recorded_audio_"+timeStamp+"_";

        File storageDir= context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);

        File image=File.createTempFile(
                imageFileName,
                ".wav",
                storageDir
        );


        return image.getAbsolutePath();
    }

    @Override
    public void creatAudio(String path) throws IOException {
        //String filePath = Environment.getExternalStorageDirectory() + "/recorded_audio.wav";
        int color = context.getResources().getColor(R.color.colorPrimaryDark);
        int requestCode = 0;
        AndroidAudioRecorder.with((Activity) view)
                // Required
                .setFilePath(path)
                .setColor(color)
                .setRequestCode(requestCode)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(true)
                .setKeepDisplayOn(true)

                // Start recording
                .record();
    }

    @Override
    public double[] myGetLastPosition() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        final double location_result[] = new double[3];
        Task<Location> task = mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    location_result[0] = location.getLatitude();
                    location_result[1] = location.getLongitude();

                    Toast.makeText(context,"We get location",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context,"can't get location",Toast.LENGTH_SHORT).show();

                }
            }
        });
        return location_result;
    }

    @Override
    public void creatVocalTableWithLocation(String titre, String description, int voyageID, String fileLink, double latitude, double longitude) {
        vocalManager.createVocaleWithLocation(titre,description,voyageID,fileLink, latitude, longitude);

        view.showDialogueSucc();
    }
}
