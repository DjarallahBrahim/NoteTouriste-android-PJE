package databaseTable.voyageManager;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.query.In;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import databaseTable.DatabaseHelper;
import databaseTable.table.VoyageTable;

/**
 * Created by romain on 12/11/17.
 */

public class VoyageManagerWithDB implements VoyageManagerItf {

    protected Context context;

    protected DatabaseHelper db;

    public VoyageManagerWithDB(Context context)
    {
        this.context = context;

        db = new DatabaseHelper(context);
    }

    public List<VoyageTable> getAllVoyage() {
        try {
            return this.db.getVoyageDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<VoyageTable>();
        }
    }

    public int createVoyage(VoyageTable voyagetab) {
        try {
            this.db.getVoyageDao().create(voyagetab);
            return voyagetab.getVoyageID();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void removeVoyageById(int id) {
        try {
            this.db.getVoyageDao().deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public VoyageTable getVoyageById(int id) {
        try{
            Dao<VoyageTable, Integer> voyageDao = this.db.getVoyageDao();
            Iterator<VoyageTable> it = voyageDao.query(voyageDao.queryBuilder().where().eq("voyageID", id).prepare()).iterator();

            while(it.hasNext())
            {
                VoyageTable myVoyage = it.next();
                return myVoyage;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<VoyageTable> getUnderTravel(int idParent) {
        List<VoyageTable> voyageList = new ArrayList<>();
        try
        {
            Dao<VoyageTable, Integer> voyageDao = this.db.getVoyageDao();
            Iterator<VoyageTable> it = voyageDao.query(voyageDao.queryBuilder().where().eq("voyageParentID", idParent).prepare()).iterator();
            while(it.hasNext())
            {
                voyageList.add(it.next());
            }
            return voyageList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<VoyageTable> getAllVoyageParents()
    {
        List<VoyageTable> voyageList = new ArrayList<>();
        try{
            Dao<VoyageTable, Integer> voyageDao = this.db.getVoyageDao();
            Iterator<VoyageTable> it = voyageDao.query(voyageDao.queryBuilder().where().eq("voyageParentID",-1).prepare()).iterator();
            while(it.hasNext())
            {
                voyageList.add(it.next());
            }
            return voyageList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
