/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.status;

import com.google.common.collect.Sets;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.TypeResultaat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * SelectieTaakResultaatOntvangerImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieTaakResultaatOntvangerImplTest {

    @Mock
    private SelectieJobRunStatusService selectieJobRunStatusService;

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private SelectieTaakResultaatOntvangerImpl selectieTaakResultaatOntvanger;


    @Before
    public void voorTest() {
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(new SelectieJobRunStatus());
    }

    @Test
    public void testHappyFlowFragmentMaak() {

        SelectieTaakResultaat selectieTaakResultaat = new SelectieTaakResultaat();
        selectieTaakResultaat.setType(TypeResultaat.VERWERK);
        selectieTaakResultaat.setOngeldigeTaken(Sets.newHashSet());
        selectieTaakResultaatOntvanger.ontvang(selectieTaakResultaat);

        Assert.assertEquals(1, selectieJobRunStatusService.getStatus().getVerwerkTakenKlaarCount());
    }

    @Test
    public void testHappyFlowFragmentSchrijf() {

        SelectieTaakResultaat selectieTaakResultaat = new SelectieTaakResultaat();
        selectieTaakResultaat.setType(TypeResultaat.SCHRIJF);
        selectieTaakResultaat.setOngeldigeTaken(Sets.newHashSet());
        selectieTaakResultaatOntvanger.ontvang(selectieTaakResultaat);

        Assert.assertEquals(1, selectieJobRunStatusService.getStatus().getSchrijfTakenKlaarCount());
    }

    @Test
    public void testHappyFlowResultaatSchrijf() {

        SelectieTaakResultaat selectieTaakResultaat = new SelectieTaakResultaat();
        selectieTaakResultaat.setType(TypeResultaat.SELECTIE_RESULTAAT_SCHRIJF);
        selectieTaakResultaat.setOngeldigeTaken(Sets.newHashSet());
        selectieTaakResultaatOntvanger.ontvang(selectieTaakResultaat);

        Assert.assertEquals(1, selectieJobRunStatusService.getStatus().getSelectieResultaatSchrijfTaakResultaatCount());
    }

    @Test
    public void testHappyFlowFout() {

        SelectieTaakResultaat selectieTaakResultaat = new SelectieTaakResultaat();
        selectieTaakResultaat.setType(TypeResultaat.FOUT);
        selectieTaakResultaat.setOngeldigeTaken(Sets.newHashSet());
        selectieTaakResultaatOntvanger.ontvang(selectieTaakResultaat);

        Mockito.verify(applicationContext).publishEvent(Mockito.any());
    }


}
