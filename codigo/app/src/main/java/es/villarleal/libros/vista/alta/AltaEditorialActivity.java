package es.villarleal.libros.vista.alta;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.libros.R;
import es.villarleal.libros.comun.Constantes;
import es.villarleal.libros.comun.Mensaxes;
import es.villarleal.libros.modelo.Modelo;
import es.villarleal.libros.modelo.entidades.Editorial;
import es.villarleal.libros.modelo.entidades.Entidade;

public class AltaEditorialActivity extends ActXestionEntidades{
    EditText edtNomeEditorial = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void cargarOnCreate()
    {
        setContentView(R.layout.activity_alta_editorial);
        srv = Modelo.servizoEditoriais;
        edtNomeEditorial = (EditText) findViewById(R.id.edtNomeEditorial);
    }

    protected void limpar()
    {
        edtNomeEditorial.setText(Constantes.CTE_BALEIRO);
    }

    public void altaEditorial(View view)
    {
        String nomeEditorial = edtNomeEditorial.getText().toString();

        Editorial editorial = new Editorial();
        editorial.setNome(nomeEditorial);
        if (srv.existeEntidade(editorial))
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgAltaEditorialErroXaExiste);
            return;
        }
        if (eModoEdicion)
        {
            editarEditorial(editorial);
        }
        else
        {
            grabarEditorial(editorial);
        }
    }

    private void editarEditorial(Editorial editorial)
    {
        editorial.setId(idEdicion);
        if (srv.editarEntidade(editorial))
        {
            Mensaxes.mostrarMensaxeExito(this, R.string.msgEdicionEditorialExito);
            finish();
        }
        else
        {
            Mensaxes.mostrarMensaxeExito(this, R.string.msgEdicionEditorialErroXenerico);
        }
    }

    private void grabarEditorial(Editorial editorial)
    {
        editorial.setId(srv.obterIdSeguinte());
        if (srv.grabarEntidade(editorial))
        {
            Mensaxes.mostrarMensaxeExito(this, R.string.msgAltaEditorialExito);
            finish();
        }
        else
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgAltaEditorialErroXenerico);
        }
    }

    protected void preencher(Entidade entidade)
    {
        Editorial editorial = (Editorial)entidade;

        edtNomeEditorial.setText(editorial.getNome());
    }

}
