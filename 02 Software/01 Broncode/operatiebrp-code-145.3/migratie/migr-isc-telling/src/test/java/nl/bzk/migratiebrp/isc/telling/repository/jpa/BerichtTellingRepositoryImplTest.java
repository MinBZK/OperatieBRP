/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository.jpa;

import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.telling.AbstractDatabaseTest;
import nl.bzk.migratiebrp.isc.telling.entiteit.BerichtTelling;
import nl.bzk.migratiebrp.isc.telling.repository.BerichtTellingRepository;
import nl.bzk.migratiebrp.isc.telling.util.DBUnit.InsertBefore;
import org.junit.Assert;
import org.junit.Test;

public class BerichtTellingRepositoryImplTest extends AbstractDatabaseTest {

    private static final String KANAAL = "VOISC";
    private static final String GELDIG_BERICHT_TYPE = "La01";
    private static final String ONGELDIG_BERICHT_TYPE = "If01";
    private static final Timestamp GELDIGE_DATUM = new Timestamp(System.currentTimeMillis());
    private static final Timestamp ONGELDIGE_DATUM = new Timestamp(10000L);

    @Inject
    private BerichtTellingRepository service;

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testHaalBerichtTellingOp() {

        final BerichtTelling nieuweBerichtTelling = new BerichtTelling();
        nieuweBerichtTelling.setKanaal(KANAAL);
        nieuweBerichtTelling.setDatum(GELDIGE_DATUM);
        nieuweBerichtTelling.setBerichtType(GELDIG_BERICHT_TYPE);
        nieuweBerichtTelling.setAantalIngaand(1);
        nieuweBerichtTelling.setAantalUitgaand(1);
        service.save(nieuweBerichtTelling);

        BerichtTelling berichtTelling = service.haalBerichtTellingOp(ONGELDIG_BERICHT_TYPE, KANAAL, ONGELDIGE_DATUM);
        Assert.assertNull(berichtTelling);

        berichtTelling = service.haalBerichtTellingOp(GELDIG_BERICHT_TYPE, KANAAL, ONGELDIGE_DATUM);
        Assert.assertNull(berichtTelling);

        berichtTelling = service.haalBerichtTellingOp(ONGELDIG_BERICHT_TYPE, KANAAL, GELDIGE_DATUM);
        Assert.assertNull(berichtTelling);

        berichtTelling = service.haalBerichtTellingOp(GELDIG_BERICHT_TYPE, KANAAL, GELDIGE_DATUM);
        Assert.assertNotNull(berichtTelling);
        Assert.assertEquals(berichtTelling.getBerichtType(), GELDIG_BERICHT_TYPE);
        Assert.assertEquals(nieuweBerichtTelling.getAantalIngaand(), berichtTelling.getAantalIngaand());
        Assert.assertEquals(nieuweBerichtTelling.getAantalUitgaand(), berichtTelling.getAantalUitgaand());
    }

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testSave() {

        final BerichtTelling nieuweBerichtTelling = new BerichtTelling();
        nieuweBerichtTelling.setKanaal(KANAAL);
        nieuweBerichtTelling.setDatum(GELDIGE_DATUM);
        nieuweBerichtTelling.setBerichtType(GELDIG_BERICHT_TYPE);
        nieuweBerichtTelling.setAantalIngaand(1);
        nieuweBerichtTelling.setAantalUitgaand(1);
        service.save(nieuweBerichtTelling);

        final BerichtTelling berichtTelling = service.haalBerichtTellingOp(GELDIG_BERICHT_TYPE, KANAAL, GELDIGE_DATUM);
        Assert.assertNotNull(berichtTelling);
        berichtTelling.setAantalIngaand(10);
        berichtTelling.setAantalUitgaand(100);
        final BerichtTelling gewijzigdeBerichtTelling = service.save(berichtTelling);
        Assert.assertEquals(Integer.valueOf(10), gewijzigdeBerichtTelling.getAantalIngaand());
        Assert.assertEquals(Integer.valueOf(100), gewijzigdeBerichtTelling.getAantalUitgaand());
        Assert.assertNotSame(nieuweBerichtTelling.getAantalIngaand(), gewijzigdeBerichtTelling.getAantalIngaand());
        Assert.assertNotSame(nieuweBerichtTelling.getAantalUitgaand(), gewijzigdeBerichtTelling.getAantalUitgaand());
    }
}
