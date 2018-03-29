/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import nl.bzk.brp.domain.berichtmodel.VrijBerichtAntwoordBericht;
import nl.bzk.brp.service.algemeen.ServiceCallback;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link VrijBerichtVerwerkerImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtVerwerkerImplTest {

    @InjectMocks
    private VrijBerichtVerwerkerImpl vrijBerichtVerwerker;
    @Mock
    private VrijBerichtArchiveringService archiveringService;
    @Mock
    private VrijBerichtService vrijBerichtService;
    @Mock
    private VrijBerichtBerichtFactory vrijBerichtBerichtFactory;

    private VrijBerichtAntwoordBericht berichtAntwoordBericht;

    @Test
    public void happyFlow() throws AutorisatieException {
        VrijBerichtVerzoek verzoek = new VrijBerichtVerzoek();
        verzoek.getParameters().setOntvangerVrijBericht("1");
        final VrijBerichtResultaat resultaat = new VrijBerichtResultaat(verzoek);
        when(vrijBerichtService.verwerkVerzoek(any())).thenReturn(resultaat);
        final TestCallback callback = new TestCallback();

        vrijBerichtVerwerker.stuurVrijBericht(verzoek, callback);

        InOrder inOrder = inOrder(vrijBerichtService, vrijBerichtBerichtFactory, archiveringService);
        inOrder.verify(vrijBerichtService).verwerkVerzoek(verzoek);
        inOrder.verify(vrijBerichtBerichtFactory).maakAntwoordBericht(resultaat);
        inOrder.verify(archiveringService).archiveer(resultaat, berichtAntwoordBericht, callback.getBerichtResultaat());
    }

    private final class TestCallback implements ServiceCallback<VrijBerichtAntwoordBericht, String> {

        @Override
        public void verwerkBericht(final VrijBerichtAntwoordBericht vrijBerichtAntwoordBericht) {
            VrijBerichtVerwerkerImplTest.this.berichtAntwoordBericht = vrijBerichtAntwoordBericht;
        }

        @Override
        public String getBerichtResultaat() {
            return "resultaat";
        }
    }
}
