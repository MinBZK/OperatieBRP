/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import nl.bzk.brp.domain.berichtmodel.StufAntwoordBericht;
import nl.bzk.brp.service.algemeen.ServiceCallback;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link StufBerichtVerwerkerImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class StufBerichtVerwerkerImplTest {

    @InjectMocks
    private StufBerichtVerwerkerImpl stufBerichtVerwerker;
    @Mock
    private StufBerichtBerichtFactory stufBerichtBerichtFactory;
    @Mock
    private StufBerichtArchiveringService archiveringService;
    @Mock
    private StufBerichtService stufBerichtService;

    private StufAntwoordBericht berichtAntwoordBericht;

    @Test
    public void happyFlow() throws AutorisatieException {
        StufBerichtVerzoek verzoek = new StufBerichtVerzoek();
        final StufBerichtResultaat resultaat = new StufBerichtResultaat(verzoek);
        when(stufBerichtService.verwerkVerzoek(any())).thenReturn(resultaat);
        final TestCallback callback = new TestCallback();

        stufBerichtVerwerker.verwerkVerzoek(verzoek, callback);

        InOrder inOrder = inOrder(stufBerichtService, stufBerichtBerichtFactory, archiveringService);
        inOrder.verify(stufBerichtService).verwerkVerzoek(verzoek);
        inOrder.verify(stufBerichtBerichtFactory).maakAntwoordBericht(resultaat);
        inOrder.verify(archiveringService).archiveer(resultaat, berichtAntwoordBericht, callback.getBerichtResultaat());
    }

    private final class TestCallback implements ServiceCallback<StufAntwoordBericht, String> {

        @Override
        public void verwerkBericht(final StufAntwoordBericht stufAntwoordBericht) {
            StufBerichtVerwerkerImplTest.this.berichtAntwoordBericht = stufAntwoordBericht;
        }

        @Override
        public String getBerichtResultaat() {
            return "resultaat";
        }
    }
}
