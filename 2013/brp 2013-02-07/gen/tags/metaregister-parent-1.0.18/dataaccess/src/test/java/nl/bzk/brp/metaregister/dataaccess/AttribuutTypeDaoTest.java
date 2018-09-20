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
import nl.bzk.brp.metaregister.model.AttribuutType;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class AttribuutTypeDaoTest extends AbstractRepositoryTestCase {

    @Inject
    private AttribuutTypeDao                   dao;

    @Inject
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void testGetAll() {
        List<AttribuutType> attribuutTypes = dao.getAll();
        Assert.assertFalse(attribuutTypes.isEmpty());
        String sql = "select count(0) from element where soort = 'AT' and versie_tag = 'W' and laag = 1749 and (insom is null or insom in ('M', 'B'))";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);
        Assert.assertEquals(count, attribuutTypes.size());
    }

}
