/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.relatie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRAL2111Test {

    @Test
    public void testGetRegel() {
        final BRAL2111 bral2111 = new BRAL2111();

        Assert.assertEquals(Regel.BRAL2111, bral2111.getRegel());
    }

    @Test
    public void testHuwelijkTweePartners() {
        final int aantalPartners = 2;
        final BRAL2111 bral2111 = new BRAL2111();

        final List<BerichtEntiteit> resultaat = bral2111.voerRegelUit(null, bouwHuwelijk(aantalPartners), null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testHuwelijkEenPartner() {
        final int aantalPartners = 1;
        final BRAL2111 bral2111 = new BRAL2111();

        final List<BerichtEntiteit> resultaat = bral2111.voerRegelUit(null, bouwHuwelijk(aantalPartners), null, null);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof HuwelijkGeregistreerdPartnerschapBericht);
    }

    @Test
    public void testHuwelijkDriePartners() {
        final int aantalPartners = 3;
        final BRAL2111 bral2111 = new BRAL2111();

        final List<BerichtEntiteit> resultaat = bral2111.voerRegelUit(null, bouwHuwelijk(aantalPartners), null, null);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof HuwelijkGeregistreerdPartnerschapBericht);
    }

    @Test
    public void testGeregistreerdPartnerschapTweePartners() {
        final int aantalPartners = 2;
        final BRAL2111 bral2111 = new BRAL2111();

        final List<BerichtEntiteit> resultaat = bral2111.voerRegelUit(null, bouwGeregistreerdPartnerschap(
                aantalPartners),
                                                                null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testGeregistreerdPartnerschapEenPartner() {
        final int aantalPartners = 1;
        final BRAL2111 bral2111 = new BRAL2111();

        final List<BerichtEntiteit> resultaat = bral2111.voerRegelUit(null, bouwGeregistreerdPartnerschap(aantalPartners),
                                                                null, null);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof HuwelijkGeregistreerdPartnerschapBericht);
    }

    @Test
    public void testGeregistreerdPartnerschapDriePartners() {
        final int aantalPartners = 3;
        final BRAL2111 bral2111 = new BRAL2111();

        final List<BerichtEntiteit> resultaat = bral2111.voerRegelUit(null, bouwGeregistreerdPartnerschap(aantalPartners),
                                                                null, null);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof HuwelijkGeregistreerdPartnerschapBericht);
    }

    @Test
    public void testHuwelijkMetFoutieveSoort() {
        final int aantalPartners = 2;
        final BRAL2111 bral2111 = new BRAL2111();

        final List<BerichtEntiteit> resultaat = bral2111.voerRegelUit(null, bouwHuwelijkMetFoutieveSoort(aantalPartners),
                                                                      null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testHuwelijkMetFoutieveBetrokkenheid() {
        final int aantalPartners = 2;
        final BRAL2111 bral2111 = new BRAL2111();

        final List<BerichtEntiteit> resultaat = bral2111.voerRegelUit(null,
                                                                  bouwHuwelijkMetFoutieveBetrokkenheid(aantalPartners),
                                                                  null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Bouwt een huwelijk bericht.
     *
     * @param aantalPartnerBetrokkenheden aantal partner betrokkenheden
     * @return huwelijk bericht
     */
    private HuwelijkBericht bouwHuwelijk(final int aantalPartnerBetrokkenheden) {
        final HuwelijkBericht huwelijkBericht = new HuwelijkBericht();

        voegPartnerBetrokkenhedenToe(huwelijkBericht, aantalPartnerBetrokkenheden);

        return huwelijkBericht;
    }

    /**
     * Bouwt een geregistreerd partnerschap bericht.
     *
     * @param aantalPartnerBetrokkenheden aantal partner betrokkenheden
     * @return geregistreerd partnerschap bericht
     */
    private GeregistreerdPartnerschapBericht bouwGeregistreerdPartnerschap(final int aantalPartnerBetrokkenheden) {
        final GeregistreerdPartnerschapBericht geregistreerdPartnerschapBericht =
                new GeregistreerdPartnerschapBericht();

        voegPartnerBetrokkenhedenToe(geregistreerdPartnerschapBericht, aantalPartnerBetrokkenheden);

        return geregistreerdPartnerschapBericht;
    }

    /**
     * Bouwt een huwelijk bericht met foutief soort relatie.
     *
     * @param aantalPartnerBetrokkenheden aantal partner betrokkenheden
     * @return huwelijk bericht
     */
    private HuwelijkBericht bouwHuwelijkMetFoutieveSoort(final int aantalPartnerBetrokkenheden) {
        final HuwelijkBericht huwelijkBericht = new HuwelijkBericht();
        ReflectionTestUtils.setField(huwelijkBericht, "soort", new SoortRelatieAttribuut(SoortRelatie.DUMMY));

        voegPartnerBetrokkenhedenToe(huwelijkBericht, aantalPartnerBetrokkenheden);

        return huwelijkBericht;
    }

    /**
     * Bouwt een huwelijk bericht met een extra foutieve betrokkenheid.
     *
     * @param aantalPartnerBetrokkenheden aantal partner betrokkenheden
     * @return huwelijk bericht
     */
    private HuwelijkBericht bouwHuwelijkMetFoutieveBetrokkenheid(final int aantalPartnerBetrokkenheden) {
        final HuwelijkBericht huwelijkBericht = new HuwelijkBericht();

        voegPartnerBetrokkenhedenToe(huwelijkBericht, aantalPartnerBetrokkenheden);

        // Voeg foutieve betrokkenheid toe (coverage)
        huwelijkBericht.getBetrokkenheden().add(new KindBericht());

        return huwelijkBericht;
    }

    /**
     * Voegt partner betrokkenheden toe aan de relatie.
     *
     * @param bericht relatie bericht
     * @param aantalPartnerBetrokkenheden aantal partner betrokkenheden
     */
    private void voegPartnerBetrokkenhedenToe(final HuwelijkGeregistreerdPartnerschapBericht bericht,
                                              final int aantalPartnerBetrokkenheden)
    {
        bericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        for (int i = 0; i < aantalPartnerBetrokkenheden; i++) {
            bericht.getBetrokkenheden().add(new PartnerBericht());
        }
    }
}
