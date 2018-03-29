/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.opschoner.dao.MigDao;
import org.junit.Assert;
import org.junit.Test;

public class MigDaoImplTest extends AbstractDaoTest {

    private static final Long PROCES_ID = (long) 3367;
    private static final Long GERELATEERD_PROCES_ID = (long) 3369;

    @Inject
    private MigDao migDao;

    @Test
    public void testBepalenDatumLaatsteBericht() throws SQLException {

        final Timestamp opgehaaldeDatumLaatsteBericht =
                geefQueryResultaat("select max(tijdstip) from mig_bericht where process_instance_id = " + PROCES_ID, Timestamp.class);

        final Timestamp datumLaatsteBericht = migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(PROCES_ID);

        Assert.assertEquals(opgehaaldeDatumLaatsteBericht, datumLaatsteBericht);
    }

    @Test
    public void testOphalenGerelateerdeProcessen() throws SQLException {

        final List<Long> opgehaaldeIds = new ArrayList<>();
        opgehaaldeIds.add(GERELATEERD_PROCES_ID);

        final List<Long> datumLaatsteBericht = migDao.selecteerGerelateerdeProcessenVoorProces(PROCES_ID);

        Assert.assertEquals(opgehaaldeIds, datumLaatsteBericht);
    }

    @Test
    public void testVerwijderenBerichten() throws SQLException {

        final String controleQuery = "select id from mig_bericht where process_instance_id = " + PROCES_ID;

        List<Integer> opgehaaldeIds = geefQueryLijstResultaat(controleQuery, Integer.class);

        Assert.assertEquals(9, opgehaaldeIds.size());

        migDao.verwijderBerichtenVanProces(PROCES_ID);

        opgehaaldeIds = geefQueryLijstResultaat(controleQuery, Integer.class);

        Assert.assertEquals(0, opgehaaldeIds.size());
    }

    @Test
    public void testVerwijderenGerelateerdeProces() throws SQLException {

        List<Long> opgehaaldeIds = migDao.selecteerGerelateerdeProcessenVoorProces(PROCES_ID);

        Assert.assertEquals(1, opgehaaldeIds.size());

        migDao.verwijderGerelateerdProcesVerwijzingVoorProces(PROCES_ID, GERELATEERD_PROCES_ID);

        opgehaaldeIds = migDao.selecteerGerelateerdeProcessenVoorProces(PROCES_ID);

        Assert.assertEquals(0, opgehaaldeIds.size());
        //
        // voerInsertQueryUit("INSERT INTO mig_proces_relatie VALUES (3367, 3369);", 1);
    }
}
