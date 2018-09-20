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
import nl.bzk.migratiebrp.isc.telling.entiteit.Bericht;
import nl.bzk.migratiebrp.isc.telling.entiteit.ProcesExtractie;
import nl.bzk.migratiebrp.isc.telling.repository.BerichtRepository;
import nl.bzk.migratiebrp.isc.telling.util.DBUnit.InsertBefore;
import org.junit.Assert;
import org.junit.Test;

public class BerichtRepositoryImplTest extends AbstractDatabaseTest {

    private static final Long GELDIG_PROCES_INSTANTIE_ID = 2131L;
    // private static final Long ONGELDIG_PROCES_INSTANTIE_ID = 2130L;
    private static final Long GELDIG_BERICHT_ID = 4688L;
    private static final Long ONGELDIG_BERICHT_ID = 4600L;
    private static final Timestamp DATUM_TOT = new Timestamp(System.currentTimeMillis());
    private static final Timestamp DATUM_TOT_ONGELDIG = new Timestamp(10000L);

    @Inject
    private BerichtRepository service;

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testSelecteerInTellingTeVerwerkenBerichten() {

        List<Bericht> berichtLijst = service.selecteerInTellingTeVerwerkenBerichten(null, null);
        Assert.assertNotNull(berichtLijst);
        Assert.assertEquals(0, berichtLijst.size());

        berichtLijst = service.selecteerInTellingTeVerwerkenBerichten(DATUM_TOT_ONGELDIG, null);
        Assert.assertNotNull(berichtLijst);
        Assert.assertEquals(0, berichtLijst.size());

        berichtLijst = service.selecteerInTellingTeVerwerkenBerichten(DATUM_TOT, 10000);
        Assert.assertNotNull(berichtLijst);
        Assert.assertEquals(7, berichtLijst.size());
    }

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testUpdateIndicatieGestartGeteldBerichten() {

        // Null parameter, resultaat is true.
        Assert.assertTrue(service.updateIndicatieGeteldBerichten(null));

        // Lege lijst parameter, resultaat is true.
        final List<Long> teUpdatenBerichtenLijst = new ArrayList<>();
        Assert.assertTrue(service.updateIndicatieGeteldBerichten(teUpdatenBerichtenLijst));

        // Één proces extractie in lijst parameter, resultaat is true.
        teUpdatenBerichtenLijst.add(GELDIG_BERICHT_ID);
        Assert.assertTrue(service.updateIndicatieGeteldBerichten(teUpdatenBerichtenLijst));

        // Twee proces extracties in lijst parameter, resultaat wijzigy aangezien er geen bericht voor
        // het tweede id is.
        teUpdatenBerichtenLijst.add(ONGELDIG_BERICHT_ID);
        Assert.assertFalse(service.updateIndicatieGeteldBerichten(teUpdatenBerichtenLijst));
    }

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testSave() {
        final List<ProcesExtractie> procesExtractieLijst = new ArrayList<>();
        final ProcesExtractie geldigeProcesExtractie = new ProcesExtractie();
        geldigeProcesExtractie.setProcesInstantieId(GELDIG_PROCES_INSTANTIE_ID);
        procesExtractieLijst.add(geldigeProcesExtractie);

        final List<Bericht> berichtenLijst = service.selecteerInTellingTeVerwerkenBerichten(DATUM_TOT, 10000);
        final Bericht testBericht = berichtenLijst.get(0);
        testBericht.setIndicatieGeteld(true);
        final Bericht gewijzigdBericht = service.save(testBericht);
        Assert.assertTrue(gewijzigdBericht.getIndicatieGeteld());

        final Long aantalBerichten = service.telInTellingTeVerwerkenBerichten(DATUM_TOT);
        Assert.assertEquals(Long.valueOf(6), aantalBerichten);
    }
}
