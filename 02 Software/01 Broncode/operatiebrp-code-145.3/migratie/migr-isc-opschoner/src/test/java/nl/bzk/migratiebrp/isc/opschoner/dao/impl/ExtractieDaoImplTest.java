/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.opschoner.dao.ExtractieDao;
import org.junit.Assert;
import org.junit.Test;

public class ExtractieDaoImplTest extends AbstractDaoTest {

    @Inject
    private ExtractieDao extractieDao;

    @Test
    public void testOphalenOpTeSchonenProcessen() throws SQLException {

        final List<Long> procesIds = extractieDao.haalProcesIdsOpBasisVanBeeindigdeProcesExtractiesOp(new Timestamp(System.currentTimeMillis()));

        Assert.assertEquals(1, procesIds.size());
    }

    @Test
    public void testUpdatenVerwijderTijd() throws SQLException {

        final List<Long> procesIds = extractieDao.haalProcesIdsOpBasisVanBeeindigdeProcesExtractiesOp(new Timestamp(System.currentTimeMillis()));

        Assert.assertEquals(1, procesIds.size());

        final Timestamp verwachteVerwijderDatum = new Timestamp(System.currentTimeMillis() + 3600000);

        extractieDao.updateVerwachteVerwijderDatum(procesIds.get(0), verwachteVerwijderDatum);

        final Timestamp opgehaaldTijdstip = geefQueryResultaat(
                "select verwachte_verwijder_datum from mig_extractie_proces where process_instance_id = " + procesIds.get(0), Timestamp.class);

        Assert.assertEquals(verwachteVerwijderDatum, opgehaaldTijdstip);

        voerUpdateQueryUit("update mig_extractie_proces set verwachte_verwijder_datum = null where process_instance_id = " + procesIds.get(0), 1);

    }
}
