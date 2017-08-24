package es.villarleal.libros.modelo.entidades;

/**
 * Created by santiago on 4/04/17.
 */

public class LibrosDeAutores extends Entidade
{
    private long _idAutor;
    private long _idLibro;

    public long getIdLibro() {
        return _idLibro;
    }

    public void setIdLibro(long idLibro) {
        this._idLibro = idLibro;
    }

    public long getIdAutor() {
        return _idAutor;
    }

    public void setIdAutor(long idAutor) {
        this._idAutor = idAutor;
    }

    public int compare(Entidade e1, Entidade e2)
    {
        return -1; //Non ten sentido.
    }

}
