/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.stappen.populatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class LeveringsautorisatieVerwerkingContextTest {

    private static final long TEST_ADMINISTRATIEVE_HANDELING_ID = 6;

    @Mock
    private Attribuut attribuut;

    @Test
    public final void testAfnemerVerwerkingContext() {
        final AdministratieveHandelingModel handelingModel =
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND),
                                                  StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE,
                                                  new OntleningstoelichtingAttribuut(""),
                                                  DatumTijdAttribuut.nu());
        ReflectionTestUtils.setField(handelingModel, "iD", TEST_ADMINISTRATIEVE_HANDELING_ID);

        final Map<Integer, Populatie> teLeverenPersoonIds = new HashMap<>();
        teLeverenPersoonIds.put(123, Populatie.BINNEN);

        final List<PersoonHisVolledig> teLeverenPersonen = new ArrayList<>();

        final LeveringsautorisatieVerwerkingContext context = new LeveringsautorisatieVerwerkingContextImpl(
                handelingModel, teLeverenPersonen, teLeverenPersoonIds, null, null
        );

        assertEquals(Long.valueOf(TEST_ADMINISTRATIEVE_HANDELING_ID), context.getReferentieId());
        assertEquals(context.getTeLeverenPersoonIds(), teLeverenPersoonIds);
        assertEquals(context.getAdministratieveHandeling(), handelingModel);
        assertEquals(context.getBijgehoudenPersonenVolledig(), teLeverenPersonen);
        assertNull(context.getResultaatId());
    }

    @Test
    public final void testContextZonderAdministratieveHandeling() {
        final LeveringsautorisatieVerwerkingContext context = new LeveringsautorisatieVerwerkingContextImpl(
                null, new ArrayList<PersoonHisVolledig>(), new HashMap<Integer, Populatie>(), null, null
        );

        assertNull(context.getReferentieId());
    }

    @Test
    public final void testOverigeGetterEnSetter() {
        final LeveringsautorisatieVerwerkingContext context = new LeveringsautorisatieVerwerkingContextImpl(
                null, new ArrayList<PersoonHisVolledig>(), new HashMap<Integer, Populatie>(), null, null
        );

        final List<Attribuut> attributenDieGeleverdMogenWorden = new ArrayList<>();
        attributenDieGeleverdMogenWorden.add(attribuut);

        final List<PersoonHisVolledigView> persoonViews = new ArrayList<>();
        final List<SynchronisatieBericht> leveringBerichten = new ArrayList<>();
        final List<String> platteTekstBerichten = Arrays.asList("foo", "bar");

        // act
        context.setAttributenDieGeleverdMogenWorden(attributenDieGeleverdMogenWorden);
        context.setBijgehoudenPersoonViews(persoonViews);
        context.setLeveringBerichten(leveringBerichten);
        context.setUitgaandePlatteTekstBerichten(platteTekstBerichten);

        // assert
        assertEquals(context.getAttributenDieGeleverdMogenWorden(), attributenDieGeleverdMogenWorden);
        assertEquals(context.getBijgehoudenPersoonViews(), persoonViews);
        assertEquals(context.getLeveringBerichten(), leveringBerichten);
        assertEquals(context.getUitgaandePlatteTekstBerichten(), platteTekstBerichten);
    }
}
