package es.villarleal.libros.modelo.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by santiago on 4/04/17.
 */

public class Libro extends Entidade
{
    private String titulo;

    private List<Long> autores = new ArrayList<Long>(); //Relación N-N

    public List<Long> getAutores() {
        return autores;
    }

    public void setAutores(List<Long> autores) {
        this.autores = autores;
    }

    public void asociarAutor(long idAutor)
    {
        if (autores.indexOf(idAutor) == -1) autores.add(idAutor);
    }

    public void desasociarAutor(long idAutor)
    {
        int index = autores.indexOf(idAutor);
        if (index != -1) autores.remove(index);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int compare(Entidade e1, Entidade e2)
    {
        Libro l1 = (Libro)e1;
        Libro l2 = (Libro)e2;
        String l1Str = l1.getTitulo();
        String l2Str = l2.getTitulo();

        return getCollator().compare(l1Str.toLowerCase(), l2Str.toLowerCase());
    }

}
