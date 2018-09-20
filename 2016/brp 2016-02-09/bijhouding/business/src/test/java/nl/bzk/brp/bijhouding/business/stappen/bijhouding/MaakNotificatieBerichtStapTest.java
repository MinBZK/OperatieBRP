/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import nl.bzk.brp.bijhouding.business.bericht.MarshallService;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.NotificeerBijhoudingsplanBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakNotificatieBerichtStapTest {

    @InjectMocks
    private MaakNotificatieBerichtStap stap;

    @Mock
    private MarshallService marshallService;

    private BijhoudingsBericht       bijhoudingsBericht;
    private BijhoudingBerichtContext berichtContext;

    @Mock
    private MaterieleHistorieSet<HisPersoonBijhoudingModel> historie;

    @Before
    public void setUp() {
        bijhoudingsBericht = new RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht();

        berichtContext = mock(BijhoudingBerichtContext.class);
        final PartijAttribuut partijAttribuut = mock(PartijAttribuut.class);
        final Partij partij = TestPartijBuilder.maker().metCode(123).maak();
        when(partijAttribuut.getWaarde()).thenReturn(partij);
        when(berichtContext.getPartij()).thenReturn(partijAttribuut);

        HisPersoonBijhoudingModel model = mock(HisPersoonBijhoudingModel.class);
        when(model.getBijhoudingspartij()).thenReturn(partijAttribuut);
        when(historie.getActueleRecord()).thenReturn(model);
        PersoonHisVolledigImpl pers = mock(PersoonHisVolledigImpl.class);
        when(pers.getPersoonBijhoudingHistorie()).thenReturn(historie);
        when(berichtContext.getBijgehoudenPersonen()).thenReturn(Collections.singletonList(pers));
    }

    @Test
    public void testVoerStapUitIndienGeenNotificatieberichtNodig() throws Exception {
        // bericht waarvoor we geen notificaties moeten gaan sturen
        bijhoudingsBericht = new RegistreerNationaliteitBericht();
        final Resultaat resultaat = stap.voerStapUit(bijhoudingsBericht, berichtContext);
        verify(marshallService, never()).maakBericht(any(NotificeerBijhoudingsplanBericht.class));
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testVoerStapUitWelNotificatieberichtNodig() throws Exception {
        final Resultaat resultaat = stap.voerStapUit(bijhoudingsBericht, berichtContext);
        verify(marshallService).maakBericht(any(NotificeerBijhoudingsplanBericht.class));
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

}
