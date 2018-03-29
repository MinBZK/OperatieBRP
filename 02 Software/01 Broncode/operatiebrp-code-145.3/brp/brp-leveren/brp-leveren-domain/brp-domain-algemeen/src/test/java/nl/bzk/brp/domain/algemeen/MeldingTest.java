/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import org.junit.Assert;
import org.junit.Test;

/**
 * MeldingTest.
 */
public class MeldingTest {

    @Test
    public void testEquals() {
        final Melding melding1 = new Melding(Regel.R1244);
        final Melding melding2 = new Melding(Regel.R1244);
        Assert.assertEquals(melding1, melding2);
        Assert.assertEquals(melding1, melding1);
        Assert.assertEquals(melding1.hashCode(), melding2.hashCode());
        final Melding melding3 = new Melding(SoortMelding.FOUT, Regel.R1244, "x");
        Assert.assertNotEquals(melding1, melding3);
        final Melding melding4 = new Melding(Regel.R1245);
        Assert.assertNotEquals(melding1, melding4);
        Assert.assertNotEquals(melding1, "ietsanders");
        final Melding melding5 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.R1244, "x");
        Assert.assertNotEquals(melding4, melding5);
        //null equals
        Assert.assertNotEquals(melding4, null);

        final Melding melding6 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.R1244, "y");
        final Melding melding7 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.R1244, "y");
        final Melding melding8 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.R1244, null);
        Assert.assertNotEquals(melding5, melding6);
        Assert.assertNotEquals(melding8, melding7);
        Assert.assertEquals(melding6, melding7);

        final Melding melding9 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.R1244, null);
        melding9.setReferentieID("referentieID_A");
        final Melding melding10 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.R1244, null);
        melding10.setReferentieID("referentieID_B");
        final Melding melding11 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.R1244, null);
        melding11.setReferentieID("referentieID_B");
        final Melding melding12 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.R1244, null);
        melding12.setReferentieID(null);
        Assert.assertNotEquals(melding9, melding10);
        Assert.assertEquals(melding10, melding11);
        Assert.assertNotEquals(melding9, melding12);
    }


    @Test
    public void testConstruct() {
        final String meldingTekst = "tekst";
        final Melding melding1 = new Melding(SoortMelding.FOUT, Regel.R1244, meldingTekst, meldingTekst);
        Assert.assertEquals(meldingTekst, melding1.getMeldingTekst());
        final String referentieId = "ref10";
        melding1.setReferentieID(referentieId);
        Assert.assertNotNull(melding1.getRegel());
        Assert.assertEquals(Regel.R1244, melding1.getRegel());
        Assert.assertEquals(meldingTekst, melding1.getMeldingTekst());
        Assert.assertEquals(referentieId, melding1.getReferentieID());
        Assert.assertEquals(SoortMelding.FOUT, melding1.getSoort());
    }

    @Test
    public void testConstructZonderMeldingsTekst() {
        final Melding melding1 = new Melding(Regel.R1244);
        Assert.assertEquals(Regel.R1244.getMelding(), melding1.getMeldingTekst());
    }
}
