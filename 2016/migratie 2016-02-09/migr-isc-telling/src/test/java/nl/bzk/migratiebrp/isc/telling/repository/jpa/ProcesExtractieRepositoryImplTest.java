/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository.jpa;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.telling.AbstractDatabaseTest;
import nl.bzk.migratiebrp.isc.telling.entiteit.ProcesExtractie;
import nl.bzk.migratiebrp.isc.telling.repository.ProcesExtractieRepository;
import nl.bzk.migratiebrp.isc.telling.util.DBUnit.InsertBefore;
import org.junit.Assert;
import org.junit.Test;

public class ProcesExtractieRepositoryImplTest extends AbstractDatabaseTest {

    private static final String BERICHT_TYPE = "Lg01";
    private static final String GELDIGE_PROCES_NAAM = "uc202";
    private static final Long GELDIG_PROCES_EXTRACTIE_ID = 2131L;
    private static final Long ONGELDIG_PROCES_EXTRACTIE_ID = 1111L;
    private static final Timestamp GELDIGE_DATUM = new Timestamp(System.currentTimeMillis());
    private static final Timestamp ONGELDIGE_DATUM = new Timestamp(10000L);

    @Inject
    private ProcesExtractieRepository service;

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testSelecteerInTellingTeVerwerkenGestarteProcesInstanties() {

        Assert.assertNull(service.selecteerInTellingTeVerwerkenGestarteProcesInstanties(null, null));

        List<ProcesExtractie> procesExtractieLijst = service.selecteerInTellingTeVerwerkenGestarteProcesInstanties(ONGELDIGE_DATUM, null);
        Assert.assertNotNull(procesExtractieLijst);
        Assert.assertEquals(0, procesExtractieLijst.size());

        procesExtractieLijst = service.selecteerInTellingTeVerwerkenGestarteProcesInstanties(GELDIGE_DATUM, 10000);

        Assert.assertNotNull(procesExtractieLijst);
        Assert.assertEquals(3, procesExtractieLijst.size());

        final Long aantalGestarteProcesInstanties = service.telInTellingTeVerwerkenGestarteProcessen(GELDIGE_DATUM);
        Assert.assertEquals(Long.valueOf(3), aantalGestarteProcesInstanties);

        Assert.assertEquals(procesExtractieLijst.get(0).getBerichtType(), BERICHT_TYPE);
        Assert.assertEquals(procesExtractieLijst.get(0).getProcesnaam(), GELDIGE_PROCES_NAAM);
    }

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testSelecteerInTellingTeVerwerkenBeeindigdeProcesInstanties() {

        Assert.assertNull(service.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(null, null));

        List<ProcesExtractie> procesExtractieLijst = service.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(ONGELDIGE_DATUM, null);
        Assert.assertNotNull(procesExtractieLijst);
        Assert.assertEquals(0, procesExtractieLijst.size());

        procesExtractieLijst = service.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(GELDIGE_DATUM, 1000);
        Assert.assertNotNull(procesExtractieLijst);
        Assert.assertEquals(3, procesExtractieLijst.size());

        final Long aantalBeeindigdeProcesInstanties = service.telInTellingTeVerwerkenBeeindigdeProcessen(GELDIGE_DATUM);
        Assert.assertEquals(Long.valueOf(3), aantalBeeindigdeProcesInstanties);

        Assert.assertEquals(procesExtractieLijst.get(0).getBerichtType(), BERICHT_TYPE);
        Assert.assertEquals(procesExtractieLijst.get(0).getProcesnaam(), GELDIGE_PROCES_NAAM);
    }

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testSave() {

        final List<ProcesExtractie> procesExtractieLijst =
                service.selecteerInTellingTeVerwerkenGestarteProcesInstanties(GELDIGE_DATUM, null);
        procesExtractieLijst.get(0).setIndicatieGestartGeteld(true);
        final ProcesExtractie gewijzigdeProcesExtractie = service.save(procesExtractieLijst.get(0));
        Assert.assertTrue(gewijzigdeProcesExtractie.getIndicatieGestartGeteld());
    }

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testUpdateGestart() {

        // Null parameter, resultaat is true.
        Assert.assertTrue(service.updateIndicatieGestartGeteldProcesExtracties(null));

        // Lege lijst parameter, resultaat is true.
        final List<Long> teUpdatenProcesInstantieLijst = new ArrayList<>();
        Assert.assertTrue(service.updateIndicatieGestartGeteldProcesExtracties(teUpdatenProcesInstantieLijst));

        // Één proces extractie in lijst parameter, resultaat is true.
        teUpdatenProcesInstantieLijst.add(GELDIG_PROCES_EXTRACTIE_ID);
        Assert.assertTrue(service.updateIndicatieGestartGeteldProcesExtracties(teUpdatenProcesInstantieLijst));

        // Twee proces extracties in lijst parameter, resultaat wijzigy aangezien er geen bericht voor
        // het tweede id is.
        teUpdatenProcesInstantieLijst.add(ONGELDIG_PROCES_EXTRACTIE_ID);
        Assert.assertFalse(service.updateIndicatieGestartGeteldProcesExtracties(teUpdatenProcesInstantieLijst));
    }

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testUpdateBeeindigd() {

        // Null parameter, resultaat is true.
        Assert.assertTrue(service.updateIndicatieGestartGeteldProcesExtracties(null));

        // Lege lijst parameter, resultaat is true.
        final List<Long> teUpdatenProcesInstantieLijst = new ArrayList<>();
        Assert.assertTrue(service.updateIndicatieBeeindigdGeteldProcesExtracties(teUpdatenProcesInstantieLijst));

        // Één proces extractie in lijst parameter, resultaat is true.
        teUpdatenProcesInstantieLijst.add(GELDIG_PROCES_EXTRACTIE_ID);
        Assert.assertTrue(service.updateIndicatieBeeindigdGeteldProcesExtracties(teUpdatenProcesInstantieLijst));

        // Twee proces extracties in lijst parameter, resultaat wijzigy aangezien er geen bericht voor
        // het tweede id is.
        teUpdatenProcesInstantieLijst.add(ONGELDIG_PROCES_EXTRACTIE_ID);
        Assert.assertFalse(service.updateIndicatieBeeindigdGeteldProcesExtracties(teUpdatenProcesInstantieLijst));
    }

}
