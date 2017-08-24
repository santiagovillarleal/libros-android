package es.villarleal.libros.vista.alta;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.libros.R;
import es.villarleal.libros.comun.Constantes;
import es.villarleal.libros.comun.Mensaxes;
import es.villarleal.libros.modelo.Modelo;
import es.villarleal.libros.modelo.entidades.Autor;
import es.villarleal.libros.modelo.entidades.Entidade;

public class AltaAutorActivity extends ActXestionEntidades {

    EditText edtNomeAutor = null;
    EditText edtApelidosAutor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected void cargarOnCreate()
    {
        setContentView(R.layout.activity_alta_autor);
        srv = Modelo.servizoAutores;

        edtNomeAutor = (EditText) findViewById(R.id.edtNomeAutor);
        edtApelidosAutor = (EditText) findViewById(R.id.edtApelidosAutor);
    }

    protected void limpar()
    {
        edtNomeAutor.setText(Constantes.CTE_BALEIRO);
        edtApelidosAutor.setText(Constantes.CTE_BALEIRO);
    }

    public void gardarAutor(View view)
    {
        String nomeAutor = edtNomeAutor.getText().toString();
        String apelidosAutor = edtApelidosAutor.getText().toString();

        Autor autor = new Autor();
        autor.setNome(nomeAutor);
        autor.setApelidos(apelidosAutor);

        if (srv.existeEntidade(autor))
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgAltaAutorErroXaExiste);
            return;
        }
        if (eModoEdicion) editarAutor(autor);
        else grabarAutor(autor);
    }

    private void editarAutor(Autor autor)
    {
        autor.setId(idEdicion);
        if (srv.editarEntidade(autor))
        {
            Mensaxes.mostrarMensaxeExito(this, R.string.msgEdicionAutorExito);
            finish();
        }
        else
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgEdicionAutorErroXenerico);
        }
    }

    private void grabarAutor(Autor autor)
    {
        autor.setId(Modelo.servizoAutores.obterIdSeguinte());
        if (srv.grabarEntidade(autor))
        {
            Mensaxes.mostrarMensaxeExito(this, R.string.msgAltaAutorExito);
            finish();
        }
        else
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgAltaAutorErroXenerico);
        }
    }

    protected void preencher(Entidade entidade)
    {
        Autor autor = (Autor)entidade;

        edtNomeAutor.setText(autor.getNome());
        edtApelidosAutor.setText(autor.getApelidos());

    }
}
