package es.villarleal.libros.modelo;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import es.villarleal.libros.modelo.esquemas.IEsquemaAutor;
import es.villarleal.libros.modelo.esquemas.IEsquemaEditorial;
import es.villarleal.libros.modelo.esquemas.IEsquemaExemplar;
import es.villarleal.libros.modelo.esquemas.IEsquemaIdioma;
import es.villarleal.libros.modelo.esquemas.IEsquemaLibro;
import es.villarleal.libros.modelo.esquemas.IEsquemaLibrosDeAutores;
import es.villarleal.libros.modelo.servizos.ServizoAutores;
import es.villarleal.libros.modelo.servizos.ServizoEditoriais;
import es.villarleal.libros.modelo.servizos.ServizoExemplares;
import es.villarleal.libros.modelo.servizos.ServizoIdiomas;
import es.villarleal.libros.modelo.servizos.ServizoLibros;

/**
 * Created by santiago on 10/04/17.
 */

public class Modelo
{
    private static Modelo _modelo = null;
    private static final String TAG = "LogDBLibros";
    private static final String DB_NAME = "DBLibros";
    private DatabaseHelper mDbHelper;
    private static final int DB_VERSION = 6;
    private Context mContext = null;
    public static ServizoIdiomas servizoIdiomas = null;
    public static ServizoEditoriais servizoEditoriais = null;
    public static ServizoAutores servizoAutores = null;
    public static ServizoLibros servizoLibros = null;
    public static ServizoExemplares servizoExemplares = null;

    private Modelo(Context context)
    {
        this.mContext = context;
    }

    public static Modelo crearInstancia(Context ctx)
    {
        if (_modelo == null)
        {
            _modelo = new Modelo(ctx);
            _modelo.open();
        }
        return _modelo;
    }

    public Modelo open() throws SQLException
    {
        mDbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

        servizoIdiomas = ServizoIdiomas.crearInstancia(mDb);
        servizoEditoriais = ServizoEditoriais.crearInstancia(mDb);
        servizoAutores = ServizoAutores.crearInstancia(mDb);
        servizoLibros = ServizoLibros.crearInstancia(mDb);
        servizoExemplares = ServizoExemplares.crearInstancia(mDb);

        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(IEsquemaIdioma.TABOA_CREATE);
            db.execSQL(IEsquemaEditorial.TABOA_CREATE);
            db.execSQL(IEsquemaAutor.TABOA_CREATE);
            db.execSQL(IEsquemaLibro.TABOA_CREATE);
            db.execSQL(IEsquemaLibrosDeAutores.TABOA_CREATE);
            db.execSQL(IEsquemaExemplar.TABOA_CREATE);
        }

        public void onUpgrade(SQLiteDatabase db, int versionVella, int versionNova)
        {
            //Cambio en LDA, quito a chave primaria que tiña.
            //A táboa nace dunha relación N-N, polo tanto a súa chave primaria é a formada polas
            //dúas chaves foráneas, id_autor e id_libro.
            Log.w(TAG, "Atualizando base de datos da versión " + versionVella + " á versión " + versionNova
                + "que vai eliminar os datos");
            db.execSQL("DROP TABLE IF EXISTS " + IEsquemaLibrosDeAutores.TABOA);
            db.execSQL("DROP TABLE IF EXISTS " + IEsquemaIdioma.TABOA);
            db.execSQL("DROP TABLE IF EXISTS " + IEsquemaEditorial.TABOA);
            db.execSQL("DROP TABLE IF EXISTS " + IEsquemaAutor.TABOA);
            db.execSQL("DROP TABLE IF EXISTS " + IEsquemaLibro.TABOA);
            db.execSQL("DROP TABLE IF EXISTS " + IEsquemaExemplar.TABOA);

            onCreate(db);
        }
    }
}
