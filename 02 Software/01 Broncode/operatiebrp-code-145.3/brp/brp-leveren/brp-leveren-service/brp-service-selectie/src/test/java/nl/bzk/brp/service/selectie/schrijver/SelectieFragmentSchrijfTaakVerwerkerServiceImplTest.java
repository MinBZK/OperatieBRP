/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import static org.mockito.Mockito.times;

import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieFragmentSchrijfTaakVerwerkerServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieFragmentSchrijfTaakVerwerkerServiceImplTest {

    @Mock
    private SelectieFragmentWriter selectieFragmentWriter;

    @Mock
    private SelectieTaakResultaatPublicatieService selectieTaakResultaatPublicatieService;

    @InjectMocks
    private SelectieFragmentSchrijfTaakVerwerkerServiceImpl selectieFragmentSchrijfTaakVerwerkerService;

    @Test
    public void testHappyFlow() throws SelectieException {
        final SelectieFragmentSchrijfBericht selectieSchrijfTaak = new SelectieFragmentSchrijfBericht();
        selectieSchrijfTaak.setSelectieRunId(1);
        selectieSchrijfTaak.setDienstId(1);
        selectieSchrijfTaak.setToegangLeveringsAutorisatieId(1);
        selectieFragmentSchrijfTaakVerwerkerService.verwerk(selectieSchrijfTaak);

        Mockito.verify(selectieFragmentWriter, times(1)).verwerk(Mockito.any());
        Mockito.verify(selectieTaakResultaatPublicatieService, times(1)).publiceerSelectieTaakResultaat(Mockito.any());
    }

    @Test
    public void testFout() throws SelectieException {
        final SelectieFragmentSchrijfBericht selectieSchrijfTaak = new SelectieFragmentSchrijfBericht();
        selectieSchrijfTaak.setSelectieRunId(1);
        selectieSchrijfTaak.setDienstId(1);
        selectieSchrijfTaak.setToegangLeveringsAutorisatieId(1);
        selectieFragmentSchrijfTaakVerwerkerService.verwerk(selectieSchrijfTaak);
        Mockito.doThrow(new SelectieException(null)).when(selectieFragmentWriter).verwerk(Mockito.any());

        Mockito.verify(selectieTaakResultaatPublicatieService, times(0)).publiceerFout();
    }
}
