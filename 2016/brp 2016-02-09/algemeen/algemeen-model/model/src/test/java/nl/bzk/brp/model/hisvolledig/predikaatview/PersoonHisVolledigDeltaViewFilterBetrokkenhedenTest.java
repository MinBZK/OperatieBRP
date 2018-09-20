/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class PersoonHisVolledigDeltaViewFilterBetrokkenhedenTest {

    private static final DatumTijdAttribuut TIJDSTIP_REGISTRATIE = new DatumTijdAttribuut(new Date());

    private static final int PERSOON1_ID = 1;
    private static final int PERSOON2_ID = 2;
    private static final int PERSOON3_ID = 3;

    private final PersoonHisVolledigImpl persoonHisVolledigImpl = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

    private final PersoonHisVolledigView persoonHisVolledigDeltaView =
        new PersoonHisVolledigView(persoonHisVolledigImpl,
            new AdministratieveHandelingDeltaPredikaat(1L),
            TIJDSTIP_REGISTRATIE);

    @Mock
    private PersoonHisVolledigImpl persoon2;

    @Mock
    private PersoonHisVolledigImpl persoon3;

    @Mock
    private AbstractMaterieelHistorischMetActieVerantwoording materieelHistorischMetActieVerantwoording;
    @Mock
    private AdministratieveHandelingModel                     administratieveHandeling;
    @Mock
    private ActieModel                                        actieInhoud;

    @Mock
    private AdministratieveHandelingModel administratieveHandelingWaarHetNietOmGaat;

    @Before
    public void setup() {
        when(administratieveHandeling.getID()).thenReturn(1L);
        when(actieInhoud.getAdministratieveHandeling()).thenReturn(administratieveHandeling);
        when(materieelHistorischMetActieVerantwoording.getVerantwoordingInhoud()).thenReturn(actieInhoud);

        when(administratieveHandelingWaarHetNietOmGaat.getID()).thenReturn(2L);

        ReflectionTestUtils.setField(persoonHisVolledigImpl, "iD", PERSOON1_ID);
        when(persoon2.getID()).thenReturn(PERSOON2_ID);
        when(persoon3.getID()).thenReturn(PERSOON3_ID);

        final HashSet<BetrokkenheidHisVolledigImpl> persoonBetrokkenheden = new HashSet<>();
        persoonHisVolledigImpl.setBetrokkenheden(persoonBetrokkenheden);
    }

    @Test
    public void testOverbodigeBetrokkenhedenFilterZelfBijHuwelijk() {
        creeerPartnerBetrokkenheidMetRelatieMetAnderePartner();

        final Set<? extends BetrokkenheidHisVolledig> persoonDeltaBetrokkenheden = persoonHisVolledigDeltaView.getBetrokkenheden();
        final Set<? extends BetrokkenheidHisVolledig> relatieDeltaBetrokkenheden =
            persoonDeltaBetrokkenheden.iterator().next().getRelatie().getBetrokkenheden();

        assertEquals(1, relatieDeltaBetrokkenheden.size());
        assertEquals(Integer.valueOf(PERSOON2_ID), relatieDeltaBetrokkenheden.iterator().next().getPersoon().getID());
    }

    @Test
    public void testOverbodigeBetrokkenhedenFilterZelfBijKind() {
        creeerKindBetrokkenheidMetRelatieMetTweeOuders();

        final Set<? extends BetrokkenheidHisVolledig> persoonDeltaBetrokkenheden = persoonHisVolledigDeltaView.getBetrokkenheden();
        final Set<? extends BetrokkenheidHisVolledig> relatieDeltaBetrokkenheden =
            persoonDeltaBetrokkenheden.iterator().next().getRelatie().getBetrokkenheden();

        assertEquals(2, relatieDeltaBetrokkenheden.size());

        // Important: check order
        final Iterator<? extends BetrokkenheidHisVolledig> iter = relatieDeltaBetrokkenheden.iterator();
        assertTrue(PERSOON2_ID == iter.next().getPersoon().getID());
        assertTrue(PERSOON3_ID == iter.next().getPersoon().getID());
    }

    @Test
    public void testOverbodigeBetrokkenhedenFilterAllesBehalveKindBijOuder() {
        creeerOuderBetrokkenheidMetRelatieMetAndereOuderEnKind();

        final Set<? extends BetrokkenheidHisVolledig> persoonDeltaBetrokkenheden = persoonHisVolledigDeltaView.getBetrokkenheden();
        final Set<? extends BetrokkenheidHisVolledig> relatieDeltaBetrokkenheden =
            persoonDeltaBetrokkenheden.iterator().next().getRelatie().getBetrokkenheden();

        assertEquals(1, relatieDeltaBetrokkenheden.size());
        assertEquals(Integer.valueOf(PERSOON3_ID), relatieDeltaBetrokkenheden.iterator().next().getPersoon().getID());
    }

    private void creeerPartnerBetrokkenheidMetRelatieMetAnderePartner() {
        final HuwelijkHisVolledigImpl huwelijkHisVolledig = new HuwelijkHisVolledigImpl();

        final PartnerHisVolledigImpl persoonBetrokkenheid = new PartnerHisVolledigImpl(huwelijkHisVolledig, persoonHisVolledigImpl);
        ReflectionTestUtils.setField(persoonBetrokkenheid, "iD", persoonHisVolledigImpl.getID());
        persoonHisVolledigImpl.getBetrokkenheden().add(persoonBetrokkenheid);

        final HisRelatieModel hisHuwelijkGeregistreerdPartnerschapModel = mock(HisRelatieModel.class);
        when(hisHuwelijkGeregistreerdPartnerschapModel.getVerantwoordingInhoud()).thenReturn(actieInhoud);

        final Set<HisRelatieModel> hisHuwelijkModellen = new HashSet<>(
                        Arrays.asList(new HisRelatieModel[] { hisHuwelijkGeregistreerdPartnerschapModel }));

        ReflectionTestUtils.setField(huwelijkHisVolledig.getRelatieHistorie(), "interneSet", hisHuwelijkModellen);

        final Set<BetrokkenheidHisVolledigImpl> huwelijkBetrokkenheden = new HashSet<>();
        final PartnerHisVolledigImpl partnerBetrokkenheid = new PartnerHisVolledigImpl(huwelijkHisVolledig, persoon2);
        ReflectionTestUtils.setField(partnerBetrokkenheid, "iD", persoon2.getID());
        huwelijkBetrokkenheden.add(persoonBetrokkenheid);
        huwelijkBetrokkenheden.add(partnerBetrokkenheid);
        huwelijkHisVolledig.setBetrokkenheden(huwelijkBetrokkenheden);
    }

    private void creeerKindBetrokkenheidMetRelatieMetTweeOuders() {
        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekkingHisVolledig = new FamilierechtelijkeBetrekkingHisVolledigImpl();

        final KindHisVolledigImpl persoonBetrokkenheid = new KindHisVolledigImpl(familierechtelijkeBetrekkingHisVolledig, persoonHisVolledigImpl);
        ReflectionTestUtils.setField(persoonBetrokkenheid, "iD", persoonHisVolledigImpl.getID());
        persoonHisVolledigImpl.getBetrokkenheden().add(persoonBetrokkenheid);

        final OuderHisVolledigImpl ouder1Betrokkenheid = new OuderHisVolledigImpl(familierechtelijkeBetrekkingHisVolledig, persoon2);
        ReflectionTestUtils.setField(ouder1Betrokkenheid, "iD", persoon2.getID());
        final OuderHisVolledigImpl ouder2Betrokkenheid = new OuderHisVolledigImpl(familierechtelijkeBetrekkingHisVolledig, persoon3);
        ReflectionTestUtils.setField(ouder2Betrokkenheid, "iD", persoon3.getID());
        geefOuderHistorieZodatDeltaViewDataGeeft(ouder1Betrokkenheid);
        geefOuderHistorieZodatDeltaViewDataGeeft(ouder2Betrokkenheid);

        final Set<BetrokkenheidHisVolledigImpl> familieBetrokkenheden = new HashSet<>();
        familieBetrokkenheden.add(persoonBetrokkenheid);
        familieBetrokkenheden.add(ouder1Betrokkenheid);
        familieBetrokkenheden.add(ouder2Betrokkenheid);
        familierechtelijkeBetrekkingHisVolledig.setBetrokkenheden(familieBetrokkenheden);
    }

    private void creeerOuderBetrokkenheidMetRelatieMetAndereOuderEnKind() {
        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekkingHisVolledig = new FamilierechtelijkeBetrekkingHisVolledigImpl();

        final OuderHisVolledigImpl persoonBetrokkenheid = new OuderHisVolledigImpl(familierechtelijkeBetrekkingHisVolledig, persoonHisVolledigImpl);
        ReflectionTestUtils.setField(persoonBetrokkenheid, "iD", persoonHisVolledigImpl.getID());
        persoonHisVolledigImpl.getBetrokkenheden().add(persoonBetrokkenheid);
        final OuderHisVolledigImpl ouder2Betrokkenheid = new OuderHisVolledigImpl(familierechtelijkeBetrekkingHisVolledig, persoon3);
        ReflectionTestUtils.setField(ouder2Betrokkenheid, "iD", persoon3.getID());

        geefOuderHistorieZodatDeltaViewDataGeeft(persoonBetrokkenheid);
        geefOuderHistorieZodatDeltaViewDataGeeft(ouder2Betrokkenheid);

        final Set<BetrokkenheidHisVolledigImpl> familieBetrokkenheden = new HashSet<>();
        final KindHisVolledigImpl kindBetrokkenheid = new KindHisVolledigImpl(familierechtelijkeBetrekkingHisVolledig, persoon3);
        ReflectionTestUtils.setField(kindBetrokkenheid, "iD", persoon3.getID());
        familieBetrokkenheden.add(persoonBetrokkenheid);
        familieBetrokkenheden.add(ouder2Betrokkenheid);
        familieBetrokkenheden.add(kindBetrokkenheid);
        familierechtelijkeBetrekkingHisVolledig.setBetrokkenheden(familieBetrokkenheden);
    }

    private void geefOuderHistorieZodatDeltaViewDataGeeft(final OuderHisVolledigImpl ouderBetrokkenheid) {
        final HisOuderOuderschapModel hisOuderOuderschapModel = mock(HisOuderOuderschapModel.class);
        when(hisOuderOuderschapModel.getVerantwoordingInhoud()).thenReturn(actieInhoud);

        final Set<HisOuderOuderschapModel> hisOuderOuderschapModellen =
            new HashSet<>(Arrays.asList(new HisOuderOuderschapModel[] { hisOuderOuderschapModel }));

        ReflectionTestUtils.setField(ouderBetrokkenheid.getOuderOuderlijkGezagHistorie(), "interneSet", hisOuderOuderschapModellen);
    }
}
