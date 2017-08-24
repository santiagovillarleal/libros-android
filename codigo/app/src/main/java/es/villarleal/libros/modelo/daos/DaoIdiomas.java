package es.villarleal.libros.modelo.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.entidades.Idioma;
import es.villarleal.libros.modelo.esquemas.IEsquemaIdioma;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 5/04/17.
 */

public class DaoIdiomas extends DaoXenerico implements IEsquemaIdioma
{
    private static DaoIdiomas _daoIdiomas = null;

    private DaoIdiomas(SQLiteDatabase db)
    {
        super(db);
    }

    public static DaoIdiomas crearInstancia(SQLiteDatabase db)
    {
        if (_daoIdiomas == null) _daoIdiomas = new DaoIdiomas(db);
        return _daoIdiomas;
    }

    public String obterNomeTaboa()
    {
        return TABOA;
    }
    public String obterNomeColId()
    {
        return COL_ID;
    }

    public List<Idioma> buscarIdiomas()
    {
        List<Idioma> listaIdiomas = null;
        Idioma idioma = null;
        cursor = super.query(TABOA, COLS_IDIOMA, null, null, COL_ID);
        if (cursor != null)
        {
            listaIdiomas = new ArrayList<Idioma>();
            cursor.moveToFirst();
            while (! cursor.isAfterLast())
            {
                idioma = cursorToEntity(cursor);
                listaIdiomas.add(idioma);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return listaIdiomas;
    }

    protected Idioma cursorToEntity(Cursor cursor)
    {
        Idioma idioma = null;
        int idIndex;
        int nomeIndex;

        if (cursor != null)
        {
            idioma = new Idioma();
            idIndex = cursor.getColumnIndex(COL_ID);
            nomeIndex = cursor.getColumnIndex(COL_NOME);
            if (idIndex != -1) idioma.setId(cursor.getLong(idIndex));
            if (nomeIndex != -1) idioma.setNome(cursor.getString(nomeIndex));
        }
        return idioma;
    }

    protected void establecerValoresIniciais(Entidade entidade)
    {
        Idioma idioma = (Idioma)entidade;
        setValoresIniciais(new ContentValues());
        getValoresIniciais().put(COL_ID, idioma.getId());
        getValoresIniciais().put(COL_NOME, idioma.getNome());

    }

    public Cursor obterItems()
    {
        String parteSelect = COL_ID + " as _id, " + COL_NOME;
        String parteFrom = TABOA;
        String parteOrderBy = COL_NOME;
        Cursor cursorItems = super.facerConsulta(parteSelect, parteFrom, parteOrderBy);

        return cursorItems;
    }

    protected String getTag()
    {
        return DaoIdiomas.class.getName();
    }
    protected String[] obterNomeTodasCols() { return COLS_IDIOMA; }

    public boolean existeEntidade(Entidade entidade)
    {
        return false;
    }

}
