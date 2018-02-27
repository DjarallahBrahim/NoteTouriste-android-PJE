package lamoot.djrbrahim.note.touristic.touristicnote.Photo_note_function;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

/**
 * Created by ASUS on 16/11/2017.
 */

public interface Add_PhotoNote_presenterItf {
    File createImageFile() throws IOException;

    void galleryAddPic(ImageView imageView);

    void creatPhotoNote(String title, String description, int voyageID, String fileLink, String tags, String ville, String direction);

    void getLocation(Activity ac, final int PLACE_PICKER_REQUEST);

    boolean checkGPS(Context add_photoNote_ui);
    boolean permissionCheker();

    double[] myGetLastPosition();

    void creatPhotoNoteWithLocation(String s, String s1, int idVoyage, String mCurrentPhotoPath, String s2, String s3, String s4, double v, double v1);
    //void checkGPS(final Context context);

}
