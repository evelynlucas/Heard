package org.pursuit.heard.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "profiles.db";
    private static final String TABLE_USER = "User";
    private static final String TABLE_PROFILES = "Profiles";
    private static final int SCHEMA_VERSION = 1;
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
        db.execSQL("CREATE TABLE " + TABLE_USER +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, artists TEXT);");

        db.execSQL("CREATE TABLE " + TABLE_PROFILES +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, artists TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        onCreate(db);
    }

    public void addProfile(UserProfile userProfile) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " +
                TABLE_PROFILES +
                " WHERE username = '" +
                userProfile.getUsername() +
                "' AND artists = '" +
                userProfile.getArtists() +
                "';", null);

        if (cursor.getCount() == 0) {
            getWritableDatabase().execSQL("INSERT INTO " +
                    TABLE_PROFILES +
                    "(username, artists) " +
                    "VALUES('" +
                    userProfile.getUsername() +
                    "', '" +
                    userProfile.getArtists() +
                    "');");
        }
        cursor.close();
    }

    public List<UserProfile> getProfiles() {
        List<UserProfile> profiles = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_PROFILES + ";", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    UserProfile profile = new UserProfile(
                            cursor.getString(cursor.getColumnIndex("username")),
                            Collections.singletonList(cursor.getString(cursor.getColumnIndex("artists"))));
                    profiles.add(profile);
                } while (cursor.moveToNext());
            }
        }
        return profiles;
    }
}

