package Recycler_Adapter_notes;

/**
 * Created by romain on 01/11/17.
 */

public class NoteObject {

    private String title, description;
    private int idVoyage, idNote;
    private double latitude, longitude;

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setIdVoyage(int idVoyage) {
        this.idVoyage = idVoyage;
    }

    public int getIdVoyage() {
        return this.idVoyage;
    }

    public void setIdNote(int idNote)
    {
        this.idNote = idNote;
    }

    public int getIdNote()
    {
        return this.idNote;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
