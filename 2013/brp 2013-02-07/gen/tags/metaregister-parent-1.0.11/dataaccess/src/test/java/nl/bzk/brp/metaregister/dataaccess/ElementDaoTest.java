/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.brp.metaregister.model.Element;
import nl.bzk.brp.metaregister.model.Laag;
import nl.bzk.brp.metaregister.model.SetOfModel;
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
        String sql = "select count(0) from element where laag = 1749 and (insom is null or insom in ('M', 'B'))";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);
        Assert.assertEquals(count, elements.size());
    }

    @Test
    public void testGetAllOperationeel() {
        Laag.OPERATIONEEL.set();
        List<Element> elements = dao.getAll();
        Assert.assertFalse(elements.isEmpty());
        String sql = "select count(0) from element where laag = 1751 and (insom is null or insom in ('M', 'B'))";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);
        Assert.assertEquals(count, elements.size());
    }

    @Test
    public void testGetAllWeb() {
        SetOfModel.setWaardes(SetOfModel.BEIDE);
        List<Element> elements = dao.getAll();
        Assert.assertFalse(elements.isEmpty());
        String sql = "select count(0) from element where laag = 1749 and (insom is null or insom in ('B'))";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);
        Assert.assertEquals(count, elements.size());
    }

}
