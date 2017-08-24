package es.villarleal.libros.modelo.servizos;

import android.database.sqlite.SQLiteDatabase;

import es.villarleal.libros.comun.IEnums;
import es.villarleal.libros.modelo.daos.DaoIdiomas;

/**
 * Created by santiago on 30/03/17.
 */

public class ServizoIdiomas extends ServizoXenerico
{
    private static ServizoIdiomas _servizoIdiomas = null;
    private DaoIdiomas _daoIdiomas = null;

    private ServizoIdiomas(SQLiteDatabase db)
    {
        super(DaoIdiomas.crearInstancia(db));
        _daoIdiomas = (DaoIdiomas) _dao;

    }

    public static ServizoIdiomas crearInstancia(SQLiteDatabase db)
    {
        if (_servizoIdiomas == null) _servizoIdiomas = new ServizoIdiomas(db);
        return _servizoIdiomas;
    }

    public IEnums.Tipo obterTipo() { return IEnums.Tipo.IDIOMA; }

}
