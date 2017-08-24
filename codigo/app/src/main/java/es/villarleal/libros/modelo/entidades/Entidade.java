package es.villarleal.libros.modelo.entidades;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by santiago on 14/04/17.
 */

public abstract class Entidade implements Comparator<Entidade>
{
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private static Collator collator = null;

    protected static Collator getCollator()
    {
        if (collator == null) collator = Collator.getInstance();
        collator.setStrength(Collator.PRIMARY);

        return collator;
    }

}
