/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import nl.bzk.brp.metaregister.model.Instelling;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class InstellingDaoTest extends AbstractRepositoryTestCase {

    @Inject
    private InstellingDao dao;

    @Inject
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void testGetAll() {
        List<Instelling> instellingen = dao.getAll();
        Assert.assertFalse(instellingen.isEmpty());
        String sql = "select count(0) from instelling";
        long count = jdbcTemplate.queryForObject(sql, (Map<String, ?>) null, Long.class);
        Assert.assertEquals(count, instellingen.size());
    }

}
