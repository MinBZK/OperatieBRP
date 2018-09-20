/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.util.PersoonHisVolledigViewUtil;
import nl.bzk.brp.util.PersoonViewUtil;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.apache.commons.collections.Predicate;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 */
public class PersoonHisVolledigViewTest {

    private final PersoonHisVolledigImpl persoon   = TestPersoonJohnnyJordaan.maak();
    private final Predicate              predikaat = HistorieVanafPredikaat.geldigOpEnNa(DatumAttribuut.vandaag());

    @Test
    public void testKanEenViewMaken() {
        // act
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);

        // assert
        assertThat(view.heeftBetrokkenhedenVoorLeveren(), is(false));
        assertThat(view.getBetrokkenheden().size(), is(4));
    }

    @Test
    public void zouGeenBetrokkenhedenMoetenHebben() {
        final PersoonHisVolledigImpl persoonHisVolledigImpl = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoonHisVolledigImpl, new AdministratieveHandelingDeltaPredikaat(1L));

        assertThat(view.getBetrokkenheden().size(), is(0));
    }

    @Test
    public void zouBetrokkenHedenUitDeLokaleCacheMoetenHalen() {
        // act
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);

        // assert
        assertThat(view.heeftBetrokkenhedenVoorLeveren(), is(false));
        assertThat(view.getBetrokkenheden().size(), is(4));

        persoon.getBetrokkenheden().add(Mockito.mock(KindHisVolledigImpl.class));

        assertThat(view.getBetrokkenheden().size(), is(4));
    }

    @Test
    public void zouBetrokkenHedenMoetenHebbenInDeViewIndienDeRelatieGeleverdMagWorden() {
        // act
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);
        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView: view.getBetrokkenheden()) {
            ((RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie()).setMagLeveren(true);
        }

        assertThat(view.heeftBetrokkenhedenVoorLeveren(), is(true));
    }

    @Test
    public void testKanVerwerkingsSoortZettenEnLezen() {
        // arrange
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);

        // act
        view.setVerwerkingssoort(Verwerkingssoort.IDENTIFICATIE);

        // assert
        assertThat(view.getVerwerkingssoort(), is(Verwerkingssoort.IDENTIFICATIE));
    }

    @Test
    public void testKanCommunicatieIDZettenEnLezen() {
        // arrange
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);
        final String commId = "FooBar-123";

        // act
        view.setCommunicatieID(commId);

        // assert
        assertThat(view.getCommunicatieID(), is(commId));
    }

    @Test
    public void testElementLijsten() {
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);

        final List<HistorieEntiteit> totaleLijst = view.getTotaleLijstVanHisElementenOpPersoonsLijst();
        final Set<HistorieEntiteit> groepenLijst = view.getAlleHistorieRecords();
        final Set<HistorieEntiteit> nietIdentificerendeHistorieRecords = view.getNietIdentificerendeHistorieRecords();
        final Set<HistorieEntiteit> identificerendeHistorieRecords = view.getIdentificerendeHistorieRecords();

        assertTrue(totaleLijst.containsAll(groepenLijst));
        assertTrue(totaleLijst.containsAll(nietIdentificerendeHistorieRecords));
        assertTrue(totaleLijst.containsAll(identificerendeHistorieRecords));
        assertEquals(32, totaleLijst.size());
        assertEquals(8, groepenLijst.size());
        assertEquals(6, nietIdentificerendeHistorieRecords.size());
        assertEquals(2, identificerendeHistorieRecords.size());
    }

    @Test
    public void testHeeftAdministratieveHandelingenVoorLeverenLeeg() {
        persoon.getAdministratieveHandelingen().removeAll(persoon.getAdministratieveHandelingen());

        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);

        assertFalse(view.heeftAdministratieveHandelingenVoorLeveren());
    }

    @Test
    public void testHeeftAdministratieveHandelingenVoorLeverenFalse() {
        for (final AdministratieveHandelingHisVolledig administratieveHandeling : persoon.getAdministratieveHandelingen()) {
            administratieveHandeling.setMagGeleverdWorden(false);
        }

        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);

        view.getAdministratieveHandelingen().add(mock(AdministratieveHandelingHisVolledigImpl.class));

        assertFalse(view.heeftAdministratieveHandelingenVoorLeveren());
    }

    @Test
    public void testHeeftAdministratieveHandelingenVoorLeverenTrue() {
        for (final AdministratieveHandelingHisVolledig administratieveHandeling : persoon.getAdministratieveHandelingen()) {
            administratieveHandeling.setMagGeleverdWorden(true);
        }

        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);

        assertTrue(view.heeftAdministratieveHandelingenVoorLeveren());
    }

    @Test
    public void testAttributenVerwijzenNaarGroepen() {
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);
        PersoonHisVolledigViewUtil.zetGroepenOpAttributen(view);

        for (final HistorieEntiteit historieEntiteit : view.getAlleHistorieRecords()) {
            if (historieEntiteit instanceof Groep) {
                final Groep groep = (Groep) historieEntiteit;
                final List<Attribuut> attributen = groep.getAttributen();
                for (final Attribuut attribuut : attributen) {
                    final String foutmelding =
                        "Attribuut (" + attribuut.getClass().getSimpleName() + ") is aan incorrecte groep gekoppeld: " + groep.getClass().getSimpleName();
                    assertEquals(foutmelding, groep, attribuut.getGroep());
                }
            }
        }
    }

    @Test
    public void testHeeftOnderzoekshistorieVoorLeveren() {
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);
        PersoonViewUtil.zetAlleMagGeleverdWordenVlaggen(view);

        assertThat(view.heeftOnderzoekshistorieVoorLeveren(), is(true));
    }

    @Test
    public void testHeeftGeenOnderzoekshistorieVoorLeverenBijGeenOnderzoeken() {
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);
        PersoonViewUtil.zetAlleMagGeleverdWordenVlaggen(view);
        persoon.getOnderzoeken().removeAll(persoon.getOnderzoeken());

        assertThat(view.heeftOnderzoekshistorieVoorLeveren(), is(false));
    }

    @Test
    public void testHeeftGeenOnderzoekshistorieVoorLeverenBijOnderzoekenZonderGegevens() {
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoon, predikaat);
        final OnderzoekHisVolledigImpl onderzoek = persoon.getOnderzoeken().iterator().next().getOnderzoek();
        onderzoek.getGegevensInOnderzoek().removeAll(onderzoek.getGegevensInOnderzoek());

        assertThat(view.heeftOnderzoekshistorieVoorLeveren(), is(false));
    }

}
