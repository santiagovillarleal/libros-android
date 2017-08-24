package es.villarleal.libros.vista.alta;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.libros.R;
import es.villarleal.libros.comun.Constantes;
import es.villarleal.libros.comun.Mensaxes;
import es.villarleal.libros.comun.Utilidades;
import es.villarleal.libros.modelo.Modelo;
import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.entidades.Exemplar;
import es.villarleal.libros.modelo.esquemas.IEsquemaEditorial;
import es.villarleal.libros.modelo.esquemas.IEsquemaIdioma;
import es.villarleal.libros.modelo.esquemas.IEsquemaLibro;

public class AltaExemplarActivity extends ActXestionEntidades {

    Spinner spnLibros = null;
    EditText edtCodExemplar = null;
    Spinner spnEditoriais = null;
    EditText edtEdicion = null;
    Spinner spnIdioma = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    protected void cargarOnCreate()
    {
        setContentView(R.layout.activity_alta_exemplar);
        srv = Modelo.servizoExemplares;
        spnLibros = (Spinner) findViewById(R.id.spnLibros);
        edtCodExemplar = (EditText) findViewById(R.id.edtCodExemplar);
        spnEditoriais = (Spinner) findViewById(R.id.spnEditorial);
        edtEdicion = (EditText) findViewById(R.id.edtEdicion);
        spnIdioma = (Spinner) findViewById(R.id.spnIdioma);

        cargarCombos();
        //Informamos textbox Código Libro (Sinatura).
        long codExemplar = Modelo.servizoExemplares.obterNumEntidades() + 1;
        String codExemplarStr = (new Long(codExemplar)).toString();
        String codLibro = "BSVL-" + "0000".substring(codExemplarStr.length()) + codExemplarStr;
        edtCodExemplar.setText(codLibro);
    }

    protected void preencher(Entidade entidade)
    {
        Exemplar exemplar = (Exemplar)entidade;
        Long idLibro = exemplar.getIdLibro();
        Long idEditorial = exemplar.getIdEditorial();
        Long idIdioma = exemplar.getIdIdioma();

        Utilidades.posicionarCombo(spnLibros, idLibro);
        edtCodExemplar.setText(exemplar.getCodExemplar());
        Utilidades.posicionarCombo(spnEditoriais, idEditorial);
        edtEdicion.setText(exemplar.getEdicion());
        Utilidades.posicionarCombo(spnIdioma, idIdioma);
    }

    protected void limpar()
    {
        spnLibros.setSelection(Constantes.CTE_INT_MENOS_UN);
        edtCodExemplar.setText(Constantes.CTE_BALEIRO);
        spnEditoriais.setSelection(Constantes.CTE_INT_MENOS_UN);
        edtEdicion.setText(Constantes.CTE_BALEIRO);
        spnIdioma.setSelection(Constantes.CTE_INT_MENOS_UN);
    }

    public void gardarExemplar(View view)
    {
        Long idLibro = spnLibros.getSelectedItemId();
        String codExemplar = edtCodExemplar.getText().toString();
        Long idEditorial = spnEditoriais.getSelectedItemId();
        String edicion = edtEdicion.getText().toString();
        Long idIdioma = spnIdioma.getSelectedItemId();

        Exemplar exemplar = new Exemplar();
        exemplar.setIdLibro(idLibro);
        exemplar.setCodExemplar(codExemplar);
        exemplar.setIdEditorial(idEditorial);
        exemplar.setEdicion(edicion);
        exemplar.setIdIdioma(idIdioma);

        if (srv.existeEntidade(exemplar))
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgAltaExemplarErroXaExiste);
            return;
        }

        if (eModoEdicion) editarExemplar(exemplar);
        else grabarExemplar(exemplar);
    }

    private void editarExemplar(Exemplar exemplar)
    {
        exemplar.setId(idEdicion);
        if (srv.editarEntidade(exemplar))
        {
            Mensaxes.mostrarMensaxeExito(this, R.string.msgEdicionExemplarExito);
            finish();
        }
        else
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgEdicionExemplarErroXenerico);
        }
    }

    private void grabarExemplar(Exemplar exemplar)
    {
        exemplar.setId(Modelo.servizoExemplares.obterIdSeguinte());
        if (srv.grabarEntidade(exemplar))
        {
            Mensaxes.mostrarMensaxeExito(this, R.string.msgAltaExemplarExito);
            finish();
        }
        else
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgAltaExemplarErroXenerico);
        }
    }

    private void cargarCombos()
    {
        String[] colunasAdapterLibros = new String[] {IEsquemaLibro.COL_TITULO};
        String[] colunasAdapterEditoriais = new String[] {IEsquemaEditorial.COL_NOME};
        String[] colunasAdapterIdiomas = new String[] {IEsquemaIdioma.COL_NOME};

        Utilidades.cargarCombo(this, Modelo.servizoLibros, spnLibros, colunasAdapterLibros);
        long idLibroMaisReciente = Modelo.servizoLibros.obterNumEntidades();
        Utilidades.posicionarCombo(spnLibros, idLibroMaisReciente);
        Utilidades.cargarCombo(this, Modelo.servizoEditoriais, spnEditoriais, colunasAdapterEditoriais);
        Utilidades.cargarCombo(this, Modelo.servizoIdiomas, spnIdioma, colunasAdapterIdiomas);
    }

    public void altaElemento(View view)
    {
        Intent intent = null;
        Class claseAltaElemento = null;
        if (view.getId() == R.id.imgBtnAltaIdioma) claseAltaElemento = AltaIdiomaActivity.class;
        else if (view.getId() == R.id.imgBtnAltaEditorial) claseAltaElemento = AltaEditorialActivity.class;
        else if (view.getId() == R.id.imgBtnAltaLibro) claseAltaElemento = AltaLibroActivity.class;

        if (claseAltaElemento == null) return;
        intent = new Intent(this, claseAltaElemento);

        startActivity(intent);
    }

}
