/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;

/**
 * BijgehoudenPersoonTest.
 */
public class BijgehoudenPersoonTest {

    @Test
    public void testBijgehoudenPersoon() {
        final Persoonslijst persoonsLijst = TestBuilders.maakPersoonMetHandelingen(1);
        final BerichtElement berichtElement = BerichtElement.builder().build();
        final BijgehoudenPersoon.Builder builder = new BijgehoudenPersoon.Builder(persoonsLijst, berichtElement);
        final Integer communicatieId = Integer.valueOf(1);
        builder.metCommunicatieId(communicatieId);
        final boolean volledigBericht = false;
        builder.metVolledigBericht(volledigBericht);

        final BijgehoudenPersoon bijgehoudenPersoon = builder.build();
        Assert.assertEquals(berichtElement, bijgehoudenPersoon.getBerichtElement());
        Assert.assertEquals(persoonsLijst, bijgehoudenPersoon.getPersoonslijst());
        Assert.assertEquals(volledigBericht, bijgehoudenPersoon.isVolledigBericht());
        Assert.assertEquals(communicatieId, bijgehoudenPersoon.getCommunicatieId());

    }
}
