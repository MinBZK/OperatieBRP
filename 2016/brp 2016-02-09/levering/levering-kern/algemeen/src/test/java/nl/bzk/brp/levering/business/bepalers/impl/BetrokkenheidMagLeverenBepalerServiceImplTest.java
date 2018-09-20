/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.levering.business.bepalers.LegeBerichtBepaler;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroep;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroepAttribuutImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelGroepBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.OuderHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.brp.util.RelatieTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class BetrokkenheidMagLeverenBepalerServiceImplTest {

    private static final String                                    ID      = "iD";
    @InjectMocks
    private final        BetrokkenheidMagLeverenBepalerServiceImpl service = new BetrokkenheidMagLeverenBepalerServiceImpl();

    @Mock
    private LegeBerichtBepaler legeBerichtBepaler;

    @Before
    public void setup() {
        final ElementEnumBepaler elementEnumBepaler = new ElementEnumBepaler();
        ReflectionTestUtils.setField(service, "elementEnumBepaler", elementEnumBepaler);
    }

    @Test
    public void levertIndienGeenBetrokkenhedenInRelatieMaarWelHistorieRecords() {
        // given:
        final PersoonHisVolledig persoon = mock(PersoonHisVolledigImpl.class);
        final BetrokkenheidHisVolledig betrokkenheid = mock(KindHisVolledig.class);
        when(betrokkenheid.getRol()).thenReturn(new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.KIND));
        when(betrokkenheid.getID()).thenReturn(1);

        // kind met relatie waarin geen andere personen zijn maar wel een relatie lijst is.
        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekking = mock(FamilierechtelijkeBetrekkingHisVolledigImpl.class);
        final Set<HisRelatieModel> hisRelatieModellen = new HashSet<>();
        hisRelatieModellen.add(mock(HisRelatieModel.class));

        final FormeleHistorieSetImpl<HisRelatieModel> formeleHistorieSet = new FormeleHistorieSetImpl<>(hisRelatieModellen);
        when(familierechtelijkeBetrekking.getRelatieHistorie()).thenReturn(formeleHistorieSet);
        when(betrokkenheid.getRelatie()).thenReturn(familierechtelijkeBetrekking);

        final Set<BetrokkenheidHisVolledig> betrokkenhedenSet = new HashSet<>();
        betrokkenhedenSet.add(betrokkenheid);
        doReturn(betrokkenhedenSet).when(persoon).getBetrokkenheden();
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(persoon, null);

        // when:
        service.bepaalMagLeveren(persoonHisVolledigView, maakDienst(), false);

        // then:
        assertNotNull(persoon);
        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : persoonHisVolledigView.getBetrokkenheden()) {
            assertThat(betrokkenheidHisVolledigView.isMagLeveren(), is(true));
            assertThat(((RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie()).isMagLeveren(), is(true));
        }
    }

    @Test
    public void leveringAfhankelijkVanObjectAutorisatieIndienGeenBetrokkenhedenInRelatie() {
        // given:
        final PersoonHisVolledig persoon = mock(PersoonHisVolledigImpl.class);
        final BetrokkenheidHisVolledig betrokkenheid = mock(KindHisVolledig.class);
        when(betrokkenheid.getRol()).thenReturn(new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.KIND));
        when(betrokkenheid.getID()).thenReturn(1);

        // kind met relatie waarin geen andere personen zijn en ook geen relatie lijst is.
        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekking = mock(FamilierechtelijkeBetrekkingHisVolledigImpl.class);

        final FormeleHistorieSetImpl<HisRelatieModel> formeleHistorieSet = new FormeleHistorieSetImpl<>(Collections.<HisRelatieModel>emptySet());
        when(familierechtelijkeBetrekking.getRelatieHistorie()).thenReturn(formeleHistorieSet);
        when(betrokkenheid.getRelatie()).thenReturn(familierechtelijkeBetrekking);

        final Set<BetrokkenheidHisVolledig> betrokkenhedenSet = new HashSet<>();
        betrokkenhedenSet.add(betrokkenheid);
        doReturn(betrokkenhedenSet).when(persoon).getBetrokkenheden();

        checkMagLeverenVoorAutorisatieObject(persoon, false, ElementEnum.GERELATEERDEERKENNER_PERSOON);
        checkMagLeverenVoorAutorisatieObject(persoon, true, ElementEnum.GERELATEERDEOUDER_PERSOON);
        checkMagLeverenVoorAutorisatieObject(persoon, true, ElementEnum.GERELATEERDEOUDER);
    }

    @Test
    public void zouAllePersonenEnBetrokkenhedenMogenLeveren() {
        // given:
        final PersoonHisVolledigImpl kind = bouwFamilie();
        checkMagLeverenVoorAutorisatieObject(kind, true, ElementEnum.GERELATEERDEOUDER_PERSOON, ElementEnum.GERELATEERDEOUDER,
            ElementEnum.FAMILIERECHTELIJKEBETREKKING);
    }

    @Test
    public void zouAllePersonenEnBetrokkenhedenMogenLeverenOokZonderGerelateerdeOuderElementEnum() {
        // given:
        final PersoonHisVolledigImpl kind = bouwFamilie();
        checkMagLeverenVoorAutorisatieObject(kind, true, ElementEnum.GERELATEERDEOUDER_PERSOON, ElementEnum.FAMILIERECHTELIJKEBETREKKING);
    }

    @Test
    public void magPersonenNietLeveren() {
        final PersoonHisVolledigImpl kind = bouwFamilie();


        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(kind, null);

        // when:
        service.bepaalMagLeveren(persoonHisVolledigView, maakDienst(ElementEnum.FAMILIERECHTELIJKEBETREKKING), false);

        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : persoonHisVolledigView.getBetrokkenheden()) {
            assertThat(betrokkenheidHisVolledigView.isMagLeveren(), is(true));
            final RelatieHisVolledigView relatie = (RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie();
            assertThat(relatie.isMagLeveren(), is(true));
            for (final BetrokkenheidHisVolledig relatieBetrokkenheid : relatie.getBetrokkenheden()) {
                assertThat(((OuderHisVolledigView) relatieBetrokkenheid).isMagLeveren(), is(false));
            }
        }
    }

    @Test
    public void magMutatieBerichtCompleetLeverenWegensLegeBerichtBepaler() {
        final PersoonHisVolledigImpl kind = bouwFamilie();
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(kind, null);

        when(legeBerichtBepaler.magBetrokkenPersoonGeleverdWorden(any(PersoonHisVolledigView.class))).thenReturn(true);

        // when:
        service.bepaalMagLeveren(persoonHisVolledigView, maakDienst(), true);
        levertVolledig(true, persoonHisVolledigView);
    }

    @Test
    public void magMutatieBerichtCompleetLeverenWegensHistoryRecords() {
        final PersoonHisVolledigImpl kind = bouwFamilie();
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(kind, null);

        when(legeBerichtBepaler.magBetrokkenPersoonGeleverdWorden(any(PersoonHisVolledigView.class))).thenReturn(false);

        // when:
        service.bepaalMagLeveren(persoonHisVolledigView, maakDienst(), true);
        levertVolledig(true, persoonHisVolledigView);
    }

    private PersoonHisVolledigImpl bouwFamilie() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                new DatumEvtDeelsOnbekendAttribuut(
                    19400909), null, new DatumTijdAttribuut(new Date()), null);

        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        ReflectionTestUtils.setField(kind, ID, 1);

        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        ReflectionTestUtils.setField(moeder, ID, 2);

        final PersoonHisVolledigImpl vader = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        ReflectionTestUtils.setField(vader, ID, 3);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(moeder, vader, kind, actie);
        return kind;
    }

    private void checkMagLeverenVoorAutorisatieObject(final PersoonHisVolledig persoon, final boolean verwachtMagLeveren,
        final ElementEnum... elementEnums)
    {
        final Set<ElementEnum> autorisatieObjecten = new HashSet<>(Arrays.asList(elementEnums));

        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(persoon, null);

        // when:
        service.bepaalMagLeveren(persoonHisVolledigView, maakDienst(elementEnums), false);

        assertNotNull(persoon);
        levertVolledig(verwachtMagLeveren, persoonHisVolledigView);
    }

    private void levertVolledig(final boolean verwachtMagLeveren, final PersoonHisVolledigView persoonHisVolledigView) {
        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : persoonHisVolledigView.getBetrokkenheden()) {
            assertThat(betrokkenheidHisVolledigView.isMagLeveren(), is(verwachtMagLeveren));
            final RelatieHisVolledigView relatie = (RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie();
            assertThat(relatie.isMagLeveren(), is(verwachtMagLeveren));
            for (final BetrokkenheidHisVolledig relatieBetrokkenheid : relatie.getBetrokkenheden()) {
                assertThat(((OuderHisVolledigView) relatieBetrokkenheid).isMagLeveren(), is(verwachtMagLeveren));
            }
        }
    }


    private Dienst maakDienst(final ElementEnum... elementEnums)
    {
        final Dienst dienst = TestDienstBuilder.maker().maak();
        TestDienstbundelBuilder.maker().metDiensten(dienst).maak();

        final DienstbundelGroep dienstbundelGroep = TestDienstbundelGroepBuilder.maker().maak();
        TestDienstbundelBuilder.maker().metDiensten(dienst).metGroepen(dienstbundelGroep).maak();
        final Set<DienstbundelGroepAttribuutImpl> attributen = new HashSet<>();
        if (elementEnums != null) {
            for (ElementEnum anEnum : elementEnums) {
                Element objectType = TestElementBuilder.maker().metElementNaam(anEnum.getNaam()).metNaam(anEnum).maak();
                Element groep = TestElementBuilder.maker().metElementNaam(anEnum.getNaam()).metElementObjectType(objectType).metNaam(anEnum).maak();
                Element element = TestElementBuilder.maker().metElementGroep(groep).metElementNaam(anEnum.getNaam()).metNaam(anEnum).maak();
                    attributen.add(new DienstbundelGroepAttribuutImpl(dienstbundelGroep, element));
            }
        }
        dienstbundelGroep.setAttributen(attributen);


        return dienst;
    }
}
