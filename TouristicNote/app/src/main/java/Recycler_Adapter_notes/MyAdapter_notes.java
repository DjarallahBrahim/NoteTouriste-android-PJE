package Recycler_Adapter_notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import databaseTable.AppManager;
import cn.pedant.SweetAlert.SweetAlertDialog;
import databaseTable.noteManager.NoteManagerItf;
import lamoot.djrbrahim.note.touristic.touristicnote.Note_text_function.Add_NoteText_UI;
import lamoot.djrbrahim.note.touristic.touristicnote.R;
import lamoot.djrbrahim.note.touristic.touristicnote.Note_text_function.Get_NoteText_UI;

/**
 * Created by romain on 01/11/17.
 */

public class MyAdapter_notes extends RecyclerView.Adapter<MyAdapter_notes.ViewHolder> {

    private ArrayList<NoteObject> notesList;

    //Constructor
    public MyAdapter_notes(ArrayList<NoteObject> notesList)
    {
        this.notesList = notesList;
    }

    //ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView titleView;
        public final TextView descriptionView;
        public NoteObject mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            titleView = (TextView) view.findViewById(R.id.titleNoteRecycler);
            descriptionView = (TextView) view.findViewById(R.id.descriptionNoteRecycler);
        }

        @Override
        public String toString()
        {
            return titleView.getText() + " - " + descriptionView.getText();
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_notes, parent,false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.mItem = notesList.get(position);
        holder.titleView.setText(notesList.get(position).getTitle());
        holder.descriptionView.setText(notesList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Add_NoteText_UI.class);
                i.putExtra("idNote", notesList.get(position).getIdNote());
                view.getContext().startActivity(i);
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int idVoyage = notesList.get(position).getIdVoyage();
                int idNote = notesList.get(position).getIdNote();
                showDialog(v.getContext(), v, idVoyage, idNote);
                return false;
            }
        });
    }

    public int getItemCount()
    {
        return notesList.size();
    }


    public void deleteFromTable(Context context, View view, int idVoyage, int idNote){


        NoteManagerItf noteManager = AppManager.instance.getNoteManager();
        noteManager.removeNoteById(idNote);

        Intent i = new Intent(view.getContext(),Get_NoteText_UI.class);
        i.putExtra("SendidVoyage",idVoyage);
        view.getContext().startActivity(i);
    }

    private void showDialog(final Context context, final View view, final int idVoyage, final int idNote) {
        new SweetAlertDialog(view.getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this note!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        deleteFromTable(context,view,idVoyage, idNote);
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();


    }

}
