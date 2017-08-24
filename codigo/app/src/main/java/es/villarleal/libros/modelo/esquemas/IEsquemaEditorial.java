package es.villarleal.libros.modelo.esquemas;

import es.villarleal.libros.comun.Constantes;

/**
 * Created by santiago on 4/04/17.
 */

public interface IEsquemaEditorial
{
    String NOME_PEST = "EDITORIAIS";
    String NOME_ENTIDADE = "editorial";
    String TABOA = "editoriais";
    String COL_ID = "id_editorial";
    String COL_NOME = "nome_editorial";

    String COL_CUALIF_ID = TABOA + Constantes.CTE_PUNTO + COL_ID;
    String COL_CUALIF_NOME = TABOA + Constantes.CTE_PUNTO + COL_NOME;

    String TABOA_CREATE = "CREATE TABLE IF NOT EXISTS "
        + TABOA
        + " ("
        + COL_ID + " INTEGER PRIMARY KEY, "
        + COL_NOME + " VARCHAR(" + Constantes.obterInstancia().getTamColNome() + ") NOT NULL"
        + " )";

    String [] COLS_EDITORIAL = new String[] {COL_ID, COL_NOME};

}
