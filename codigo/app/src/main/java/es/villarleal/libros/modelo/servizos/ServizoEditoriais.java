package es.villarleal.libros.modelo.servizos;

import android.database.sqlite.SQLiteDatabase;

import es.villarleal.libros.comun.IEnums;
import es.villarleal.libros.modelo.daos.DaoEditoriais;

/**
 * Created by santiago on 30/03/17.
 */

public class ServizoEditoriais extends ServizoXenerico
{
    private static ServizoEditoriais _servizoEditoriais = null;
    private DaoEditoriais _daoEditoriais = null;

    private ServizoEditoriais(SQLiteDatabase db)
    {
        super(DaoEditoriais.crearInstancia(db));
        _daoEditoriais = (DaoEditoriais) _dao;
    }

    public static ServizoEditoriais crearInstancia(SQLiteDatabase db)
    {
        if (_servizoEditoriais == null) _servizoEditoriais = new ServizoEditoriais(db);
        return _servizoEditoriais;
    }

    public IEnums.Tipo obterTipo() { return IEnums.Tipo.EDITORIAL; }

}
