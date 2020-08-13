package org.pursuit.heard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.pursuit.heard.model.Artist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProfileDatabase extends SQLiteOpenHelper  {

    private static final String DATABASE_NAME = "profiles.db";
    private static final String TABLE_USER = "User";
    private static final String TABLE_ARTISTS = "artists";
    private static final int SCHEMA_VERSION = 2;

    private static final String USERNAME = "username";
    private static final String COL_ID_USER = "USERID";
    private static final String COL_ID_ARTIST = "ARTISTID";
    private static final String USER_ID_ARTIST = "USER_ARTISTID";

    private static final String ARTIST = "artist";
    private static final String ARTIST_URL = "artistUrl";


    private static final String SQL_CREATE_USER_ENTRY =
            "CREATE TABLE " + TABLE_USER + " ("
                    + COL_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USERNAME + " TEXT)";

    private static final String SQL_CREATE_ARTIST_ENTRY =
            "CREATE TABLE " + TABLE_ARTISTS + " ("
                    + COL_ID_ARTIST + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USER_ID_ARTIST + " INTEGER," +
                    ARTIST + " TEXT," +
                    ARTIST_URL + " TEXT)";

    private static ProfileDatabase profileDatabaseInstance;

    private ProfileDatabase(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    public static ProfileDatabase getInstance(Context context) {
        if (profileDatabaseInstance == null) {
            profileDatabaseInstance = new ProfileDatabase(context.getApplicationContext());
        }
        return profileDatabaseInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_ENTRY);
        db.execSQL(SQL_CREATE_ARTIST_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTISTS);
        onCreate(db);
    }

    public void addProfile(String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, username);
        try {
            long insertRowID = db.insertOrThrow(TABLE_USER, null, contentValues);
            Log.d("ProfileDatabase", "inserted row " + insertRowID);

            db.close();
        } catch (Exception e) {
            Log.e("ProfileDatabase", "error", e);
        }
    }

    public long getProfile(String username){

        String query = "SELECT " + COL_ID_USER + " FROM "
                + TABLE_USER + " WHERE " + USERNAME + " = ?";
        String selectionArgs[] = {username};
        Cursor cursor = this.getReadableDatabase().rawQuery(query, selectionArgs);

        if (cursor != null){
            if (cursor.moveToFirst()){
                return cursor.getLong(cursor.getColumnIndex(COL_ID_USER));
            }
        }

        return 0;
    }

    public void addArtist(long userId, Artist artist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID_ARTIST, userId);
        contentValues.put(ARTIST, artist.getArtistName());
        contentValues.put(ARTIST_URL, artist.getArtworkUrl100());
        try {
            long id = db.insertOrThrow(TABLE_ARTISTS, null, contentValues);
            Log.d("ProfileDatabase", "add artist: " + id);
        } catch (Exception e) {
            Log.d("ProfileDatabase", "error", e);
        }
    }

    public List<Artist> getArtists(long userID){
        List<Artist> artistList = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_ARTISTS + ";", null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    Artist artist = new Artist(
                            cursor.getString(cursor.getColumnIndex(ARTIST)),
                            cursor.getString(cursor.getColumnIndex(ARTIST_URL)));
                    artistList.add(artist);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return artistList;
    }

}

