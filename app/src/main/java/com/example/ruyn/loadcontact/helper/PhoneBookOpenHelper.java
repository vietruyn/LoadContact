package com.example.ruyn.loadcontact.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ruyn.loadcontact.model.Friend;
import com.example.ruyn.loadcontact.model.PhoneBook;
import com.example.ruyn.loadcontact.model.User;


public class PhoneBookOpenHelper extends SQLiteOpenHelper {
    public Context context;

    public static final String DATABASE_NAME = "CLOSECHAT_DB";

    public static final int VERSION = 1;
    /**
     * List contact table
     */
    public static final String TABLE_LISTCONTACT = "LIST_CONTACT";
    public static final String KEY_ID = "_id";
    public static final String FNAME = "_fname";
    public static final String PNUMBER = "_phonenumber";
    public static final String EMAIL = "_email";
    public static final String TYPE = "_type";

    /**
     * User table
     */
    public static final String TABLE_USER = "USER_INFODDDD";
    public static final String U_NAME = "_uname";
    public static final String U_PASSWORD = "_upassword";
    public static final String U_FULLNAME = "_ufullname";
    public static final String U_PHONUMBER = "_uphonenumber";

    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */

    /**
     * @param context
     */
    public PhoneBookOpenHelper(Context context) {

        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    /**
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try {
            String CREATE_LISTCONTACT_TB = "create table " + TABLE_LISTCONTACT + " ("
                    + KEY_ID + " integer primary key autoincrement, " + FNAME
                    + " text not null, " + PNUMBER + " text not null, " + EMAIL + " text, " + TYPE + " INTEGER);";


            String CREATE_TABLE_USER = "CREATE TABLE "
                    + TABLE_USER + "("
                    + U_NAME + " TEXT,"
                    + U_PASSWORD + " TEXT,"
                    + U_FULLNAME + " TEXT,"
                    + U_PHONUMBER + " TEXT)";


            /**
             * Create table
             */
            db.execSQL(CREATE_LISTCONTACT_TB);
            db.execSQL(CREATE_TABLE_USER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTCONTACT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param name
     * @param phoneNumber
     * @param email
     * @param type
     */
    public void putInfomationContacList(String name, String phoneNumber, String email, int type) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FNAME, name);
            values.put(PNUMBER, phoneNumber);
            values.put(EMAIL, email);
            values.put(TYPE, type);
            db.insert(TABLE_LISTCONTACT, null, values);
            db.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public void putUser(User user) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(U_NAME, user.username);
            values.put(U_PASSWORD, user.password);
            values.put(U_FULLNAME, user.fullname);
            values.put(U_PHONUMBER, user.number);

            // Inserting Row
            db.insert(TABLE_USER, null, values);
            db.close(); // Closing database connection
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void putContact(Friend friend) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(FNAME, friend.fullName);
            values.put(PNUMBER, friend.phoneNumber);
            values.put(EMAIL, friend.email);
            values.put(TYPE, friend.type);

            // Inserting Row
            db.insert(TABLE_LISTCONTACT, null, values);
            db.close(); // Closing database connection
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }





    // delete all data in User table

    /**
     * Delete table
     */
    public void deleteAllUserInfor() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete  from " + TABLE_USER);
            db.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Delete table
     */
    public void deleteAllContact() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete  from " + TABLE_LISTCONTACT);
            db.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * lay thong tin cua listcontact
     *
     * @param db
     * @return
     */

    public Cursor getInfomationContactList(PhoneBookOpenHelper db) {
        try {
            SQLiteDatabase SQ = db.getReadableDatabase();
            String[] colum = {KEY_ID, FNAME, PNUMBER, EMAIL, TYPE};
            Cursor cr = SQ.query(TABLE_LISTCONTACT, colum, null, null, null, null,
                    null);
            return cr;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lay thong tin cua username
     *
     * @param db
     * @return
     */
    public Cursor getInfomationUser(PhoneBookOpenHelper db) {
        try {
            SQLiteDatabase SQ = db.getReadableDatabase();
            String[] colum = {U_NAME, U_PASSWORD, U_FULLNAME, U_PHONUMBER};
            Cursor cr = SQ.query(TABLE_USER, colum, null, null, null, null,
                    null);
            return cr;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Update contact
     *
     * @param type
     * @param username
     */

    public void updateFriend(String username, int type) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TYPE, type);
            db.update(TABLE_LISTCONTACT, values, EMAIL + "=?",
                    new String[]{username});
            db.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void updateContactList(int type, String username) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "UPDATE LIST_CONTACT SET _type = " + type  + " WHERE _email = '" + username + "'";
            db.execSQL(strSQL);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int getCountContact() {
        String SQLQuery = "SELECT COUNT(" + KEY_ID + ") FROM " + TABLE_LISTCONTACT + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }
    public boolean deleteUser(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LISTCONTACT, EMAIL + "=" + name, null)>0;
    }



}
