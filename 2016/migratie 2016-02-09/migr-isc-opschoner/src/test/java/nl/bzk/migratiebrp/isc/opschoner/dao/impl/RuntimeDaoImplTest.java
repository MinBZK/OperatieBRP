/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao.impl;

import java.sql.SQLException;
import javax.inject.Inject;
import junit.framework.Assert;
import nl.bzk.migratiebrp.isc.opschoner.dao.RuntimeDao;
import org.junit.Test;

public class RuntimeDaoImplTest extends AbstractDaoTest {

    private static final String RUNTIME_NAAM = "opschonen";
    private static final String CONTROLE_QUERY = "select count(*) from mig_runtime where runtime_naam = '" + RUNTIME_NAAM + "'";

    @Inject
    private RuntimeDao runtimeDao;

    @Test
    public void testVoegRuntimeToe() throws SQLException {

        Assert.assertEquals(Long.valueOf(0), geefQueryResultaat(CONTROLE_QUERY, Long.class));

        runtimeDao.voegRuntimeToe(RUNTIME_NAAM);

        Assert.assertEquals(Long.valueOf(1), geefQueryResultaat(CONTROLE_QUERY, Long.class));
    }

    @Test
    public void testVerwijderLock() throws SQLException {

        final Long aantalRijenInTabel = geefQueryResultaat(CONTROLE_QUERY, Long.class);

        if (aantalRijenInTabel == 0) {
            runtimeDao.voegRuntimeToe(RUNTIME_NAAM);
        }

        Assert.assertEquals(Long.valueOf(1), geefQueryResultaat(CONTROLE_QUERY, Long.class));

        runtimeDao.verwijderRuntime(RUNTIME_NAAM);

        Assert.assertEquals(Long.valueOf(0), geefQueryResultaat(CONTROLE_QUERY, Long.class));
    }
}
