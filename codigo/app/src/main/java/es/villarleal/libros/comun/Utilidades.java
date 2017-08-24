package es.villarleal.libros.comun;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.example.libros.R;

import es.villarleal.libros.modelo.servizos.ServizoXenerico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by santiago on 11/04/17.
 */

public class Utilidades
{
    public static boolean eListaBaleira(List<?> lista)
    {
        return ((lista == null) || (lista.size() == 0));
    }

    /**
     * @param carpetaFilla
     *
     * @return Se vai todo ben, a ruta absoluta
     */
    private static String crearCarpeta(Context ctx, String carpetaFilla)
    {
        String res = null;
        String dirCarpetaAbs = Environment.getExternalStorageDirectory()+ Constantes.CARPETA_PRINCIPAL + carpetaFilla;
        File fileDirCopiaDatabase = new File(dirCarpetaAbs);
        try
        {
            if (! fileDirCopiaDatabase.exists()) fileDirCopiaDatabase.mkdirs();
            res = dirCarpetaAbs;
        }
        catch (Exception e)
        {
            Mensaxes.mostrarMensaxeExito(ctx, "Problema ó crear a carpeta.");
        }
        return res;
    }

    public static void exportarXml(Context ctx)
    {
        Mensaxes.mostrarMensaxeExito(ctx, "pouquinho a pouco");
    }

    public static void exportarJson(Context ctx)
    {
        Mensaxes.mostrarMensaxeExito(ctx, "pouquinho a pouco");
    }

    public static void facerCopia(Context ctx)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String tmpstmp = sdf.format(calendar.getTime());

        final String inDatabase = "/data/data/es.villarleal.libros/databases/DBLibros";
        File dbFile = new File(inDatabase);
        try
        {
            FileInputStream fis = new FileInputStream(dbFile);

            String dirCopiaDatabase = crearCarpeta(ctx, "/copias_bd/");

            if (TextUtils.isEmpty(dirCopiaDatabase)) return;
            String outFileName = dirCopiaDatabase + "DBLibros_copia_" + tmpstmp + ".db";

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer))>0){
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();
        }
        catch (FileNotFoundException e)
        {
            Mensaxes.mostrarMensaxeExito(ctx, "erro ó copiar db, filanotfoundexception. ");
            return;
        }
        catch (IOException e)
        {
            Mensaxes.mostrarMensaxeExito(ctx, "erro ó copiar db, ioexception e");
            return;
        }

        Mensaxes.mostrarMensaxeExito(ctx, "a copia acabou corretamente.");
    }

    public static void cargarCombo(Activity act, ServizoXenerico srv, Spinner spn, String[] colunasAdapter)
    {
        Cursor cursorCombo = srv.obterItems();

        int[] colunasViews = new int[] {R.id.txt_spinner_1};
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(act,
                R.layout.simple_spinner_drowndown_item, cursorCombo, colunasAdapter,
                colunasViews, 0);
        cursorAdapter.setDropDownViewResource(R.layout.simple_spinner_drowndown_item);
        spn.setAdapter(cursorAdapter);
    }

    public static String truncar(String str, int tam)
    {
        if (TextUtils.isEmpty(str)) return str;
        if (str.length() <= tam) return str;
        return str.substring(0, tam-1);
    }

    public static void posicionarCombo(Spinner spn, Long id)
    {
        int position = getSpinnerPositionFromId(spn, id);
        if (position == -1) return;
        spn.setSelection(position);
    }

    public static int getSpinnerPositionFromId(Spinner spn, long id)
    {
        Adapter adapter = spn.getAdapter();
        int count = adapter.getCount();

        for (int i = 0; i<count; i++)
        {
            if (id == adapter.getItemId(i)) return i;
        }
        return -1;
    }

    public static IEnums.Tipo obterTipoPorNomePest(String nomePest)
    {
        if (TextUtils.isEmpty(nomePest)) return null;
        for (IEnums.Tipo tipo : IEnums.Tipo.values())
        {
            if (nomePest.contentEquals(tipo.getNomePest())) return tipo;
        }
        return null;
    }

}
