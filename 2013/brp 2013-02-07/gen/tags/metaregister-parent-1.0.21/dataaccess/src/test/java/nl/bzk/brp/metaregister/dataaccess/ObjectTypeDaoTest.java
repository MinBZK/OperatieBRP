/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import nl.bzk.brp.metaregister.model.SoortElement;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class ObjectTypeDaoTest extends AbstractRepositoryTestCase {

    @Inject
    private ObjectTypeDao              dao;

    @Inject
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void testGetAll() {
        List<ObjectType> objectTypes = dao.getAll();
        Assert.assertFalse(objectTypes.isEmpty());
        String sql =
            "select count(0) from element where soort = 'OT' and laag = 1749";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);
        Assert.assertEquals(count, objectTypes.size());
    }

    @Test
    public void testGetEnumeratieTypes() {
        List<ObjectType> enumeratieTypes = dao.getEnumeratieTypes();
        Assert.assertFalse(enumeratieTypes.isEmpty());
        for (ObjectType enumeratieType : enumeratieTypes) {
            Assert.assertEquals('X', enumeratieType.getSoortInhoud().charValue());
            for (Tuple tuple : enumeratieType.getTuples()) {
                Assert.assertEquals("T", tuple.getSoortElement().getCode());
            }
        }
    }

    @Test
    public void testGetComponentTypes() {
        List<ObjectType> objectTypes = dao.getComponentTypes();
        Assert.assertFalse(objectTypes.isEmpty());

        String sql =
            "select count(*) from (select e.id from element e join element a on (a.ouder=e.id)"
                + " where a.soort = 'A' and a.inverse_associatie_naam != '' group by e.id having count(*) = 1)";

        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);
        Assert.assertEquals(count, objectTypes.size());
    }

}
