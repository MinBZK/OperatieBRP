/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.afnemerindicatie;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.TypeResultaat;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * VerwerkAfnemerindicatieServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class VerwerkAfnemerindicatieServiceImplTest {

    private static final Integer SELECTIE_TAAK_ID = 1;
    @InjectMocks
    private VerwerkAfnemerindicatieServiceImpl service;

    @Mock
    private OnderhoudAfnemerindicatieSelectieService onderhoudAfnemerindicatieService;
    @Mock
    private SelectieTaakResultaatPublicatieService selectieTaakResultaatPublicatieService;

    @Captor
    private ArgumentCaptor<SelectieTaakResultaat> selectieTaakResultaatArgumentCaptor;


    private SelectieAfnemerindicatieTaak verzoek;


    @Before
    public void before() throws Exception {
        verzoek = new SelectieAfnemerindicatieTaak();
        verzoek.setDienstId(1);
        verzoek.setToegangLeveringsautorisatieId(1);
        verzoek.setPersoonId(1L);
        verzoek.setSelectieTaakId(SELECTIE_TAAK_ID);
    }

    @Test
    public void testBijGeenFoutPlaatsenSuccesResultaat() throws Exception {

        service.verwerk(Collections.singleton(verzoek));

        verify(selectieTaakResultaatPublicatieService, times(1)).publiceerSelectieTaakResultaat(selectieTaakResultaatArgumentCaptor.capture());
        final List<SelectieTaakResultaat> resultaatList = selectieTaakResultaatArgumentCaptor.getAllValues();
        assertThat(resultaatList.get(0).getType(), is(TypeResultaat.AFNEMERINDICATIE_VERWERKT));
        assertThat(resultaatList.get(0).getTaakId(), is(SELECTIE_TAAK_ID));
    }


    @Test
    public void testBijFoutPlaatsenNietSuccesResultaat() throws Exception {
        doThrow(new RuntimeException("", null)).when(onderhoudAfnemerindicatieService)
                .verwerk(any());

        service.verwerk(Collections.singleton(verzoek));

        verify(selectieTaakResultaatPublicatieService, times(0)).publiceerSelectieTaakResultaat(selectieTaakResultaatArgumentCaptor.capture());
        verify(selectieTaakResultaatPublicatieService, times(1)).publiceerFout();

    }
}
