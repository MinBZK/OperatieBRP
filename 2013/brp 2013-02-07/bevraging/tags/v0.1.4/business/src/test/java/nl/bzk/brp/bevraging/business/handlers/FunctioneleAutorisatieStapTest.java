/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
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
    private BrpBerichtCommand          berichtMock;
    @Mock
    private ToegangsBewakingService    serviceMock;

    private FunctioneleAutorisatieStap stap;
    private Abonnement                 abonnement;

    /**
     * Unit test indien partij functioneel niet gerechtigd is.
     */
    @Test
    public void testNietFunctioneelGerechtigd() {
        Mockito.when(serviceMock.isFunctioneelGeautoriseerd(abonnement, berichtMock)).thenReturn(false);

        boolean result = stap.voerVerwerkingsStapUitVoorBericht(berichtMock);

        Mockito.verify(serviceMock).isFunctioneelGeautoriseerd(abonnement, berichtMock);
        Mockito.verify(berichtMock).voegFoutToe(Matchers.isA(BerichtVerwerkingsFout.class));

        assertFalse(result);
    }

    /**
     * Unit test indien partij functioneel wel gerechtigd is.
     */
    @Test
    public void testWelFunctioneelGerechtigd() {
        Mockito.when(serviceMock.isFunctioneelGeautoriseerd(abonnement, berichtMock)).thenReturn(true);

        boolean result = stap.voerVerwerkingsStapUitVoorBericht(berichtMock);

        Mockito.verify(serviceMock).isFunctioneelGeautoriseerd(abonnement, berichtMock);
        Mockito.verify(berichtMock, Mockito.never()).voegFoutToe(Matchers.isA(BerichtVerwerkingsFout.class));

        assertTrue(result);
    }

    /**
     * Initializeert de mocks die in deze unit test class worden gebruikt.
     */
    @Before
    public final void initMocksEnService() {
        MockitoAnnotations.initMocks(this);

        BrpBerichtContext context = new BrpBerichtContext();
        abonnement = new Abonnement(null, SoortAbonnement.LEVERING);
        context.setAbonnement(abonnement);

        Mockito.when(berichtMock.getContext()).thenReturn(context);

        stap = new FunctioneleAutorisatieStap();
        ReflectionTestUtils.setField(stap, "toegangsBewakingService", serviceMock);
    }

}
