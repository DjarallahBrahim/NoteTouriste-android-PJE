package Recycle_Adapter_vocal;

/**
 * Created by ASUS on 28/10/2017.
 */

public class CreateListVocal {

    private String vocal_path;

    private int idVoyage;

    private String title;
    private int idNoteVocal;

    private double latitude, longitude;

    public String getVocal_path() {
        return vocal_path;
    }

    public void setVocal_path(String vocal_path) {
        this.vocal_path = vocal_path;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIdVoyage(int idVoyage) {
        this.idVoyage = idVoyage;
    }

    public int getIdVoyage() {
        return this.idVoyage;
    }


    public int getIdNoteVocal() {
        return idNoteVocal;
    }

    public void setIdNoteVocal(int idNoteVocal) {
        this.idNoteVocal = idNoteVocal;
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