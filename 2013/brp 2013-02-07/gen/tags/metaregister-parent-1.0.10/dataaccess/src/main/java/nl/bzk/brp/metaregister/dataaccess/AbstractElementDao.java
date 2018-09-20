/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.io.Serializable;
import java.util.List;

import nl.bzk.brp.metaregister.model.Laag;


public abstract class AbstractElementDao<T extends Serializable> extends AbstractJpaDao<T> {

    private static ThreadLocal<Laag> laagThreadLocal = new ThreadLocal<Laag>();

    static {
        laagThreadLocal.set(Laag.LOGISCH);
    }

    @Override
    protected List<T> getResultList(final String qlString) {
        if (!qlString.contains("where ")) {
            throw new RuntimeException(String.format("de query bevat geen where clause [%s]", qlString));
        }
        String template = qlString.replaceAll("where ", "where e.elementByLaag = %d and ");
        String qlStringMetFilter = String.format(template, getLaag().getId());
        return super.getResultList(qlStringMetFilter);
    }

    @Override
    public List<T> getAll() {
        String qlString = String.format("select e from %s e where 1=1", getModelClassName());
        return getResultList(qlString);
    }

    public static Laag getLaag() {
        return laagThreadLocal.get();
    }

    public static void setLaag(final Laag laag) {
        laagThreadLocal.set(laag);
    }

}
