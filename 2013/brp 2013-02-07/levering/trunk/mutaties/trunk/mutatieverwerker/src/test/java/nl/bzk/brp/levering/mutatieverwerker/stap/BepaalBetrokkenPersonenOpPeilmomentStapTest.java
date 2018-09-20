/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.levering.mutatieverwerker.AdministratieveHandelingTestBouwer;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.levering.mutatieverwerker.stap.util.PersoonConverteerder;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class BepaalBetrokkenPersonenOpPeilmomentStapTest {

    private final BepaalBetrokkenPersonenOpPeilmomentStap bepaalBetrokkenPersonenOpPeilmomentStap =
            new BepaalBetrokkenPersonenOpPeilmomentStap();

    @Mock
    private PersoonConverteerder persoonConverteerder;

    @Mock
    private PersoonHisVolledig persoonVolledig1;

    @Mock
    private PersoonHisVolledig persoonVolledig2;

    @Mock
    private Persoon persoon1;

    @Mock
    private Persoon persoon2;

    private final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContext();

    private final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();

    private final List<PersoonHisVolledig> betrokkenPersonenVolledig = new ArrayList<PersoonHisVolledig>();

    private final List<Persoon> betrokkenPersonen = new ArrayList<Persoon>();

    private DatumTijd peilmoment;

    private final AdministratieveHandelingModel administratieveHandelingModel =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

    @Before
    public void setup() {
        ReflectionTestUtils.setField(bepaalBetrokkenPersonenOpPeilmomentStap, "persoonConverteerder",
                persoonConverteerder);
        ReflectionTestUtils.setField(persoonVolledig1, "iD", 123456);
        ReflectionTestUtils.setField(persoonVolledig2, "iD", 654321);

        betrokkenPersonenVolledig.add(persoonVolledig1);
        betrokkenPersonenVolledig.add(persoonVolledig2);
        context.setBetrokkenPersonenVolledig(betrokkenPersonenVolledig);

        context.setHuidigeAdministratieveHandeling(administratieveHandelingModel);

        peilmoment = context.getHuidigeAdministratieveHandeling().getTijdstipOntlening();

        when(persoonConverteerder.bepaalBetrokkenPersoonOpPeilmoment(persoonVolledig1, peilmoment))
                .thenReturn(persoon1);
        when(persoonConverteerder.bepaalBetrokkenPersoonOpPeilmoment(persoonVolledig2, peilmoment))
                .thenReturn(persoon2);

        betrokkenPersonen.add(persoon1);
        betrokkenPersonen.add(persoon2);
    }

    @Test
    public void testVoerStapUitEnControleerSuccesvol() {
        //test
        bepaalBetrokkenPersonenOpPeilmomentStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        //verify
        assertTrue(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

    @Test
    public void testVoerStapUitEnControleerConverteerderAanroep() {
        //test
        bepaalBetrokkenPersonenOpPeilmomentStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        verify(persoonConverteerder).bepaalBetrokkenPersoonOpPeilmoment(persoonVolledig1, peilmoment);
        verify(persoonConverteerder).bepaalBetrokkenPersoonOpPeilmoment(persoonVolledig2, peilmoment);
    }

    @Test
    public void testVoerStapUitEnControleerContextGevuld() {
        //test
        bepaalBetrokkenPersonenOpPeilmomentStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        assertEquals(betrokkenPersonen, context.getBetrokkenPersonen());
    }

    @Test
    public void testVoerStapUitConverterenLevertExceptieOp() {
        when(persoonConverteerder.bepaalBetrokkenPersoonOpPeilmoment(any(PersoonHisVolledig.class), any(DatumTijd.class))).thenThrow(RuntimeException.class);

        final boolean resultaat =
                bepaalBetrokkenPersonenOpPeilmomentStap.voerStapUit(getAdministratieveHandelingMutatie(), context, administratieveHandelingVerwerkingResultaat);

        assertEquals(AbstractStap.STOPPEN, resultaat);
        assertFalse(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

    @Test
    public void testVoerStapUitConverterenLevertNullOp() {
        when(persoonConverteerder.bepaalBetrokkenPersoonOpPeilmoment(any(PersoonHisVolledig.class), any(DatumTijd.class))).thenReturn(null);

        final boolean resultaat =
                bepaalBetrokkenPersonenOpPeilmomentStap.voerStapUit(getAdministratieveHandelingMutatie(), context, administratieveHandelingVerwerkingResultaat);

        assertEquals(AbstractStap.STOPPEN, resultaat);
        assertFalse(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

    private AdministratieveHandelingMutatie getAdministratieveHandelingMutatie() {
        return new AdministratieveHandelingMutatie(
                administratieveHandelingModel.getID(), null);
    }

}
