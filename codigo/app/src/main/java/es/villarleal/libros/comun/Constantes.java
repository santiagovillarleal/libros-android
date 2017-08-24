package es.villarleal.libros.comun;

import android.content.Context;

import com.example.libros.R;

/**
 * Created by santiago on 29/03/17.
 */

public class Constantes
{
    private static Constantes _constantes = null;
    private Context mContext = null;
    private int tamColNome;
    private int tamColCodExemplar;
    private int tamColEdicion;
    private int tamColApelidos;
    private int tamColTitulo;

    public int getTamColApelidos() {
        return tamColApelidos;
    }

    public int getTamColTitulo() {
        return tamColTitulo;
    }

    private Constantes(Context ctx)
    {
        mContext = ctx;
        tamColNome = mContext.getResources().getInteger(R.integer.tam_col_nome);
        tamColCodExemplar = mContext.getResources().getInteger(R.integer.tam_col_cod_exemplar);
        tamColEdicion = mContext.getResources().getInteger(R.integer.tam_col_edicion);
        tamColApelidos = mContext.getResources().getInteger(R.integer.tam_col_apelidos);
        tamColTitulo = mContext.getResources().getInteger(R.integer.tam_col_titulo);
    }

    public static Constantes crearInstancia(Context ctx)
    {
        if (_constantes == null) _constantes = new Constantes(ctx);
        return _constantes;
    }
    public static Constantes obterInstancia()
    {
        return _constantes;
    }

    public int getTamColNome() {
        return tamColNome;
    }

    public int getTamColCodExemplar() {
        return tamColCodExemplar;
    }

    public int getTamColEdicion() {
        return tamColEdicion;
    }

    public final static String CTE_INTENT_EXTRA_TIPO = "com.example.libros.TIPO";
    public final static String CTE_INTENT_EXTRA_ID = "com.example.libros.ID";

    public final static String CTE_ESPAZO = " ";
    public final static String CTE_PUNTO = ".";
    public final static String CTE_COMA = ",";
    public final static String CTE_BALEIRO = "";
    public final static int CTE_TAM_MAX_TAG = 23;
    public final static int CTE_INT_MENOS_UN = -1;
    public final static int NUM_PEST = IEnums.Tipo.values().length;
    public final static String CARPETA_PRINCIPAL = "/Libros";
}
