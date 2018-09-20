/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSetImpl;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNummerverwijzingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonPersoonskaartModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingKiesrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfsrechtModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.apache.commons.collections.Predicate;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

public class PersoonPredikaatViewTest {

    @Test
    public void smokeTestPredikaatFalse() {
        final PersoonHisVolledigImpl persoonHisVolledig = maakPersooon();
        final PersoonPredikaatView view = new PersoonPredikaatView(persoonHisVolledig, new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return false;
            }
        });

        Assert.assertFalse(view.heeftActueleGegevens());
        final List<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst = getTotaleLijstVanHisElementenOpPersoonsLijst(view);
        Assert.assertEquals(1, totaleLijstVanHisElementenOpPersoonsLijst.size());
        Assert.assertNull(totaleLijstVanHisElementenOpPersoonsLijst.get(0));

        final Collection<BetrokkenheidPredikaatView> betrokkenheden = view.getBetrokkenheden();
        Assert.assertTrue(betrokkenheden.isEmpty());
    }

    @Test
    public void smokeTestPredikaatTrue() {
        final PersoonHisVolledigImpl persoonHisVolledig = maakPersooon();
        final PersoonPredikaatView view = new PersoonPredikaatView(persoonHisVolledig, new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return true;
            }
        });

        Assert.assertTrue(view.heeftActueleGegevens());
        final List<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst = getTotaleLijstVanHisElementenOpPersoonsLijst(view);
        Assert.assertTrue(totaleLijstVanHisElementenOpPersoonsLijst.size() > 1);
        Assert.assertNotNull(totaleLijstVanHisElementenOpPersoonsLijst.get(0));

        final Collection<BetrokkenheidPredikaatView> betrokkenheden = view.getBetrokkenheden();
        Assert.assertTrue(betrokkenheden.size() > 1);

    }

    @Test
    public void testMetActiesPredikaatZonderOngeldigeActies() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final PersoonUitgeslotenActiesPredikaat persoonPredikaatUitgeslotenActiesPredikaat = new PersoonUitgeslotenActiesPredikaat();

        final PersoonPredikaatView persoonPredikaatView = new PersoonPredikaatView(persoonHisVolledig, persoonPredikaatUitgeslotenActiesPredikaat);

        final Collection<PersoonVoornaamPredikaatView> voornamen = persoonPredikaatView.getVoornamen();

        final List<String> actueleVoornamen = new ArrayList<>();
        for (PersoonVoornaamPredikaatView voornaam : voornamen) {
            actueleVoornamen.add(voornaam.getStandaard().getNaam().getWaarde());
        }
        assertThat(actueleVoornamen, containsInAnyOrder("Sjonnie", "Donny"));
        assertThat(actueleVoornamen.size(), equalTo(2));
    }

    @Test
    public void testPredikaatLevertMeerdereRecordsOpPerGroep() {
        final PersoonHisVolledig persoonHisVolledig = Mockito.mock(PersoonHisVolledig.class);
        Mockito.when(persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie()).thenReturn(
            maakFormeleHisSetMet2Voorkomens(HisPersoonAfgeleidAdministratiefModel.class));
        Mockito.when(persoonHisVolledig.getPersoonIdentificatienummersHistorie()).thenReturn(
            maakMaterieleHisSetMet2Voorkomens(HisPersoonIdentificatienummersModel.class));
        Mockito.when(persoonHisVolledig.getPersoonSamengesteldeNaamHistorie()).thenReturn(
            maakMaterieleHisSetMet2Voorkomens(HisPersoonSamengesteldeNaamModel.class));
        Mockito.when(persoonHisVolledig.getPersoonGeboorteHistorie()).thenReturn(
            maakFormeleHisSetMet2Voorkomens(HisPersoonGeboorteModel.class));
        Mockito.when(persoonHisVolledig.getPersoonGeslachtsaanduidingHistorie()).thenReturn(
            maakMaterieleHisSetMet2Voorkomens(HisPersoonGeslachtsaanduidingModel.class));
        Mockito.when(persoonHisVolledig.getPersoonInschrijvingHistorie()).thenReturn(
            maakFormeleHisSetMet2Voorkomens(HisPersoonInschrijvingModel.class));
        Mockito.when(persoonHisVolledig.getPersoonNummerverwijzingHistorie()).thenReturn(
            maakMaterieleHisSetMet2Voorkomens(HisPersoonNummerverwijzingModel.class));
        Mockito.when(persoonHisVolledig.getPersoonBijhoudingHistorie()).thenReturn(
            maakMaterieleHisSetMet2Voorkomens(HisPersoonBijhoudingModel.class));
        Mockito.when(persoonHisVolledig.getPersoonOverlijdenHistorie()).thenReturn(
            maakFormeleHisSetMet2Voorkomens(HisPersoonOverlijdenModel.class));
        Mockito.when(persoonHisVolledig.getPersoonNaamgebruikHistorie()).thenReturn(
            maakFormeleHisSetMet2Voorkomens(HisPersoonNaamgebruikModel.class));
        Mockito.when(persoonHisVolledig.getPersoonMigratieHistorie()).thenReturn(
            maakMaterieleHisSetMet2Voorkomens(HisPersoonMigratieModel.class));
        Mockito.when(persoonHisVolledig.getPersoonVerblijfsrechtHistorie()).thenReturn(
            maakFormeleHisSetMet2Voorkomens(HisPersoonVerblijfsrechtModel.class));
        Mockito.when(persoonHisVolledig.getPersoonUitsluitingKiesrechtHistorie()).thenReturn(
            maakFormeleHisSetMet2Voorkomens(HisPersoonUitsluitingKiesrechtModel.class));
        Mockito.when(persoonHisVolledig.getPersoonDeelnameEUVerkiezingenHistorie()).thenReturn(
            maakFormeleHisSetMet2Voorkomens(HisPersoonDeelnameEUVerkiezingenModel.class));
        Mockito.when(persoonHisVolledig.getPersoonPersoonskaartHistorie()).thenReturn(
            maakFormeleHisSetMet2Voorkomens(HisPersoonPersoonskaartModel.class));


        final PersoonPredikaatView view = new PersoonPredikaatView(persoonHisVolledig, new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return true;
            }
        });

        final List<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst = getTotaleLijstVanHisElementenOpPersoonsLijst(view);
        Assert.assertFalse(totaleLijstVanHisElementenOpPersoonsLijst.isEmpty());
    }

    @Test
    @Ignore("Werkt niet omdat de builders de acties niet goed vullen. Zie ook TEAMBRP-2169")
    public void testMetActiesPredikaatMetOngeldigeActies() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonJohnnyJordaan.maak();

        Long actieId = null;
        for (PersoonVoornaamHisVolledig persoonVoornaamHisVolledig : persoonHisVolledig.getVoornamen()) {
            if (persoonVoornaamHisVolledig.getVolgnummer().getWaarde().equals(2)) {
                //Zet de actieInhoud van de oude versie van de tweede voornaam op ongeldig
                actieId = persoonVoornaamHisVolledig.getPersoonVoornaamHistorie().getActueleRecord().getVerantwoordingInhoud().getID();
            }
        }

        final Set<Long> uitgeslotenActies = new HashSet<>(Arrays.asList(new Long[]{ actieId }));
        final PersoonUitgeslotenActiesPredikaat persoonPredikaatUitgeslotenActiesPredikaat =
            new PersoonUitgeslotenActiesPredikaat(uitgeslotenActies);

        final PersoonPredikaatView persoonPredikaatView = new PersoonPredikaatView(persoonHisVolledig, persoonPredikaatUitgeslotenActiesPredikaat);

        final Collection<PersoonVoornaamPredikaatView> voornamen = persoonPredikaatView.getVoornamen();

        final StringBuffer stringBuffer = new StringBuffer();
        for (PersoonVoornaamPredikaatView voornaam : voornamen) {
            stringBuffer.append(voornaam.getStandaard().getNaam().getWaarde());
        }
        assertEquals("GonnyDonny", stringBuffer.toString());
    }

    private PersoonHisVolledigImpl maakPersooon() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        TestPersoonAntwoordPersoon.voegKindBetrokkenheidToeAanPersoon(persoonHisVolledig);
        TestPersoonAntwoordPersoon.voegPartnerBetrokkenheidToeAanPersoon(persoonHisVolledig);
        return persoonHisVolledig;
    }

    private List<HistorieEntiteit> getTotaleLijstVanHisElementenOpPersoonsLijst(final PersoonPredikaatView view) {
        final List<HistorieEntiteit> resultaat = new ArrayList<>();
        resultaat.addAll(getAlleHistorieRecords(view));
        resultaat.addAll(getAlleHistorieRecordsVanuitLijsten(view));

        if (view.getIndicatieDerdeHeeftGezag() != null && view.getIndicatieDerdeHeeftGezag().getStandaard() != null) {
            resultaat.add(view.getIndicatieDerdeHeeftGezag().getStandaard());
        }
        if (view.getIndicatieOnderCuratele() != null && view.getIndicatieOnderCuratele().getStandaard() != null) {
            resultaat.add(view.getIndicatieOnderCuratele().getStandaard());
        }
        if (view.getIndicatieVolledigeVerstrekkingsbeperking() != null && view.getIndicatieVolledigeVerstrekkingsbeperking().getStandaard() != null) {
            resultaat.add(view.getIndicatieVolledigeVerstrekkingsbeperking().getStandaard());
        }
        if (view.getIndicatieVastgesteldNietNederlander() != null && view.getIndicatieVastgesteldNietNederlander().getStandaard() != null) {
            resultaat.add(view.getIndicatieVastgesteldNietNederlander().getStandaard());
        }
        if (view.getIndicatieBehandeldAlsNederlander() != null && view.getIndicatieBehandeldAlsNederlander().getStandaard() != null) {
            resultaat.add(view.getIndicatieBehandeldAlsNederlander().getStandaard());
        }
        if (view.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument() != null
            && view.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument().getStandaard() != null)
        {
            resultaat.add(view.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument().getStandaard());
        }
        if (view.getIndicatieStaatloos() != null && view.getIndicatieStaatloos().getStandaard() != null) {
            resultaat.add(view.getIndicatieStaatloos().getStandaard());
        }
        if (view.getIndicatieBijzondereVerblijfsrechtelijkePositie() != null
            && view.getIndicatieBijzondereVerblijfsrechtelijkePositie().getStandaard() != null)
        {
            resultaat.add(view.getIndicatieBijzondereVerblijfsrechtelijkePositie().getStandaard());
        }
        return resultaat;
    }

    private List<HistorieEntiteit> getAlleHistorieRecordsVanuitLijsten(final PersoonPredikaatView view) {
        final List<HistorieEntiteit> resultaat = new ArrayList<>();
        for (final PersoonAdresPredikaatView adresHisVolledig : view.getAdressen()) {
            resultaat.add(adresHisVolledig.getStandaard());
        }
        for (final PersoonGeslachtsnaamcomponentPredikaatView geslachtsnaamcomponent : view.getGeslachtsnaamcomponenten()) {
            resultaat.add(geslachtsnaamcomponent.getStandaard());
        }
        for (final PersoonIndicatiePredikaatView indicatiePredikaatView : view.getIndicaties()) {
            resultaat.add(indicatiePredikaatView.getStandaard());
        }
        for (final PersoonNationaliteitPredikaatView nationaliteitPredikaatView : view.getNationaliteiten()) {
            resultaat.add(nationaliteitPredikaatView.getStandaard());
        }
        for (final PersoonReisdocumentPredikaatView reisdocumentPredikaatView : view.getReisdocumenten()) {
            resultaat.add(reisdocumentPredikaatView.getStandaard());
        }
        for (final PersoonVoornaamPredikaatView voornaamPredikaatView : view.getVoornamen()) {
            resultaat.add(voornaamPredikaatView.getStandaard());
        }
        for (final PersoonVerificatiePredikaatView verificatiePredikaatView : view.getVerificaties()) {
            resultaat.add(verificatiePredikaatView.getStandaard());
        }
        return resultaat;
    }

    private Set<HistorieEntiteit> getAlleHistorieRecords(final PersoonPredikaatView view) {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        resultaat.add(view.getAfgeleidAdministratief());
        resultaat.add(view.getIdentificatienummers());
        resultaat.add(view.getSamengesteldeNaam());
        resultaat.add(view.getGeboorte());
        resultaat.add(view.getGeslachtsaanduiding());
        resultaat.add(view.getInschrijving());
        resultaat.add(view.getNummerverwijzing());
        resultaat.add(view.getBijhouding());
        resultaat.add(view.getOverlijden());
        resultaat.add(view.getNaamgebruik());
        resultaat.add(view.getMigratie());
        resultaat.add(view.getVerblijfsrecht());
        resultaat.add(view.getUitsluitingKiesrecht());
        resultaat.add(view.getDeelnameEUVerkiezingen());
        resultaat.add(view.getPersoonskaart());
        return resultaat;
    }

    private <T extends AbstractFormeelHistorischMetActieVerantwoording> FormeleHistorieSet maakFormeleHisSetMet2Voorkomens(final Class<T> clazz) {
        final HashSet<T> hashSet = new HashSet<>();
        hashSet.add(Mockito.mock(clazz));
        hashSet.add(Mockito.mock(clazz));
        return new FormeleHistorieSetImpl<>(hashSet);
    }

    private <T extends AbstractMaterieelHistorischMetActieVerantwoording> MaterieleHistorieSet maakMaterieleHisSetMet2Voorkomens(final Class<T> clazz) {
        final HashSet<T> hashSet = new HashSet<>();
        hashSet.add(Mockito.mock(clazz));
        hashSet.add(Mockito.mock(clazz));
        return new MaterieleHistorieSetImpl<>(hashSet);
    }

}
