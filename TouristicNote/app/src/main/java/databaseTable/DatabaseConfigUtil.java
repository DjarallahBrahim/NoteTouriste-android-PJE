package databaseTable;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;

/**
 * Created by lamoot on 12/10/17.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    public static void main(String[] args) throws IOException, java.sql.SQLException {
        writeConfigFile("ormlite_config.txt");
    }
}
