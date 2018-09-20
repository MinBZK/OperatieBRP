/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.business.toegangsbewaking.ToegangsBewakingService;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.SoortAbonnement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link FunctioneleAutorisatieStap} class.
 */
public class FunctioneleAutorisatieStapTest {

    @Mock
    private BerichtVerzoek<BerichtAntwoord>    berichtVerzoek;
    @Mock
    private BerichtAntwoord                berichtAntwoord;
    @Mock
    private ToegangsBewakingService    serviceMock;

    private FunctioneleAutorisatieStap stap;
    private BerichtContext          berichtContext;
    private Abonnement                 abonnement;

    /**
     * Unit test indien partij functioneel niet gerechtigd is.
     */
    @Test
    public void testNietFunctioneelGerechtigd() {
        Mockito.when(serviceMock.isFunctioneelGeautoriseerd(abonnement, berichtVerzoek)).thenReturn(false);

        boolean result = stap.voerVerwerkingsStapUitVoorBericht(berichtVerzoek, berichtContext, berichtAntwoord);

        Mockito.verify(serviceMock).isFunctioneelGeautoriseerd(abonnement, berichtVerzoek);
        Mockito.verify(berichtAntwoord).voegFoutToe(Matchers.isA(BerichtVerwerkingsFout.class));

        assertFalse(result);
    }

    /**
     * Unit test indien partij functioneel wel gerechtigd is.
     */
    @Test
    public void testWelFunctioneelGerechtigd() {
        Mockito.when(serviceMock.isFunctioneelGeautoriseerd(abonnement, berichtVerzoek)).thenReturn(true);

        boolean result = stap.voerVerwerkingsStapUitVoorBericht(berichtVerzoek, berichtContext, berichtAntwoord);

        Mockito.verify(serviceMock).isFunctioneelGeautoriseerd(abonnement, berichtVerzoek);
        Mockito.verify(berichtAntwoord, Mockito.never()).voegFoutToe(Matchers.isA(BerichtVerwerkingsFout.class));

        assertTrue(result);
    }

    /**
     * Initializeert de mocks die in deze unit test class worden gebruikt.
     */
    @Before
    public final void initMocksEnService() {
        MockitoAnnotations.initMocks(this);

        abonnement = new Abonnement(null, SoortAbonnement.LEVERING);
        berichtContext = new BerichtContext();
        berichtContext.setAbonnement(abonnement);

        stap = new FunctioneleAutorisatieStap();
        ReflectionTestUtils.setField(stap, "toegangsBewakingService", serviceMock);
    }

}
