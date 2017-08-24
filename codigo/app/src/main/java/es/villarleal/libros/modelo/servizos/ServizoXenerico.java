package es.villarleal.libros.modelo.servizos;

import android.database.Cursor;

import es.villarleal.libros.comun.IEnums;
import es.villarleal.libros.modelo.Modelo;
import es.villarleal.libros.modelo.daos.DaoXenerico;
import es.villarleal.libros.modelo.entidades.Entidade;

/**
 * Created by santiago on 12/04/17.
 */

public abstract class ServizoXenerico
{
    protected DaoXenerico _dao = null;

    protected ServizoXenerico(DaoXenerico dao)
    {
        _dao = dao;
    }

    public abstract IEnums.Tipo obterTipo();

    public static ServizoXenerico obterServizo(IEnums.Tipo tipo)
    {
        switch (tipo)
        {
            case EXEMPLAR: return Modelo.servizoExemplares;
            case LIBRO: return Modelo.servizoLibros;
            case AUTOR: return Modelo.servizoAutores;
            case EDITORIAL: return Modelo.servizoEditoriais;
            case IDIOMA: return Modelo.servizoIdiomas;
            default: return null;
        }
    }

    public boolean grabarEntidade(Entidade entidade) { return _dao.grabarEntidade(entidade); }
    public Entidade buscarEntidade(long id) { return _dao.buscarEntidade(id); }
    public boolean editarEntidade(Entidade entidade)
    {
        return _dao.editarEntidade(entidade);
    }
    public boolean borrarEntidade(Entidade entidade) { return _dao.borrarEntidade(entidade); }

    public boolean existeEntidade(Entidade entidade) { return _dao.existeEntidade(entidade); }

    public Cursor obterItems() { return _dao.obterItems(); };
    public long obterIdSeguinte() { return _dao.obterIdSeguinte(); }

    public long obterNumEntidades() { return _dao.obterNumEntidades(); }

}
