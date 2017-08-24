package es.villarleal.libros.modelo.esquemas;

import es.villarleal.libros.comun.Constantes;

/**
 * Created by santiago on 4/04/17.
 */

public interface IEsquemaLibro
{
    String NOME_PEST = "LIBROS";
    String NOME_ENTIDADE = "libro";
    String TABOA = "libros";
    String COL_ID = "id_libro";
    String COL_TITULO = "titulo";

    String COL_CUALIF_ID = TABOA + Constantes.CTE_PUNTO + COL_ID;
    String COL_CUALIF_TITULO = TABOA + Constantes.CTE_PUNTO + COL_TITULO;

    String TABOA_CREATE = "CREATE TABLE IF NOT EXISTS "
        + TABOA
        + " ("
        + COL_ID + " INTEGER PRIMARY KEY, "
        + COL_TITULO + " VARCHAR(" + Constantes.obterInstancia().getTamColTitulo() + ") NOT NULL"
        + " )";

    String[] COLS_LIBRO = new String[] {COL_ID, COL_TITULO};
}
