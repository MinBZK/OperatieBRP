/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.actie.BijhoudingRegelService;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.SoortFout;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.GedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAdresBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class GedeblokkeerdeMeldingenValidatieStapTest {

    private GedeblokkeerdeMeldingenValidatieStap                       stap;
    private BijhoudingsBericht                                         bericht;
    private List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> gedeblokkeerdeMeldingen;
    private BijhoudingRegelService                                     bijhoudingRegelService;

    @Before
    public void init() {
        this.stap = new GedeblokkeerdeMeldingenValidatieStap();
        bijhoudingRegelService = Mockito.mock(BijhoudingRegelService.class);
        ReflectionTestUtils.setField(stap, "bijhoudingRegelService", bijhoudingRegelService);
        when(bijhoudingRegelService.getRegelParametersVoorRegel(Regel.BRAL9003)).thenReturn(
            new RegelParameters(null, SoortMelding.DEBLOKKEERBAAR, Regel.BRAL9003, null));
        when(bijhoudingRegelService.getRegelParametersVoorRegel(Regel.BRAL0004)).thenReturn(
            new RegelParameters(null, SoortMelding.FOUT, Regel.BRAL0004, null, SoortFout.VERWERKING_KAN_DOORGAAN));
        when(bijhoudingRegelService.getRegelParametersVoorRegel(Regel.ALG0001)).thenReturn(null);
        this.bericht = new CorrigeerAdresBericht();
        AdministratieveHandelingBericht handeling = new HandelingCorrectieAdresBericht();
        this.gedeblokkeerdeMeldingen = new ArrayList<>();
        handeling.setGedeblokkeerdeMeldingen(this.gedeblokkeerdeMeldingen);
        this.bericht.getStandaard().setAdministratieveHandeling(handeling);
    }

    @Test
    public void testGeenGedeblokkeerdeMeldingen() {
        final Resultaat resultaat = stap.voerStapUit(bericht);

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testWelDeblokkeerbaar() {
        // Given
        AdministratieveHandelingGedeblokkeerdeMeldingBericht administratieveHandelingGedeblokkeerdeMeldingBericht
                = new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        GedeblokkeerdeMeldingBericht gedeblokkeerdeMeldingBericht = new GedeblokkeerdeMeldingBericht();
        gedeblokkeerdeMeldingBericht.setRegel(new RegelAttribuut(Regel.BRAL9003));
        administratieveHandelingGedeblokkeerdeMeldingBericht.setGedeblokkeerdeMelding(gedeblokkeerdeMeldingBericht);
        this.gedeblokkeerdeMeldingen.add(administratieveHandelingGedeblokkeerdeMeldingBericht);

        // When
        final Resultaat resultaat = stap.voerStapUit(bericht);

        // Then
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testNietDeblokkeerbaar() {
        // Given
        AdministratieveHandelingGedeblokkeerdeMeldingBericht administratieveHandelingGedeblokkeerdeMeldingBericht
                = new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        GedeblokkeerdeMeldingBericht gedeblokkeerdeMeldingBericht = new GedeblokkeerdeMeldingBericht();
        String communicatieId = "12345";
        gedeblokkeerdeMeldingBericht.setCommunicatieID(communicatieId);
        gedeblokkeerdeMeldingBericht.setRegel(new RegelAttribuut(Regel.BRAL0004));
        administratieveHandelingGedeblokkeerdeMeldingBericht.setGedeblokkeerdeMelding(gedeblokkeerdeMeldingBericht);
        this.gedeblokkeerdeMeldingen.add(administratieveHandelingGedeblokkeerdeMeldingBericht);

        // When
        final Resultaat resultaat = stap.voerStapUit(bericht);

        // Then
        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(communicatieId, resultaat.getMeldingen().iterator().next().getReferentieID());
    }

    @Test
    public void testGeenRegel() {
        // Given
        AdministratieveHandelingGedeblokkeerdeMeldingBericht administratieveHandelingGedeblokkeerdeMeldingBericht
                = new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        GedeblokkeerdeMeldingBericht gedeblokkeerdeMeldingBericht = new GedeblokkeerdeMeldingBericht();
        String communicatieId = "12345";
        gedeblokkeerdeMeldingBericht.setCommunicatieID(communicatieId);
        administratieveHandelingGedeblokkeerdeMeldingBericht.setGedeblokkeerdeMelding(gedeblokkeerdeMeldingBericht);
        this.gedeblokkeerdeMeldingen.add(administratieveHandelingGedeblokkeerdeMeldingBericht);

        // When
        final Resultaat resultaat = stap.voerStapUit(bericht);

        // Then
        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(communicatieId, resultaat.getMeldingen().iterator().next().getReferentieID());
    }

    @Test
    public void testGeenParameters() {
        // Given
        AdministratieveHandelingGedeblokkeerdeMeldingBericht administratieveHandelingGedeblokkeerdeMeldingBericht
                = new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        GedeblokkeerdeMeldingBericht gedeblokkeerdeMeldingBericht = new GedeblokkeerdeMeldingBericht();
        String communicatieId = "12345";
        gedeblokkeerdeMeldingBericht.setCommunicatieID(communicatieId);
        gedeblokkeerdeMeldingBericht.setRegel(new RegelAttribuut(Regel.ALG0001));
        administratieveHandelingGedeblokkeerdeMeldingBericht.setGedeblokkeerdeMelding(gedeblokkeerdeMeldingBericht);
        this.gedeblokkeerdeMeldingen.add(administratieveHandelingGedeblokkeerdeMeldingBericht);

        // When
        final Resultaat resultaat = stap.voerStapUit(bericht);

        // Then
        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(communicatieId, resultaat.getMeldingen().iterator().next().getReferentieID());
    }

}
