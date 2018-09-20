/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.levering.mutatieverwerker.AdministratieveHandelingTestBouwer;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class HaalBetrokkenPersonenVolledigOpStapTest {

    @InjectMocks
    private final HaalBetrokkenPersonenVolledigOpStap haalBetrokkenPersonenVolledigOpStap =
            new HaalBetrokkenPersonenVolledigOpStap();

    @Mock
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    private final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContext();

    private final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();

    private List<Integer> betrokkenPersonenIds;

    private List<PersoonHisVolledig> personenVolledig;

    private static final Integer PERSOON_ID_1 = 123456;
    private static final Integer PERSOON_ID_2 = 654321;

    @Mock
    private PersoonHisVolledig persoonHisVolledig1;

    @Mock
    private PersoonHisVolledig persoonHisVolledig2;

    private final AdministratieveHandelingModel administratieveHandelingModel =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

    @Before
    public void setup() {
        betrokkenPersonenIds = new ArrayList<Integer>();
        betrokkenPersonenIds.add(PERSOON_ID_1);
        betrokkenPersonenIds.add(PERSOON_ID_2);
        context.setBetrokkenPersonenIds(betrokkenPersonenIds);

        ReflectionTestUtils.setField(persoonHisVolledig1, "iD", 123456);
        ReflectionTestUtils.setField(persoonHisVolledig2, "iD", 654321);

        personenVolledig = Arrays.asList(new PersoonHisVolledig[]{persoonHisVolledig1, persoonHisVolledig2});

        when(persoonHisVolledigRepository.haalPersonenOp(anyList())).thenReturn(personenVolledig);
    }

    @Test
    public void testVoerStapUitEnControleerSuccesvol() {
        //test
        haalBetrokkenPersonenVolledigOpStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        //verify
        assertTrue(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

    @Test
    public void testVoerStapUitEnControleerRepositoryAanroep() {
        //test
        haalBetrokkenPersonenVolledigOpStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        verify(persoonHisVolledigRepository).haalPersonenOp(betrokkenPersonenIds);
    }

    @Test
    public void testVoerStapUitEnControleerContextGevuld() {
        //test
        haalBetrokkenPersonenVolledigOpStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        verify(persoonHisVolledigRepository).haalPersonenOp(betrokkenPersonenIds);

        assertEquals(personenVolledig, context.getBetrokkenPersonenVolledig());
    }

    @Test
    public void testVoerStapUitKanBetrokkenPersoonHisVolledigNietOphalen() {
        when(persoonHisVolledigRepository.haalPersonenOp(anyList())).thenReturn(null);

        final boolean resultaat =
                haalBetrokkenPersonenVolledigOpStap.voerStapUit(
                        getAdministratieveHandelingMutatie(), context, administratieveHandelingVerwerkingResultaat);

        assertEquals(AbstractStap.STOPPEN, resultaat);
        assertFalse(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

    private AdministratieveHandelingMutatie getAdministratieveHandelingMutatie() {
        return new AdministratieveHandelingMutatie(
                administratieveHandelingModel.getID(), null);
    }

}
