package co.edu.uniminuto.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;

import co.edu.uniminuto.entity.User;

public class UserDao {
    private ManagerDatabase managerDatabase;

    Context context;
    View view;
    private User user;

    public UserDao(Context context, View view) {
        this.context = context;
        this.view = view;

        //Instanciar
        managerDatabase = new ManagerDatabase(this.context);
    }

    //Metodo - Busqueda
    @SuppressLint("Range")
    public User getUserByDocument(int document) {
        User user = null;
        try {
            SQLiteDatabase db = managerDatabase.getReadableDatabase();
            if (db != null) {
                Cursor cursor = db.query("users", null, "use_document = ?", new String[]{String.valueOf(document)}, null, null, null);
                if (cursor.moveToFirst()) {
                    // Si se encuentra un usuario con el documento dado, crear un objeto User con los datos obtenidos
                    user = new User();
                    user.setDocument(cursor.getInt(cursor.getColumnIndex("use_document")));
                    user.setNames(cursor.getString(cursor.getColumnIndex("use_names")));
                    user.setLastName(cursor.getString(cursor.getColumnIndex("use_lastNames")));
                    user.setUser(cursor.getString(cursor.getColumnIndex("use_user")));
                    user.setPassword(cursor.getString(cursor.getColumnIndex("use_password")));
                }
                cursor.close();
            }
        } catch (SQLException e) {
            Log.i("80", "" + e);
        }
        return user;
    }



    //Metodo - Agregar Funcionalidad de Actualización
    public void updateUser(User user) {
        try {
            SQLiteDatabase db = managerDatabase.getWritableDatabase();
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put("use_names", user.getNames());
                values.put("use_lastNames", user.getLastName());
                values.put("use_user", user.getUser());
                values.put("use_password", user.getPassword());

                int rowsAffected = db.update("users", values, "use_document = ?", new String[]{String.valueOf(user.getDocument())});
                if (rowsAffected > 0) {
                    Snackbar.make(this.view, "Usuario actualizado correctamente", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(this.view, "No se pudo actualizar el usuario", Snackbar.LENGTH_LONG).show();
                }
            }
        } catch (SQLException e) {
            Log.i("Error", "" + e);
        }
    }
    public void insertUser (User user){
        try {
            SQLiteDatabase db = managerDatabase.getWritableDatabase();
            if(db != null){
                ContentValues values = new ContentValues();
                values.put("use_document",user.getDocument());
                values.put("use_user",user.getUser());
                values.put("use_names",user.getNames());
                values.put(("use_lastNames"),user.getLastName());
                values.put("use_password",user.getPassword());
                values.put("use_Status","1");

                long cod = db.insert("users", null,values);
                Snackbar.make(this.view,"Se ha registrado el usuario: "+cod,Snackbar.LENGTH_LONG).show();
            }else {
                Snackbar.make(this.view,"No ha registrado el usuario: ",Snackbar.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            Log.i("80",""+ e);
        }
    }

    public ArrayList<User> getUserList(){
        ArrayList<User> listUsers = new ArrayList<>();
        try{
            SQLiteDatabase db = managerDatabase.getReadableDatabase();
            String  query = "select * from users where use_Status = 1;";
            Cursor cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do{
                    User user1 = new User();
                    user1.setDocument(cursor.getInt(0));
                    user1.setUser(cursor.getString(1));
                    user1.setNames(cursor.getString(2));
                    user1.setLastName(cursor.getString(3));
                    user1.setPassword(cursor.getString(4));

                    listUsers.add(user1);
                }while ((cursor.moveToNext()));
            }
            cursor.close();
            db.close();
        }catch (SQLException e){
            Log.i("80",""+ e);
        }
        return listUsers;
    }

}
