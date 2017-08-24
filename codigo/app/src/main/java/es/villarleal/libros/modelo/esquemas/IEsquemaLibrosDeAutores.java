package es.villarleal.libros.modelo.esquemas;

import es.villarleal.libros.comun.Constantes;

/**
 * Created by santiago on 4/04/17.
 */

public interface IEsquemaLibrosDeAutores
{
    String NOME_ENTIDADE = "librosdeautores";
    String TABOA = "libros_de_autores";
    String COL_ID_AUTOR = "id_autor";
    String COL_ID_LIBRO = "id_libro";

    String COL_CUALIF_ID_AUTOR = TABOA + Constantes.CTE_PUNTO + COL_ID_AUTOR;
    String COL_CUALIF_ID_LIBRO = TABOA + Constantes.CTE_PUNTO + COL_ID_LIBRO;

    String TABOA_CREATE = "CREATE TABLE IF NOT EXISTS "
        + TABOA
        + " ( "
        + COL_ID_AUTOR + " INTEGER NOT NULL, "
        + COL_ID_LIBRO + " INTEGER NOT NULL, "
        + " FOREIGN KEY (" + COL_ID_AUTOR + ") " +
            " REFERENCES " + IEsquemaAutor.TABOA + "("+ IEsquemaAutor.COL_ID +"), "
        + " FOREIGN KEY (" + COL_ID_LIBRO + ") " +
            " REFERENCES " + IEsquemaLibro.TABOA + "(" + IEsquemaLibro.COL_ID + "), "
        + " PRIMARY KEY ( " + COL_ID_AUTOR + ", " + COL_ID_LIBRO + " )"
        + " )";

    String[] COLS_LDA = new String[] {COL_ID_AUTOR, COL_ID_LIBRO};
}
