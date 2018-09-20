/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.util.List;

import javax.persistence.Query;

import nl.bzk.brp.metaregister.model.ObjectType;
import org.springframework.stereotype.Repository;


@Repository
public class ObjectTypeDao extends AbstractElementDao<ObjectType> {

    @Override
    protected Class<ObjectType> getModelClass() {
        return ObjectType.class;
    }

    public List<ObjectType> getEnumeratieTypes() {
        String template = "select e from %s e where e.soortInhoud = 'X'";
        String qlString = String.format(template, getModelClassName());
        return getResultList(qlString);
    }

    public List<ObjectType> getStatischeObjectTypes() {
        String template = "select e from %s e where e.soortInhoud = 'S'";
        String qlString = String.format(template, getModelClassName());
        return getResultList(qlString);
    }

    public List<ObjectType> getDynamischeObjectTypes() {
        String template = "select e from %s e where e.soortInhoud = 'D'";
        String qlString = String.format(template, getModelClassName());
        return getResultList(qlString);
    }

    @SuppressWarnings("unchecked")
    public List<ObjectType> getComponentTypes() {
        String sqlString = "select e.* from element e join element a on (a.ouder=e.id)"
                + " where a.soort = 'A' and a.inverse_associatie_naam != '' group by e.id having count(*) = 1";
        Query query = getEntityManager().createNativeQuery(sqlString, getModelClass());
        return query.getResultList();
    }

}
