/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.dataaccess.repository.ToegangLeveringsautorisatieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class LeveringsautorisatieServiceImplTest {

    private static final int ZENDENDE_PARTIJ_CODE            = 123;
    private static final int ALTERNATIEVE_TRANSPORTEUR_CODE  = 456;
    private static final int ALTERNATIEVE_ONDERTEKENAAR_CODE = 789;

    private static final Partij ZENDENDE_PARTIJ            = TestPartijBuilder.maker().metCode(ZENDENDE_PARTIJ_CODE).
        metOin(new OINAttribuut(String.valueOf(ZENDENDE_PARTIJ_CODE))).maak();
    private static final Partij ALTERNATIEVE_TRANSPORTEUR  = TestPartijBuilder.maker().metCode(ALTERNATIEVE_TRANSPORTEUR_CODE).
        metOin(new OINAttribuut(String.valueOf(ALTERNATIEVE_TRANSPORTEUR_CODE))).maak();
    private static final Partij ALTERNATIEVE_ONDERTEKENAAR = TestPartijBuilder.maker().metCode(ALTERNATIEVE_ONDERTEKENAAR_CODE).
        metOin(new OINAttribuut(String.valueOf(ALTERNATIEVE_ONDERTEKENAAR_CODE))).maak();

    private static final Leveringsautorisatie LEVERINGSAUTORISATIE = TestLeveringsautorisatieBuilder.maker().metId(11).maak();

    @InjectMocks
    private LeveringsautorisatieService service = new LeveringsautorisatieServiceImpl();

    @Mock
    private ToegangLeveringsautorisatieRepository toegangLeveringsautorisatieRepository;

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        ReflectionTestUtils.setField(ZENDENDE_PARTIJ, "partijrollen", new HashSet<>(singletonList(
            new PartijRol(ZENDENDE_PARTIJ, Rol.AFNEMER, null, null))));
    }

    @Test
    public void isNietGeautoriseerdAlsGeenZendendePartijOfOffloadGegevens() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.AUTH0001));

        service.controleerAutorisatie(1, "1", null);
    }

    @Test
    public void isNietGeautoriseerdPartijNietGevonden() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2120));
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(null);

        service.controleerAutorisatie(1, "" + ZENDENDE_PARTIJ_CODE, new AutorisatieOffloadGegevens(
            TestPartijBuilder.maker().maak(), TestPartijBuilder.maker().maak()));
    }

    @Test
    public void leveringautorisatieNietGevonden() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2053));
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
                .metDatumIngang(DatumAttribuut.gisteren())
                .metOndertekenaar(null)
                .metTransporteur(null).maak();
        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(singletonList(tla));

        int onbekendeLeveringautorisatie = 10;
        service.controleerAutorisatie(onbekendeLeveringautorisatie, "" + ZENDENDE_PARTIJ_CODE,
                new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ZENDENDE_PARTIJ));
    }

    /**
     * Als ondertekenaar en transporteur leeg zijn dan moet het OIN van de zendende partij gelijk zijn
     * aan de OINs van de certificaten
     */
    @Test
    public void directeToegang() throws AutorisatieException {
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);

        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
                .metDatumIngang(DatumAttribuut.gisteren())
                .metOndertekenaar(null)
                .metTransporteur(null).maak();
        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(singletonList(tla));

        final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ZENDENDE_PARTIJ);
        service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
    }

    /**
     * Als er een leveringautorisatie gevonden wordt met correcte OINs, maar de leveringautorisatie verlopen is,
     * dan volgt een AuthenticatieVerlopenException
     */
    @Test
    public void autorisatieNietGeldig() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R1258));
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
                .metDatumIngang(DatumAttribuut.morgen())
                .metOndertekenaar(null)
                .metTransporteur(null).maak();
        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(singletonList(tla));

        final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ZENDENDE_PARTIJ);
        service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
    }

    /**
     * Indien de leveringsautorisaie een afwijkende transporteur bevat, dan moet het certificaat
     * voor transport gelijk zijn. Dit testgeval test dat er een fout optreedt omdat ze niet gelijk zijn.
     */
    @Test
    public void bewerkersconstructieTransporteurFoutief() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2122));
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);

        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
                .metDatumIngang(DatumAttribuut.vandaag())
                .metOndertekenaar(null)
                .metTransporteur(ALTERNATIEVE_TRANSPORTEUR).maak();
        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(singletonList(tla));

        final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ZENDENDE_PARTIJ);
        service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
    }


    /**
     * Indien de leveringsautorisaie een afwijkende transporteur bevat, dan moet het certificaat
     * voor transport gelijk zijn. Dit testgeval test dat er geen fout optreeds als ze gelijk zijn,
     */
    @Test
    public void bewerkersconstructieAfwijkendeTransporteur() throws AutorisatieException {
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);

        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
                .metDatumIngang(DatumAttribuut.gisteren())
                .metOndertekenaar(null)
                .metTransporteur(ALTERNATIEVE_TRANSPORTEUR)
                .maak();
        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(singletonList(tla));

        final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ALTERNATIEVE_TRANSPORTEUR);
        service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
    }

    /**
     * Indien de leveringsautorisaie een afwijkende ondertekenaar bevat, dan moet het certificaat
     * voor ondertekenen gelijk zijn. Dit testgeval test dat er een fout optreedt omdat ze niet gelijk zijn.
     */
    @Test(expected = AutorisatieException.class)
    public void bewerkersconstructieOndertekenaarFoutief() throws AutorisatieException {
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);

        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
                .metOndertekenaar(ALTERNATIEVE_ONDERTEKENAAR)
                .metTransporteur(null).
                        maak();
        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(singletonList(tla));

        final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ZENDENDE_PARTIJ);
        service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
    }

    @Test(expected = AutorisatieException.class)
    public void geenLeveringsautorisatieMetzelfdeId() throws AutorisatieException {
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);

        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker()
            .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
            .metOndertekenaar(ALTERNATIEVE_ONDERTEKENAAR)
            .metTransporteur(null).
                maak();
        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(singletonList(tla));

        final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ZENDENDE_PARTIJ);
        service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID() + 1, "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
    }


    /**
     * Indien de leveringsautorisaie een afwijkende ondertekenaar bevat, dan moet het certificaat
     * voor ondertekenen gelijk zijn. Dit testgeval test dat er geen fout optreedt als ze gelijk zijn.
     */
    @Test
    public void bewerkersconstructieAfwijkendeOndertekenaar() throws AutorisatieException {
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);

        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
                .metDatumIngang(DatumAttribuut.gisteren())
                .metOndertekenaar(ALTERNATIEVE_ONDERTEKENAAR)
                .metTransporteur(null)
                .maak();
        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(singletonList(tla));

        final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ALTERNATIEVE_ONDERTEKENAAR, ZENDENDE_PARTIJ);
        service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
    }

    /**
     * Bij partiele outsourcing bestaan er meerdere toegangleveringautorisaties. Hiermee is het mogelijk
     * dat de afnemer meerdere bewerkerconstructies naast elkaar gebruikt
     */
    @Test
    public void partieleOutsourcing() throws AutorisatieException {

        final ToegangLeveringsautorisatie tla1 = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
                .metDatumIngang(DatumAttribuut.gisteren())
                .metOndertekenaar(null)
                .metTransporteur(null)
                .maak();

        final ToegangLeveringsautorisatie tla2 = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
                .metDatumIngang(DatumAttribuut.gisteren())
                .metOndertekenaar(ALTERNATIEVE_ONDERTEKENAAR)
                .metTransporteur(null)
                .maak();

        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(Arrays.asList(tla1, tla2));
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);

        {
            //test directe toegang
            final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ZENDENDE_PARTIJ);
            service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
        }
        {
            //test afwijkende toegang voor ondertekenaar
            final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ALTERNATIEVE_ONDERTEKENAAR, ZENDENDE_PARTIJ);
            service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
        }

    }

    /**
     * Bij partiele outsourcing bestaan er meerdere toegangleveringautorisaties. Hiermee is het mogelijk
     * dat de afnemer meerdere bewerkerconstructies naast elkaar gebruikt
     */
    @Test
    public void partieleOutsourcingOndertekenaarEnTransporteur() throws AutorisatieException {

        final ToegangLeveringsautorisatie tla1 = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
                .metDatumIngang(DatumAttribuut.gisteren())
                .metOndertekenaar(null)
                .metTransporteur(null)
                .maak();

        final ToegangLeveringsautorisatie tla2 = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
                .metDatumIngang(DatumAttribuut.gisteren())
                .metOndertekenaar(ALTERNATIEVE_ONDERTEKENAAR)
                .metTransporteur(ALTERNATIEVE_TRANSPORTEUR)
                .maak();

        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(Arrays.asList(tla1, tla2));
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);

        {
            //test directe toegang
            final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ZENDENDE_PARTIJ);
            service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
        }
        {
            //test afwijkende toegang voor ondertekenaar en transporteur
            final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ALTERNATIEVE_ONDERTEKENAAR, ALTERNATIEVE_TRANSPORTEUR);
            service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
        }
    }

    @Test
    public void meerdereStelsels() throws AutorisatieException {

        final Leveringsautorisatie laBrp = TestLeveringsautorisatieBuilder.maker().metId(1).metStelsel(Stelsel.BRP).maak();
        final ToegangLeveringsautorisatie tlaBrp = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(laBrp)
                .metDatumIngang(DatumAttribuut.gisteren())
                .metOndertekenaar(ALTERNATIEVE_ONDERTEKENAAR)
                .metTransporteur(null)
                .maak();

        final Leveringsautorisatie laGba = TestLeveringsautorisatieBuilder.maker().metId(1).metStelsel(Stelsel.GBA).maak();
        final ToegangLeveringsautorisatie tlaGba = TestToegangLeveringautorisatieBuilder.maker()
                .metLeveringsautorisatie(laGba)
                .metDatumIngang(DatumAttribuut.gisteren())
                .metOndertekenaar(null)
                .metTransporteur(null)
                .maak();

        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(Arrays.asList(tlaBrp, tlaGba));
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);

        {
            //test directe toegang GBA stelsel
            final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ZENDENDE_PARTIJ);
            service.controleerAutorisatie(laGba.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
        }
        {
            //test afwijkende toegang voor ondertekenaar in BRP stelsel
            final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ALTERNATIEVE_ONDERTEKENAAR, ZENDENDE_PARTIJ);
            service.controleerAutorisatie(laBrp.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
        }
    }

    @Test
    public void gooitExceptieMetRegelR2120AlsGeenAutorisatiesGevonden() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2120));
        final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ZENDENDE_PARTIJ);
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);
        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE))
            .thenReturn(Collections.<ToegangLeveringsautorisatie>emptyList());

        service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR2052AlsDeToegangLeveringAutorisatieGeblokkeerdIs() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R2052));
        final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ZENDENDE_PARTIJ);
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker()
            .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
            .metDatumIngang(DatumAttribuut.gisteren())
            .metOndertekenaar(null)
            .metTransporteur(null)
            .metIndicatieGeblokkeerd(new JaAttribuut(Ja.J))
            .maak();
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);
        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(singletonList(tla));

        service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), "" + ZENDENDE_PARTIJ_CODE, offloadGegevens);
    }

    @Test
    public void gooitExceptieMetRegelR1257AlsCombinatieOndertekenaarEnTransporteurOnbekendIs() throws AutorisatieException {
        expectedEx.expect(new ExceptionRegelMatcher(Regel.R1257));
        final AutorisatieOffloadGegevens offloadGegevens = new AutorisatieOffloadGegevens(ZENDENDE_PARTIJ, ALTERNATIEVE_TRANSPORTEUR);
        final ToegangLeveringsautorisatie tla1 = TestToegangLeveringautorisatieBuilder.maker()
            .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
            .metDatumIngang(DatumAttribuut.gisteren())
            .metOndertekenaar(ZENDENDE_PARTIJ)
            .metTransporteur(null)
            .maak();
        final ToegangLeveringsautorisatie tla2 = TestToegangLeveringautorisatieBuilder.maker()
            .metLeveringsautorisatie(LEVERINGSAUTORISATIE)
            .metDatumIngang(DatumAttribuut.gisteren())
            .metOndertekenaar(ALTERNATIEVE_ONDERTEKENAAR)
            .metTransporteur(ALTERNATIEVE_TRANSPORTEUR)
            .maak();
        List<ToegangLeveringsautorisatie> toegangLeveringsautorisaties = asList(tla1, tla2);
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(ZENDENDE_PARTIJ_CODE))).thenReturn(ZENDENDE_PARTIJ);
        when(toegangLeveringsautorisatieRepository.geefToegangLeveringsautorisaties(ZENDENDE_PARTIJ_CODE)).thenReturn(toegangLeveringsautorisaties);

        service.controleerAutorisatie(LEVERINGSAUTORISATIE.getID(), Integer.toString(ZENDENDE_PARTIJ_CODE), offloadGegevens);
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
