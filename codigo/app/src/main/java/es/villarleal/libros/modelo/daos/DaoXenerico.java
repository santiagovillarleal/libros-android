package es.villarleal.libros.modelo.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import es.villarleal.libros.comun.Constantes;
import es.villarleal.libros.comun.Utilidades;
import es.villarleal.libros.modelo.entidades.Entidade;

/**
 * Created by santiago on 4/04/17.
 */

public abstract class DaoXenerico {
    private static SQLiteDatabase mDb;
    protected Cursor cursor;
    protected ContentValues valoresIniciais;

    protected DaoXenerico(SQLiteDatabase db) {
        this.mDb = db;
    }

    public ContentValues getValoresIniciais()
    {
        return valoresIniciais;
    }

    public void setValoresIniciais(ContentValues valoresIniciais)
    {
        this.valoresIniciais = valoresIniciais;
    }

    public int delete(String tableName, String selection,
                      String[] selectionArgs) {
        return mDb.delete(tableName, selection, selectionArgs);
    }

    public long insert(String tableName, ContentValues values) {
        return mDb.insert(tableName, null, values);
    }

    public Cursor query(String tableName, String[] columns,
                        String selection, String[] selectionArgs, String sortOrder) {

        final Cursor cursor = mDb.query(tableName, columns,
                selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    public Cursor query(String tableName, String[] columns,
                        String selection, String[] selectionArgs, String sortOrder,
                        String limit) {

        return mDb.query(tableName, columns, selection,
                selectionArgs, null, null, sortOrder, limit);
    }

    public Cursor query(String tableName, String[] columns,
                        String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit) {

        return mDb.query(tableName, columns, selection,
                selectionArgs, groupBy, having, orderBy, limit);
    }

    public int update(String tableName, ContentValues values,
                      String selection, String[] selectionArgs) {
        return mDb.update(tableName, values, selection,
                selectionArgs);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return mDb.rawQuery(sql, selectionArgs);
    }

    public Cursor facerConsulta(
        final String parteSelect, final String parteFrom, final String parteWhere,
        final String parteGroupBy, final String parteHaving,
        final String parteOrder, String[] selectionArgs)
    {
        StringBuilder stb = new StringBuilder();
        String query = null;

        stb.append("SELECT " + parteSelect);
        stb.append(" FROM " + parteFrom);
        if (!TextUtils.isEmpty(parteWhere)) stb.append(" WHERE " + parteWhere);
        if (!TextUtils.isEmpty(parteGroupBy)) stb.append(" GROUP BY " + parteGroupBy);
        if (!TextUtils.isEmpty(parteHaving)) stb.append(" HAVING " + parteHaving);
        if (!TextUtils.isEmpty(parteOrder)) stb.append(" ORDER BY " + parteOrder);

        query = stb.toString();

        return mDb.rawQuery(query, selectionArgs);
    }

    public Cursor facerConsulta(
            final String parteSelect, final String parteFrom, final String parteWhere,
            final String parteGroupBy, final String parteHaving,
            String[] selectionArgs)
    {
        return facerConsulta(parteSelect, parteFrom, parteWhere, parteGroupBy, parteHaving, null, selectionArgs);
    }

    public Cursor facerConsulta(
            final String parteSelect, final String parteFrom, final String parteOrderBy)
    {
        return facerConsulta(parteSelect, parteFrom, null, null, null, parteOrderBy, null);
    }

    public Cursor facerConsulta(
            final String parteSelect, final String parteFrom, final String parteWhere,
            String[] selectionArgs)
    {
        return facerConsulta(parteSelect, parteFrom, parteWhere, null, null, null, selectionArgs);
    }

    public Cursor facerConsulta(
            final String parteSelect, final String parteFrom, final String parteWhere,
            String[] selectionArgs, final String parteOrder)
    {
        return facerConsulta(parteSelect, parteFrom, parteWhere, null, null, parteOrder, selectionArgs);
    }

    public Cursor facerConsulta(
            final String parteSelect, final String parteFrom)
    {
        return facerConsulta(parteSelect, parteFrom, null, null, null, null, null);
    }

    public long obterIdSeguinte()
    {
        long id = 1;
        int indexMaxId;
        String parteSelect = " MAX("+obterNomeColId()+") AS MAX_ID ";
        String parteFrom = obterNomeTaboa();
        cursor = facerConsulta(parteSelect, parteFrom);

        if (cursor == null) return id;
        cursor.moveToFirst();
        indexMaxId = cursor.getColumnIndex("MAX_ID");
        if (indexMaxId != -1) id = cursor.getLong(indexMaxId)+1;

        return id;
    }

    public boolean editarEntidade(Entidade entidade)
    {
        String tableName = obterNomeTaboa();
        String selection = obterNomeColId() + " = ? ";
        String[] selectionArgs = new String[] {String.valueOf(entidade.getId())};
        establecerValoresIniciais(entidade);
        try
        {
            return update(tableName, getValoresIniciais(), selection, selectionArgs) > 0;
        }
        catch (Exception e)
        {
            String tag = Utilidades.truncar(getTag() + ".editarEnt", Constantes.CTE_TAM_MAX_TAG);
            Log.e(tag, e.toString());
            return false;
        }
    }

    public Entidade buscarEntidade(long id)
    {
        final String[] selectionArgs = {String.valueOf(id)};
        final String selection = obterNomeColId() + " = ? ";
        Entidade entidade = null;
        cursor = query(obterNomeTaboa(), obterNomeTodasCols(), selection, selectionArgs, obterNomeColId());
        if (cursor != null)
        {
            cursor.moveToFirst();
            while (! cursor.isAfterLast())
            {
                entidade = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return entidade;
    }

    public boolean grabarEntidade(Entidade entidade)
    {
        establecerValoresIniciais(entidade);
        try
        {
            return insert(obterNomeTaboa(), getValoresIniciais()) > 0;
        }
        catch (Exception e) //SQLiteConstraintException
        {
            String msg = Utilidades.truncar(getTag() + "grabarEnt", Constantes.CTE_TAM_MAX_TAG);
            Log.e(msg, e.toString());
            return false;
        }
    }

    public boolean borrarEntidade(Entidade entidade)
    {
        String selection = obterNomeColId() + " = ? ";
        String[] selectionArgs = new String[] {String.valueOf(entidade.getId())};

        try
        {
            return delete(obterNomeTaboa(), selection, selectionArgs) > 0;
        }
        catch (Exception e)
        {
            String msg = Utilidades.truncar(getTag() + "borrarEnt", Constantes.CTE_TAM_MAX_TAG);
            Log.e(msg, e.toString());
            return false;
        }
    }

    protected abstract <T> T cursorToEntity(Cursor cursor);
    protected abstract void establecerValoresIniciais(Entidade entidade);
    //Patrón Template Method pra implementar o método obterIdSeguinte()
    protected abstract String obterNomeTaboa();
    protected abstract String[] obterNomeTodasCols();
    protected abstract String obterNomeColId();
    public abstract Cursor obterItems();
    protected abstract String getTag();

    public abstract boolean existeEntidade(Entidade entidade);

    public long obterNumEntidades()
    {
        long numEntidades = 0;
        int indexNum;
        String parteSelect = "COUNT(*) as num ";
        String parteFrom = obterNomeTaboa();
        Cursor cursor = facerConsulta(parteSelect, parteFrom);
        if (cursor != null)
        {
            cursor.moveToFirst();
            indexNum = cursor.getColumnIndex("num");
            if (indexNum != -1) numEntidades = cursor.getLong(indexNum);
        }

        return numEntidades;
    }

}
