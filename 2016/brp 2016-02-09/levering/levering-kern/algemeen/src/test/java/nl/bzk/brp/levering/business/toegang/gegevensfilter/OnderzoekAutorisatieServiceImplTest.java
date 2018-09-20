/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.PersoonHisVolledigViewUtil;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor {@link OnderzoekAutorisatieServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class OnderzoekAutorisatieServiceImplTest {

    @Mock
    private StamTabelService stamTabelService;

    @InjectMocks
    private final OnderzoekAutorisatieService onderzoekAutorisatieService = new OnderzoekAutorisatieServiceImpl();

    private PersoonHisVolledigView                persoonHisVolledigView;
    private Map<Integer, Map<Integer, List<Attribuut>>> persoonIdAttributenMap;
    private BurgerservicenummerAttribuut          attribuut;
    private Map<Integer, List<Attribuut>>               elementMap;

    @Before
    public void before() {
        persoonHisVolledigView = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);
        PersoonHisVolledigViewUtil.initialiseerView(persoonHisVolledigView);
        persoonIdAttributenMap = new HashMap<>();
        elementMap = new HashMap<>();
        attribuut = persoonHisVolledigView.getPersoonIdentificatienummersHistorie().getActueleRecord().getBurgerservicenummer();
        attribuut.setMagGeleverdWorden(true);
        elementMap.put(1, Collections.<Attribuut>singletonList(attribuut));
        persoonIdAttributenMap.put(1, elementMap);
        final Element element = TestElementBuilder.maker().metSoort(SoortElement.DUMMY).maak();
        Mockito.when(stamTabelService.geefElementById(Mockito.anyInt())).thenReturn(element);
    }

    @Test
    public void testAutoriseerOnderzoeken() {
        final List<Attribuut> geraakteAttributen = onderzoekAutorisatieService.autoriseerOnderzoeken(persoonHisVolledigView, persoonIdAttributenMap);

        final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig = persoonHisVolledigView.getOnderzoeken().iterator().next().getOnderzoek()
            .getGegevensInOnderzoek().iterator().next();
        assertThat(gegevenInOnderzoekHisVolledig.getVoorkomenSleutelGegeven().isMagGeleverdWorden(), is(true));
        assertThat(gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven().isMagGeleverdWorden(), is(true));
        assertThat(gegevenInOnderzoekHisVolledig.getElement().isMagGeleverdWorden(), is(true));

        Assert.assertTrue(geraakteAttributen.size() > 0);
    }

    @Test
    public void testAutoriseerOnderzoekenGegevenNietInPersoonslijst() {
        attribuut = new BurgerservicenummerAttribuut(123456789);
        attribuut.setMagGeleverdWorden(true);
        elementMap.remove(1);
        elementMap.put(1, Collections.<Attribuut>singletonList(attribuut));
        persoonIdAttributenMap.remove(1);
        persoonIdAttributenMap.put(1, elementMap);

        final List<Attribuut> geraakteAttributen = onderzoekAutorisatieService.autoriseerOnderzoeken(persoonHisVolledigView, persoonIdAttributenMap);

        final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig = persoonHisVolledigView.getOnderzoeken().iterator().next().getOnderzoek()
            .getGegevensInOnderzoek().iterator().next();
        assertThat(gegevenInOnderzoekHisVolledig.getVoorkomenSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getElement().isMagGeleverdWorden(), is(false));

        assertEquals(0, geraakteAttributen.size());
    }

    @Test
    public void testAutoriseerOnderzoekenAttribuutMagNietGeleverdWorden() {
        persoonHisVolledigView.getPersoonIdentificatienummersHistorie().getActueleRecord().getBurgerservicenummer().setMagGeleverdWorden(false);
        final List<Attribuut> geraakteAttributen = onderzoekAutorisatieService.autoriseerOnderzoeken(persoonHisVolledigView, persoonIdAttributenMap);

        final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig = persoonHisVolledigView.getOnderzoeken().iterator().next().getOnderzoek()
            .getGegevensInOnderzoek().iterator().next();
        assertThat(gegevenInOnderzoekHisVolledig.getVoorkomenSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getElement().isMagGeleverdWorden(), is(false));

        assertEquals(0, geraakteAttributen.size());
    }

    @Test
    public void testAutoriseerOnderzoekenNietAlsAttribuutNietGeleverdMagWorden() {
        attribuut.setMagGeleverdWorden(false);

        final List<Attribuut> geraakteAttributen = onderzoekAutorisatieService.autoriseerOnderzoeken(persoonHisVolledigView, persoonIdAttributenMap);

        final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig = persoonHisVolledigView.getOnderzoeken().iterator().next().getOnderzoek()
            .getGegevensInOnderzoek()
            .iterator().next();
        assertThat(gegevenInOnderzoekHisVolledig.getVoorkomenSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getElement().isMagGeleverdWorden(), is(false));

        assertEquals(0, geraakteAttributen.size());
    }

    @Test
    public void testAutoriseerOnderzoekenNietAlsAttribuutNietGevonden() {
        elementMap.put(1, null);

        final List<Attribuut> geraakteAttributen = onderzoekAutorisatieService.autoriseerOnderzoeken(persoonHisVolledigView, persoonIdAttributenMap);

        final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig = persoonHisVolledigView.getOnderzoeken().iterator().next().getOnderzoek()
            .getGegevensInOnderzoek()
            .iterator().next();
        assertThat(gegevenInOnderzoekHisVolledig.getVoorkomenSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getElement().isMagGeleverdWorden(), is(false));

        assertEquals(0, geraakteAttributen.size());
    }

    @Test
    public void testAutoriseerOnderzoekenNietAlsPersoonGeenAttributenMapHeeft() {
        persoonIdAttributenMap.put(1, null);

        final List<Attribuut> geraakteAttributen = onderzoekAutorisatieService.autoriseerOnderzoeken(persoonHisVolledigView, persoonIdAttributenMap);

        final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig = persoonHisVolledigView.getOnderzoeken().iterator().next().getOnderzoek()
            .getGegevensInOnderzoek()
            .iterator().next();
        assertThat(gegevenInOnderzoekHisVolledig.getVoorkomenSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getElement().isMagGeleverdWorden(), is(false));

        assertEquals(0, geraakteAttributen.size());
    }

    @Test
    public void testAutoriseerOnderzoekenNietAlsOnderzoekHeeftGeenHistorieRecords() {
        final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig
            = persoonHisVolledigView.getOnderzoeken().iterator().next().getOnderzoek().getGegevensInOnderzoek().iterator().next();

        final PersoonHisVolledig persoon = persoonHisVolledigView.getPersoon();
        ReflectionTestUtils.setField(persoon, "onderzoeken", new HashSet<PersoonOnderzoekHisVolledigImpl>());
        final List<Attribuut> geraakteAttributen = onderzoekAutorisatieService.autoriseerOnderzoeken(persoonHisVolledigView, persoonIdAttributenMap);

        assertThat(gegevenInOnderzoekHisVolledig.getVoorkomenSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven().isMagGeleverdWorden(), is(false));
        assertThat(gegevenInOnderzoekHisVolledig.getElement().isMagGeleverdWorden(), is(false));

        assertEquals(0, geraakteAttributen.size());
    }
}
