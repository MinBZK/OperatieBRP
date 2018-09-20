/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import static org.junit.Assert.assertEquals;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.apache.commons.collections.functors.TruePredicate;
import org.junit.Test;

public class PersoonHisVolledigViewVerwerkingssoortTest {

    private PersoonHisVolledigImpl testPersoon     = TestPersoonJohnnyJordaan.maak();
    private PersoonHisVolledigView testPersoonView = new PersoonHisVolledigView(testPersoon, TruePredicate.getInstance());

    @Test
    public void testVerwerkingssoortOpBetrokkenheidBlijftBestaanVanwegeCaching() {
        for (BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : testPersoonView.getBetrokkenheden()) {
            betrokkenheidHisVolledigView.setVerwerkingssoort(Verwerkingssoort.WIJZIGING);
        }

        for (BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : testPersoonView.getBetrokkenheden()) {
            assertEquals(Verwerkingssoort.WIJZIGING, betrokkenheidHisVolledigView.getVerwerkingssoort());
        }
    }

}
