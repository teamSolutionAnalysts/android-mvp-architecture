package com.sa.baseproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sa.baseproject.model.UserModel;

import java.util.ArrayList;

/**
 * Created by sa on 01/04/17.
 *
 */

public class DBUser extends DBHelper {

    static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_PHONE = "phone";


    static final String CREATE_TABLE_USER = " CREATE TABLE " + TABLE_USER + " (" +
            COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
            COLUMN_USER_NAME + " TEXT, " +
            COLUMN_PHONE + " TEXT)";


    private SQLiteDatabase db;

    public DBUser(final Context context) {
        super(context);
        db = getDb();
    }


    public boolean insertUser(final String userName, final String number) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, userName);
        contentValues.put(COLUMN_PHONE, number);
        return db.insert(TABLE_USER, null, contentValues) > 0;
    }

    public boolean updateUser(final String id, final String userName, final String number) {
        final String whereClause = COLUMN_USER_ID + " = ? ";
        final String[] whereArgs = {id};
        final ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, userName);
        contentValues.put(COLUMN_PHONE, number);
        return db.update(TABLE_USER, contentValues, whereClause, whereArgs) > 0;
    }

    /**
     * Using Transaction we can insert bulk data in db. More faster then one by one insert.
     * @param contactArrayList Data which we wants to insert in SQlite.
     */
    public void insertUserModel(final ArrayList<UserModel> contactArrayList) {
        try {
            db.beginTransaction();
            contactArrayList.trimToSize();
            for (int t = 0; t < contactArrayList.size(); t++) {
                final UserModel contactModel = contactArrayList.get(t);
                final String id = contactModel.getId();
                final String userName = contactModel.getName();
                final String phone = contactModel.getPhone();
                final ContentValues values = new ContentValues();
                values.put(COLUMN_USER_NAME, userName);
                values.put(COLUMN_PHONE, phone);
                db.insert(TABLE_USER, null, values);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteUsers() {
        return db.delete(TABLE_USER, null, null) > 0;
    }


    /**
     * Retrieve report data from table
     */
    public ArrayList<UserModel> getUserList() {
        final ArrayList<UserModel> userModelArrayList = new ArrayList<>();
        try {
            final Cursor cursor = db.query(TABLE_USER, null, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        final UserModel userModel = new UserModel();
                        userModel.setId(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
                        userModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                        userModel.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                        userModelArrayList.add(userModel);
                    } while (cursor.moveToNext());
                }
            }
            userModelArrayList.trimToSize();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userModelArrayList;
    }


    public boolean updateUser(final int id, final String name, final String phone) {
        final String[] args = {"" + id};
        final String where = COLUMN_USER_ID + " = ? ";
        final ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_PHONE, phone);
        return db.update(TABLE_USER, values, where, args) > 0;
    }
}
