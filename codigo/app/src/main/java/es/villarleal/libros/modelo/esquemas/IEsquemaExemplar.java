package es.villarleal.libros.modelo.esquemas;

import es.villarleal.libros.comun.Constantes;

/**
 * Created by santiago on 4/04/17.
 */

public interface IEsquemaExemplar
{
    String NOME_PEST = "EXEMPLARES";
    String NOME_ENTIDADE = "exemplar";
    String TABOA = "exemplares";

    String COL_ID = "id_exemplar";
    String COL_COD_EXEMPLAR = "cod_exemplar";
    String COL_EDICION = "edicion";

    String COL_ID_LIBRO = "id_libro";
    String COL_ID_EDITORIAL = "id_editorial";
    String COL_ID_IDIOMA = "id_idioma";

    String COL_CUALIF_ID = TABOA + Constantes.CTE_PUNTO + COL_ID;
    String COL_CUALIF_COD_EXEMPLAR = TABOA + Constantes.CTE_PUNTO + COL_COD_EXEMPLAR;
    String COL_CUALIF_EDICION = TABOA + Constantes.CTE_PUNTO + COL_EDICION;
    String COL_CUALIF_ID_LIBRO = TABOA + Constantes.CTE_PUNTO + COL_ID_LIBRO;
    String COL_CUALIF_ID_EDITORIAL = TABOA + Constantes.CTE_PUNTO + COL_ID_EDITORIAL;
    String COL_CUALIF_ID_IDIOMA = TABOA + Constantes.CTE_PUNTO + COL_ID_IDIOMA;

    String TABOA_CREATE = "CREATE TABLE IF NOT EXISTS "
        + TABOA
        + " ( "
        + COL_ID + " INTEGER PRIMARY KEY, "
        + COL_COD_EXEMPLAR + " VARCHAR(" + Constantes.obterInstancia().getTamColCodExemplar() + "), "
        + COL_EDICION + " VARCHAR(" + Constantes.obterInstancia().getTamColEdicion() + "), "
        + COL_ID_LIBRO + " INTEGER NOT NULL, "
        + COL_ID_IDIOMA + " INTEGER NOT NULL, "
        + COL_ID_EDITORIAL + " INTEGER NOT NULL, "
        + " FOREIGN KEY (" + COL_ID_LIBRO + ") " +
            " REFERENCES " + IEsquemaLibro.TABOA + "(" + IEsquemaLibro.COL_ID + "),"
        + " FOREIGN KEY (" + COL_ID_EDITORIAL + ") " +
            " REFERENCES " + IEsquemaEditorial.TABOA + "(" + IEsquemaEditorial.COL_ID + "), "
        + " FOREIGN KEY (" + COL_ID_IDIOMA + ") " +
            " REFERENCES " + IEsquemaIdioma.TABOA + " ("+ IEsquemaIdioma.COL_ID + ")"
        + ")";

    String[] COLS_EXEMPLAR = new String[] {
        COL_ID, COL_COD_EXEMPLAR, COL_EDICION, COL_ID_LIBRO, COL_ID_EDITORIAL, COL_ID_IDIOMA};
}
