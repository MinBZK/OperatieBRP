/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.basis.service;

import static org.mockito.Mockito.verify;

import java.util.List;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresBericht;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import nl.bzk.brp.webservice.business.service.BijhoudingsautorisatieService;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link AbstractBijhoudingsautorisatieWebService}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractBijhoudingsautorisatieWebServiceTest {

    public static final String ZENDENDE_PARTIJ_CODE = "123";

    @InjectMocks
    private AbstractBijhoudingsautorisatieWebService service = new TestAbstractBijhoudingsautorisatieWebService();

    @Mock
    private BijhoudingsautorisatieService authenticatieService;

    @Test
    public void testIsGeautoriseerd() throws Exception {
        final AutorisatieOffloadGegevens autenticatieOffloadGegevens = new AutorisatieOffloadGegevens(TestPartijBuilder.maker().metOin(new
            OINAttribuut("123")).maak(), TestPartijBuilder.maker().metOin(new
            OINAttribuut("456")).maak());

        final BerichtBericht bericht = new CorrigeerAdresBericht();
        final BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        stuurgegevens.setZendendePartijCode(ZENDENDE_PARTIJ_CODE);
        bericht.setStuurgegevens(stuurgegevens);
        bericht.getStandaard().setAdministratieveHandeling(new AdministratieveHandelingBericht(new SoortAdministratieveHandelingAttribuut(
            SoortAdministratieveHandeling.WIJZIGING_ADRES_INFRASTRUCTUREEL)) {});

        service.checkAutorisatie(autenticatieOffloadGegevens, bericht);

        verify(authenticatieService).controleerAutorisatie(bericht.getAdministratieveHandeling().getSoort().getWaarde(), ZENDENDE_PARTIJ_CODE, autenticatieOffloadGegevens);
    }

    private static class TestAbstractBijhoudingsautorisatieWebService extends AbstractBijhoudingsautorisatieWebService {

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