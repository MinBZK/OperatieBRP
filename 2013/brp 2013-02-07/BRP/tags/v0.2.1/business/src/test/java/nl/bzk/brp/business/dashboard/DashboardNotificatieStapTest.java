/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dashboard;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class DashboardNotificatieStapTest {

    @Mock
    private VerhuizingBericht     verhuisBericht;

    @Mock
    private BerichtContext        context;

    @Mock
    private BerichtResultaat      resultaat;

    @Mock
    private VerhuizingNotificator verhuizingNotificator;

    @Mock
    private GeboorteNotificator   geboorteNotificator;

    @Before
    public void init() throws URISyntaxException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVoorverwerking() {
        DashboardNotificatieStap stap = new DashboardNotificatieStap();
        List<AbstractDashboardNotificator<?, ?>> notificators = new ArrayList<AbstractDashboardNotificator<?, ?>>();
        notificators.add(geboorteNotificator);
        notificators.add(verhuizingNotificator);
        stap.setNotificators(notificators);
        stap.setActief(true);

        Mockito.when(geboorteNotificator.kanVerwerken(verhuisBericht)).thenReturn(Boolean.FALSE);
        Mockito.when(verhuizingNotificator.kanVerwerken(verhuisBericht)).thenReturn(Boolean.TRUE);

        // test
        stap.voerVerwerkingsStapUitVoorBericht(verhuisBericht, context, resultaat);

        ArgumentCaptor<BijhoudingsBericht> berichtArgument = ArgumentCaptor.forClass(BijhoudingsBericht.class);

        ArgumentCaptor<BerichtContext> contextArgument = ArgumentCaptor.forClass(BerichtContext.class);

        Mockito.verify(geboorteNotificator, Mockito.times(0)).voorbereiden(berichtArgument.capture(),
                contextArgument.capture());

        Mockito.verify(verhuizingNotificator).voorbereiden(berichtArgument.capture(), contextArgument.capture());

        Assert.assertSame(verhuisBericht, berichtArgument.getValue());
        Assert.assertSame(context, contextArgument.getValue());
    }

    @Test
    public void testGeboorteBericht() {
        DashboardNotificatieStap stap = new DashboardNotificatieStap();
        List<AbstractDashboardNotificator<?, ?>> notificators = new ArrayList<AbstractDashboardNotificator<?, ?>>();
        notificators.add(geboorteNotificator);
        notificators.add(verhuizingNotificator);
        stap.setNotificators(notificators);
        stap.setActief(true);

        Mockito.when(geboorteNotificator.kanVerwerken(verhuisBericht)).thenReturn(Boolean.TRUE);
        Mockito.when(verhuizingNotificator.kanVerwerken(verhuisBericht)).thenReturn(Boolean.FALSE);

        // test
        stap.naVerwerkingsStapVoorBericht(verhuisBericht, context, resultaat);

        ArgumentCaptor<BijhoudingsBericht> berichtArgument = ArgumentCaptor.forClass(BijhoudingsBericht.class);

        ArgumentCaptor<BerichtContext> contextArgument = ArgumentCaptor.forClass(BerichtContext.class);

        ArgumentCaptor<BerichtResultaat> resultaatArgument = ArgumentCaptor.forClass(BerichtResultaat.class);

        Mockito.verify(geboorteNotificator).notificeerDashboard(berichtArgument.capture(), contextArgument.capture(),
                resultaatArgument.capture());

        Mockito.verify(verhuizingNotificator, Mockito.times(0)).notificeerDashboard(berichtArgument.capture(),
                contextArgument.capture(), resultaatArgument.capture());

        Assert.assertSame(verhuisBericht, berichtArgument.getValue());
        Assert.assertSame(context, contextArgument.getValue());
        Assert.assertSame(resultaat, resultaatArgument.getValue());
    }

    @Test
    public void testVerhuisBericht() {
        DashboardNotificatieStap stap = new DashboardNotificatieStap();
        List<AbstractDashboardNotificator<?, ?>> notificators = new ArrayList<AbstractDashboardNotificator<?, ?>>();
        notificators.add(geboorteNotificator);
        notificators.add(verhuizingNotificator);
        stap.setNotificators(notificators);
        stap.setActief(true);

        Mockito.when(geboorteNotificator.kanVerwerken(verhuisBericht)).thenReturn(Boolean.FALSE);
        Mockito.when(verhuizingNotificator.kanVerwerken(verhuisBericht)).thenReturn(Boolean.TRUE);

        // test
        stap.naVerwerkingsStapVoorBericht(verhuisBericht, context, resultaat);

        ArgumentCaptor<BijhoudingsBericht> berichtArgument = ArgumentCaptor.forClass(BijhoudingsBericht.class);

        ArgumentCaptor<BerichtContext> contextArgument = ArgumentCaptor.forClass(BerichtContext.class);

        ArgumentCaptor<BerichtResultaat> resultaatArgument = ArgumentCaptor.forClass(BerichtResultaat.class);

        Mockito.verify(geboorteNotificator, Mockito.times(0)).notificeerDashboard(berichtArgument.capture(),
                contextArgument.capture(), resultaatArgument.capture());

        Mockito.verify(verhuizingNotificator).notificeerDashboard(berichtArgument.capture(), contextArgument.capture(),
                resultaatArgument.capture());

        Assert.assertSame(verhuisBericht, berichtArgument.getValue());
        Assert.assertSame(context, contextArgument.getValue());
        Assert.assertSame(resultaat, resultaatArgument.getValue());
    }

}
