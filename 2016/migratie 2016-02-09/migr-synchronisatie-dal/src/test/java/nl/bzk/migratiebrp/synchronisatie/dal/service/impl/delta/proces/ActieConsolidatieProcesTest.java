/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractFormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractDeltaTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.ConsolidatieData;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaRootEntiteitMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaStapelMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unittest voor {@link ActieConsolidatieProces}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ActieConsolidatieProcesTest extends AbstractDeltaTest {

    private DeltaBepalingContext context;
    private DeltaProces proces;
    private Persoon bestaandPersoon;
    private Persoon nieuwPersoon;
    @Mock
    private ConsolidatieData consolidatieDataMock;
    @Mock
    private VergelijkerResultaat vergelijkerResultaatMock;

    @Before
    public void setUp() throws Exception {
        bestaandPersoon = maakPersoon(true);
        nieuwPersoon = maakPersoon(false);

        context = new DeltaBepalingContext(nieuwPersoon, bestaandPersoon, null, false);
        proces = new ActieConsolidatieProces();
        SynchronisatieLogging.init();
    }

    @Test
    public void testBepaalVerschillenGeenConsolidatie() {
        context.addAllActieConsolidatieData(consolidatieDataMock);

        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(bestaandPersoon, nieuwPersoon, null, null);
        match.setVergelijkerResultaat(vergelijkerResultaatMock);
        context.setDeltaRootEntiteitMatches(Collections.singleton(match));

        final FormeleHistorie nieuweHistorie = nieuwPersoon.getPersoonInschrijvingHistorieSet().iterator().next();
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(nieuweHistorie);

        final List<VerschilGroep> verschilGroepen = new ArrayList<>();
        verschilGroepen.add(verschilGroep);
        mockitoCallsVoorbereiden(verschilGroepen, Collections.<DeltaStapelMatch>emptySet());

        proces.verwerkVerschillen(context);
        proces.bepaalVerschillen(context);

        assertTrue(context.isBijhoudingActueel());
    }

    @Test
    public void testBepaalVerschillenConsolidatie() throws NoSuchFieldException {
        context.addAllActieConsolidatieData(consolidatieDataMock);

        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(bestaandPersoon, nieuwPersoon, null, null);
        match.setVergelijkerResultaat(vergelijkerResultaatMock);
        context.setDeltaRootEntiteitMatches(Collections.singleton(match));

        final FormeleHistorie nieuweHistorie = nieuwPersoon.getPersoonInschrijvingHistorieSet().iterator().next();
        final FormeleHistorie bestaandeHistorie = bestaandPersoon.getPersoonInschrijvingHistorieSet().iterator().next();
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(nieuweHistorie);

        final List<VerschilGroep> verschilGroepen = new ArrayList<>();
        verschilGroepen.add(verschilGroep);

        final EntiteitSleutel sleutel = new EntiteitSleutel(Persoon.class, AbstractFormeleHistorie.INDICATIE_VOORKOMEN_TBV_LEVERING_MUTATIES, null);
        final Verschil mRijVerschil =
                new Verschil(sleutel, null, nieuweHistorie.getActieInhoud(), VerschilType.ELEMENT_NIEUW, bestaandeHistorie, nieuweHistorie);

        final Verschil nieuweRijElementAangepastVerschil =
                new Verschil(sleutel, null, nieuweHistorie.getActieInhoud(), VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST, bestaandeHistorie, nieuweHistorie);
        final Verschil aLaagRijToegevoegd = new Verschil(sleutel, null, new PersoonNationaliteit(nieuwPersoon, new Nationaliteit("Nederlands", (short)2)), VerschilType.RIJ_TOEGEVOEGD, null, null);
        final Verschil nieuweRijToegevoegd = new Verschil(sleutel, null, nieuweHistorie, VerschilType.RIJ_TOEGEVOEGD, null, nieuweHistorie);

        final EntiteitSleutel actieSleutel = new EntiteitSleutel(Persoon.class, AbstractFormeleHistorie.ACTIE_VERVAL, null);
        final Verschil actieVervalAangepast = new Verschil(actieSleutel, null, bestaandeHistorie.getActieInhoud(), VerschilType.ELEMENT_AANGEPAST, null, null);


        verschilGroep.addVerschil(nieuweRijElementAangepastVerschil);
        verschilGroep.addVerschil(mRijVerschil);
        verschilGroep.addVerschil(aLaagRijToegevoegd);
        verschilGroep.addVerschil(nieuweRijToegevoegd);
        verschilGroep.addVerschil(actieVervalAangepast);

        final Set<DeltaStapelMatch> deltaStapelMatchSet = new HashSet<>();
        final Field veld = Persoon.class.getDeclaredField("persoonInschrijvingHistorieSet");
        final DeltaStapelMatch deltaStapelMatch =
                new DeltaStapelMatch(Collections.singleton(bestaandeHistorie), Collections.singleton(nieuweHistorie), bestaandPersoon, sleutel, veld);
        deltaStapelMatchSet.add(deltaStapelMatch);

        mockitoCallsVoorbereiden(verschilGroepen, deltaStapelMatchSet);
        doNothing().when(vergelijkerResultaatMock).voegVerschillenToe(anyCollectionOf(Verschil.class));

        proces.bepaalVerschillen(context);

        assertTrue(context.isBijhoudingOverig());
    }

    private void mockitoCallsVoorbereiden(final List<VerschilGroep> verschilGroepen, final Set<DeltaStapelMatch> deltaStapelMatchSet) {
        when(vergelijkerResultaatMock.getVerschilGroepen()).thenReturn(verschilGroepen);
        when(consolidatieDataMock.verwijderActieInMRijen(anySetOf(FormeleHistorie.class), anyListOf(Verschil.class))).thenReturn(consolidatieDataMock);
        when(consolidatieDataMock.voegNieuweActiesToe(anySetOf(BRPActie.class))).thenReturn(consolidatieDataMock);
        when(consolidatieDataMock.verwijderCat07Cat13Acties()).thenReturn(consolidatieDataMock);
        when(consolidatieDataMock.bepaalTeVervangenStapels()).thenReturn(deltaStapelMatchSet);
    }
}
