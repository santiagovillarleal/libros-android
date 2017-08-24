package es.villarleal.libros.modelo.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import es.villarleal.libros.comun.Constantes;
import es.villarleal.libros.comun.Utilidades;
import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.entidades.Libro;
import es.villarleal.libros.modelo.entidades.LibrosDeAutores;
import es.villarleal.libros.modelo.esquemas.IEsquemaLibrosDeAutores;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 15/04/17.
 */

public class DaoLda extends DaoXenerico implements IEsquemaLibrosDeAutores
{
    private static DaoLda _daoLda = null;
    private DaoLda(SQLiteDatabase db)
    {
        super(db);
    }

    public static DaoLda crearInstancia(SQLiteDatabase db)
    {
        if (_daoLda == null) _daoLda = new DaoLda(db);
        return _daoLda;
    }

    public String obterNomeTaboa()
    {
        return TABOA;
    }
    public String obterNomeColId()
    {
        return "";
    }

    public LibrosDeAutores buscarLdaPorId(long idAutor, long idLibro)
    {
        final String[] selectionArgs = new String[] {String.valueOf(idAutor), String.valueOf(idLibro)};
        final String selection = COL_ID_AUTOR + " = ? AND " + COL_ID_LIBRO + " = ? ";
        LibrosDeAutores lda = null;

        cursor = super.query(TABOA, COLS_LDA, selection, selectionArgs, COL_ID_AUTOR + ", " + COL_ID_LIBRO);
        if (cursor != null)
        {
            lda = new LibrosDeAutores();
            cursor.moveToFirst();

            while (!cursor.isAfterLast())
            {
                lda = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return lda;
    }

    protected LibrosDeAutores cursorToEntity(Cursor cursor)
    {
        LibrosDeAutores lda = null;
        int indexIdAutor;
        int indexIdLibro;

        if (cursor != null)
        {
            lda = new LibrosDeAutores();
            indexIdAutor = cursor.getColumnIndex(COL_ID_AUTOR);
            indexIdLibro = cursor.getColumnIndex(COL_ID_LIBRO);

            if (indexIdAutor != -1) lda.setIdAutor(cursor.getLong(indexIdAutor));
            if (indexIdLibro != -1) lda.setIdLibro(cursor.getLong(indexIdLibro));
        }
        return lda;
    }

    public List<Long> buscarLibrosPorAutor(long idAutor)
    {
        List<Long> listaIdLibros = null;
        LibrosDeAutores lda = null;
        final String[] selectionArgs = new String[] {String.valueOf(idAutor)};
        final String selection = COL_ID_AUTOR + " = ? ";
        final String[] selectionCols = new String [] {COL_ID_LIBRO};

        //SELECT id_libro FROM lda WHERE id_autor = ?

        cursor = super.query(TABOA, selectionCols, selection, selectionArgs, COL_ID_LIBRO);

        if (cursor != null)
        {
            listaIdLibros = new ArrayList<Long>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                lda = cursorToEntity(cursor);
                listaIdLibros.add(lda.getIdLibro());
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listaIdLibros;
    }

    public List<Long> buscarAutoresPorLibro(long idLibro)
    {
        List<Long> listaIdAutores = null;
        LibrosDeAutores lda = null;
        final String[] selectionArgs = new String[] {String.valueOf(idLibro)};
        final String selection = COL_ID_LIBRO + " = ? ";
        final String[] selectionCols = new String [] {COL_ID_AUTOR};

        //SELECT id_autor FROM lda WHERE id_libro = ?

        cursor = super.query(TABOA, selectionCols, selection, selectionArgs, COL_ID_AUTOR);

        if (cursor != null)
        {
            listaIdAutores = new ArrayList<Long>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                lda = cursorToEntity(cursor);
                listaIdAutores.add(lda.getIdAutor());
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listaIdAutores;
    }
    public Cursor obterItems() {return null; }
    protected String getTag() { return DaoLda.class.getName(); }

    protected void establecerValoresIniciais(Entidade entidade)
    {
        LibrosDeAutores lda = (LibrosDeAutores) entidade;
        setValoresIniciais(new ContentValues());

        getValoresIniciais().put(COL_ID_AUTOR, lda.getIdAutor());
        getValoresIniciais().put(COL_ID_LIBRO, lda.getIdLibro());
    }

    public boolean grabarLda(Libro libro)
    {
        LibrosDeAutores lda = null;
        boolean res = false;
        if (Utilidades.eListaBaleira(libro.getAutores())) return true;
        List<Long> listaIdAutores = libro.getAutores();

        for (Long idAutor : listaIdAutores)
        {
            lda = new LibrosDeAutores();
            lda.setIdLibro(libro.getId());
            lda.setIdAutor(idAutor);
            establecerValoresIniciais(lda);
            try
            {
                res = super.insert(TABOA, getValoresIniciais()) > 0;
            }
            catch (Exception e)
            {
                String msg = Utilidades.truncar(getTag()+"grabarLda", 23);
                Log.e(msg, e.toString());
                return false;
            }
        }
        return res;
    }
    protected String[] obterNomeTodasCols() { return COLS_LDA; }

    public boolean existeEntidade(Entidade entidade)
    {
        return false;
    }

    private boolean eliminarLda(long idAutor, long idLibro) throws Exception
    {
        String selection = COL_ID_AUTOR + " = ? AND " + COL_ID_LIBRO + " = ? ";
        String[] selectionArgs = new String[] {String.valueOf(idAutor), String.valueOf(idLibro)};

        return super.delete(TABOA, selection, selectionArgs) > 0;
    }

    public boolean eliminarAsocAutores(Long idLibro)
    {
        List<Long> listaIdAutoresBorrar = buscarAutoresPorLibro(idLibro);
        if (Utilidades.eListaBaleira(listaIdAutoresBorrar)) return true;
        try
        {
            for (Long idAutor : listaIdAutoresBorrar)
            {
                if (!eliminarLda(idAutor, idLibro)) return false;
            }
        }
        catch (Exception e)
        {
            String msg = Utilidades.truncar(getTag() + "eliminarAsocAutores", Constantes.CTE_TAM_MAX_TAG);
            Log.e(msg, e.toString());
        }
        return true;
    }


}
