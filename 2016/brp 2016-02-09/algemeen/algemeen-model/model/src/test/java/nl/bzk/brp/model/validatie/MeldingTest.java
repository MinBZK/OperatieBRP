/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test klasse voor de {@link Melding} klasse.
 */
public class MeldingTest {

    @Test
    public void testConstructorZonderOmschrijving() {
        Melding melding = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001);
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR, melding.getSoort());
        Assert.assertEquals(Regel.ALG0001, melding.getRegel());
        Assert.assertEquals(Regel.ALG0001.getOmschrijving(), melding.getMeldingTekst());
    }

    @Test
    public void testConstructorMetOmschrijving() {
        Melding melding = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, "Test");
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR, melding.getSoort());
        Assert.assertEquals(Regel.ALG0001, melding.getRegel());
        Assert.assertEquals("Test", melding.getMeldingTekst());
    }

    @Test
    public void testZettenOmschrijving() {
        Melding melding = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, "Test");
        melding.setMeldingTekst("Andere Inhoud");
        Assert.assertEquals("Andere Inhoud", melding.getMeldingTekst());
    }

    @Test
    public void testCompareTo() {
        Melding melding1 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, "Test1");
        Melding melding2 = new Melding(SoortMelding.FOUT, Regel.ALG0001, "Test2");
        Melding melding3 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, "Test3");
        Melding melding4 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.BRAL0001, "Test4");

        Assert.assertEquals(1, melding1.compareTo(melding2));
        Assert.assertEquals(-1, melding2.compareTo(melding1));
        Assert.assertEquals(0, melding1.compareTo(melding3));
        Assert.assertEquals(-1, melding3.compareTo(melding4));
        Assert.assertEquals(1, melding4.compareTo(melding3));
    }

    @Test
    public void zouAlsGelijkMoetenVergelijkenOmdatReferentieIDEnAttribuutnaamNullZijn() {
        Melding melding1 = new Melding(SoortMelding.FOUT, Regel.ALG0001);
        Melding melding2 = new Melding(SoortMelding.FOUT, Regel.ALG0001);
        Assert.assertNull(melding1.getAttribuutNaam());
        Assert.assertNull(melding2.getAttribuutNaam());
        Assert.assertNull(melding1.getReferentieID());
        Assert.assertNull(melding1.getReferentieID());
        Assert.assertEquals(0, melding1.compareTo(melding2));
    }

    @Test
    public void zouNietNullReferentieIDBovenNullReferentieIDMoetenSorteren() {
        Melding melding1 = new Melding(SoortMelding.FOUT, Regel.ALG0001);
        Melding melding2 = new Melding(SoortMelding.FOUT, Regel.ALG0001);
        melding1.setReferentieID("a");
        Assert.assertNull(melding1.getAttribuutNaam());
        Assert.assertNull(melding2.getAttribuutNaam());
        Assert.assertNotNull(melding1.getReferentieID());
        Assert.assertNull(melding2.getReferentieID());
        Assert.assertEquals(1, melding1.compareTo(melding2));
        melding1.setReferentieID(null);
        melding2.setReferentieID("a");
        Assert.assertEquals(-1, melding1.compareTo(melding2));
    }

    @Test
    public void zouNietNullAttribuutNaamBovenNullAttribuutNaamMoetenSorterenAlsReferentieIDsNull() {
        Melding melding1 = new Melding(SoortMelding.FOUT, Regel.ALG0001);
        Melding melding2 = new Melding(SoortMelding.FOUT, Regel.ALG0001);
        melding1.setAttribuutNaam("a");
        Assert.assertNotNull(melding1.getAttribuutNaam());
        Assert.assertNull(melding2.getAttribuutNaam());
        Assert.assertNull(melding1.getReferentieID());
        Assert.assertNull(melding2.getReferentieID());
        Assert.assertEquals(1, melding1.compareTo(melding2));
        melding1.setAttribuutNaam(null);
        melding2.setAttribuutNaam("1");
        Assert.assertEquals(-1, melding1.compareTo(melding2));
    }

}
