package es.villarleal.libros.modelo.esquemas;

import es.villarleal.libros.comun.Constantes;

/**
 * Created by santiago on 4/04/17.
 */

public interface IEsquemaAutor
{
    String NOME_PEST = "AUTORES";
    String NOME_ENTIDADE = "autor";
    String TABOA = "autores";
    String COL_ID = "id_autor";
    String COL_NOME = "nome_autor";
    String COL_APELIDOS = "apelidos";
    String COL_NOME_COMPLETO = "nome_completo"; //Transient

    String COL_CUALIF_ID = TABOA + Constantes.CTE_PUNTO + COL_ID;
    String COL_CUALIF_NOME = TABOA + Constantes.CTE_PUNTO + COL_NOME;
    String COL_CUALIF_APELIDOS = TABOA + Constantes.CTE_PUNTO + COL_APELIDOS;
    String COL_CUALIF_NOME_COMPLETO = COL_NOME + " || " + "' '"+ "||" + COL_APELIDOS + " AS " + COL_NOME_COMPLETO;

    String TABOA_CREATE = "CREATE TABLE IF NOT EXISTS "
        + TABOA
        + " ( "
        + COL_ID + " INTEGER PRIMARY KEY, "
        + COL_NOME + " VARCHAR(" + Constantes.obterInstancia().getTamColNome() + ") NOT NULL, "
        + COL_APELIDOS + " VARCHAR(" + Constantes.obterInstancia().getTamColApelidos() + ") NOT NULL"
        + " ) ";

    String[] COLS_AUTOR = new String[] {COL_ID, COL_NOME, COL_APELIDOS};
}
