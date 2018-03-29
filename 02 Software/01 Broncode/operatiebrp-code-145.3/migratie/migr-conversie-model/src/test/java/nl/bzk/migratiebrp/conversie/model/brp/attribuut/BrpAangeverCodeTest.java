/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Assert;
import org.junit.Test;

public class BrpAangeverCodeTest {

    @Test
    public void testZonderOnderzoek() {
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), new Lo3Datum(0), null);
        final BrpAangeverCode aangeverCode = new BrpAangeverCode('C', onderzoek);
        final BrpAangeverCode aangeverCodeZonderOnderzoek = aangeverCode.verwijderOnderzoek();
        Assert.assertNotNull(aangeverCodeZonderOnderzoek);
        Assert.assertFalse(aangeverCode.equals(aangeverCodeZonderOnderzoek));
        Assert.assertEquals(aangeverCode.getWaarde(), aangeverCodeZonderOnderzoek.getWaarde());
        Assert.assertNull(aangeverCodeZonderOnderzoek.getOnderzoek());
    }

    @Test
    public void testWrapWelWaardeGeenOnderzoek() {
        final String waarde = "Test";
        final BrpString result = BrpString.wrap(waarde, null);
        Assert.assertNotNull(result);
        Assert.assertEquals(waarde, result.getWaarde());
        Assert.assertNull(result.getOnderzoek());
    }

    @Test
    public void testWrapGeenWaardeWelOnderzoek() {
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), new Lo3Datum(0), null);
        final BrpString result = BrpString.wrap(null, onderzoek);
        Assert.assertNotNull(result);
        Assert.assertNull(result.getWaarde());
        Assert.assertEquals(onderzoek, result.getOnderzoek());
    }

    @Test
    public void testWrapMetWaardeEnOnderzoek() {
        final String waarde = "Test";
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer("010000", null), new Lo3Datum(0), null);
        final BrpString result = BrpString.wrap(waarde, onderzoek);
        Assert.assertNotNull(result);
        Assert.assertEquals(waarde, result.getWaarde());
        Assert.assertEquals(onderzoek, result.getOnderzoek());
    }

    @Test(expected = NullPointerException.class)
    public void createBrpAangeverCodeZonderWaardeEnZonderOnderzoek() {
        new BrpAangeverCode(null, null);
    }

    @Test
    public void verwijderOnderzoekMetWaardeAanwezig() {
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), new Lo3Datum(0), null);
        BrpAangeverCode brp = new BrpAangeverCode('A', onderzoek);
        Assert.assertNotNull(brp.getOnderzoek());
        brp = brp.verwijderOnderzoek();
        Assert.assertNull(brp.getOnderzoek());
    }

    @Test
    public void verwijderOnderzoekZonder() {
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), new Lo3Datum(0), null);
        final BrpAangeverCode brp = new BrpAangeverCode(null, onderzoek);
        Assert.assertNotNull(brp.getOnderzoek());
        Assert.assertNull(brp.verwijderOnderzoek());
    }

}
