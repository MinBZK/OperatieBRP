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
import nl.bzk.brp.metaregister.model.Tekst;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class TekstDaoTest extends AbstractRepositoryTestCase {

    @Inject
    private TekstDao                   dao;
    @Inject
    private ObjectTypeDao              objectDao;
    @Inject
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void testGetAll() {
        List<Tekst> teksten = dao.getAll();
        Assert.assertFalse(teksten.isEmpty());
        String sql = "select count(0) from tekst;";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);
        Assert.assertEquals(count, teksten.size());
    }

    @Test
    public void shouldGetJavadocForObjectPersoon() {
        ObjectType persoon = objectDao.getBySyncId(3010);
        String tekst = dao.getJavaDocForObject(persoon);
        Assert.assertNotNull(tekst);
    }

    @Test
    public void shouldGetFullJavadocForObjectPersoon() {
        ObjectType persoon = objectDao.getBySyncId(3010);
        String tekst = dao.getFullJavaDocForObject(persoon);
        Assert.assertNotNull(tekst);
    }

}
