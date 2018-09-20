/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.util.List;

import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.springframework.stereotype.Repository;


@Repository
public class GroepDao extends AbstractElementDao<Groep> {

    @Override
    protected Class<Groep> getModelClass() {
        return Groep.class;
    }

    public List<Groep> getGroepen(final ObjectType objectType) {
        String template = "select e from %s e where e.objectType.id = %d order by e.volgnummerG";
        String qlString = String.format(template, getModelClassName(), objectType.getId());
        return getResultList(qlString);
    }

    public List<Groep> getDynamischHistorisch() {
        String template = "select e from %s e where e.objectType.soortInhoud = 'D' and e.historieVastleggen != 'G'";
        String qlString = String.format(template, getModelClassName());
        return getResultList(qlString);
    }

}
