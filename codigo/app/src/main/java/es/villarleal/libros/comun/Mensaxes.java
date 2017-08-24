package es.villarleal.libros.comun;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by santiago on 30/03/17.
 */

public class Mensaxes
{
    public static void mostrarMensaxeExito(Context ctx, int resId)
    {
        Toast.makeText(ctx, resId, Toast.LENGTH_SHORT).show();
    }

    public static void mostrarMensaxeExito(Context ctx, String msx)
    {
        Toast.makeText(ctx, msx, Toast.LENGTH_SHORT).show();
    }

    public static void mostrarMensaxeErro(Context ctx, int resId)
    {
        String msg = ctx.getString(resId);
        Toast.makeText(ctx, "Erro: " + msg, Toast.LENGTH_SHORT).show();
    }
}
