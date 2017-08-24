package es.villarleal.libros.modelo.entidades;

import java.text.Collator;

import es.villarleal.libros.comun.Constantes;

/**
 * Created by santiago on 4/04/17.
 */

public class Autor extends Entidade
{
    private String nome;
    private String apelidos;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelidos() {
        return apelidos;
    }

    public void setApelidos(String apelidos) {
        this.apelidos = apelidos;
    }

    public String getNomeCompleto()
    {
        return nome + Constantes.CTE_ESPAZO + apelidos;
    }

    public int compare(Entidade o1, Entidade o2)
    {
        Autor a1 = (Autor)o1;
        Autor a2 = (Autor)o2;
        String a1Str = a1.getNomeCompleto();
        String a2Str = a2.getNomeCompleto();

        return getCollator().compare(a1Str.toLowerCase(), a2Str.toLowerCase());

    }

}
