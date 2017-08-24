package es.villarleal.libros.modelo.servizos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import es.villarleal.libros.comun.IEnums;
import es.villarleal.libros.comun.Utilidades;
import es.villarleal.libros.modelo.Modelo;
import es.villarleal.libros.modelo.daos.DaoLibros;
import es.villarleal.libros.modelo.daos.DaoLda;
import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.entidades.Libro;

import java.util.List;

/**
 * Created by santiago on 30/03/17.
 */

public class ServizoLibros extends ServizoXenerico
{
    private static ServizoLibros _servizoLibros = null;
    private DaoLibros _daoLibros = null;
    private DaoLda _daoLda = null;

    private ServizoLibros(SQLiteDatabase db)
    {
        super(DaoLibros.crearInstancia(db));
        _daoLibros = (DaoLibros) _dao;
        _daoLda = DaoLda.crearInstancia(db);
    }

    public static ServizoLibros crearInstancia(SQLiteDatabase db)
    {
        if (_servizoLibros == null) _servizoLibros = new ServizoLibros(db);
        return _servizoLibros;
    }

    @Override
    public boolean grabarEntidade(Entidade entidade)
    {
        if (super.grabarEntidade(entidade))
        {
            Libro libro = (Libro)entidade;
            return _daoLda.grabarLda(libro);
        }
        return false;
    }

    @Override
    public Entidade buscarEntidade(long id)
    {
        Entidade entidade = super.buscarEntidade(id);
        Libro libro = (Libro)entidade;

        if (libro == null) return libro;

        List<Long> listaIdAutores = _daoLda.buscarAutoresPorLibro(id);
        if (Utilidades.eListaBaleira(listaIdAutores)) return libro;

        for (Long idAutor : listaIdAutores)
        {
            libro.asociarAutor(idAutor);
        }
        return libro;
    }

    @Override
    public boolean editarEntidade(Entidade entidade)
    {
        boolean res = super.editarEntidade(entidade);
        if (!res) return false;
        Libro libro = (Libro)entidade;
        return _daoLda.eliminarAsocAutores(libro.getId()) && _daoLda.grabarLda(libro);
    }

    @Override
    public boolean borrarEntidade(Entidade entidade)
    {
        boolean res = super.borrarEntidade(entidade);
        if (!res) return false;
        Libro libro = (Libro)entidade;
        return _daoLda.eliminarAsocAutores(libro.getId());
    }

    public IEnums.Tipo obterTipo() { return IEnums.Tipo.LIBRO; }

    public Cursor obterAutores(long idLibro)
    {
        Cursor cursor = null;
        List<Long> listIdAutores = _daoLda.buscarAutoresPorLibro(idLibro);
        if (Utilidades.eListaBaleira(listIdAutores)) return null;
        cursor = Modelo.servizoAutores.obterAutores(listIdAutores);

        return cursor;
    }

}
