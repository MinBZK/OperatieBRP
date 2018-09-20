/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import nl.bzk.brp.bevraging.business.berichtcmds.BerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.domein.ber.SoortBericht;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link BerichtUitvoerStap} class.
 */
public class BerichtUitvoerStapTest {

    @Mock
    private BerichtVerzoek<BerichtAntwoord>                                  verzoek;
    @Mock
    private BerichtContext                                                   context;
    @Mock
    private BerichtAntwoord                                                  antwoord;
    @Mock
    private BerichtCommand<BerichtVerzoek<BerichtAntwoord>, BerichtAntwoord> command;
    @Mock
    private ApplicationContext                                               applicationContext;

    /**
     * Unit test voor de {@link BerichtUitvoerStap#voerVerwerkingsStapUitVoorBericht(BerichtCommand)} methode.
     */
    @Test
    public void testVoerVerwerkingsStapUitVoorBericht() {
        BerichtUitvoerStap berichtUitvoerStap = new BerichtUitvoerStap();
        ReflectionTestUtils.setField(berichtUitvoerStap, "applicationContext", applicationContext);

        berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(verzoek, context, antwoord);
        Mockito.verify(command).voerUit(antwoord);
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(verzoek.getSoortBericht()).thenReturn(SoortBericht.OPVRAGENPERSOONVRAAG);
        Mockito.when(applicationContext.getBean(Matchers.anyString(), Matchers.any(), Matchers.any())).thenReturn(
                command);
    }
}
