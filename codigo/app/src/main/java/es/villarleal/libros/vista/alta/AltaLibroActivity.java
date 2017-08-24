package es.villarleal.libros.vista.alta;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.libros.R;
import es.villarleal.libros.comun.Constantes;
import es.villarleal.libros.comun.Mensaxes;
import es.villarleal.libros.comun.Utilidades;
import es.villarleal.libros.modelo.Modelo;
import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.entidades.Libro;
import es.villarleal.libros.modelo.esquemas.IEsquemaAutor;

import java.util.ArrayList;
import java.util.List;

public class AltaLibroActivity extends ActXestionEntidades
{
    EditText edtTitulo = null;
    ListView lvAutores = null;
    Libro libro = null;
    List<Long> listaIdAutoresSeleccionados = new ArrayList<Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (!eModoEdicion) atualizarListViewAutoresBaleira();
    }

    protected void cargarOnCreate()
    {
        setContentView(R.layout.activity_alta_libro);
        srv = Modelo.servizoLibros;
        edtTitulo = (EditText) findViewById(R.id.edtTitulo);
        lvAutores = (ListView) findViewById(R.id.lvAutores);

        lvAutores.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    asociarAutores();
                }
            }
        );

    }

    private void asociarAutores()
    {
        final Cursor cursor = Modelo.servizoAutores.obterItemsSelec(false, listaIdAutoresSeleccionados);
        final List<Long> listaIdAutoresSeleccProvisional = new ArrayList<Long>(listaIdAutoresSeleccionados);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Asociar autores")
            .setMultiChoiceItems(cursor, "selec", IEsquemaAutor.COL_NOME_COMPLETO, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    int indexSelec;
                    int indexId;
                    long idAutorSelec;

                    indexId = cursor.getColumnIndex("_id");
                    idAutorSelec = cursor.getLong(indexId);

                    if (isChecked)
                    {
                        listaIdAutoresSeleccProvisional.add(idAutorSelec);
                    }
                    else
                    {
                        listaIdAutoresSeleccProvisional.remove(idAutorSelec);
                    }
                }
            }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listaIdAutoresSeleccionados = new ArrayList<Long>(listaIdAutoresSeleccProvisional);
                    atualizarListViewAutores();
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }
        ).show();
    }

    private void atualizarListViewAutores()
    {
        Cursor cursorAutores = Modelo.servizoAutores.obterItemsSelec(true, listaIdAutoresSeleccionados);
        if (cursorAutores == null)
        {
            atualizarListViewAutoresBaleira();
        }
        else
        {
            ListAdapter adapterLv = new SimpleCursorAdapter(
                    this, R.layout.simple_list_item, cursorAutores, new String[] {IEsquemaAutor.COL_NOME_COMPLETO}, new int[]{R.id.txtSimpleItem1}, 0);
            lvAutores.setAdapter(adapterLv);
        }
    }
    private void atualizarListViewAutoresBaleira()
    {
        ListAdapter adapterLv = new ArrayAdapter(this, R.layout.simple_list_item, R.id.txtSimpleItem1, new String[] {"Clica para engadir autores."});
        lvAutores.setAdapter(adapterLv);

    }
    protected void preencher(Entidade entidade)
    {
        ListAdapter adapterLv = null;
        libro = (Libro)entidade;
        listaIdAutoresSeleccionados = libro.getAutores();

        edtTitulo.setText(libro.getTitulo());

        List<Long> idAutores = libro.getAutores();
        if (Utilidades.eListaBaleira(idAutores))
        {
            atualizarListViewAutoresBaleira();
        }
        else
        {
            Cursor cursorAutores = Modelo.servizoLibros.obterAutores(libro.getId());
            adapterLv = new SimpleCursorAdapter(
                    this, R.layout.simple_list_item, cursorAutores, new String[] {IEsquemaAutor.COL_NOME_COMPLETO}, new int[]{R.id.txtSimpleItem1}, 0);
            lvAutores.setAdapter(adapterLv);
        }
    }

    protected void limpar()
    {
        edtTitulo.setText(Constantes.CTE_BALEIRO);
    }

    public void gardarLibro(View view)
    {
        String titulo = edtTitulo.getText().toString();
        //List<Long> listaIdAutores = obterListaIdAutores(spnAutor);

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutores(listaIdAutoresSeleccionados);

        if (srv.existeEntidade(libro))
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgAltaLibroErroXaExiste);
            return;
        }

        if (eModoEdicion) editarLibro(libro);
        else grabarLibro(libro);
    }

    private void editarLibro(Libro libro)
    {
        libro.setId(idEdicion);
        if (srv.editarEntidade(libro))
        {
            Mensaxes.mostrarMensaxeExito(this, R.string.msgEdicionLibroExito);
            finish();
        }
        else
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgEdicionLibroErrorXenerico);
        }
    }

    private void grabarLibro(Libro libro)
    {
        libro.setId(srv.obterIdSeguinte());
        if (srv.grabarEntidade(libro))
        {
            Mensaxes.mostrarMensaxeExito(this, R.string.msgAltaLibroExito);
            finish();
        }
        else
        {
            Mensaxes.mostrarMensaxeErro(this, R.string.msgAltaLibroErrorXenerico);
        }
    }

    public void altaAutor(View view)
    {
        Intent intent = new Intent(this, AltaAutorActivity.class);
        startActivity(intent);
    }
}
