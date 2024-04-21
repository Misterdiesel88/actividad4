package com.example.prueba6.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.example.prueba6.entity.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UserDao {
    private ManagerdataBase managerDataBase;
    Context context;
    View view;
    private User user;

    public UserDao(Context context, View view) {
        this.context = context;
        this.view = view;
        managerDataBase = new ManagerdataBase(this.context);
    }
    public void insertUsers(User user) {
        try {
            SQLiteDatabase db = managerDataBase.getWritableDatabase();
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put("use_document", user.getDocument());
                values.put("use_user", user.getUser());
                values.put("use_names", user.getNames());
                values.put("use_lastNames", user.getLastNames());
                values.put("use_password", user.getPassword());
                values.put("use_status", "1");
                long cod = db.insert("users", null, values);
                Snackbar.make(this.view, "Se ha resgistrado el usuario:" + cod, Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(this.view, "No Se ha registrado el usuario", Snackbar.LENGTH_LONG).show();
            }

        } catch (SQLException e) {
            Log.i("BD", "" + e);
        }
    }

        public void updateUser(User user){
            try {
                SQLiteDatabase db = managerDataBase.getWritableDatabase();
                if(db != null) {
                    ContentValues values = new ContentValues();
                    values.put("use_user", user.getUser());
                    values.put("use_names", user.getNames());
                    values.put("use_lastNames", user.getLastNames());
                    values.put("use_password", user.getPassword());
                    int rowsAffected = db.update("users", values, "use_document = ?", new String[]{String.valueOf(user.getDocument())});
                    if(rowsAffected > 0) {
                        Snackbar.make(this.view, "Usuario actualizado correctamente", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(this.view, "No se pudo actualizar el usuario", Snackbar.LENGTH_LONG).show();
                    }
                }
            } catch (SQLException e){
                Log.i("BD", ""+e);
            }
        }


    public ArrayList <User> getUserList(){
        ArrayList<User> listUsers = new ArrayList<>();
        try{
            SQLiteDatabase db = managerDataBase.getReadableDatabase();
            String query = "select * from users where use_status = 1;";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do {
                    User user1 = new User();
                    user1.setDocument(cursor.getInt(0));
                    user1.setUser(cursor.getString(1));
                    user1.setNames(cursor.getString(2));
                    user1.setLastNames(cursor.getString(3));
                    user1.setPassword(cursor.getString(4));
                    listUsers.add(user1);
                }while (cursor.moveToNext());
            }
            cursor .close();
            db.close();
        }catch (SQLException e) {
            Log.i("BD", "" + e);
        }
        return listUsers;
    }
    public ArrayList<User> searchUsersByName(String name){
        ArrayList<User> userList = new ArrayList<>();
        try{
            SQLiteDatabase db = managerDataBase.getReadableDatabase();
            String query = "SELECT * FROM users WHERE use_names LIKE ?";
            String[] selectionArgs = {"%" + name + "%"};
            Cursor cursor = db.rawQuery(query, selectionArgs);
            if (cursor.moveToFirst()){
                do {
                    User user = new User();
                    user.setDocument(cursor.getInt(0));
                    user.setUser(cursor.getString(1));
                    user.setNames(cursor.getString(2));
                    user.setLastNames(cursor.getString(3));
                    user.setPassword(cursor.getString(4));
                    userList.add(user);
                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (SQLException e) {
            Log.i("BD", "" + e);
        }
        return userList;
    }
}
