/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.basis.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import nl.bzk.brp.webservice.business.service.LeveringsautorisatieService;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link AbstractLeveringsautorisatieWebService}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractLeveringsautorisatieWebServiceTest {

    @InjectMocks
    private AbstractLeveringsautorisatieWebService service = new TestAbstractLeveringsautorisatieWebService();

    @Mock
    private LeveringsautorisatieService authenticatieService;

    @Test
    public void testIsGeautoriseerd() throws Exception {
        final AutorisatieOffloadGegevens autenticatieOffloadGegevens = new AutorisatieOffloadGegevens(TestPartijBuilder.maker().metOin(new
            OINAttribuut("123")).maak(), TestPartijBuilder.maker().metOin(new
            OINAttribuut("456")).maak());
        final BerichtBericht berichtMock = mock(BerichtBericht.class);
        final BerichtStuurgegevensGroepBericht stuurgegevensGroepBericht = new BerichtStuurgegevensGroepBericht();

        final int zendendePartijCode = 15;
        stuurgegevensGroepBericht.setZendendePartijCode(String.valueOf(zendendePartijCode));
        Mockito.when(berichtMock.getStuurgegevens()).thenReturn(stuurgegevensGroepBericht);
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setLeveringsautorisatieID("1");
        when(berichtMock.getParameters()).thenReturn(parameters);

        service.checkAutorisatie(autenticatieOffloadGegevens, berichtMock);
        verify(authenticatieService).controleerAutorisatie(1, "" + zendendePartijCode, autenticatieOffloadGegevens);
    }

    private static class TestAbstractLeveringsautorisatieWebService extends AbstractLeveringsautorisatieWebService {

        @Override
        protected BerichtVerwerkingsResultaat verwerkBericht(final BerichtBericht bericht, final BerichtContext context) {
            return null;
        }

        @Override
        protected BerichtContext bouwBerichtContext(final ReferentienummerAttribuut berichtReferentieNummer, final BerichtenIds berichtenIds,
            final Partij partij, final CommunicatieIdMap identificeerbareObjecten)
        {
            return null;
        }

        @Override
        protected BerichtVerwerkingsResultaat getResultaatInstantie(final List meldingen) {
            return null;
        }
    }
}