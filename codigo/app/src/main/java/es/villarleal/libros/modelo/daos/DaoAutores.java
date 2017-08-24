package es.villarleal.libros.modelo.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import es.villarleal.libros.comun.Constantes;
import es.villarleal.libros.comun.Utilidades;
import es.villarleal.libros.modelo.entidades.Autor;
import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.esquemas.IEsquemaAutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 5/04/17.
 */

public class DaoAutores extends DaoXenerico implements IEsquemaAutor
{
    private static DaoAutores _daoAutores = null;
    public DaoAutores(SQLiteDatabase db)
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

    public static DaoAutores crearInstancia(SQLiteDatabase db)
    {
        if (_daoAutores == null) _daoAutores = new DaoAutores(db);
        return _daoAutores;
    }

    public List<Autor> buscarAutores()
    {
        List<Autor> listaAutores = null;
        Autor autor = null;

        cursor = super.query(TABOA, COLS_AUTOR, null, null, COL_ID);
        if (cursor != null)
        {
            listaAutores = new ArrayList<Autor>();
            cursor.moveToFirst();
            while (! cursor.isAfterLast())
            {
                autor = cursorToEntity(cursor);
                listaAutores.add(autor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listaAutores;

    }

    protected Autor cursorToEntity(Cursor cursor)
    {
        Autor autor = null;
        int indexId;
        int indexNome;
        int indexApelidos;

        if (cursor != null)
        {
            autor = new Autor();
            indexId = cursor.getColumnIndex(COL_ID);
            indexNome = cursor.getColumnIndex(COL_NOME);
            indexApelidos = cursor.getColumnIndex(COL_APELIDOS);

            if (indexId != -1) autor.setId(cursor.getLong(indexId));
            if (indexNome != -1) autor.setNome(cursor.getString(indexNome));
            if (indexApelidos != -1) autor.setApelidos(cursor.getString(indexApelidos));
        }
        return autor;
    }

    protected void establecerValoresIniciais(Entidade entidade)
    {
        Autor autor = (Autor)entidade;
        setValoresIniciais(new ContentValues());

        getValoresIniciais().put(COL_ID, autor.getId());
        getValoresIniciais().put(COL_NOME, autor.getNome());
        getValoresIniciais().put(COL_APELIDOS, autor.getApelidos());
    }

    public Cursor obterItems()
    {
        return obterItemsSelec(false, null);
    }

    public Cursor obterItemsSelec(boolean soloColSelec, List<Long> listaIdSelec)
    {
        String parteSelectSelec = null;
        String listaIdStr = null;
        String parteWhere = null;
        if (Utilidades.eListaBaleira(listaIdSelec)) parteSelectSelec = "0";
        else
        {
            listaIdStr = TextUtils.join(Constantes.CTE_COMA, listaIdSelec);
            parteSelectSelec = " CASE WHEN " + COL_ID + " IN (" + listaIdStr + ") THEN 1 ELSE 0 END ";
        }

        String parteSelect = COL_ID + " as _id, " + COL_CUALIF_NOME_COMPLETO + ", " + parteSelectSelec + " AS selec ";
        String parteFrom = TABOA;
        String parteOrderBy = COL_NOME + " COLLATE NOCASE ";

        if (soloColSelec)
        {
            if (!Utilidades.eListaBaleira(listaIdSelec))
            {
                parteWhere = COL_ID + " IN (" + listaIdStr + " ) ";
            }
            else
            {
                return null; //devolver sólo filas seleccionadas, non hai ningunha.
            }
        }
        else
        {
            parteWhere = Constantes.CTE_BALEIRO; //devolvémolas todas.
        }
        Cursor cursorItems = super.facerConsulta(parteSelect, parteFrom, parteWhere, null, parteOrderBy);

        return cursorItems;
    }

    protected String getTag()
    {
        return DaoAutores.class.getName();
    }

    protected String[] obterNomeTodasCols() { return COLS_AUTOR; }

    public boolean existeEntidade(Entidade entidade)
    {
        return false;
    }


    public Cursor obterAutores(List<Long> listaIdAutores)
    {
        String parteSelect = COL_ID + " as _id, " + COL_CUALIF_NOME_COMPLETO;
        String parteFrom = TABOA;
        String parteWhere = null;
        String parteOrderBy = " 2 "; //Nome completo.
        if (! Utilidades.eListaBaleira(listaIdAutores))
        {
            parteWhere = COL_ID + " IN ( " + TextUtils.join(Constantes.CTE_COMA, listaIdAutores) + " ) ";
        }
        Cursor cursor = super.facerConsulta(parteSelect, parteFrom, parteWhere, null, parteOrderBy);

        return cursor;
    }

}

