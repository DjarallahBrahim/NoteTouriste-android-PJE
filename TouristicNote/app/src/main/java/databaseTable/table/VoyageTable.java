package databaseTable.table;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by lamoot on 12/10/17.
 */

@DatabaseTable
public class VoyageTable extends Activity implements Serializable {
    @DatabaseField(generatedId = true)
    protected int voyageID;

    @DatabaseField
    protected  String titre;

    @DatabaseField
    public String description;

    @DatabaseField
    public double latitude;

    @DatabaseField
    public double longitude;

    @DatabaseField
    public int voyageParentID = -1;

    public VoyageTable(){

    }

    public VoyageTable(final String titre, final String description)
    {
        this.titre = titre;
        this.description = description;
    }

    public VoyageTable(final String titre, final String description, final double latitude, final double longitude)
    {
        this.titre = titre;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getVoyageID() {
        return voyageID;
    }

    public void setVoyageID(int voyageID) {
        this.voyageID = voyageID;
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

    public double getLatitude()
    {
        return this.latitude;
    }

    public double getLongitude()
    {
        return this.longitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public void setPosition(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getVoyageParentID()
    {
        return this.voyageParentID;
    }

    public void setVoyageParentID(int voyageParentID)
    {
        this.voyageParentID = voyageParentID;
    }


}

