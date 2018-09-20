/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOuderBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningNaGeboorteBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BijhoudingBerichtVerplichteObjectenValidatieStapTest {

    @InjectMocks
    private BijhoudingBerichtVerplichteObjectenValidatieStap stap;

    @Test
    public void testVoerStapUit() {

    }

    @Test
    public void testBijhoudingsBerichtMetNullAlsActies() {
        final BijhoudingsBericht bericht = maakNieuwBericht();
        final Resultaat resultaat = stap.voerStapUit(bericht);
        assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBijhoudingsBerichtMetMetLegeActies() {
        final BijhoudingsBericht bericht = maakNieuwBericht();
        final Resultaat resultaat = stap.voerStapUit(bericht);

        assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBijhoudingsBerichtMetNullAlsRootObjectInActie() {
        final ActieBericht actie = new ActieRegistratieOuderBericht();

        final BijhoudingsBericht bericht = maakNieuwBericht(actie);

        final ActieBericht actieBericht = bericht.getAdministratieveHandeling().getActies().get(0);
        actieBericht.setRootObject(null);
        final Resultaat resultaat = stap.voerStapUit(bericht);

        assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBijhoudingsBerichtMetRootObjectsInActie() {
        final ActieBericht actie = new ActieRegistratieOuderBericht();

        final BijhoudingsBericht bericht = maakNieuwBericht(actie);
        final Resultaat resultaat = stap.voerStapUit(bericht);
        assertFalse(resultaat.getMeldingen().isEmpty());
    }

    /**
     * Instantieert een bericht met de opgegeven acties.
     *
     * @param acties de acties waaruit het bericht dient te bestaan.
     * @return een nieuw bericht met opgegeven acties.
     */
    private BijhoudingsBericht maakNieuwBericht(final ActieBericht... acties) {
        final BijhoudingsBericht bericht = new BijhoudingsBericht(null) {
        };
        if (acties == null) {
            //           bericht.setBrpActies(null);
        } else {
            bericht.getStandaard()
                .setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
            bericht.getAdministratieveHandeling().setActies(Arrays.asList(acties));
        }
        return bericht;
    }

    /**
     * Bouwt en retourneert een standaard {@link BerichtContext} instantie, met ingevulde in- en uitgaande bericht ids, een authenticatiemiddel id en een
     * partij.
     *
     * @return een geldig bericht context.
     */
    private BijhoudingBerichtContext bouwBerichtContext() {
        final BerichtenIds ids = new BerichtenIds(2L, 3L);
        return new BijhoudingBerichtContext(ids, Mockito.mock(Partij.class), "ref", null);
    }
}
