/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository.jpa;

import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.telling.AbstractDatabaseTest;
import nl.bzk.migratiebrp.isc.telling.entiteit.ProcesTelling;
import nl.bzk.migratiebrp.isc.telling.repository.ProcesTellingenRepository;
import nl.bzk.migratiebrp.isc.telling.util.DBUnit.InsertBefore;
import org.junit.Assert;
import org.junit.Test;

public class ProcesTellingRepositoryImplTest extends AbstractDatabaseTest {

    private static final String KANAAL = "VOISC";
    private static final String BERICHT_TYPE = "Lg01";
    private static final String GELDIGE_PROCES_NAAM = "uc309";
    private static final String ONGELDIGE_PROCES_NAAM = "foutafhandeling";
    private static final Timestamp GELDIGE_DATUM = new Timestamp(System.currentTimeMillis());
    private static final Timestamp ONGELDIGE_DATUM = new Timestamp(10000L);

    @Inject
    private ProcesTellingenRepository service;

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testHaalProcesTellingOp() {

        final ProcesTelling nieuweProcesTelling = new ProcesTelling();
        nieuweProcesTelling.setKanaal(KANAAL);
        nieuweProcesTelling.setDatum(GELDIGE_DATUM);
        nieuweProcesTelling.setProcesnaam(GELDIGE_PROCES_NAAM);
        nieuweProcesTelling.setAantalGestarteProcessen(1);
        nieuweProcesTelling.setAantalBeeindigdeProcessen(1);
        nieuweProcesTelling.setBerichtType(BERICHT_TYPE);
        service.save(nieuweProcesTelling);

        ProcesTelling procesTelling = service.haalProcesTellingOp(ONGELDIGE_PROCES_NAAM, KANAAL, BERICHT_TYPE, ONGELDIGE_DATUM);
        Assert.assertNull(procesTelling);

        procesTelling = service.haalProcesTellingOp(GELDIGE_PROCES_NAAM, KANAAL, BERICHT_TYPE, ONGELDIGE_DATUM);
        Assert.assertNull(procesTelling);

        procesTelling = service.haalProcesTellingOp(ONGELDIGE_PROCES_NAAM, KANAAL, BERICHT_TYPE, GELDIGE_DATUM);
        Assert.assertNull(procesTelling);

        procesTelling = service.haalProcesTellingOp(GELDIGE_PROCES_NAAM, KANAAL, BERICHT_TYPE, GELDIGE_DATUM);
        Assert.assertNotNull(procesTelling);
        Assert.assertEquals(procesTelling.getBerichtType(), BERICHT_TYPE);
        Assert.assertEquals(procesTelling.getProcesnaam(), GELDIGE_PROCES_NAAM);
        Assert.assertEquals(nieuweProcesTelling.getAantalGestarteProcessen(), procesTelling.getAantalGestarteProcessen());
        Assert.assertEquals(nieuweProcesTelling.getAantalBeeindigdeProcessen(), procesTelling.getAantalBeeindigdeProcessen());
    }

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testSave() {

        final ProcesTelling nieuweProcesTelling = new ProcesTelling();
        nieuweProcesTelling.setKanaal(KANAAL);
        nieuweProcesTelling.setDatum(GELDIGE_DATUM);
        nieuweProcesTelling.setProcesnaam(GELDIGE_PROCES_NAAM);
        nieuweProcesTelling.setAantalGestarteProcessen(1);
        nieuweProcesTelling.setAantalBeeindigdeProcessen(1);
        nieuweProcesTelling.setBerichtType(BERICHT_TYPE);
        service.save(nieuweProcesTelling);

        final ProcesTelling procesTelling = service.haalProcesTellingOp(GELDIGE_PROCES_NAAM, KANAAL, BERICHT_TYPE, GELDIGE_DATUM);
        Assert.assertNotNull(procesTelling);
        procesTelling.setAantalBeeindigdeProcessen(10);
        procesTelling.setAantalGestarteProcessen(100);
        final ProcesTelling gewijzigdeProcesTelling = service.save(procesTelling);
        Assert.assertEquals(Integer.valueOf(10), gewijzigdeProcesTelling.getAantalBeeindigdeProcessen());
        Assert.assertEquals(Integer.valueOf(100), gewijzigdeProcesTelling.getAantalGestarteProcessen());
        Assert.assertNotSame(nieuweProcesTelling.getAantalBeeindigdeProcessen(), gewijzigdeProcesTelling.getAantalBeeindigdeProcessen());
        Assert.assertNotSame(nieuweProcesTelling.getAantalGestarteProcessen(), gewijzigdeProcesTelling.getAantalGestarteProcessen());
    }
}
