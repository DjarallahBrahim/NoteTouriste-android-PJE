package Recycle_Adapter_vocal;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import databaseTable.AppManager;
import cn.pedant.SweetAlert.SweetAlertDialog;
import databaseTable.noteVocalesManager.NoteVocalesManagerItf;
import lamoot.djrbrahim.note.touristic.touristicnote.R;
import lamoot.djrbrahim.note.touristic.touristicnote.Audio_note_function.Get_AudioNote_UI;

/**
 * Created by ASUS on 28/10/2017.
 */

public class MyAdapter_vocal extends RecyclerView.Adapter<MyAdapter_vocal.ViewHolder> {
    private ArrayList<CreateListVocal> vocalList;
    private  MediaPlayer mMediaPlayer;//play the audio file !

    public MyAdapter_vocal(ArrayList<CreateListVocal> galleryList) {
        this.vocalList = galleryList;
    }

    //ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView text;

        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.vocalImage);
            text = (TextView) view.findViewById(R.id.vocalTitle);
        }
    }

    @Override
    public MyAdapter_vocal.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_row_vocal, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyAdapter_vocal.ViewHolder viewHolder, int i) {
        final int cpt=i;

        viewHolder.text.setText(vocalList.get(cpt).getTitle());
        viewHolder.img.setImageResource(R.drawable.audio);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //play audio !
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setLooping(false);
                try {
                    mMediaPlayer.setDataSource(vocalList.get(cpt).getVocal_path()); //give the path of audio file !
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    mMediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mMediaPlayer.start();
            }
        });

        //Action for longClick (ask for deleting)

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String path=vocalList.get(cpt).getVocal_path();//get audio file
                int idVoyage=vocalList.get(cpt).getIdVoyage();//get idVoyge
                int idNoteVocal = vocalList.get(cpt).getIdNoteVocal();
                showDialog(viewHolder.itemView.getContext(), path, v,idVoyage, idNoteVocal);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return vocalList.size();
    }



    public void deleteFromTable(Context context, View view, int idVoyage, int idNoteVocal){
        NoteVocalesManagerItf noteVocaleManager = AppManager.instance.getNoteVocalesManager();
        noteVocaleManager.removeVocaleById(idNoteVocal);

        Intent i=new Intent(view.getContext(),Get_AudioNote_UI.class);
        i.putExtra("SendidVoyage",idVoyage);
        view.getContext().startActivity(i);
    }

    private void showDialog(final Context context,final String path,final View view,final int idVoyage, final  int idNoteVocale) {
        new SweetAlertDialog(view.getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        //supp le path
                        deleteFromTable(context,view,idVoyage,idNoteVocale);
                        //supp le fichier
                        deleteFilePhysically(path,context);
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();


    }

    private void deleteFilePhysically(String inputPath, Context context) {
        try {
            // delete the original file
            new File(inputPath).delete();
            Toast.makeText(context,"DELETED",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
}