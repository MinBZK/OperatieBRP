/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.GedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAdresBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresBericht;
import org.junit.Before;
import org.junit.Test;

public class GedeblokkeerdeMeldingenOverschotControleStapTest {

    private GedeblokkeerdeMeldingenOverschotControleStap stap;
    private BijhoudingsBericht bericht;
    private List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> gedeblokkeerdeMeldingen;

    @Before
    public void init() {
        this.stap = new GedeblokkeerdeMeldingenOverschotControleStap();
        this.bericht = new CorrigeerAdresBericht();
        AdministratieveHandelingBericht handeling = new HandelingCorrectieAdresBericht();
        this.gedeblokkeerdeMeldingen = new ArrayList<>();
        handeling.setGedeblokkeerdeMeldingen(this.gedeblokkeerdeMeldingen);
        this.bericht.getStandaard().setAdministratieveHandeling(handeling);
    }

    @Test
    public void testGeenOverschot() {
        final Resultaat resultaat = stap.voerStapUit(bericht);
        assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testWelOverschot() {
        // Given
        AdministratieveHandelingGedeblokkeerdeMeldingBericht administratieveHandelingGedeblokkeerdeMeldingBericht
                = new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        GedeblokkeerdeMeldingBericht gedeblokkeerdeMeldingBericht = new GedeblokkeerdeMeldingBericht();
        String communicatieId = "12345";
        gedeblokkeerdeMeldingBericht.setCommunicatieID(communicatieId);
        // Fictief, zomaar een regel als test.
        gedeblokkeerdeMeldingBericht.setRegel(new RegelAttribuut(Regel.BRAL0001));
        administratieveHandelingGedeblokkeerdeMeldingBericht.setGedeblokkeerdeMelding(gedeblokkeerdeMeldingBericht);
        this.gedeblokkeerdeMeldingen.add(administratieveHandelingGedeblokkeerdeMeldingBericht);

        // When
        final Resultaat resultaat = stap.voerStapUit(bericht);

        // Then
        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(communicatieId, resultaat.getMeldingen().iterator().next().getReferentieID());
    }

    @Test
    public void testNullVoorGedeblokkeerdeMeldingen() {
        bericht.getAdministratieveHandeling().setGedeblokkeerdeMeldingen(null);
        final Resultaat resultaat = stap.voerStapUit(bericht);
        assertTrue(resultaat.getMeldingen().isEmpty());
    }

}
