package es.villarleal.libros.comun;

import es.villarleal.libros.modelo.esquemas.IEsquemaAutor;
import es.villarleal.libros.modelo.esquemas.IEsquemaEditorial;
import es.villarleal.libros.modelo.esquemas.IEsquemaExemplar;
import es.villarleal.libros.modelo.esquemas.IEsquemaIdioma;
import es.villarleal.libros.modelo.esquemas.IEsquemaLibro;

/**
 * Created by santiago on 12/04/17.
 */

public interface IEnums
{
    enum Tipo
    {
        EXEMPLAR (IEsquemaExemplar.TABOA, IEsquemaExemplar.NOME_ENTIDADE, IEsquemaExemplar.NOME_PEST),
        LIBRO (IEsquemaLibro.TABOA, IEsquemaLibro.NOME_ENTIDADE, IEsquemaLibro.NOME_PEST),
        AUTOR (IEsquemaAutor.TABOA, IEsquemaAutor.NOME_ENTIDADE, IEsquemaAutor.NOME_PEST),
        EDITORIAL (IEsquemaEditorial.TABOA, IEsquemaEditorial.NOME_ENTIDADE, IEsquemaEditorial.NOME_PEST),
        IDIOMA (IEsquemaIdioma.TABOA, IEsquemaIdioma.NOME_ENTIDADE, IEsquemaIdioma.NOME_PEST);

        private final String _descripcion;
        private final String _descSingular;
        private final String _nomePest;

        Tipo(String descripcion, String descSingular, String nomePest)
        {

            _descripcion = descripcion;
            _descSingular = descSingular;
            _nomePest = nomePest;
        }

        public String getDescripcion() {return _descripcion; }
        public String getDescSingular() { return _descSingular; }
        public String getNomePest() { return _nomePest; }

    };
}
