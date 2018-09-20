/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.List;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.DatumUtil;
import nl.bzk.brp.util.RelatieBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRBY0438Test {

    private BRBY0438 brby0438;

    @Before
    public void init() {
        brby0438 = new BRBY0438();
    }

    @Test
    public void testDatumHuwelijkLigtInVerleden() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().setDatumAanvang(20080404).getRelatie();
        final List<Melding> meldingen = brby0438.executeer(null, huwelijk, null);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testDatumHuwelijkIsVandaag() {
        RelatieBericht huwelijk = new RelatieBuilder().
                bouwHuwlijkRelatie().setDatumAanvang(DatumUtil.vandaag().getWaarde()).getRelatie();
        final List<Melding> meldingen = brby0438.executeer(null, huwelijk, null);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testDatumHuwelijkLigtInToekomst() {
        final Datum vandaag = DatumUtil.vandaag();
        int vandaagInt = vandaag.getWaarde();
        vandaagInt++;
        RelatieBericht huwelijk = new RelatieBuilder().
                bouwHuwlijkRelatie().setDatumAanvang(vandaagInt).getRelatie();
        final List<Melding> meldingen = brby0438.executeer(null, huwelijk, null);
        Assert.assertTrue(meldingen.size() == 1);
        Melding melding = meldingen.get(0);
        Assert.assertEquals(MeldingCode.BRBY0438, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals("Datum aanvang huwelijk mag niet in de toekomst liggen.", melding.getOmschrijving());
    }
}
