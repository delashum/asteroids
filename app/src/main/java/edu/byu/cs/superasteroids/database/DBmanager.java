package edu.byu.cs.superasteroids.database;

import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * Created by Jacob on 10/25/2016.
 */
public class DBmanager {

    private HashMap<String,LinkedHashSet<String>> tables;
    private LinkedHashSet<String> orderedTables;
    private SQLiteDatabase db;

    public DBmanager() {


        orderedTables = new LinkedHashSet<>();
        orderedTables.add("asteroid");
        orderedTables.add("object");
        orderedTables.add("level");
        orderedTables.add("levelobject");
        orderedTables.add("levelasteroid");
        orderedTables.add("mainbody");
        orderedTables.add("cannon");
        orderedTables.add("extrapart");
        orderedTables.add("engine");
        orderedTables.add("powercore");

        tables = new HashMap<>();
        for (String e : orderedTables) {
            tables.put(e,new LinkedHashSet<String>());
        }
        Initialize();
    }

    public void CreateDB(SQLiteDatabase db) {
        String statement;
        for (String e : orderedTables) {
            statement = "DROP TABLE IF EXISTS " + e;
            db.execSQL(statement);
        }


        for (String e : orderedTables) {
            statement = "create TABLE " + e +
                    "(";
            for (String line : tables.get(e)) {
                statement += line + ",";
            }

            statement = statement.substring(0,statement.length()-1);

            statement += ")";
            db.execSQL(statement);
        }
    }



    private void Initialize() {
        for (HashMap.Entry<String, LinkedHashSet<String>> entry : tables.entrySet()) {
            switch(entry.getKey()) {
                case "asteroid" :
                    entry.getValue().add("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
                    entry.getValue().add("name TEXT NOT NULL");
                    entry.getValue().add("image TEXT NOT NULL");
                    entry.getValue().add("imageWidth INTEGER NOT NULL");
                    entry.getValue().add("imageHeight INTEGER NOT NULL");
                    entry.getValue().add("type TEXT NOT NULL");
                    break;

                case "object" :
                    entry.getValue().add("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
                    entry.getValue().add("image TEXT NOT NULL");
                    break;

                case "levelobject" :
                    entry.getValue().add("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
                    entry.getValue().add("object_id INTEGER NOT NULL");
                    entry.getValue().add("level_id INTEGER NOT NULL");
                    entry.getValue().add("position TEXT NOT NULL");
                    entry.getValue().add("scale FLOAT NOT NULL");
                    entry.getValue().add("FOREIGN KEY (object_id) REFERENCES object(id)");
                    entry.getValue().add("FOREIGN KEY (level_id) REFERENCES level(number)");
                    break;

                case "levelasteroid" :
                    entry.getValue().add("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
                    entry.getValue().add("asteroid_id INTEGER NOT NULL");
                    entry.getValue().add("level_id INTEGER NOT NULL");
                    entry.getValue().add("number INTEGER NOT NULL");
                    entry.getValue().add("FOREIGN KEY (asteroid_id) REFERENCES asteroid(id)");
                    entry.getValue().add("FOREIGN KEY (level_id) REFERENCES level(number)");
                    break;

                case "level" :
                    entry.getValue().add("number INTEGER NOT NULL PRIMARY KEY");
                    entry.getValue().add("title TEXT NOT NULL");
                    entry.getValue().add("hint TEXT NOT NULL");
                    entry.getValue().add("width INTEGER NOT NULL");
                    entry.getValue().add("height INTEGER NOT NULL");
                    entry.getValue().add("music TEXT NOT NULL");
                    break;

                case "mainbody" :
                    entry.getValue().add("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
                    entry.getValue().add("cannonAttach TEXT NOT NULL");
                    entry.getValue().add("engineAttach TEXT NOT NULL");
                    entry.getValue().add("extraAttach TEXT NOT NULL");
                    entry.getValue().add("image TEXT NOT NULL");
                    entry.getValue().add("imageWidth INTEGER NOT NULL");
                    entry.getValue().add("imageHeight INTEGER NOT NULL");
                    break;

                case "cannon" :
                    entry.getValue().add("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
                    entry.getValue().add("attachPoint TEXT NOT NULL");
                    entry.getValue().add("emitPoint TEXT NOT NULL");
                    entry.getValue().add("image TEXT NOT NULL");
                    entry.getValue().add("imageWidth INTEGER NOT NULL");
                    entry.getValue().add("imageHeight INTEGER NOT NULL");
                    entry.getValue().add("attackImage TEXT NOT NULL");
                    entry.getValue().add("attackImageWidth INTEGER NOT NULL");
                    entry.getValue().add("attackImageHeight INTEGER NOT NULL");
                    entry.getValue().add("attackSound TEXT NOT NULL");
                    entry.getValue().add("damage INTEGER NOT NULL");
                    break;

                case "extrapart" :
                    entry.getValue().add("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
                    entry.getValue().add("attachPoint TEXT NOT NULL");
                    entry.getValue().add("image TEXT NOT NULL");
                    entry.getValue().add("imageWidth INTEGER NOT NULL");
                    entry.getValue().add("imageHeight INTEGER NOT NULL");
                    break;

                case "engine" :
                    entry.getValue().add("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
                    entry.getValue().add("attachPoint TEXT NOT NULL");
                    entry.getValue().add("image TEXT NOT NULL");
                    entry.getValue().add("imageWidth INTEGER NOT NULL");
                    entry.getValue().add("imageHeight INTEGER NOT NULL");
                    entry.getValue().add("baseSpeed INTEGER NOT NULL");
                    entry.getValue().add("baseTurnRate INTEGER NOT NULL");
                    break;

                case "powercore" :
                    entry.getValue().add("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
                    entry.getValue().add("cannonBoost INTEGER NOT NULL");
                    entry.getValue().add("engineBoost INTEGER NOT NULL");
                    entry.getValue().add("image TEXT NOT NULL");
                    break;
            }
        }
    }

}
