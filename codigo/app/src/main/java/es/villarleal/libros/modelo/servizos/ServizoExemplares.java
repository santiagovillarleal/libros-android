package es.villarleal.libros.modelo.servizos;

import android.database.sqlite.SQLiteDatabase;

import es.villarleal.libros.comun.IEnums;
import es.villarleal.libros.modelo.daos.DaoExemplares;

/**
 * Created by santiago on 3/04/17.
 */

public class ServizoExemplares extends ServizoXenerico
{
    private static ServizoExemplares _servizoExemplares = null;
    private DaoExemplares _daoExemplares = null;

    private ServizoExemplares(SQLiteDatabase db)
    {
        super(DaoExemplares.crearInstancia(db));
        _daoExemplares = (DaoExemplares) _dao;
    }

    public static ServizoExemplares crearInstancia(SQLiteDatabase db)
    {
        if (_servizoExemplares == null) _servizoExemplares = new ServizoExemplares(db);
        return _servizoExemplares;
    }

    public IEnums.Tipo obterTipo() { return IEnums.Tipo.EXEMPLAR; }

}
