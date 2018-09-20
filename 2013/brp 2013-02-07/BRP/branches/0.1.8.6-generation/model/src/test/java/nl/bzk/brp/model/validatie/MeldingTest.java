/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie;

import junit.framework.Assert;
import org.junit.Test;


/**
 * Unit test klasse voor de {@link Melding} klasse.
 */
public class MeldingTest {

    @Test
    public void testConstructorZonderOmschrijving() {
        Melding melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001);
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, melding.getCode());
        Assert.assertEquals(MeldingCode.ALG0001.getOmschrijving(), melding.getOmschrijving());
    }

    @Test
    public void testConstructorMetOmschrijving() {
        Melding melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001, "Test");
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, melding.getCode());
        Assert.assertEquals("Test", melding.getOmschrijving());
    }

    @Test
    public void testZettenOmschrijving() {
        Melding melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001, "Test");
        melding.setOmschrijving("Andere Inhoud");
        Assert.assertEquals("Andere Inhoud", melding.getOmschrijving());
    }
}
