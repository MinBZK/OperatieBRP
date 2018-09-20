/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 */
public class PersoonHisVolledigUtilTest {

    @Test
    public void testGetHGPs() throws Exception {
        final PersoonHisVolledigImpl partner1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        final PersoonHisVolledigImpl partner2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        final HuwelijkHisVolledigImpl huwelijk =
            new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(20120101).eindeRecord().build();

        final PartnerHisVolledigImpl partner1Betr = new PartnerHisVolledigImpl(huwelijk, partner1);
        final PartnerHisVolledigImpl partner2Betr = new PartnerHisVolledigImpl(huwelijk, partner2);

        huwelijk.getBetrokkenheden().add(partner1Betr);
        huwelijk.getBetrokkenheden().add(partner2Betr);

        partner1.getBetrokkenheden().add(partner1Betr);

        final List<HuwelijkGeregistreerdPartnerschapHisVolledig> hgPs = PersoonHisVolledigUtil.getHGPs(partner1);
        Assert.assertFalse(hgPs.isEmpty());
        assertEquals(huwelijk, hgPs.get(0));
    }

    @Test
    public void testGetOuderBetrokkenheden() throws Exception {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               19900909), null, new DatumTijdAttribuut(new Date()), null);

        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind, actie);

        final List<OuderHisVolledig> ouderBetrokkenhedenOuder = PersoonHisVolledigUtil.getOuderBetrokkenheden(ouder1);
        assertEquals(1, ouderBetrokkenhedenOuder.size());

        final List<OuderHisVolledig> ouderBetrokkenhedenKind = PersoonHisVolledigUtil.getOuderBetrokkenheden(kind);
        Assert.assertTrue(ouderBetrokkenhedenKind.isEmpty());

    }

    @Test
    public void testGetActueleHGPs() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();

        final List<HuwelijkGeregistreerdPartnerschapHisVolledig> resultaat = PersoonHisVolledigUtil.getActueleHGPs(persoonHisVolledig);

        assertEquals(1, resultaat.size());
    }

    @Test
    public void testGetPartnerPersoon() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        HuwelijkGeregistreerdPartnerschapHisVolledig huwelijk = null;
        for (final BetrokkenheidHisVolledigImpl betrokkenheid : persoonHisVolledig.getBetrokkenheden()) {
            if (betrokkenheid.getRelatie() instanceof HuwelijkGeregistreerdPartnerschapHisVolledig) {
                huwelijk = (HuwelijkGeregistreerdPartnerschapHisVolledig) betrokkenheid.getRelatie();
            }
        }
        PersoonHisVolledig partnerPersoon = null;
        for (final BetrokkenheidHisVolledig betrokkenheid : huwelijk.getBetrokkenheden()) {
            if (betrokkenheid.getPersoon() != persoonHisVolledig) {
                partnerPersoon = betrokkenheid.getPersoon();
            }
        }

        final PersoonHisVolledig resultaat = PersoonHisVolledigUtil.getPartnerPersoon(persoonHisVolledig,
            huwelijk);

        assertEquals(partnerPersoon, resultaat);
    }

}
