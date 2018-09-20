/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.dataaccess.repository.ToegangBijhoudingsautorisatieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.BijhoudingsautorisatieSoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestBijhoudingsautorisatieSoortAdministratieveHandelingBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangBijhoudingsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangBijhoudingsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor {@link BijhoudingsautorisatieServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BijhoudingsautorisatieServiceImplTest {

    public static final String TRANSPORTEUR_OIN  = "123";
    public static final String ONDERTEKENAAR_OIN = "123";
    public static final int    ZENDENDE_PARTIJ   = 123;

    @InjectMocks
    private BijhoudingsautorisatieServiceImpl service;

    @Mock
    private ToegangBijhoudingsautorisatieRepository toegangBijhoudingsautorisatieRepository;

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private Partij                     transporteur    = TestPartijBuilder.maker().metId(1).metOin(new OINAttribuut(TRANSPORTEUR_OIN)).maak();
    private Partij                     ondertekenaar   = TestPartijBuilder.maker().metId(2).metOin(new OINAttribuut(ONDERTEKENAAR_OIN)).maak();
    private AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ondertekenaar, transporteur);

    private ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie;

    @Before
    public void before() {
        Partij partij = TestPartijBuilder.maker().metCode(ZENDENDE_PARTIJ).metOin(new OINAttribuut(Integer.toString(ZENDENDE_PARTIJ))).maak();
        ReflectionTestUtils.setField(partij, "partijrollen", new HashSet<>(singletonList(new PartijRol(partij, Rol.BIJHOUDINGSORGAAN_COLLEGE, null,
            null))));
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ))).thenReturn(partij);
        toegangBijhoudingsautorisatie = TestToegangBijhoudingsautorisatieBuilder.maker().metDatumIngang(DatumAttribuut.gisteren())
            .metDatumEinde(DatumAttribuut.morgen()).metGeautoriseerdeHandelingen(Collections.<BijhoudingsautorisatieSoortAdministratieveHandeling>emptyList()).maak();
        List<ToegangBijhoudingsautorisatie> toegangBijhoudingsautorisaties = singletonList(toegangBijhoudingsautorisatie);
        when(toegangBijhoudingsautorisatieRepository.geefToegangBijhoudingsautorisatie(ZENDENDE_PARTIJ)).thenReturn(toegangBijhoudingsautorisaties);
    }

    @Test
    public void gooitExceptieMetRegelAuth0001AlsGeenOffloadGegevens() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.AUTH0001));

        service.controleerAutorisatie(null, "999", null);
    }

    @Test
    public void gooitExceptieMetRegelAuth0001AlsGeenZendendePartij() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.AUTH0001));

        service.controleerAutorisatie(null, null, offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR2120AlsGeenBijhouder() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2120));
        Partij partij = TestPartijBuilder.maker().maak();
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ))).thenReturn(partij);

        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR2120AlsPartijOnbekend() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2120));
        Partij partij = TestPartijBuilder.maker().maak();
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ))).thenReturn(partij);

        service.controleerAutorisatie(null, Integer.toString(999), offloadGegevens);
    }

    @Test
    public void isGeautoriseerdAlsZendendePartijIsOndertekenaarEnTransporteurZonderHandelingen() throws AutorisatieException {
        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void isGeautoriseerdAlsZendendePartijIsOndertekenaarEnTransporteurEnHandelingGeautoriseerd() throws AutorisatieException {
        final List<BijhoudingsautorisatieSoortAdministratieveHandeling> geautoriseerdeHandelingen = new ArrayList<>();
        geautoriseerdeHandelingen.add(TestBijhoudingsautorisatieSoortAdministratieveHandelingBuilder.maker().metToegangBijhoudingsautorisatie
            (toegangBijhoudingsautorisatie).metSoortAdministratieveHandeling(SoortAdministratieveHandeling
            .AANVANG_ONDERZOEK).maak());
        ReflectionTestUtils.setField(toegangBijhoudingsautorisatie, "geautoriseerdeHandelingen", geautoriseerdeHandelingen);

        service.controleerAutorisatie(SoortAdministratieveHandeling.AANVANG_ONDERZOEK, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void isGeautoriseerdAlsZendendePartijIsNietOndertekenaarEnNietTransporteur() throws AutorisatieException {
        transporteur = TestPartijBuilder.maker().metId(1).metOin(new OINAttribuut("321")).maak();
        ondertekenaar = TestPartijBuilder.maker().metId(2).metOin(new OINAttribuut("321")).maak();
        offloadGegevens = new AutorisatieOffloadGegevens(ondertekenaar, transporteur);

        toegangBijhoudingsautorisatie = TestToegangBijhoudingsautorisatieBuilder.maker().metOndertekenaar(ondertekenaar)
            .metTransporteur(transporteur).metDatumIngang(DatumAttribuut.gisteren()).metDatumEinde(DatumAttribuut.morgen()).metGeautoriseerdeHandelingen
                (Collections.<BijhoudingsautorisatieSoortAdministratieveHandeling>emptyList()).maak();
        List<ToegangBijhoudingsautorisatie> toegangBijhoudingsautorisaties = singletonList(toegangBijhoudingsautorisatie);
        when(toegangBijhoudingsautorisatieRepository.geefToegangBijhoudingsautorisatie(ZENDENDE_PARTIJ)).thenReturn(toegangBijhoudingsautorisaties);

        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void isGeautoriseerdAlsZendendePartijIsOndertekenaarMaarNietTransporteur() throws AutorisatieException {
        transporteur = TestPartijBuilder.maker().metId(1).metOin(new OINAttribuut("321")).maak();
        offloadGegevens = new AutorisatieOffloadGegevens(ondertekenaar, transporteur);

        toegangBijhoudingsautorisatie = TestToegangBijhoudingsautorisatieBuilder.maker().metOndertekenaar(ondertekenaar)
            .metTransporteur(transporteur).metDatumIngang(DatumAttribuut.gisteren()).metDatumEinde(DatumAttribuut.morgen()).metGeautoriseerdeHandelingen
                (Collections.<BijhoudingsautorisatieSoortAdministratieveHandeling>emptyList()).maak();
        List<ToegangBijhoudingsautorisatie> toegangBijhoudingsautorisaties = singletonList(toegangBijhoudingsautorisatie);
        when(toegangBijhoudingsautorisatieRepository.geefToegangBijhoudingsautorisatie(ZENDENDE_PARTIJ)).thenReturn(toegangBijhoudingsautorisaties);

        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void isGeautoriseerdAlsZendendePartijIsNietOndertekenaarMaarWelTransporteur() throws AutorisatieException {
        ondertekenaar = TestPartijBuilder.maker().metId(2).metOin(new OINAttribuut("321")).maak();
        offloadGegevens = new AutorisatieOffloadGegevens(ondertekenaar, transporteur);

        toegangBijhoudingsautorisatie = TestToegangBijhoudingsautorisatieBuilder.maker().metOndertekenaar(ondertekenaar)
            .metTransporteur(transporteur).metDatumIngang(DatumAttribuut.gisteren()).metDatumEinde(DatumAttribuut.morgen()).metGeautoriseerdeHandelingen
                (Collections.<BijhoudingsautorisatieSoortAdministratieveHandeling>emptyList()).maak();
        List<ToegangBijhoudingsautorisatie> toegangBijhoudingsautorisaties = singletonList(toegangBijhoudingsautorisatie);
        when(toegangBijhoudingsautorisatieRepository.geefToegangBijhoudingsautorisatie(ZENDENDE_PARTIJ)).thenReturn(toegangBijhoudingsautorisaties);

        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR2120AlsGeenAutorisatiesGevonden() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2120));
        when(toegangBijhoudingsautorisatieRepository.geefToegangBijhoudingsautorisatie(ZENDENDE_PARTIJ))
            .thenReturn(Collections.<ToegangBijhoudingsautorisatie>emptyList());

        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR2121AlsOndertekenaarNietGeautoriseerd() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2121));
        ondertekenaar = TestPartijBuilder.maker().metId(2).metOin(new OINAttribuut("321")).maak();
        offloadGegevens = new AutorisatieOffloadGegevens(ondertekenaar, transporteur);

        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR2122AlsTransporteurNietGeautoriseerd() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2122));
        transporteur = TestPartijBuilder.maker().metId(2).metOin(new OINAttribuut("321")).maak();
        offloadGegevens = new AutorisatieOffloadGegevens(ondertekenaar, transporteur);

        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR1258AlsSysteemDatumKleinerDanDatumAanvangAutorisatie() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R1258));
        toegangBijhoudingsautorisatie = TestToegangBijhoudingsautorisatieBuilder.maker().metOndertekenaar(ondertekenaar)
            .metTransporteur(transporteur).metDatumIngang(DatumAttribuut.morgen()).metDatumEinde(DatumAttribuut.morgen()).maak();
        when(toegangBijhoudingsautorisatieRepository.geefToegangBijhoudingsautorisatie(ZENDENDE_PARTIJ))
            .thenReturn(singletonList(toegangBijhoudingsautorisatie));

        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR1258AlsSysteemDatumGroterDanDatumEindeAutorisatie() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R1258));
        toegangBijhoudingsautorisatie = TestToegangBijhoudingsautorisatieBuilder.maker().metOndertekenaar(ondertekenaar)
            .metTransporteur(transporteur).metDatumIngang(DatumAttribuut.gisteren()).metDatumEinde(DatumAttribuut.gisteren()).maak();
        when(toegangBijhoudingsautorisatieRepository.geefToegangBijhoudingsautorisatie(ZENDENDE_PARTIJ))
            .thenReturn(singletonList(toegangBijhoudingsautorisatie));

        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR2052AlsDeAutorisatieGeblokkeerdIs() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2052));
        toegangBijhoudingsautorisatie = TestToegangBijhoudingsautorisatieBuilder.maker().metOndertekenaar(ondertekenaar)
            .metTransporteur(transporteur).metDatumIngang(DatumAttribuut.gisteren()).metDatumEinde(DatumAttribuut.morgen()).metIndicatieGeblokkeerd(true)
            .maak();
        when(toegangBijhoudingsautorisatieRepository.geefToegangBijhoudingsautorisatie(ZENDENDE_PARTIJ))
            .thenReturn(singletonList(toegangBijhoudingsautorisatie));

        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR1257AlsCombinatieOndertekenaarEnTransporteurOnbekendIs() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R1257));
        Partij ondertekenaar2 = TestPartijBuilder.maker().metId(1).metOin(new OINAttribuut("321")).maak();
        Partij transporteur2 = TestPartijBuilder.maker().metId(2).metOin(new OINAttribuut("456")).maak();
        offloadGegevens = new AutorisatieOffloadGegevens(ondertekenaar2, transporteur2);
        ToegangBijhoudingsautorisatie tb1 = TestToegangBijhoudingsautorisatieBuilder.maker().metOndertekenaar(ondertekenaar)
            .metTransporteur(transporteur2).metDatumIngang(DatumAttribuut.gisteren()).metDatumEinde(DatumAttribuut.morgen()).maak();
        ToegangBijhoudingsautorisatie tb2 = TestToegangBijhoudingsautorisatieBuilder.maker().metOndertekenaar(ondertekenaar2)
            .metTransporteur(transporteur).metDatumIngang(DatumAttribuut.gisteren()).metDatumEinde(DatumAttribuut.morgen()).maak();
        List<ToegangBijhoudingsautorisatie> toegangBijhoudingsautorisaties = asList(tb1, tb2);
        when(toegangBijhoudingsautorisatieRepository.geefToegangBijhoudingsautorisatie(ZENDENDE_PARTIJ)).thenReturn(toegangBijhoudingsautorisaties);

        service.controleerAutorisatie(null, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR2106AlsAdministratieveHandelingNietGeautoriseerd() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2106));
        final List<BijhoudingsautorisatieSoortAdministratieveHandeling> geautoriseerdeHandelingen = new ArrayList<>();
        geautoriseerdeHandelingen.add(TestBijhoudingsautorisatieSoortAdministratieveHandelingBuilder.maker().metToegangBijhoudingsautorisatie
            (toegangBijhoudingsautorisatie).metSoortAdministratieveHandeling(SoortAdministratieveHandeling
            .AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND).maak());
        ReflectionTestUtils.setField(toegangBijhoudingsautorisatie, "geautoriseerdeHandelingen", geautoriseerdeHandelingen);

        service.controleerAutorisatie(SoortAdministratieveHandeling.AANVANG_ONDERZOEK, Integer.toString(ZENDENDE_PARTIJ), offloadGegevens);
    }

    private static class ExceptionRegelMatcher extends TypeSafeMatcher<AutorisatieException> {
        private Regel regel;

        public ExceptionRegelMatcher(Regel regel) {
            this.regel = regel;
        }

        @Override
        protected boolean matchesSafely(AutorisatieException exceptie) {
            return exceptie.getRegel() == regel;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("expects regel ").appendValue(regel);
        }

        @Override
        protected void describeMismatchSafely(AutorisatieException exceptie, Description omschrijving) {
            omschrijving.appendText("was ").appendValue(exceptie.getRegel());
        }
    }
}
