package databaseTable.table;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by lamoot on 12/10/17.
 */

public class NotesTable implements Serializable{

    @DatabaseField(generatedId = true)
    protected int notesID;

    @DatabaseField
    protected String titre;

    @DatabaseField
    protected String description;

    @DatabaseField
    protected int voyageID;

    @DatabaseField
    protected double latitude;

    @DatabaseField
    protected double longitude;

    public NotesTable()
    {

    }

    public NotesTable(final String titre, final String description, final int voyageID)
    {
        this.titre = titre;
        this.description = description;
        this.voyageID = voyageID;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public NotesTable(String titre, String description, int idVoyage, double latitude, double longitude) {
        this.titre = titre;
        this.description = description;
        this.voyageID = idVoyage;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getNotesID() {
        return notesID;
    }

    public void setNotesID(int notesID) {
        this.notesID = notesID;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVoyageID() {
        return voyageID;
    }

    public void setVoyageID(int voyageID) {
        this.voyageID = voyageID;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLatitude(double lat)
    {
        this.latitude = lat;
    }

    public void setLongitude(double longi)
    {
        this.longitude = longi;
    }
}
