/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.io.Serializable;
import java.util.List;

import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Laag;
import nl.bzk.brp.metaregister.model.SetOfModel;
import org.springframework.util.StringUtils;


public abstract class AbstractElementDao<T extends Serializable> extends AbstractJpaDao<T> {

    @Override
    protected List<T> getResultList(final String qlString) {
        if (!qlString.contains("where ")) {
            throw new RuntimeException(String.format("de query bevat geen where clause [%s]", qlString));
        }
        String template = qlString.replaceAll("where ", "where e.elementByLaag = %d and (insom is null or insom in (%s)) and ");
        String qlStringMetFilter = String.format(template, Laag.getLaag().getId(), getSetOfModelAlsCsv());
        return super.getResultList(qlStringMetFilter);
    }

    private Object getSetOfModelAlsCsv() {
        return StringUtils.collectionToDelimitedString(SetOfModel.getCodes(), ",", "'", "'");
    }

    @Override
    public List<T> getAll() {
        String qlString = String.format("select e from %s e where 1=1", getModelClassName());
        return getResultList(qlString);
    }

    public T getBySyncId(final int id) {
        String qlString = String.format("select e from %s e where e.syncid = %d", getModelClassName(), id);
        List<T> resultList = getResultList(qlString);
        if (resultList.size() > 1) {
            throw new RuntimeException("syncid niet uniek " + id);
        }
        return resultList.get(0);
    }

    public T cast(final GeneriekElement element) {
        String qlString = String.format("select e from %s e where e.id = %d", getModelClassName(), element.getId());
        return getSingleResult(qlString);
    }

}
