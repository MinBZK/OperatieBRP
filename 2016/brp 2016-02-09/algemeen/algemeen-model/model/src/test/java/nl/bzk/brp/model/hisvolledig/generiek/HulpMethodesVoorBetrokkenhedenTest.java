/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.generiek;

import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class HulpMethodesVoorBetrokkenhedenTest {
    PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();

    @Test
    public void kanPartnerBetrokkehedenOphalen() {
        Assert.assertEquals(1, persoon.getPartnerBetrokkenheden().size());
    }

    @Test
    public void kanOuderBetrokkenhedenOphalen() {
        Assert.assertEquals(2, persoon.getOuderBetrokkenheden().size());
    }

    @Test
    public void kanKindBetrokkenheidOphalen() {
        Assert.assertNotNull(persoon.getKindBetrokkenheid());
    }

    @Test
    public void kanWederhelftUitHuwelijkOphalen() {
        PartnerHisVolledigImpl partnerHisVolledig = persoon.getPartnerBetrokkenheden().iterator().next();
        HuwelijkGeregistreerdPartnerschapHisVolledigImpl huwelijk = (HuwelijkGeregistreerdPartnerschapHisVolledigImpl) partnerHisVolledig.getRelatie();

        PartnerHisVolledigImpl wederhelft = huwelijk.geefPartnerVan(partnerHisVolledig);
        Assert.assertNotNull(wederhelft);
        Assert.assertNotEquals(partnerHisVolledig, wederhelft);
    }

}
