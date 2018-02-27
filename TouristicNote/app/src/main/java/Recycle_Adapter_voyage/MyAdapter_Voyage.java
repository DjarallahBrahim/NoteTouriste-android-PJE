package Recycle_Adapter_voyage;

/**
 * Created by djarallah on 19/10/17.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Recycle_Adapter_gallery.CreateListImage;
import Recycle_Adapter_vocal.CreateListVocal;
import Recycler_Adapter_notes.NoteObject;
import databaseTable.AppManager;
import databaseTable.noteManager.NoteManagerItf;
import databaseTable.notePhotoManager.NotePhotoManagerItf;
import databaseTable.noteVocalesManager.NoteVocalesManagerItf;
import databaseTable.voyageManager.VoyageManagerItf;
import cn.pedant.SweetAlert.SweetAlertDialog;
import lamoot.djrbrahim.note.touristic.touristicnote.R;
import databaseTable.table.VoyageTable;
import lamoot.djrbrahim.note.touristic.touristicnote.Voyage_function.Get_AllTypesNotes_activity_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.activity_home_map_UI;

/**
 * Created by djarallah on 12/10/17.
 */

public class MyAdapter_Voyage<T> extends RecyclerView.Adapter<MyAdapter_Voyage.Holder> {

    private List mlitse;
    MyAdapter_Voyage<T> context = this;

    public MyAdapter_Voyage(List<T> mliste) {
        this.mlitse= mliste;
    }
    public static class Holder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titre;
        public TextView descritpion;
        public Holder(View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.imageRec1);
            titre = (TextView) itemView.findViewById(R.id.textRec1);
            descritpion = (TextView)itemView.findViewById(R.id.textRec2);
        }

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_view, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        final VoyageTable voyageTable = (VoyageTable) mlitse.get(position);
        holder.titre.setText(voyageTable.getTitre());
        holder.descritpion.setText(String.valueOf(voyageTable.getVoyageID()));
        holder.imageView.setImageResource(R.drawable.globe_icon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Get_AllTypesNotes_activity_UI.class);
                i.putExtra("SendidVoyage", voyageTable.getVoyageID());
                i.putExtra("titleVoyage", voyageTable.getTitre());
                i.putExtra("descrpVoyage", voyageTable.getDescription());
                view.getContext().startActivity(i);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(holder.itemView.getContext(), holder.itemView);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:

                                break;
                            case R.id.menu2:
                                int VoyageId=voyageTable.getVoyageID();
                                showDialog(v.getContext(),VoyageId,v,VoyageId);
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();


        return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mlitse.size();
    }

    private void showDialog(final Context context,final int VoyageId,final View view,final int idVoyage) {

            new SweetAlertDialog(view.getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Won't be able to recover this file!")
                    .setConfirmText("Yes,delete it!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            deleteFromTable(context,VoyageId,view);
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();


        }


//Delet voyage + All travel notes's
    public void deleteFromTable(Context context,int VoyageId,View view){
        //delet travel table

        VoyageManagerItf voyageManager = AppManager.instance.getVoyageManager();
        voyageManager.removeVoyageById(VoyageId);

        //delet all notes of the deleted travel
        NoteManagerItf noteManager = AppManager.instance.getNoteManager();
        ArrayList<NoteObject> allNote = noteManager.getAllNote(VoyageId, "notesID");
        for(int i = 0; i <  allNote.size(); i++)
        {
            noteManager.removeNoteById(allNote.get(i).getIdNote());
        }


        //get all images of the deleted travel and delete it
        NotePhotoManagerItf notePhotoManager = AppManager.instance.getNotePhotoManager();
        ArrayList<CreateListImage> allNotePhoto = notePhotoManager.getAllPhoto(VoyageId, "notesID");
        for(int i = 0; i < allNotePhoto.size(); i++)
        {
            notePhotoManager.removePhotoById(allNotePhoto.get(i).getIdNotePhoto());
            deleteFilePhysically(allNotePhoto.get(i).getImage_path());
        }


        //get all vocal of the deleted travel and delete it
        NoteVocalesManagerItf noteVocaleManager = AppManager.instance.getNoteVocalesManager();
        ArrayList<CreateListVocal> allNoteVocale = noteVocaleManager.getAllVocales(VoyageId, "notesID");
        for(int i = 0; i < allNoteVocale.size(); i++)
        {
            noteVocaleManager.removeVocaleById(allNoteVocale.get(i).getIdNoteVocal());
            deleteFilePhysically(allNoteVocale.get(i).getVocal_path());
        }

        List<VoyageTable> allSousVoyage = voyageManager.getUnderTravel(VoyageId);
        for(int i = 0; i < allSousVoyage.size(); i++)
        {
            voyageManager.removeVoyageById(allSousVoyage.get(i).getVoyageID());
        }

        view.getContext().startActivity(new Intent(context, activity_home_map_UI.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }
    private void deleteFilePhysically(String inputPath) {
        try {
            // delete the original file
            new File(inputPath).delete();
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

}