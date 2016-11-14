package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.AsteroidsGame;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.bgAsteroid;
import edu.byu.cs.superasteroids.model.bgObject;

/**
 * Created by Jacob on 10/20/2016.
 * Object to populate SQLite database with model object data
 */
public class DAO {

    private SQLiteDatabase db;
    private AsteroidsGame game;
    private DBmanager manager;


    public DAO(SQLiteDatabase db_in) {
        db = db_in;
        db.beginTransaction();
        game = null;
        manager = new DBmanager();
    }

    /**
     * import data model objects into SQLite database.
     * @param obj an object containing all the data model objects
     */
    public void UpdateDB(AsteroidsGame obj) {
        manager.CreateDB(db);
        for (String e : obj.getObjects()) {
            this.InsertObject(e);
        }

        for (Asteroid e : obj.getAsteroids()) {
            this.InsertAsteroid(e);
        }

        for (Level e : obj.getLevels()) {
            this.InsertLevel(e);
            for (bgObject a : e.getLevelObjects()) {
                this.InsertLevelObject(a,e.getNumber());
            }
            for (bgAsteroid a : e.getLevelAsteroids()) {
                this.InsertLevelAsteroid(a,e.getNumber());
            }
        }

        for (MainBody e : obj.getMainBodies()) {
            this.InsertMainBody(e);
        }

        for (Cannon e : obj.getCannons()) {
            this.InsertCannon(e);
        }

        for (ExtraPart e : obj.getExtraParts()) {
            this.InsertExtraPart(e);
        }

        for (Engine e : obj.getEngines()) {
            this.InsertEngine(e);
        }

        for (PowerCore e : obj.getPowerCores()) {
            this.InsertPowerCore(e);
        }
    }

    public AsteroidsGame getGameData() {
        if (game == null) {
            game = this.getFromDB();
        }
        return game;
    }

    private AsteroidsGame getFromDB() {

        LinkedHashSet<String> orderedTables = new LinkedHashSet<>();
        AsteroidsGame game = new AsteroidsGame();
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

        for (String e : orderedTables) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + e,null);

            cursor.moveToFirst();

            switch(e) {
                case "asteroid" :
                    ArrayList<Asteroid> asteroids = new ArrayList<>();
                    Asteroid asteroid;
                    while (!cursor.isAfterLast()) {
                        asteroid = new Asteroid();

                        asteroid.setId(cursor.getInt(0));
                        asteroid.setName(cursor.getString(1));
                        asteroid.setImage(cursor.getString(2));
                        asteroid.setImageHeight(cursor.getInt(3));
                        asteroid.setImageWidth(cursor.getInt(4));
                        asteroid.setType(cursor.getString(5));

                        asteroids.add(asteroid);
                        cursor.moveToNext();
                    }
                    game.setAsteroids(asteroids);
                    break;

                case "object" :
                    ArrayList<String> objects = new ArrayList<>();
                    while (!cursor.isAfterLast()) {
                        objects.add(cursor.getString(1));
                        cursor.moveToNext();
                    }
                    game.setObjects(objects);
                    break;

                case "levelobject" :
                    bgObject levelObj;
                    while (!cursor.isAfterLast()) {
                        levelObj = new bgObject();

                        levelObj.setObjectId(cursor.getInt(1));
                        levelObj.setLevelId(cursor.getInt(2));
                        levelObj.setPosition(cursor.getString(3));
                        levelObj.setScale(cursor.getFloat(4));

                        game.addLevelObj(levelObj);
                        cursor.moveToNext();
                    }
                    break;

                case "levelasteroid" :
                    bgAsteroid levelAst;
                    while (!cursor.isAfterLast()) {
                        levelAst = new bgAsteroid();

                        levelAst.setAsteroidId(cursor.getInt(1));
                        levelAst.setLevelId(cursor.getInt(2));
                        levelAst.setNumber(cursor.getInt(3));

                        game.addLevelAsteroid(levelAst);
                        cursor.moveToNext();
                    }
                    break;

                case "level" :
                    ArrayList<Level> levels = new ArrayList<>();
                    Level level;
                    while (!cursor.isAfterLast()) {
                        level = new Level();

                        level.setNumber(cursor.getInt(0));
                        level.setTitle(cursor.getString(1));
                        level.setHint(cursor.getString(2));
                        level.setWidth(cursor.getInt(3));
                        level.setHeight(cursor.getInt(4));
                        level.setMusic(cursor.getString(5));

                        levels.add(level);
                        cursor.moveToNext();
                    }
                    game.setLevels(levels);
                    break;

                case "mainbody" :
                    ArrayList<MainBody> mainbodies = new ArrayList<>();
                    MainBody mainBody;
                    while (!cursor.isAfterLast()) {
                        mainBody = new MainBody();

                        mainBody.setCannonAttach(cursor.getString(1));
                        mainBody.setEngineAttach(cursor.getString(2));
                        mainBody.setExtraAttach(cursor.getString(3));
                        mainBody.setImage(cursor.getString(4));
                        mainBody.setImageWidth(cursor.getInt(5));
                        mainBody.setImageHeight(cursor.getInt(6));

                        mainbodies.add(mainBody);
                        cursor.moveToNext();
                    }
                    game.setMainBodies(mainbodies);
                    break;

                case "cannon" :
                    ArrayList<Cannon> cannons = new ArrayList<>();
                    Cannon cannon;
                    while (!cursor.isAfterLast()) {
                        cannon = new Cannon();

                        cannon.setAttachPoint(cursor.getString(1));
                        cannon.setEmitPoint(cursor.getString(2));
                        cannon.setImage(cursor.getString(3));
                        cannon.setImageWidth(cursor.getInt(4));
                        cannon.setImageHeight(cursor.getInt(5));
                        cannon.setAttackImage(cursor.getString(6));
                        cannon.setAttackImageWidth(cursor.getInt(7));
                        cannon.setAttackImageHeight(cursor.getInt(8));
                        cannon.setAttackSound(cursor.getString(9));
                        cannon.setDamage(cursor.getInt(10));

                        cannons.add(cannon);
                        cursor.moveToNext();
                    }
                    game.setCannons(cannons);
                    break;

                case "extrapart" :
                    ArrayList<ExtraPart> extraParts = new ArrayList<>();
                    ExtraPart extraPart;
                    while (!cursor.isAfterLast()) {
                        extraPart = new ExtraPart();

                        extraPart.setAttachPoint(cursor.getString(1));
                        extraPart.setImage(cursor.getString(2));
                        extraPart.setImageWidth(cursor.getInt(3));
                        extraPart.setImageHeight(cursor.getInt(4));

                        extraParts.add(extraPart);
                        cursor.moveToNext();
                    }
                    game.setExtraParts(extraParts);
                    break;

                case "engine" :
                    ArrayList<Engine> engines = new ArrayList<>();
                    Engine engine;
                    while (!cursor.isAfterLast()) {
                        engine = new Engine();

                        engine.setAttachPoint(cursor.getString(1));
                        engine.setImage(cursor.getString(2));
                        engine.setImageWidth(cursor.getInt(3));
                        engine.setImageHeight(cursor.getInt(4));
                        engine.setBaseSpeed(cursor.getInt(5));
                        engine.setBaseTurnRate(cursor.getInt(6));

                        engines.add(engine);
                        cursor.moveToNext();
                    }
                    game.setEngines(engines);
                    break;

                case "powercore" :
                    ArrayList<PowerCore> powerCores = new ArrayList<>();
                    PowerCore powerCore;
                    while (!cursor.isAfterLast()) {
                        powerCore = new PowerCore();

                        powerCore.setCannonBoost(cursor.getInt(1));
                        powerCore.setEngineBoost(cursor.getInt(2));
                        powerCore.setImage(cursor.getString(3));

                        powerCores.add(powerCore);
                        cursor.moveToNext();
                    }
                    game.setPowerCores(powerCores);
                    break;
            }

            cursor.close();

        }

        return game;
    }


    private void InsertObject(String obj) {
        ContentValues values = new ContentValues();
        values.put("image",obj);
        db.insert("object", null, values);
    }

    private String getObject(Cursor cur) {
        return cur.getString(0);
    }

    private void InsertAsteroid(Asteroid obj) {
        ContentValues values = new ContentValues();
        values.put("image",obj.getImage());
        values.put("name",obj.getName());
        values.put("imagewidth",obj.getImageWidth());
        values.put("imageHeight",obj.getImageHeight());
        values.put("type",obj.getType());
        db.insert("asteroid", null, values);
    }

    private void InsertLevel(Level obj) {
        ContentValues values = new ContentValues();
        values.put("title",obj.getTitle());
        values.put("hint",obj.getHint());
        values.put("width",obj.getWidth());
        values.put("height",obj.getHeight());
        values.put("music",obj.getMusic());
        db.insert("level", null, values);
    }

    private void InsertMainBody(MainBody obj) {
        ContentValues values = new ContentValues();
        values.put("image",obj.getImage());
        values.put("imageHeight",obj.getImageHeight());
        values.put("imageWidth",obj.getImageWidth());
        values.put("cannonAttach",obj.getCannonAttach());
        values.put("engineAttach",obj.getEngineAttach());
        values.put("extraAttach",obj.getExtraAttach());
        db.insert("mainbody", null, values);
    }

    private void InsertCannon(Cannon obj) {
        ContentValues values = new ContentValues();
        values.put("image",obj.getImage());
        values.put("imageHeight",obj.getImageHeight());
        values.put("imageWidth",obj.getImageWidth());
        values.put("attachPoint",obj.getAttachPoint());
        values.put("emitPoint",obj.getEmitPoint());
        values.put("attackImage",obj.getAttackImage());
        values.put("attackImageWidth",obj.getAttackImageWidth());
        values.put("attackImageHeight",obj.getAttackImageHeight());
        values.put("attackSound",obj.getAttackSound());
        values.put("damage",obj.getDamage());
        db.insert("cannon", null, values);
    }

    private void InsertExtraPart(ExtraPart obj) {
        ContentValues values = new ContentValues();
        values.put("image",obj.getImage());
        values.put("imageHeight",obj.getImageHeight());
        values.put("imageWidth",obj.getImageWidth());
        values.put("attachPoint",obj.getAttachPoint());
        db.insert("extrapart", null, values);
    }

    private void InsertEngine(Engine obj) {
        ContentValues values = new ContentValues();
        values.put("image",obj.getImage());
        values.put("imageHeight",obj.getImageHeight());
        values.put("imageWidth",obj.getImageWidth());
        values.put("attachPoint",obj.getAttachPoint());
        values.put("baseSpeed",obj.getBaseSpeed());
        values.put("baseTurnRate",obj.getBaseTurnRate());
        db.insert("engine", null, values);
    }

    private void InsertPowerCore(PowerCore obj) {
        ContentValues values = new ContentValues();
        values.put("image",obj.getImage());
        values.put("cannonBoost",obj.getCannonBoost());
        values.put("engineboost",obj.getEngineBoost());
        db.insert("powercore", null, values);
    }

    private void InsertLevelObject(bgObject obj,int levelId) {
        ContentValues values = new ContentValues();
        values.put("object_id",obj.getObjectId());
        values.put("level_id",levelId);
        values.put("position",obj.getPosition());
        values.put("scale",obj.getScale());
        db.insert("levelobject", null, values);
    }

    private void InsertLevelAsteroid(bgAsteroid obj,int levelId) {
        ContentValues values = new ContentValues();
        values.put("asteroid_id",obj.getAsteroidId());
        values.put("level_id",levelId);
        values.put("number",obj.getNumber());
        db.insert("levelasteroid", null, values);
    }



    /**
     * retrieve data from SQLite, and convert to POJO.
     * @return the data model objects
     */
    public AsteroidsGame getDB() {
        return null;
    }


    public void toDB(BufferedReader buffer_in) {
        final Gson gson = new Gson();
        game = gson.fromJson(buffer_in,AsteroidsGame.class).getGame();
        this.UpdateDB(game);
    }




}
