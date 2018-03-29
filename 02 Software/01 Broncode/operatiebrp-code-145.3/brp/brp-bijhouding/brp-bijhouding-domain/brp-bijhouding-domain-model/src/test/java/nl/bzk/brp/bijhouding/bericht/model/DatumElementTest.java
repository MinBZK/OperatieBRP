/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.junit.Test;

/**
 * Testen voor DatumElementTest.
 */
public class DatumElementTest {

    @Test
    public void testToegestaneWaarden() throws OngeldigeWaardeException {
        assertNotNull(DatumElement.parseWaarde("1999"));
        assertNotNull(DatumElement.parseWaarde("1999-01"));
        assertNotNull(DatumElement.parseWaarde("1999-01-01"));
        assertNotNull(DatumElement.parseWaarde("1999-02-31"));

        assertNotNull(DatumElement.parseWaarde("0000"));
        assertNotNull(DatumElement.parseWaarde("0000-01"));
        assertNotNull(DatumElement.parseWaarde("0000-01-01"));
        assertNotNull(DatumElement.parseWaarde("0000-02-31"));
    }

    @Test(expected = NullPointerException.class)
    public void testParseWaardeNull() throws OngeldigeWaardeException {
        DatumElement.parseWaarde(null);
    }

    @Test(expected = OngeldigeWaardeException.class)
    public void testDagNul() throws OngeldigeWaardeException {
        DatumElement.parseWaarde("2016-12-00");
    }

    @Test(expected = OngeldigeWaardeException.class)
    public void testParseWaardeOngeldigDag() throws OngeldigeWaardeException {
        DatumElement.parseWaarde("1999-01-32");
    }

    @Test(expected = OngeldigeWaardeException.class)
    public void testParseWaardeOngeldigMaand() throws OngeldigeWaardeException {
        DatumElement.parseWaarde("1999-13-10");
    }

    @Test
    public void testParseWaardeEquals() throws OngeldigeWaardeException {
        final DatumElement d1 = DatumElement.parseWaarde("1999-01-02");
        final DatumElement d2 = DatumElement.parseWaarde("1999-01-02");
        final DatumElement d3 = DatumElement.parseWaarde("1999-01-03");
        assertEquals(d1, d2);
        assertNotEquals(d1, d3);
    }

    @Bedrijfsregel(Regel.R2547)
    @Test
    public void R2547_datum_volledig_bekend() throws OngeldigeWaardeException {
        // Via constructor
        assertFalse(new DatumElement(2016).isVolledigBekendeDatum());
        assertFalse(new DatumElement(2016_01).isVolledigBekendeDatum());
        assertTrue(new DatumElement(2016_01_01).isVolledigBekendeDatum());
        assertTrue(new DatumElement(2016_02_31).isVolledigBekendeDatum());

        // Via parseWaarde
        assertFalse(DatumElement.parseWaarde("2016").isVolledigBekendeDatum());
        assertFalse(DatumElement.parseWaarde("2016-01").isVolledigBekendeDatum());
        assertTrue(DatumElement.parseWaarde("2016-01-01").isVolledigBekendeDatum());
        assertTrue(DatumElement.parseWaarde("2016-02-31").isVolledigBekendeDatum());
    }

    @Bedrijfsregel(Regel.R1274)
    @Test
    public void R1274_Datum_volledig_onbekend() {
        verwachtMelding(new DatumElement(0), null);
    }

    @Bedrijfsregel(Regel.R1274)
    @Test
    public void R1274_Datum_ongeldig() throws OngeldigeWaardeException {
        verwachtMelding(new DatumElement(2017_02_31), Regel.R1274);
        verwachtMelding(DatumElement.parseWaarde("0000-01-01"), Regel.R1274);
        verwachtMelding(DatumElement.parseWaarde("2016-02-31"), Regel.R1274);
    }

    @Bedrijfsregel(Regel.R1274)
    @Test
    public void R1274_Datum_geldig() throws OngeldigeWaardeException {
        verwachtMelding(new DatumElement(2016_01_01), null);
        verwachtMelding(DatumElement.parseWaarde("2016-01-01"), null);
    }

    private void verwachtMelding(final DatumElement datumElement, final Regel regel) {
        final GeboorteElement geboorteElement =
                new ElementBuilder().maakGeboorteElement("CI_geboorte_p2",
                        new ElementBuilder.GeboorteParameters().datum(1985_09_19).buitenlandsePlaats("Maceio").landGebiedCode("6008"));

        List<MeldingElement> meldingen = datumElement.valideer(geboorteElement);
        if (regel == null) {
            assertEquals(0, meldingen.size());
        } else {
            assertEquals(1, meldingen.size());
            assertEquals(regel, meldingen.get(0).getRegel());
            assertEquals(meldingen.get(0).getReferentie().getCommunicatieId(), geboorteElement.getCommunicatieId());
        }
    }

    @Test
    public void testIsDatumGeldigeKalenderDatum() {
        final DatumElement datumElement = new DatumElement(2012_01_01);
        assertTrue(datumElement.isGeldigeKalenderDatum());
        verwachtMelding(datumElement, null);
    }

    @Test
    public void testToString() throws OngeldigeWaardeException {
        assertEquals("0000", new DatumElement(0).toString());
        assertEquals("1999", new DatumElement(19990000).toString());
        assertEquals("1999", new DatumElement(19990025).toString());
        assertEquals("1999-12", new DatumElement(19991200).toString());
        assertEquals("1999-12-25", new DatumElement(19991225).toString());

        assertEquals("0000", DatumElement.parseWaarde("0000").toString());
        assertEquals("1999", DatumElement.parseWaarde("1999").toString());
        assertEquals("1999-12", DatumElement.parseWaarde("1999-12").toString());
        assertEquals("1999-12-25", DatumElement.parseWaarde("1999-12-25").toString());
    }
}
