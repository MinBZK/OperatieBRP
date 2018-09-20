/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie;

import junit.framework.Assert;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import org.junit.Test;

/**
 * Unit test klasse voor de {@link Melding} klasse.
 */
public class MeldingTest {

    @Test
    public void testConstructorZonderOmschrijving() {
        Melding melding = new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.ALG0001);
        Assert.assertEquals(SoortMelding.OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, melding.getCode());
        Assert.assertEquals(MeldingCode.ALG0001.getOmschrijving(), melding.getOmschrijving());
    }

    @Test
    public void testConstructorMetOmschrijving() {
        Melding melding = new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.ALG0001, "Test");
        Assert.assertEquals(SoortMelding.OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, melding.getCode());
        Assert.assertEquals("Test", melding.getOmschrijving());
    }

    @Test
    public void testZettenOmschrijving() {
        Melding melding =  new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.ALG0001, "Test");
        melding.setOmschrijving("Andere Inhoud");
        Assert.assertEquals("Andere Inhoud", melding.getOmschrijving());
    }

    @Test
    public void testCompareTo() {
        Melding melding1 = new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.ALG0001, "Test1");
        Melding melding2 = new Melding(SoortMelding.FOUT, MeldingCode.ALG0001, "Test2");
        Melding melding3 = new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.ALG0001, "Test3");
        Melding melding4 = new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.BRAL0001, "Test4");


        Assert.assertEquals(1, melding1.compareTo(melding2));
        Assert.assertEquals(-1, melding2.compareTo(melding1));
        Assert.assertEquals(0, melding1.compareTo(melding3));
        Assert.assertEquals(-1, melding3.compareTo(melding4));
        Assert.assertEquals(1, melding4.compareTo(melding3));
    }
}
