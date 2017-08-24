package es.villarleal.libros.modelo.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import es.villarleal.libros.comun.Constantes;
import es.villarleal.libros.comun.Utilidades;
import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.entidades.Libro;
import es.villarleal.libros.modelo.esquemas.IEsquemaLibro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 6/04/17.
 */

public class DaoLibros extends DaoXenerico implements IEsquemaLibro
{
    private static DaoLibros _daoLibros = null;
    private DaoLibros(SQLiteDatabase db)
    {
        super(db);
    }

    public static DaoLibros crearInstancia(SQLiteDatabase db)
    {
        if (_daoLibros == null) _daoLibros = new DaoLibros(db);
        return _daoLibros;
    }

    public String obterNomeTaboa()
    {
        return TABOA;
    }
    public String obterNomeColId()
    {
        return COL_ID;
    }

    public List<Libro> buscarLibros()
    {
        List<Libro> listaLibros = null;
        Libro libro = null;

        cursor = super.query(TABOA, COLS_LIBRO, null, null, COL_ID);

        if (cursor != null)
        {
            listaLibros = new ArrayList<Libro>();
            cursor.moveToFirst();
            while (! cursor.isAfterLast())
            {
                libro = cursorToEntity(cursor);
                listaLibros.add(libro);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listaLibros;
    }

    protected Libro cursorToEntity(Cursor cursor)
    {
        Libro libro = null;
        int indexId;
        int indexTitulo;

        if (cursor != null)
        {
            libro = new Libro();
            indexId = cursor.getColumnIndex(COL_ID);
            indexTitulo = cursor.getColumnIndex(COL_TITULO);

            if (indexId != -1) libro.setId(cursor.getLong(indexId));
            if (indexTitulo != -1) libro.setTitulo(cursor.getString(indexTitulo));
        }
        return libro;
    }


    protected void establecerValoresIniciais(Entidade entidade)
    {
        Libro libro = (Libro)entidade;
        setValoresIniciais(new ContentValues());

        getValoresIniciais().put(COL_ID, libro.getId());
        getValoresIniciais().put(COL_TITULO, libro.getTitulo());
    }

    public Cursor obterItems()
    {
        String parteSelect = COL_ID + " as _id, " + COL_TITULO;
        String parteFrom = TABOA;
        String parteOrderBy = COL_TITULO;
        Cursor cursorItems = super.facerConsulta(parteSelect, parteFrom, parteOrderBy);

        return cursorItems;
    }

    protected String getTag()
    {
        return DaoLibros.class.getName();
    }
    protected String[] obterNomeTodasCols() { return COLS_LIBRO; }

    public boolean existeEntidade(Entidade entidade)
    {
        return false;
    }

}
