package es.villarleal.libros.modelo.entidades;

/**
 * Created by santiago on 4/04/17.
 */

public class Exemplar extends Entidade
{
    private String codExemplar;
    private String edicion;

    //Chaves foráneas
    private long idLibro;
    private long idIdioma;
    private long idEditorial;

    public String getCodExemplar() {
        return codExemplar;
    }

    public void setCodExemplar(String codExemplar) {
        this.codExemplar = codExemplar;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(long idLibro) {
        this.idLibro = idLibro;
    }

    public long getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(long idIdioma) {
        this.idIdioma = idIdioma;
    }

    public long getIdEditorial() {
        return idEditorial;
    }

    public void setIdEditorial(long idEditorial) {
        this.idEditorial = idEditorial;
    }

    public int compare(Entidade e1, Entidade e2)
    {
        Exemplar ex1 = (Exemplar)e1;
        Exemplar ex2 = (Exemplar)e2;
        String ex1Str = ex1.getCodExemplar();
        String ex2Str = ex2.getCodExemplar();

        return getCollator().compare(ex1Str.toLowerCase(), ex2Str.toLowerCase());
    }

}
