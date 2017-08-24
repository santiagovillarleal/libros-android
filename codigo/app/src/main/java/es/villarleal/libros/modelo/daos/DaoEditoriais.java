package es.villarleal.libros.modelo.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import es.villarleal.libros.modelo.entidades.Editorial;
import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.esquemas.IEsquemaEditorial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 5/04/17.
 */

public class DaoEditoriais extends DaoXenerico implements IEsquemaEditorial
{
    private static DaoEditoriais _daoEditoriais = null;
    private DaoEditoriais(SQLiteDatabase db)
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

    public static DaoEditoriais crearInstancia(SQLiteDatabase db)
    {
        if (_daoEditoriais == null) _daoEditoriais = new DaoEditoriais(db);
        return _daoEditoriais;
    }

    public List<Editorial> buscarEditoriais()
    {
        List<Editorial> listaEditoriais = null;
        Editorial editorial = null;
        cursor = super.query(TABOA, COLS_EDITORIAL, null, null, COL_ID);
        if (cursor != null)
        {
            listaEditoriais = new ArrayList<Editorial>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                editorial = cursorToEntity(cursor);
                listaEditoriais.add(editorial);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listaEditoriais;
    }

    protected void establecerValoresIniciais(Entidade entidade)
    {
        Editorial editorial = (Editorial)entidade;
        setValoresIniciais(new ContentValues());

        getValoresIniciais().put(COL_ID, editorial.getId());
        getValoresIniciais().put(COL_NOME, editorial.getNome());

    }

    protected Editorial cursorToEntity(Cursor cursor)
    {
        Editorial editorial = null;
        int indexId;
        int indexNome;

        if (cursor != null)
        {
            editorial = new Editorial();
            indexId = cursor.getColumnIndex(COL_ID);
            indexNome = cursor.getColumnIndex(COL_NOME);
            if (indexId != -1) editorial.setId(cursor.getLong(indexId));
            if (indexNome != -1) editorial.setNome(cursor.getString(indexNome));
        }

        return editorial;
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
        return DaoEditoriais.class.getName();
    }
    protected String[] obterNomeTodasCols() { return COLS_EDITORIAL; }

    public boolean existeEntidade(Entidade entidade)
    {
        return false;
    }

}
