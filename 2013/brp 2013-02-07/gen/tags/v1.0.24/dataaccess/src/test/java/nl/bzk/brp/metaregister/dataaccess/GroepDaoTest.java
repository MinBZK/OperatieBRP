/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class GroepDaoTest extends AbstractRepositoryTestCase {

    @Inject
    private GroepDao groepDao;

    @Inject
    private ObjectTypeDao objectTypeDao;

    @Inject
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void testGetAll() {
        List<Groep> groepen = groepDao.getAll();
        Assert.assertFalse(groepen.isEmpty());
        String sql = "select count(0) from element where" +
            " soort = 'G' and laag = 1749";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);
        Assert.assertEquals(count, groepen.size());
    }

    @Test
    public void testGetDynamischHistorisch() {
        List<Groep> groepen = groepDao.getDynamischHistorisch();
        Assert.assertFalse(groepen.isEmpty());
        String sql = "select count(0) from element join element o on (ouder=o.id) where" +
            " o.soort_inhoud = 'D' and historie_vastleggen != 'G' and " +
            " soort = 'G' and laag = 1749";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);
        Assert.assertEquals(count, groepen.size());
    }

    @Test
    public void testGetGroepen() {
        List<ObjectType> objectTypes = objectTypeDao.getAll();
        Assert.assertFalse(objectTypes.isEmpty());

        ObjectType objectType = getObjectTypeOpNaam(objectTypes, "Bericht");

        // test
        Iterator<Groep> groepen = groepDao.getGroepen(objectType).iterator();
        Groep groep = groepen.next();
        Assert.assertSame(objectType, groep.getObjectType());

        Assert.assertEquals("Identiteit", groep.getNaam());
        Assert.assertEquals("Stuurgegevens", groepen.next().getNaam());
        Assert.assertEquals("Resultaat", groepen.next().getNaam());
        Assert.assertFalse(groepen.hasNext());
    }

}
