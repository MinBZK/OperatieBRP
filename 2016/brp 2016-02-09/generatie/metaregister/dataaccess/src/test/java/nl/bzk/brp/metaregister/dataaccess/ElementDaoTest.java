/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.metaregister.model.Element;
import nl.bzk.brp.metaregister.model.Laag;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class ElementDaoTest extends AbstractRepositoryTestCase {

    @Inject
    private ElementDao dao;

    @Inject
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void testGetAllLogisch() {
        List<Element> elements = dao.getAll();
        Assert.assertFalse(elements.isEmpty());
        String sql = "select count(0) from element where laag = 1749";
        long count = jdbcTemplate.queryForObject(sql, (Map<String, ?>) null, Long.class);
        Assert.assertEquals(count, elements.size());
    }

    @Test
    public void testGetAllOperationeel() {
        Laag.OPERATIONEEL.set();
        List<Element> elements = dao.getAll();
        Assert.assertFalse(elements.isEmpty());
        String sql = "select count(0) from element where laag = 1751";
        long count = jdbcTemplate.queryForObject(sql, (Map<String, ?>) null, Long.class);
        Assert.assertEquals(count, elements.size());
    }

    @Test
    public void testGetAllWeb() {
        List<Element> elements = dao.getAll();
        Assert.assertFalse(elements.isEmpty());
        String sql = "select count(0) from element where laag = 1749";
        long count = jdbcTemplate.queryForObject(sql, (Map<String, ?>) null, Long.class);
        Assert.assertEquals(count, elements.size());
    }

    @Test
    public void testGetById() {
        Assert.assertEquals(3637, dao.getBySyncId(3637).getSyncid().intValue());
    }

}
