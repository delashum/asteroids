package edu.byu.cs.superasteroids.importer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import edu.byu.cs.superasteroids.database.DAO;
import edu.byu.cs.superasteroids.database.dbHelper;
import edu.byu.cs.superasteroids.model.AsteroidsGame;
import edu.byu.cs.superasteroids.model.SingletonModel;

/**
 * Created by Jacob on 10/13/2016.
 */
public class GameDataImporter implements IGameDataImporter {

    private DAO dao;

    public GameDataImporter(dbHelper dbHelper) {

        // Open database
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dao = new DAO(db);


    }


    @Override
    public boolean importData(InputStreamReader dataInputReader) {

        BufferedReader buffer_in = new BufferedReader(dataInputReader);
        dao.toDB(buffer_in);
        SingletonModel model = SingletonModel.getInstance();
        model.setGame(dao.getGameData());
        return true;
    }

}
