package es.villarleal.libros.modelo.entidades;

/**
 * Created by santiago on 4/04/17.
 */

public class Editorial extends Entidade {
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int compare(Entidade e1, Entidade e2)
    {
        Editorial ed1 = (Editorial)e1;
        Editorial ed2 = (Editorial)e2;
        String ed1Str = ed1.getNome();
        String ed2Str = ed2.getNome();

        return getCollator().compare(ed1Str.toLowerCase(), ed2Str.toLowerCase());
    }
}
