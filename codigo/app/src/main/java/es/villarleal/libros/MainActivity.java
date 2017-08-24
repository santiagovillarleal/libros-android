package es.villarleal.libros;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.libros.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.villarleal.libros.comun.Constantes;
import es.villarleal.libros.comun.IEnums;
import es.villarleal.libros.comun.Mensaxes;
import es.villarleal.libros.comun.Utilidades;
import es.villarleal.libros.modelo.Modelo;
import es.villarleal.libros.modelo.entidades.Entidade;
import es.villarleal.libros.modelo.entidades.Libro;
import es.villarleal.libros.modelo.esquemas.IEsquemaAutor;
import es.villarleal.libros.modelo.esquemas.IEsquemaEditorial;
import es.villarleal.libros.modelo.esquemas.IEsquemaExemplar;
import es.villarleal.libros.modelo.esquemas.IEsquemaIdioma;
import es.villarleal.libros.modelo.esquemas.IEsquemaLibro;
import es.villarleal.libros.modelo.servizos.ServizoXenerico;
import es.villarleal.libros.vista.alta.ActXestionEntidades;

public class MainActivity extends FragmentActivity
{
    TabLayout tabs = null;
    MyAdapter mAdapter = null;
    ViewPager viewPager = null;
    Toolbar toolbar = null;
    FloatingActionButton fabAltaElemento = null;

    private IEnums.Tipo _tipo;
    public Modelo modelo = null;
    private Constantes constantes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (TabLayout)findViewById(R.id.tabs);
        _tipo = IEnums.Tipo.values()[0];
        fabAltaElemento = (FloatingActionButton)findViewById(R.id.fabAltaElemento);
        fabAltaElemento.setImageResource(R.mipmap.plus_circle_outline);

        /*Obtemos os tamaños dos varchar e das textview*/
        constantes = Constantes.crearInstancia(this);
        /*Abrimos a BD e inicializamos os servizos e DAOS*/
        modelo = Modelo.crearInstancia(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Libros");
        toolbar.inflateMenu(R.menu.menu_items_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.action_facer_copia:
                {
                    Utilidades.facerCopia(getBaseContext());
                    break;
                }
                case R.id.action_exportar_xml:
                {
                    Utilidades.exportarXml(getBaseContext());
                    break;
                }
                case R.id.action_exportar_json:
                {
                    Utilidades.exportarJson(getBaseContext());
                    break;
                }
            }
            return false;
            }
        });


        mAdapter = new MyAdapter(getSupportFragmentManager());

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position)
            {
                String nomePestSelec = (String)tabs.getTabAt(tabs.getSelectedTabPosition()).getText();
                _tipo = Utilidades.obterTipoPorNomePest(nomePestSelec);
                //IEnums.Tipo tipo = IEnums.Tipo.values()[];
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        viewPager.setOffscreenPageLimit(1); //Isto é pra que cargue de novo os fragments cada vez que os seleccionemos.
        tabs.setupWithViewPager(viewPager); //Facendo isto creamos unha pestaña nova por cada páxina.
        cargarPestanhas();
    }

    public void cargarPestanhas()
    {
        for (int i = 0; i<tabs.getTabCount(); i++)
        {
            tabs.getTabAt(i).setText(IEnums.Tipo.values()[i].getNomePest());
        }
    }

    public void altaElemento(View view)
    {
        Class claseAlta = ActXestionEntidades.obterActivity(_tipo);
        if (claseAlta == null) return;
        Intent intent = new Intent(this, claseAlta);
        startActivity(intent);
    }

    public static class MyAdapter extends FragmentPagerAdapter
    {
        public MyAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public int getCount()
        {
            return Constantes.NUM_PEST;
        }

        @Override
        public Fragment getItem(int position) {
            return ArrayListFragment.newInstance(position);
        }
    }


    public static class ArrayListFragment extends ListFragment
    {
        IEnums.Tipo _tipo;

        static ArrayListFragment newInstance(int posTipo)
        {
            ArrayListFragment f = new ArrayListFragment();

            Bundle args = new Bundle();
            args.putInt("posTipo", posTipo);
            f.setArguments(args);

            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            int posTipo = 0;
            super.onCreate(savedInstanceState);
            posTipo = getArguments() != null ? getArguments().getInt("posTipo") : 0;
            _tipo = IEnums.Tipo.values()[posTipo];
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState)
        {
            String[] colunasAdapter = null;
            int[] toColunas = null;
            final ServizoXenerico srv = ServizoXenerico.obterServizo(_tipo);
            Cursor cursor = srv.obterItems();

            super.onActivityCreated(savedInstanceState);

            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id)
                {
                    final DialogInterface.OnClickListener dialogInterfaceOnYesListener = new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which) {
                            Entidade entidade = srv.buscarEntidade(id);
                            if (entidade != null)
                            {
                                srv.borrarEntidade(entidade);
                                Mensaxes.mostrarMensaxeExito(getActivity(), R.string.msgBorradoElementoExito);
                            }
                            else
                            {
                                Mensaxes.mostrarMensaxeErro(getActivity(), R.string.msgBorradoElementoNonExiste);
                            }
                        }
                    };

                    new AlertDialog.Builder(parent.getContext())
                            .setTitle("Borrar elemento")
                            .setMessage("Estás seguro?")
                            .setPositiveButton(android.R.string.yes, dialogInterfaceOnYesListener)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return true;
                }
            });

/*
            List<Libro> lista = new ArrayList<Libro>();
            Comparator<Entidade> c = new Libro();
            Collections.sort(lista, c);
*/

            switch (_tipo)
            {
                case IDIOMA:
                {
                    colunasAdapter = new String[] {IEsquemaIdioma.COL_NOME};
                    toColunas = new int[]{R.id.txtSimpleItem1};
                    break;
                }
                case EDITORIAL:
                {
                    colunasAdapter = new String[] {IEsquemaEditorial.COL_NOME};
                    toColunas = new int[]{R.id.txtSimpleItem1};
                    break;
                }
                case AUTOR:
                {
                    colunasAdapter = new String[] {IEsquemaAutor.COL_NOME_COMPLETO};
                    toColunas = new int[]{R.id.txtSimpleItem1};
                    break;
                }
                case LIBRO:
                {
                    colunasAdapter = new String[] {IEsquemaLibro.COL_TITULO};
                    toColunas = new int[]{R.id.txtSimpleItem1};
                    break;
                }
                case EXEMPLAR:
                {
                    colunasAdapter = new String[] {IEsquemaExemplar.COL_COD_EXEMPLAR, IEsquemaLibro.COL_TITULO,
                            IEsquemaEditorial.COL_NOME, IEsquemaIdioma.COL_NOME};
                    toColunas = new int[]{R.id.txtSimpleItem1, R.id.txtSimpleItem2, R.id.txtSimpleItem3, R.id.txtSimpleItem4};
                    break;
                }
                default:
                {
                    return;
                }
            }

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                    R.layout.simple_list_item, cursor, colunasAdapter, toColunas, 0);
            setListAdapter(adapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id)
        {
            Class claseEdicionElemento = ActXestionEntidades.obterActivity(_tipo);
            if (claseEdicionElemento == null) return;

            Intent intent = new Intent(l.getContext(), claseEdicionElemento);
            intent.putExtra(Constantes.CTE_INTENT_EXTRA_ID, id);
            startActivity(intent);
        }

    }
}

