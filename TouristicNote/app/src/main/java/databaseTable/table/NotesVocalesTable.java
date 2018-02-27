package databaseTable.table;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by lamoot on 12/10/17.
 */

public class NotesVocalesTable extends NotesTable {

    @DatabaseField
    protected String fileLink;

    @DatabaseField
    protected int idVoyage;

    public NotesVocalesTable(String titre, String description, int voyageID, String fileLink) {
        super(titre, description, voyageID);
        this.fileLink = fileLink;
    }

    public NotesVocalesTable() {

    }

    public NotesVocalesTable(String titre, String description, int voyageID, String fileLink, double latitude, double longitude) {
        super(titre, description, voyageID, latitude, longitude);
        this.fileLink = fileLink;
    }


    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    @Override
    public void setVoyageID(int voyageID) {
        super.setVoyageID(voyageID);
        this.idVoyage=voyageID;
    }
}