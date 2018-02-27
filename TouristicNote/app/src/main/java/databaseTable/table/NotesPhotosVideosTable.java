package databaseTable.table;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by lamoot on 12/10/17.
 */

public class NotesPhotosVideosTable extends NotesTable {

    @DatabaseField
    protected String fileLink;

    @DatabaseField
    protected String tags;

    @DatabaseField
    protected String ville;

    @DatabaseField
    protected String direction;

    @DatabaseField
    protected int idVoyage;

    public NotesPhotosVideosTable()
    {

    }

    public NotesPhotosVideosTable(String titre, String description, int voyageID, final String fileLink, final String tags, final String ville, final String direction )
    {
        super(titre, description, voyageID);
        this.fileLink = fileLink;
        this.tags = tags;
        this.ville = ville;
        this.direction = direction;
    }

    public NotesPhotosVideosTable(String titre, String description, int voyageID, final String fileLink, final String tags, final String ville, final String direction, final double latitude, final double longitude )
    {
        super(titre, description, voyageID, latitude, longitude);
        this.fileLink = fileLink;
        this.tags = tags;
        this.ville = ville;
        this.direction = direction;
    }

    public String getFileLink() {
        return fileLink;
    }

    @Override
    public String getTitre() {
        return super.getTitre();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public void setVoyageID(int voyageID) {
        super.setVoyageID(voyageID);
        this.idVoyage=voyageID;
    }
}
