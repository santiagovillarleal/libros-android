package es.villarleal.libros.vista.alta;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.libros.R;
import es.villarleal.libros.comun.Constantes;
import es.villarleal.libros.comun.Mensaxes;
import es.villarleal.libros.modelo.Modelo;
import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.entidades.Idioma;

public class AltaIdiomaActivity extends ActXestionEntidades {
    EditText edtNomeIdioma = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void cargarOnCreate()
    {
        setContentView(R.layout.activity_alta_idioma);
        srv = Modelo.servizoIdiomas;
        edtNomeIdioma = (EditText)findViewById(R.id.edtNomeIdioma);
    }

    protected void preencher(Entidade entidade)
    {
        Idioma idioma = (Idioma)entidade;
        edtNomeIdioma.setText(idioma.getNome());
    }

    protected void limpar()
    {
        edtNomeIdioma.setText(Constantes.CTE_BALEIRO);
    }

    public void gardarIdioma(View view)
    {
        //Collemos o nome
        String nomeIdioma = edtNomeIdioma.getText().toString();

        Idioma idioma = new Idioma();
        idioma.setNome(nomeIdioma);

        if (srv.existeEntidade(idioma))
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgAltaIdiomaErroXaExiste);
            return;
        }

        if (eModoEdicion) editarIdioma(idioma);
        else grabarIdioma(idioma);
    }

    private void editarIdioma(Idioma idioma)
    {
        idioma.setId(idEdicion);
        if (srv.editarEntidade(idioma))
        {
            Mensaxes.mostrarMensaxeExito(this, R.string.msgEdicionIdiomaExito);
            finish();
        }
        else
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgEdicionIdiomaErroXenerico);
        }
    }

    private void grabarIdioma(Idioma idioma)
    {
        idioma.setId(srv.obterIdSeguinte());
        if (srv.grabarEntidade(idioma))
        {
            Mensaxes.mostrarMensaxeExito(this, R.string.msgAltaIdiomaExito);
            finish();
        }
        else
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgAltaIdiomaErroXenerico);
        }
    }

}
