/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.algemeen;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRAL0202Test {

    private BRAL0202 bral0202;

    @Before
    public void init() {
        bral0202 = new BRAL0202();
    }

    @Test
    public void test2VerschillendePersonenInHuwelijk() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
            voegPartnerToe(PersoonBuilder.bouwPersoon("123", null, 19840404, null, null, null, null)).
            voegPartnerToe(PersoonBuilder.bouwPersoon("45678", null, 19850404, null, null, null, null)).
            setDatumAanvang(20010404).
            getRelatie();

        List<Melding> meldingen = bral0202.executeer(null, huwelijk, null);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void test1Persoon2MaalInHuwelijk() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
            voegPartnerToe(PersoonBuilder.bouwPersoon("123", null, 19840404, null, null, null, null)).
            voegPartnerToe(PersoonBuilder.bouwPersoon("123", null, 19850404, null, null, null, null)).
            setDatumAanvang(20010404).
            getRelatie();

        List<Melding> meldingen = bral0202.executeer(null, huwelijk, null);
        Assert.assertTrue(meldingen.size() == 1);
        Melding melding = meldingen.get(0);
        Assert.assertEquals(MeldingCode.BRAL0202, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT, melding.getSoort());
        Assert.assertEquals("Een persoon mag een keer voorkomen in een relatie.", melding.getOmschrijving());
    }

    @Test
    public void testFamilieRechtelijkeBetrekking() {
        RelatieBericht familie = new RelatieBuilder().bouwFamilieRechtelijkeBetrekkingRelatie().
            voegOuderToe(PersoonBuilder.bouwPersoon("1212", null, 19840404, null, null, null, null)).
            voegOuderToe(PersoonBuilder.bouwPersoon("3232", null, 19850404, null, null, null, null)).
            voegKindToe(PersoonBuilder.bouwPersoon("4567", null, 19850404, null, null, null, null)).
            getRelatie();

        List<Melding> meldingen = bral0202.executeer(null, familie, null);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void test1Persoon2MaalInFamilieRechtelijkeBetrekking() {
        RelatieBericht familie = new RelatieBuilder().bouwFamilieRechtelijkeBetrekkingRelatie().
            voegOuderToe(PersoonBuilder.bouwPersoon("1212", null, 19840404, null, null, null, null)).
            voegOuderToe(PersoonBuilder.bouwPersoon("3232", null, 19850404, null, null, null, null)).
            voegKindToe(PersoonBuilder.bouwPersoon("1212", null, 19850404, null, null, null, null)).
            getRelatie();

        List<Melding> meldingen = bral0202.executeer(null, familie, null);
        Assert.assertTrue(meldingen.size() == 1);
        Melding melding = meldingen.get(0);
        Assert.assertEquals(MeldingCode.BRAL0202, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT, melding.getSoort());
        Assert.assertEquals("Een persoon mag een keer voorkomen in een relatie.", melding.getOmschrijving());
    }
}
