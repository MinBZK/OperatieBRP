/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.service.cache.GeldigeAttributenElementenCache;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * ConverteerBevragingElementenServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConverteerBevragingElementenServiceImplTest {

    @Mock
    private GeldigeAttributenElementenCache geldigeAttributenElementenCache;

    @InjectMocks
    private ConverteerBevragingElementenServiceImpl service;

    private Autorisatiebundel autorisatiebundel;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        autorisatiebundel = maakAutorisatieBundel();
        Mockito.when(geldigeAttributenElementenCache.geldigVoorAttribuutAutorisatie(Mockito.any())).thenReturn(true);
    }

    @Test
    public void testValide() throws ConverteerBevragingVerzoekElementException {
        final Set<String> scopingElementen = new HashSet<>();
        scopingElementen.add(getAttribuutElement(Element.PERSOON_ADRES_POSTCODE).getNaam());
        final Set<AttribuutElement> attribuutElementen = service.converteerBevragingElementen(scopingElementen, autorisatiebundel);
        Assert.assertEquals(1, attribuutElementen.size());
    }

    @Test
    public void testOnbekendElement() throws ConverteerBevragingVerzoekElementException {
        final Set<String> scopingElementen = new HashSet<>();
        scopingElementen.add("brp.onbekend");

        thrown.expect(new ConverteerBevragingVerzoekElementExceptionRegelMatcher(Regel.R2216));

        service.converteerBevragingElementen(scopingElementen, autorisatiebundel);
    }

    @Test
    public void testObjectElement() throws ConverteerBevragingVerzoekElementException {
        final Set<String> scopingElementen = new HashSet<>();
        scopingElementen.add(getObjectElement(Element.PERSOON_ADRES).getNaam());

        thrown.expect(new ConverteerBevragingVerzoekElementExceptionRegelMatcher(Regel.R2216));

        service.converteerBevragingElementen(scopingElementen, autorisatiebundel);
    }

    @Test
    public void testGroepElement() throws ConverteerBevragingVerzoekElementException {
        final Set<String> scopingElementen = new HashSet<>();
        scopingElementen.add(getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD).getNaam());

        thrown.expect(new ConverteerBevragingVerzoekElementExceptionRegelMatcher(Regel.R2216));

        service.converteerBevragingElementen(scopingElementen, autorisatiebundel);
    }

    @Test
    public void testStructuurElement() throws ConverteerBevragingVerzoekElementException {
        Mockito.when(geldigeAttributenElementenCache.geldigVoorAttribuutAutorisatie(Mockito.any())).thenReturn(false);

        final Set<String> scopingElementen = new HashSet<>();
        final AttribuutElement attribuutElement = getAttribuutElement(Element.GERELATEERDEOUDER_ROLCODE);
        Assert.assertEquals(SoortElementAutorisatie.STRUCTUUR, attribuutElement.getAutorisatie());
        scopingElementen.add(attribuutElement.getNaam());

        thrown.expect(new ConverteerBevragingVerzoekElementExceptionRegelMatcher(Regel.R2216));

        service.converteerBevragingElementen(scopingElementen, autorisatiebundel);
    }

    @Test
    public void testNietGeautoriseerdVoorScopingAttribuut() throws ConverteerBevragingVerzoekElementException {
        final Set<String> scopingElementen = new HashSet<>();
        scopingElementen.add(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER).getNaam());

        thrown.expect(new ConverteerBevragingVerzoekElementExceptionRegelMatcher(Regel.R2215));

        service.converteerBevragingElementen(scopingElementen, autorisatiebundel);
    }

    @Test
    public void testAttribuutElementIsVerantwoording() throws ConverteerBevragingVerzoekElementException {
        final Set<String> scopingElementen = new HashSet<>();
        final AttribuutElement attribuutElement = getAttribuutElement(Element.DOCUMENT_OMSCHRIJVING);
        scopingElementen.add(attribuutElement.getNaam());

        thrown.expect(new ConverteerBevragingVerzoekElementExceptionRegelMatcher(Regel.R2400));

        service.converteerBevragingElementen(scopingElementen, autorisatiebundel);
    }

    @Test
    public void testAttribuutElementIsOnderzoek() throws ConverteerBevragingVerzoekElementException {
        final Set<String> scopingElementen = new HashSet<>();
        final AttribuutElement attribuutElement = getAttribuutElement(Element.GEGEVENINONDERZOEK_ELEMENTNAAM);
        scopingElementen.add(attribuutElement.getNaam());

        thrown.expect(new ConverteerBevragingVerzoekElementExceptionRegelMatcher(Regel.R2400));

        service.converteerBevragingElementen(scopingElementen, autorisatiebundel);
    }

    private Autorisatiebundel maakAutorisatieBundel() {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, SoortDienst.GEEF_DETAILS_PERSOON);
        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);

        final DienstbundelGroep dbgNationaliteit = new DienstbundelGroep(dienstbundel, Element.PERSOON_NATIONALITEIT_STANDAARD, true, true, true);

        final DienstbundelGroep dbgGerelateerdeOuder = new DienstbundelGroep(dienstbundel, Element.GERELATEERDEOUDER, true, true, true);
        final DienstbundelGroepAttribuut dienstbundelGroepAttribuutGerelOuder =
                new DienstbundelGroepAttribuut(dbgGerelateerdeOuder, Element.GERELATEERDEOUDER_ROLCODE);
        dbgGerelateerdeOuder.addDienstbundelGroepAttribuutSet(dienstbundelGroepAttribuutGerelOuder);

        final DienstbundelGroep dbgAdres = new DienstbundelGroep(dienstbundel, Element.PERSOON_ADRES_STANDAARD, true, true, true);
        final DienstbundelGroepAttribuut dienstbundelGroepAttribuutAdres = new DienstbundelGroepAttribuut(dbgAdres, Element.PERSOON_ADRES_POSTCODE);
        dbgAdres.addDienstbundelGroepAttribuutSet(dienstbundelGroepAttribuutAdres);

        dienstbundel.setDienstbundelGroepSet(Sets.newHashSet(dbgNationaliteit, dbgGerelateerdeOuder, dbgAdres));
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, SoortDienst.GEEF_DETAILS_PERSOON);
        dienst.setDienstbundel(dienstbundel);
        return new Autorisatiebundel(tla, dienst);
    }

    /**
     * Matcher om regels binnen meldingen te checken binnen tests.
     */
    final class ConverteerBevragingVerzoekElementExceptionRegelMatcher extends TypeSafeMatcher<ConverteerBevragingVerzoekElementException> {
        private Regel regel;

        public ConverteerBevragingVerzoekElementExceptionRegelMatcher(final Regel regel) {
            this.regel = regel;
        }

        @Override
        protected boolean matchesSafely(final ConverteerBevragingVerzoekElementException exceptie) {
            return exceptie.getMelding().equals(new Melding(regel));
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("expects regel ").appendValue(regel);
        }

        @Override
        protected void describeMismatchSafely(final ConverteerBevragingVerzoekElementException exceptie, final Description omschrijving) {
            omschrijving.appendText("was ").appendValue(exceptie.getMelding().getRegel());
        }
    }

}
