package es.villarleal.libros.modelo.entidades;

/**
 * Created by santiago on 4/04/17.
 */

public class Idioma extends Entidade
{
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int compare(Entidade e1, Entidade e2)
    {
        Idioma i1 = (Idioma)e1;
        Idioma i2 = (Idioma)e2;
        String i1Str = i1.getNome();
        String i2Str = i2.getNome();

        return getCollator().compare(i1Str.toLowerCase(), i2Str.toLowerCase());
    }
}
