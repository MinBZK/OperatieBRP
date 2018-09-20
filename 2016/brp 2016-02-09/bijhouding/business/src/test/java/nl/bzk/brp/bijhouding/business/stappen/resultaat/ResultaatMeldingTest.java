/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.resultaat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import org.junit.Test;

public class ResultaatMeldingTest    {

    private static final ResultaatMelding MELDING = ResultaatMelding.builder()
            .withAttribuutNaam("MARSMAN")
            .withMeldingTekst("Denkend aan Holland ...")
            .withReferentieID("abc")
            .build();

    @Test
    public void testIsVerwerkingStoppend() {
        // Alleen DEBLOKKEERBAAR en FOUT zijn verwerking stoppend, ....
        assertTrue(ResultaatMelding.builder().withSoort(SoortMelding.DEBLOKKEERBAAR).build().isVerwerkingStoppend());
        assertTrue(ResultaatMelding.builder().withSoort(SoortMelding.FOUT).build().isVerwerkingStoppend());

        // ... de rest is dat niet.
        assertFalse(ResultaatMelding.builder().withSoort(SoortMelding.DUMMY).build().isVerwerkingStoppend());
        assertFalse(ResultaatMelding.builder().withSoort(SoortMelding.GEEN).build().isVerwerkingStoppend());
        assertFalse(ResultaatMelding.builder().withSoort(SoortMelding.INFORMATIE).build().isVerwerkingStoppend());
        assertFalse(ResultaatMelding.builder().withSoort(SoortMelding.WAARSCHUWING).build().isVerwerkingStoppend());
    }

    @Test
    public void testCompareTo() {
        ResultaatMelding twaalf = ResultaatMelding.builder().withRegel(Regel.BRAL0012).build();
        ResultaatMelding dertien = ResultaatMelding.builder().withRegel(Regel.BRAL0013).withMeldingTekst(null).build();
        ResultaatMelding dertien_a = ResultaatMelding.builder().withRegel(Regel.BRAL0013).withMeldingTekst("A").build();
        ResultaatMelding dertien_b = ResultaatMelding.builder().withRegel(Regel.BRAL0013).withMeldingTekst("B").build();

        assertTrue(twaalf.compareTo(dertien) < 0);
        assertTrue(dertien_a.compareTo(dertien_b) < 0);
        assertTrue(dertien.compareTo(dertien_a) < 0);
        assertTrue(dertien_a.compareTo(dertien) > 0);
        assertTrue(dertien.compareTo(dertien) == 0);
    }

    @Test
    public void testBuilderBuildIndienLeeg() {
        final ResultaatMelding melding = ResultaatMelding.builder().build();
        assertEquals(Regel.ALG0001, melding.getRegel());
        assertEquals(SoortMelding.FOUT, melding.getSoort());
        assertEquals("Onbekende fout opgetreden", melding.getMeldingTekst());
        assertEquals("onbekend", melding.getReferentieID());
        assertNull(melding.getAttribuutNaam());
    }

    @Test
    public void testBuilderBuildIndienKopie() {

        final ResultaatMelding kopieMelding = ResultaatMelding.builder(MELDING).build();
        assertNotSame(MELDING, kopieMelding);
        assertEquals(MELDING, kopieMelding);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderBuildMetRegelNull() {
        ResultaatMelding.builder().withRegel(null).build();
    }

    @Test
    public void testBuilderBuildMetReferentieIdNull() {
        final ResultaatMelding melding = ResultaatMelding.builder().withReferentieID(null).build();
        assertEquals("onbekend", melding.getReferentieID());
    }

    @Test
    public void testDeblokkeer() {
        final ResultaatMelding melding = ResultaatMelding
            .builder(MELDING)
            .withSoort(SoortMelding.DEBLOKKEERBAAR)
            .build();
        final ResultaatMelding gedeblokkeerd = ResultaatMelding.builder(melding)
            .withSoort(SoortMelding.WAARSCHUWING).build();
        assertEquals(gedeblokkeerd, melding.deblokkeer());
    }

    @Test(expected = IllegalStateException.class)
    public void testDeblokkeerWanneerNietDeblokkeerbaar() {
        final ResultaatMelding melding = ResultaatMelding.builder(MELDING)
            .withSoort(SoortMelding.WAARSCHUWING)
            .build();
        melding.deblokkeer();
    }

}
