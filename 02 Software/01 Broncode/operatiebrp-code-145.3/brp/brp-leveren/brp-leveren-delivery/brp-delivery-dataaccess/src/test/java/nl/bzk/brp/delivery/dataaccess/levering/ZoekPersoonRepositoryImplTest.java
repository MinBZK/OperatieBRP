/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import com.google.common.collect.Lists;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.brp.delivery.dataaccess.bevraging.SqlStamementZoekPersoon;
import nl.bzk.brp.delivery.dataaccess.bevraging.ZoekPersoonRepository;
import nl.bzk.brp.service.dalapi.QueryCancelledException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * ZoekPersoonRepositoryImplTest.
 */
@Data(resources = {
        "classpath:/data/aut-lev.xml",
        "classpath:/data/dataset_zoekpersoon.xml"})
public class ZoekPersoonRepositoryImplTest extends AbstractDataAccessTest {

    @Inject
    private ZoekPersoonRepository zoekPersoonRepository;

    private boolean postgres = false;

    @Before
    public void setup() throws SQLException {
        this.postgres = zoekPersoonRepository.isPostgres();
    }

    @Test
    public void testZoekPersoonActueel() throws SQLException, QueryCancelledException {
        final String sql = "select p.id from kern.pers p where p.bsn = ?";
        final SqlStamementZoekPersoon sqlStamementZoekPersoon = new SqlStamementZoekPersoon(sql, Lists.newArrayList("402533928"));
        final List<Long> persoonIds = zoekPersoonRepository.zoekPersonen(sqlStamementZoekPersoon, postgres);
        Assert.assertEquals(1, persoonIds.size());
    }

    @Test
    public void isPostgres() throws SQLException {
        boolean isPostres = zoekPersoonRepository.isPostgres();
        Assert.assertTrue(isPostres == postgres);
    }
}
