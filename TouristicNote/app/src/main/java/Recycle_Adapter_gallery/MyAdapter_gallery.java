package Recycle_Adapter_gallery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import databaseTable.AppManager;
import databaseTable.notePhotoManager.NotePhotoManagerItf;
import lamoot.djrbrahim.note.touristic.touristicnote.Photo_note_function.Add_PhotoNote_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.R;
import lamoot.djrbrahim.note.touristic.touristicnote.Photo_note_function.Get_PhotoNote_UI;

/**
 * Created by ASUS on 26/10/2017.
 */

public class MyAdapter_gallery  extends RecyclerView.Adapter<MyAdapter_gallery.ViewHolder> {
    private ArrayList<CreateListImage> galleryList;

    public MyAdapter_gallery(ArrayList<CreateListImage> galleryList) {
        this.galleryList = galleryList;
    }
    //ViewHolder Class

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView title;
        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.bd_photo);
            title =(TextView)view.findViewById(R.id.bdPhoto_name);
        }
    }


    @Override
    public MyAdapter_gallery.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_row_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyAdapter_gallery.ViewHolder viewHolder,final int i) {
        viewHolder.title.setText(galleryList.get(i).getImage_title());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageBitmap((galleryList.get(i).getBitmap()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), Add_PhotoNote_UI.class);
                it.putExtra("idNote", galleryList.get(i).getIdNotePhoto());
                it.putExtra("idVoyage",galleryList.get(i).getImage_ID());

                view.getContext().startActivity(it);
            }
        });

        //Action for longClick for everyElemnt of RecyclerView !==> for deleting
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //get path
                String path=galleryList.get(i).getImage_path();
                //get idVoyage
                int idVoyage=galleryList.get(i).getImage_ID();
                int idNotePhoto = galleryList.get(i).getIdNotePhoto();
                //show PopDialog for delet the pic/audio
                showDialog(v.getContext(),path,v,idVoyage, idNotePhoto);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }




    public void deleteFromTable(Context context,View view,int idVoyage, int idNotePhoto){

        NotePhotoManagerItf notePhotoManager = AppManager.instance.getNotePhotoManager();
        notePhotoManager.removePhotoById(idNotePhoto);


        Intent i=new Intent(view.getContext(),Get_PhotoNote_UI.class);
        i.putExtra("SendidVoyage",idVoyage);
        view.getContext().startActivity(i);


    }
    private void showDialog(final Context context,final String path,final View view,final int idVoyage, final int idNotePhoto) {
        new SweetAlertDialog(view.getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        //supp path
                        deleteFromTable(context,view,idVoyage, idNotePhoto);
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
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
}