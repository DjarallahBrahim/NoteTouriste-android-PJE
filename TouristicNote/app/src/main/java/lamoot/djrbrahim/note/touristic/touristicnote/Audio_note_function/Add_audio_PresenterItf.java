package lamoot.djrbrahim.note.touristic.touristicnote.Audio_note_function;

import java.io.IOException;

/**
 * Created by ASUS on 15/11/2017.
 */

public interface Add_audio_PresenterItf {
    void creatVocalTable(String titre, String description, int voyageID, String fileLink);
    String creatImageFile() throws IOException;
    void creatAudio(String path) throws IOException;

    double[] myGetLastPosition();

    void creatVocalTableWithLocation(String s, String s1, int idVoyage, String mCurrentPhotoPath, double latitude, double longitude);
}
