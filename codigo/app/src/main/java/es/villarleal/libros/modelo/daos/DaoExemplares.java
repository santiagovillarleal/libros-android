package es.villarleal.libros.modelo.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.entidades.Exemplar;
import es.villarleal.libros.modelo.esquemas.IEsquemaEditorial;
import es.villarleal.libros.modelo.esquemas.IEsquemaExemplar;
import es.villarleal.libros.modelo.esquemas.IEsquemaIdioma;
import es.villarleal.libros.modelo.esquemas.IEsquemaLibro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 10/04/17.
 */

public class DaoExemplares extends DaoXenerico implements IEsquemaExemplar
{
    private static DaoExemplares _daoExemplares = null;
    public DaoExemplares(SQLiteDatabase db)
    {
        super(db);
    }

    public String obterNomeTaboa()
    {
        return TABOA;
    }
    public String obterNomeColId()
    {
        return COL_ID;
    }

    public static DaoExemplares crearInstancia(SQLiteDatabase db)
    {
        if (_daoExemplares == null) _daoExemplares = new DaoExemplares(db);
        return _daoExemplares;
    }

    public List<Exemplar> buscarExemplares()
    {
        List<Exemplar> listaExemplares = null;
        Exemplar exemplar = null;

        cursor = super.query(TABOA, COLS_EXEMPLAR, null, null, COL_ID);

        if (cursor == null) return null;
        cursor.moveToFirst();
        listaExemplares = new ArrayList<Exemplar>();
        while (!cursor.isAfterLast())
        {
            exemplar = cursorToEntity(cursor);
            listaExemplares.add(exemplar);
            cursor.moveToNext();
        }
        cursor.close();
        return listaExemplares;
    }

    protected void establecerValoresIniciais(Entidade entidade)
    {
        Exemplar exemplar = (Exemplar)entidade;
        setValoresIniciais(new ContentValues());

        getValoresIniciais().put(COL_ID, exemplar.getId());
        getValoresIniciais().put(COL_COD_EXEMPLAR, exemplar.getCodExemplar());
        getValoresIniciais().put(COL_EDICION, exemplar.getEdicion());
        getValoresIniciais().put(COL_ID_LIBRO, exemplar.getIdLibro());
        getValoresIniciais().put(COL_ID_EDITORIAL, exemplar.getIdEditorial());
        getValoresIniciais().put(COL_ID_IDIOMA, exemplar.getIdIdioma());
    }

    public Exemplar cursorToEntity(Cursor cursor)
    {
        Exemplar exemplar = null;
        int indexIdExemplar;
        int indexCodExemplar;
        int indexEdicion;
        int indexIdLibro;
        int indexIdEditorial;
        int indexIdIdioma;

        if (cursor != null)
        {
            exemplar = new Exemplar();

            indexIdExemplar = cursor.getColumnIndex(COL_ID);
            indexCodExemplar = cursor.getColumnIndex(COL_COD_EXEMPLAR);
            indexEdicion = cursor.getColumnIndex(COL_EDICION);
            indexIdLibro = cursor.getColumnIndex(COL_ID_LIBRO);
            indexIdEditorial = cursor.getColumnIndex(COL_ID_EDITORIAL);
            indexIdIdioma = cursor.getColumnIndex(COL_ID_IDIOMA);

            if (indexIdExemplar != -1) exemplar.setId(cursor.getLong(indexIdExemplar));
            if (indexCodExemplar != -1) exemplar.setCodExemplar(cursor.getString(indexCodExemplar));
            if (indexEdicion != -1) exemplar.setEdicion(cursor.getString(indexEdicion));
            if (indexIdLibro != -1) exemplar.setIdLibro(cursor.getLong(indexIdLibro));
            if (indexIdEditorial != -1) exemplar.setIdEditorial(cursor.getLong(indexIdEditorial));
            if (indexIdIdioma != -1) exemplar.setIdIdioma(cursor.getLong(indexIdIdioma));
        }
        return exemplar;
    }

    public Cursor obterItems()
    {
        String parteSelect = COL_CUALIF_ID + " as _id, "
            + IEsquemaExemplar.COL_COD_EXEMPLAR + ", "
            + IEsquemaLibro.COL_CUALIF_TITULO + ", "
            + IEsquemaEditorial.COL_CUALIF_NOME + ", "
            + IEsquemaIdioma.COL_CUALIF_NOME;
        String parteFrom = TABOA + " INNER JOIN " + IEsquemaLibro.TABOA
                + " ON " + COL_CUALIF_ID_LIBRO + " = " + IEsquemaLibro.COL_CUALIF_ID
            + " INNER JOIN " + IEsquemaEditorial.TABOA + " ON " + COL_CUALIF_ID_EDITORIAL + " = " + IEsquemaEditorial.COL_CUALIF_ID
            + " INNER JOIN " + IEsquemaIdioma.TABOA + " ON " + COL_CUALIF_ID_IDIOMA + " = " + IEsquemaIdioma.COL_CUALIF_ID;
        String parteOrderBy = " 2 "; //Código exemplar.

        Cursor cursorItems = super.facerConsulta(parteSelect, parteFrom, parteOrderBy);
        return cursorItems;

    }

    protected String getTag()
    {
        return DaoExemplares.class.getName();
    }
    protected String[] obterNomeTodasCols() { return COLS_EXEMPLAR; }

    public boolean existeEntidade(Entidade entidade)
    {
        return false;
    }

}
