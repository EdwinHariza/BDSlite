package co.edu.uniminuto.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//Clase para gestionar la base de datos



public class ManagerDatabase extends SQLiteOpenHelper {

    private static final String DATA_BASE = "dbUsers";
    private static final int VERSION = 1;
    private static final String TABLE_USERS = "users";

    //Consulta que permite crear la base de datos
    //Crear la base de datos
    private static final String CREATE_TABLE = "CREATE TABLE " +TABLE_USERS+" (use_document INTEGER " +
            "PRIMARY KEY NOT NULL, use_user VARCHAR(50) NOT NULL, use_names VARCHAR(150) NOT NULL," +
            "use_lastNames VARCHAR(150) NOT NULL, use_password VARCHAR(25) NOT NULL, use_Status " +
            "VARCHAR (1) NOT NULL);";

    //Eliminar las base de datos
    private static final String DELETE_TABLE = "DROP TABLE IF EXITS "+TABLE_USERS;

    //Construcción
    public ManagerDatabase(@Nullable Context context) {
        super(context, DATA_BASE, null, VERSION);
    }

    //Crear las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    //Modificaciones estructurales
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate((db));
    }
}
