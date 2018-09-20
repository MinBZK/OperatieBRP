/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import nl.bzk.brp.levering.business.toegang.populatie.LeveringinformatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.*;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.MagGroepTonenPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.*;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.hamcrest.Matchers;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class VerantwoordingsinformatieFilterImplTest {

    @Mock
    private LeveringinformatieService leveringinformatieService;

    @InjectMocks
    private final VerantwoordingsinformatieFilter verantwoordingsinformatieFilter =
        new VerantwoordingsinformatieFilterImpl();

    private PersoonHisVolledigView persoonHisVolledigView;

    private final PersoonHisVolledigImpl testPersoon = TestPersoonJohnnyJordaan.maak();

    @Before
    public final void init() {
        persoonHisVolledigView =
            new PersoonHisVolledigView(testPersoon, HistorieVanafPredikaat.geldigOpEnNa(new DatumAttribuut(19550101)));

        final HisPersoonGeboorteModel geboorteGroep = persoonHisVolledigView.getPersoonGeboorteHistorie().getHistorie().iterator().next();
        //Zet de groep op magGeleverdWorden
        geboorteGroep.getDatumGeboorte().setMagGeleverdWorden(true);
        final ActieModel verantwoordingInhoudGeboorte = geboorteGroep.getVerantwoordingInhoud();
        // Zet een record van de geboorte verantwoording op magGeleverdWorden = true.
        verantwoordingInhoudGeboorte.getTijdstipRegistratie().setMagGeleverdWorden(true);

        final HisPersoonBijhoudingModel persoonBijhoudingGroep = persoonHisVolledigView.getPersoonBijhoudingHistorie().getActueleRecord();
        persoonBijhoudingGroep.getBijhoudingspartij().setMagGeleverdWorden(true);
        persoonBijhoudingGroep.getVerantwoordingInhoud().getTijdstipRegistratie().setMagGeleverdWorden(true);

        for (final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : persoonHisVolledigView.getOnderzoeken()) {
            final OnderzoekHisVolledig onderzoekHisVolledig = persoonOnderzoekHisVolledig.getOnderzoek();
            for (final HisOnderzoekModel hisOnderzoekModel : onderzoekHisVolledig.getOnderzoekHistorie()) {
                hisOnderzoekModel.getVerantwoordingInhoud().getTijdstipRegistratie().setMagGeleverdWorden(true);
                hisOnderzoekModel.getVerantwoordingInhoud().setMagGeleverdWorden(true);
            }
        }

        final Set<ElementEnum> geautoriseerdeGroepen = new HashSet<>();
        geautoriseerdeGroepen.add(ElementEnum.PERSOON_GEBOORTE);
        geautoriseerdeGroepen.add(ElementEnum.PERSOON_ONDERZOEK);
        geautoriseerdeGroepen.add(ElementEnum.PERSOON_BIJHOUDING);
        geautoriseerdeGroepen.add(ElementEnum.PERSOON_BIJHOUDING);
        geautoriseerdeGroepen.add(ElementEnum.GERELATEERDEOUDER_OUDERSCHAP);
        geautoriseerdeGroepen.add(ElementEnum.OUDER_OUDERSCHAP);
        geautoriseerdeGroepen.add(ElementEnum.PERSOON_SAMENGESTELDENAAM);
        final MagGroepTonenPredikaat magGroepTonenPredikaat = new MagGroepTonenPredikaat(geautoriseerdeGroepen);
        persoonHisVolledigView.voegPredikaatToe(magGroepTonenPredikaat);
    }

    @Test
    public final void testFilter() {
        assertEquals(5, persoonHisVolledigView.getAdministratieveHandelingen().size());

        verantwoordingsinformatieFilter.filter(persoonHisVolledigView, bouwLeveringinformatie());

        final Set<AdministratieveHandelingHisVolledig> teLeverenAdmHndNa =
            geefAdmHndDieGeleverdMogenWorden(persoonHisVolledigView.getAdministratieveHandelingen());

        assertEquals(3, teLeverenAdmHndNa.size());

        final Set<ActieHisVolledig> teLeverenActies =
            geefActiesDieGeleverdMogenWordenInVerantwoording(persoonHisVolledigView.getAdministratieveHandelingen());
        assertEquals(3, teLeverenActies.size());

        final Set<ActieModel> teLeverenActiesInGroepen = geefActiesDieGeleverdMogenWordenInGroepen(persoonHisVolledigView);
        assertEquals(3, teLeverenActiesInGroepen.size());
    }

    @Test
    public final void testFilterOnderzoek() {

        for (final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : persoonHisVolledigView.getOnderzoeken()) {
            final OnderzoekHisVolledig onderzoekHisVolledig = persoonOnderzoekHisVolledig.getOnderzoek();
            for (final HisOnderzoekModel hisOnderzoekModel : onderzoekHisVolledig.getOnderzoekHistorie()) {
                hisOnderzoekModel.getVerantwoordingInhoud().getTijdstipRegistratie().setMagGeleverdWorden(false);
                hisOnderzoekModel.getVerantwoordingInhoud().setMagGeleverdWorden(false);
            }
        }

        assertEquals(5, persoonHisVolledigView.getAdministratieveHandelingen().size());

        verantwoordingsinformatieFilter.filter(persoonHisVolledigView, bouwLeveringinformatie());

        final Set<AdministratieveHandelingHisVolledig> teLeverenAdmHndNa =
            geefAdmHndDieGeleverdMogenWorden(persoonHisVolledigView.getAdministratieveHandelingen());

        assertEquals(2, teLeverenAdmHndNa.size());

        final Set<ActieHisVolledig> teLeverenActies =
            geefActiesDieGeleverdMogenWordenInVerantwoording(persoonHisVolledigView.getAdministratieveHandelingen());
        assertEquals(2, teLeverenActies.size());

        final Set<ActieModel> teLeverenActiesInGroepen = geefActiesDieGeleverdMogenWordenInGroepen(persoonHisVolledigView);
        assertEquals(2, teLeverenActiesInGroepen.size());
    }

    @Test
    public final void testFilterMetBetrokkeneActieBuitenPLHoofdpersoon() {
        assertEquals(5, persoonHisVolledigView.getAdministratieveHandelingen().size());

        for (final BetrokkenheidHisVolledig betrokkenheid : persoonHisVolledigView.getKindBetrokkenheid().getRelatie().getBetrokkenheden()) {
            if (betrokkenheid instanceof OuderHisVolledig) {
                final HisPersoonSamengesteldeNaamModel actueleRecord =
                    betrokkenheid.getPersoon().getPersoonSamengesteldeNaamHistorie().getActueleRecord();
                actueleRecord.getVerantwoordingInhoud().getSoort().setMagGeleverdWorden(true);
                actueleRecord.getVoornamen().setMagGeleverdWorden(true);
            }
        }

        verantwoordingsinformatieFilter.filter(persoonHisVolledigView, bouwLeveringinformatie());

        final Set<AdministratieveHandelingHisVolledig> teLeverenAdmHndNa =
            geefAdmHndDieGeleverdMogenWorden(persoonHisVolledigView.getAdministratieveHandelingen());
        assertEquals(3, teLeverenAdmHndNa.size());

        final Set<ActieHisVolledig> teLeverenActies =
            geefActiesDieGeleverdMogenWordenInVerantwoording(
                persoonHisVolledigView.getAdministratieveHandelingen());
        assertEquals(3, teLeverenActies.size());

        final Set<ActieModel> teLeverenActiesInGroepen =
            geefActiesDieGeleverdMogenWordenInGroepen(persoonHisVolledigView);

        assertEquals(3, teLeverenActiesInGroepen.size());
    }

    @Test
    public final void testFilterNietRelevanteAdmHnd() {
        final AdministratieveHandelingHisVolledigImpl administratieveHandeling =
            new AdministratieveHandelingHisVolledigImpl(
                new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.DUMMY), null, null,
                new DatumTijdAttribuut(new DatumAttribuut(20130101).toDate()));
        final List<AdministratieveHandelingHisVolledigImpl> administratieveHandelingen = new ArrayList<>(testPersoon.getAdministratieveHandelingen());
        administratieveHandelingen.add(administratieveHandeling);
        ReflectionTestUtils.setField(testPersoon, "administratieveHandelingen", administratieveHandelingen);

        Assume.assumeThat(persoonHisVolledigView.getAdministratieveHandelingen().size(), Matchers.is(6));

        verantwoordingsinformatieFilter.filter(persoonHisVolledigView, bouwLeveringinformatie());

        final Set<AdministratieveHandelingHisVolledig> teLeverenAdmHndNa =
            geefAdmHndDieGeleverdMogenWorden(persoonHisVolledigView.getAdministratieveHandelingen());
        assertEquals(3, teLeverenAdmHndNa.size());

        final Set<ActieHisVolledig> teLeverenActies =
            geefActiesDieGeleverdMogenWordenInVerantwoording(persoonHisVolledigView.getAdministratieveHandelingen());
        assertEquals(3, teLeverenActies.size());

        final Set<ActieModel> teLeverenActiesInGroepen =
            geefActiesDieGeleverdMogenWordenInGroepen(persoonHisVolledigView);

        assertEquals(3, teLeverenActiesInGroepen.size());
    }

    /**
     * Geeft het aantal adm hnd die geleverd mogen worden.
     *
     * @param handelingen de handelingen
     * @return lijst met adm hnd die geleverd mogen worden
     */
    private Set<AdministratieveHandelingHisVolledig> geefAdmHndDieGeleverdMogenWorden(
        final List<AdministratieveHandelingHisVolledig> handelingen)
    {
        final Set<AdministratieveHandelingHisVolledig> teLeverenAdmHnd = new HashSet<>();
        for (final AdministratieveHandelingHisVolledig administratieveHandeling : handelingen) {
            if (administratieveHandeling.isMagGeleverdWorden()) {
                teLeverenAdmHnd.add(administratieveHandeling);
            }
        }
        return teLeverenAdmHnd;
    }

    /**
     * Geeft het aantal adm hnd die geleverd mogen worden.
     *
     * @param handelingen de handelingen
     * @return lijst met acties die geleverd mogen worden
     */
    private Set<ActieHisVolledig> geefActiesDieGeleverdMogenWordenInVerantwoording(
        final List<AdministratieveHandelingHisVolledig> handelingen)
    {
        final Set<ActieHisVolledig> teLeverenActies = new HashSet<>();
        for (final AdministratieveHandelingHisVolledig administratieveHandeling : handelingen) {
            for (final ActieHisVolledig actie : administratieveHandeling.getActies()) {
                if (actie.isMagGeleverdWorden()) {
                    teLeverenActies.add(actie);
                }
            }

        }
        return teLeverenActies;
    }

    /**
     * Geeft het aantal adm hnd die geleverd mogen worden in de groepen.
     *
     * @param persoonHisVolledigViewParam de persoon
     * @return lijst met acties die geleverd mogen worden
     */
    private Set<ActieModel> geefActiesDieGeleverdMogenWordenInGroepen(final PersoonHisVolledigView persoonHisVolledigViewParam) {
        final Set<ActieModel> teLeverenActies = new HashSet<>();
        for (final HistorieEntiteit historieEntiteit : persoonHisVolledigViewParam.getTotaleLijstVanHisElementenOpPersoonsLijst()) {
            if (historieEntiteit instanceof MaterieelVerantwoordbaar) {
                final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = (MaterieelVerantwoordbaar<ActieModel>) historieEntiteit;
                voegToeAlsGeleverdMagWorden(materieelVerantwoordbaar.getVerantwoordingInhoud(), teLeverenActies);
                voegToeAlsGeleverdMagWorden(materieelVerantwoordbaar.getVerantwoordingVerval(), teLeverenActies);
                voegToeAlsGeleverdMagWorden(materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid(), teLeverenActies);
            } else {
                final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = (FormeelVerantwoordbaar<ActieModel>) historieEntiteit;
                voegToeAlsGeleverdMagWorden(formeelVerantwoordbaar.getVerantwoordingInhoud(), teLeverenActies);
                voegToeAlsGeleverdMagWorden(formeelVerantwoordbaar.getVerantwoordingVerval(), teLeverenActies);
            }
        }
        return teLeverenActies;
    }

    /**
     * Voegt een actie toe aan een lijst als deze geleverd mag worden.
     *
     * @param actieModel De actie.
     * @param actieLijst De actie-set.
     */
    private void voegToeAlsGeleverdMagWorden(final ActieModel actieModel, final Set<ActieModel> actieLijst) {
        if (actieModel != null && actieModel.isMagGeleverdWorden()) {
            actieLijst.add(actieModel);
        }
    }

    private Leveringinformatie bouwLeveringinformatie() {
        return new Leveringinformatie(null, null);
    }
}
