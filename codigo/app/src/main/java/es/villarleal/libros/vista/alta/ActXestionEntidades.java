package es.villarleal.libros.vista.alta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.libros.R;
import es.villarleal.libros.comun.Constantes;
import es.villarleal.libros.comun.IEnums;
import es.villarleal.libros.modelo.Modelo;
import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.servizos.ServizoXenerico;

/**
 * Created by santiago on 15/04/17.
 */

public abstract class ActXestionEntidades extends AppCompatActivity
{
    protected boolean eModoEdicion = false;
    protected long idEdicion = -1;
    protected ServizoXenerico srv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarOnCreate();
        ponherModoEdicion();
    }

    protected abstract void preencher(Entidade entidade);
    protected abstract void limpar();
    protected abstract void cargarOnCreate();

    public static Class obterActivity(IEnums.Tipo tipo)
    {
        switch (tipo)
        {
            case EXEMPLAR: return AltaExemplarActivity.class;
            case LIBRO: return AltaLibroActivity.class;
            case AUTOR: return AltaAutorActivity.class;
            case EDITORIAL: return AltaEditorialActivity.class;
            case IDIOMA: return AltaIdiomaActivity.class;
            default: return null;
        }
    }

    protected void ponherModoEdicion()
    {
        Intent intent = getIntent();
        if (intent != null)
        {
            idEdicion = intent.getLongExtra(Constantes.CTE_INTENT_EXTRA_ID, -1);
            if (idEdicion == -1) return;
            eModoEdicion = true;
            this.setTitle(getString(R.string.lblEdicion) + " " + srv.obterTipo().getDescSingular());
            Entidade entidade = srv.buscarEntidade(idEdicion);
            if (entidade == null) return;
            preencher(entidade);
        }
    }

}
