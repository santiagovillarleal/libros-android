package es.villarleal.libros.modelo.servizos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import es.villarleal.libros.comun.IEnums;
import es.villarleal.libros.modelo.daos.DaoAutores;
import es.villarleal.libros.modelo.entidades.Autor;

import java.util.List;

/**
 * Created by santiago on 30/03/17.
 */

public class ServizoAutores extends ServizoXenerico {
    private static ServizoAutores _servizoAutores = null;
    private DaoAutores _daoAutores = null;

    private ServizoAutores(SQLiteDatabase db)
    {
        super(DaoAutores.crearInstancia(db));
        _daoAutores = (DaoAutores) _dao;
    }

    public static ServizoAutores crearInstancia(SQLiteDatabase db)
    {
        if (_servizoAutores == null )_servizoAutores = new ServizoAutores(db);
        return _servizoAutores;
    }

    public IEnums.Tipo obterTipo() { return IEnums.Tipo.AUTOR; }
    public Cursor obterAutores(List<Long> listaIdAutores) { return _daoAutores.obterAutores(listaIdAutores); }
    public List<Autor> buscarAutores()
    {
        return _daoAutores.buscarAutores();
    }

    public Cursor obterItemsSelec(boolean soloFilaSelec, List<Long> listaIdAutoresSelec)
    {
        return _daoAutores.obterItemsSelec(soloFilaSelec, listaIdAutoresSelec);
    }

}
