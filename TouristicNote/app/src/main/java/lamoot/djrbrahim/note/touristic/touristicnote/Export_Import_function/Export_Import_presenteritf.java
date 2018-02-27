package lamoot.djrbrahim.note.touristic.touristicnote.Export_Import_function;

/**
 * Created by lamoot on 30/11/17.
 */

interface Export_Import_presenteritf {

    boolean exportDB(String exportFileName);

    boolean importDB(String importFileName);

    //void importDB();
}
