/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Builder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3InhoudNaarBrpConversieStap;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Deze testcase test de Conversie Service.
 */
public class ConversieServiceTest extends AbstractComponentTest {

    private static final Lo3Herkomst LO3_HERKOMST = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0);

    private static final String GESLACHTSAANDUIDING_MAN = "M";
    private static final String GESLACHTSAANDUIDING_VROUW = "V";
    private static final String BSN_NUMMER = "987654321";
    private static final String A_NUMMER = "1234567890";
    private static final int DATE2 = 2011_01_02;
    private static final int DATE = 2011_01_01;
    private static final String INDICATIE_ONJUIST = "O";
    private static final String AANDUIDING_NAAMGEBRUIK = "E";
    private static final String REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE = "020";
    private static final String NATIONALITEIT_CODE = "0001";
    private static final String VOORNAMEN = "Piet Jan";
    private static final String ADELIJKE_TITEL_PREDIKAAT = "PS";
    private static final String VOORVOEGSEL = "van";
    private static final String GESLACHTSNAAM = "HorenZeggen";
    private static final String VOORNAMEN2 = "Piet Klaas";
    private static final int GEBOORTEDATUM = VerplichteStapel.GEBOORTE_DATUM;
    private static final String GEMEENTE_CODE = "1234";
    private static final String LAND_CODE = "5678";

    @Inject
    private Lo3InhoudNaarBrpConversieStap lo3InhoudNaarBrpConversieStap;
    @Inject
    private ConverteerLo3NaarBrpService conversieService;

    @Before
    public void setUp() {
        Logging.initContext();
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    @Test
    public void testComplexeAnummerHistorie() {
        final BrpPersoonslijst persoonslijst = conversieService.converteerLo3Persoonslijst(maakLo3PersoonslijstMetComplexeAnummerHistorie());
        assertNotNull(persoonslijst);
        final BrpGroep<BrpIdentificatienummersInhoud> actueleIdInhoud = persoonslijst.getIdentificatienummerStapel().getActueel();
        final BrpGroep<BrpIdentificatienummersInhoud> laatsteIdInhoud = persoonslijst.getIdentificatienummerStapel().getVorigElement(actueleIdInhoud);
        assertEquals("8049804065", actueleIdInhoud.getInhoud().getAdministratienummer().getWaarde());
        assertNotNull(laatsteIdInhoud);
        assertEquals("1401323649", laatsteIdInhoud.getInhoud().getAdministratienummer().getWaarde());
    }

    @Test
    public void testConverteerLo3Persoonslijst() {
        final BrpPersoonslijst persoonslijst = conversieService.converteerLo3Persoonslijst(maakEenvoudigeLo3Persoonslijst2());
        assertNotNull(persoonslijst);
    }

    @Test
    public void testPersoonConversieNaarBrp() throws FileNotFoundException {
        final Lo3Persoonslijst lo3Persoonslijst1 = maakEenvoudigeLo3Persoonslijst1();
        final Lo3Persoonslijst lo3Persoonslijst2 = maakEenvoudigeLo3Persoonslijst2();

        /* Persoon 1 */
        final TussenPersoonslijst tussenPersoonslijst1 = lo3InhoudNaarBrpConversieStap.converteer(lo3Persoonslijst1);
        assertEquals(1, tussenPersoonslijst1.getIdentificatienummerStapel().size());
        assertEquals(
                BrpGeslachtsaanduidingCode.MAN,
                tussenPersoonslijst1.getGeslachtsaanduidingStapel().get(0).getInhoud().getGeslachtsaanduidingCode());
        assertNull(tussenPersoonslijst1.getIdentificatienummerStapel().get(0).getInhoud().getBurgerservicenummer());
        assertEquals(A_NUMMER, BrpString.unwrap(tussenPersoonslijst1.getIdentificatienummerStapel().get(0).getInhoud().getAdministratienummer()));

        /* Persoon 2 */
        final TussenPersoonslijst tussenPersoonslijst2 = lo3InhoudNaarBrpConversieStap.converteer(lo3Persoonslijst2);
        assertEquals(2, tussenPersoonslijst2.getIdentificatienummerStapel().size());
        assertEquals(
                BrpGeslachtsaanduidingCode.VROUW,
                tussenPersoonslijst2.getGeslachtsaanduidingStapel().get(0).getInhoud().getGeslachtsaanduidingCode());
        assertEquals(
                BrpGeslachtsaanduidingCode.MAN,
                tussenPersoonslijst2.getGeslachtsaanduidingStapel().get(1).getInhoud().getGeslachtsaanduidingCode());
        assertEquals(
                BSN_NUMMER,
                BrpString.unwrap(tussenPersoonslijst2.getIdentificatienummerStapel().get(0).getInhoud().getBurgerservicenummer()));
        assertEquals(
                BSN_NUMMER,
                BrpString.unwrap(tussenPersoonslijst2.getIdentificatienummerStapel().get(1).getInhoud().getBurgerservicenummer()));
        assertEquals(A_NUMMER, BrpString.unwrap(tussenPersoonslijst2.getIdentificatienummerStapel().get(0).getInhoud().getAdministratienummer()));
        assertEquals(A_NUMMER, BrpString.unwrap(tussenPersoonslijst2.getIdentificatienummerStapel().get(1).getInhoud().getAdministratienummer()));
    }

    @Test
    public void testNationaliteitConversieNaarBrp() {
        final Lo3Persoonslijst lo3Persoonslijst1 = maakEenvoudigeLo3Persoonslijst1();
        final Lo3Persoonslijst lo3Persoonslijst2 = maakEenvoudigeLo3Persoonslijst2();

        /* Persoon 1 */
        final TussenPersoonslijst tussenPersoonslijst1 = lo3InhoudNaarBrpConversieStap.converteer(lo3Persoonslijst1);
        assertNotNull(tussenPersoonslijst1);
        assertEquals(lo3Persoonslijst1.getNationaliteitStapels().size(), tussenPersoonslijst1.getNationaliteitStapels().size());
        assertEquals(1, tussenPersoonslijst1.getNationaliteitStapels().size());
        assertEquals(lo3Persoonslijst1.getNationaliteitStapels().get(0).size(), tussenPersoonslijst1.getNationaliteitStapels().get(0).size());
        assertEquals(1, tussenPersoonslijst1.getNationaliteitStapels().get(0).size());
        assertEquals(
                lo3Persoonslijst1.getNationaliteitStapels().get(0).get(0).getHistorie(),
                tussenPersoonslijst1.getNationaliteitStapels().get(0).get(0).getHistorie());
        final TussenGroep<BrpNationaliteitInhoud> migratieNationaliteit1 = tussenPersoonslijst1.getNationaliteitStapels().get(0).get(0);
        assertEquals(new BrpNationaliteitCode(NATIONALITEIT_CODE), migratieNationaliteit1.getInhoud().getNationaliteitCode());
        assertEquals(
                new BrpRedenVerkrijgingNederlandschapCode(REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE),
                migratieNationaliteit1.getInhoud().getRedenVerkrijgingNederlandschapCode());
        assertNull(migratieNationaliteit1.getInhoud().getRedenVerliesNederlandschapCode());

        /* Persoon 2 */
        final TussenPersoonslijst tussenPersoonslijst2 = lo3InhoudNaarBrpConversieStap.converteer(lo3Persoonslijst2);
        assertNotNull(tussenPersoonslijst2);
        final TussenGroep<BrpNationaliteitInhoud> migratieNationaliteit2 = tussenPersoonslijst2.getNationaliteitStapels().get(0).get(0);
        assertEquals(
                new BrpRedenVerkrijgingNederlandschapCode(REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE),
                migratieNationaliteit2.getInhoud().getRedenVerkrijgingNederlandschapCode());
        assertNull(migratieNationaliteit2.getInhoud().getRedenVerliesNederlandschapCode());
        assertNotSame(
                lo3Persoonslijst2.getNationaliteitStapels().get(0).get(0).getHistorie(),
                tussenPersoonslijst1.getNationaliteitStapels().get(0).get(0).getHistorie());
        assertEquals(
                lo3Persoonslijst2.getNationaliteitStapels().get(0).get(0).getHistorie(),
                tussenPersoonslijst2.getNationaliteitStapels().get(0).get(0).getHistorie());
    }

    @Test
    public void testBijhoudingsaardIngezetene() {
        final Lo3Persoonslijst lo3Persoonslijst1 = maakEenvoudigeLo3Persoonslijst1();

        final Lo3InschrijvingInhoud.Builder inschrijvingBuilder = new Lo3InschrijvingInhoud.Builder();
        inschrijvingBuilder.setDatumOpschortingBijhouding(new Lo3Datum(GEBOORTEDATUM));
        inschrijvingBuilder.setRedenOpschortingBijhoudingCode(Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN.asElement());
        inschrijvingBuilder.setDatumEersteInschrijving(new Lo3Datum(GEBOORTEDATUM));
        inschrijvingBuilder.setVersienummer(new Lo3Integer(1));
        inschrijvingBuilder.setDatumtijdstempel(new Lo3Datumtijdstempel(20070401000000000L));
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<>(
                        Collections.singletonList(new Lo3Categorie<>(inschrijvingBuilder.build(), null, new Lo3Historie(null, null, null), LO3_HERKOMST)));
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(lo3Persoonslijst1);
        builder.inschrijvingStapel(lo3InschrijvingStapel);

        final TussenPersoonslijst tussenPersoonslijst1 = lo3InhoudNaarBrpConversieStap.converteer(builder.build());
        assertNotNull(tussenPersoonslijst1);
        assertNotNull(tussenPersoonslijst1.getBijhoudingStapel());
        assertEquals(BrpBijhoudingsaardCode.INGEZETENE, tussenPersoonslijst1.getBijhoudingStapel().get(0).getInhoud().getBijhoudingsaardCode());
    }

    @Test
    public void testBijhoudingsaardGeemigreerd() {
        final Lo3Persoonslijst lo3Persoonslijst1 = maakEenvoudigeLo3Persoonslijst1();
        final Lo3InschrijvingInhoud.Builder inschrijvingBuilder = new Lo3InschrijvingInhoud.Builder();
        inschrijvingBuilder.setDatumOpschortingBijhouding(new Lo3Datum(GEBOORTEDATUM));
        inschrijvingBuilder.setRedenOpschortingBijhoudingCode(Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.asElement());
        inschrijvingBuilder.setDatumEersteInschrijving(new Lo3Datum(GEBOORTEDATUM));
        inschrijvingBuilder.setVersienummer(new Lo3Integer(1));
        inschrijvingBuilder.setDatumtijdstempel(new Lo3Datumtijdstempel(20070401000000000L));

        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<>(
                        Collections.singletonList(new Lo3Categorie<>(inschrijvingBuilder.build(), null, new Lo3Historie(null, null, null), LO3_HERKOMST)));
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(lo3Persoonslijst1);
        builder.inschrijvingStapel(lo3InschrijvingStapel);

        final TussenPersoonslijst tussenPersoonslijst1 = lo3InhoudNaarBrpConversieStap.converteer(builder.build());
        assertNotNull(tussenPersoonslijst1);
        assertNotNull(tussenPersoonslijst1.getBijhoudingStapel());
        assertEquals(BrpBijhoudingsaardCode.INGEZETENE, tussenPersoonslijst1.getBijhoudingStapel().get(0).getInhoud().getBijhoudingsaardCode());
        assertEquals(
                BrpNadereBijhoudingsaardCode.EMIGRATIE,
                tussenPersoonslijst1.getBijhoudingStapel().get(0).getInhoud().getNadereBijhoudingsaardCode());
    }

    @Test
    public void testBijhoudingsaardRni() {
        final Lo3Persoonslijst lo3Persoonslijst1 = maakEenvoudigeLo3Persoonslijst1();
        final Lo3InschrijvingInhoud.Builder inschrijvingBuilder = new Lo3InschrijvingInhoud.Builder();
        inschrijvingBuilder.setDatumOpschortingBijhouding(new Lo3Datum(GEBOORTEDATUM));
        inschrijvingBuilder.setRedenOpschortingBijhoudingCode(Lo3RedenOpschortingBijhoudingCodeEnum.RNI.asElement());
        inschrijvingBuilder.setDatumEersteInschrijving(new Lo3Datum(GEBOORTEDATUM));
        inschrijvingBuilder.setVersienummer(new Lo3Integer(1));
        inschrijvingBuilder.setDatumtijdstempel(new Lo3Datumtijdstempel(20070401000000000L));

        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<>(
                        Collections.singletonList(new Lo3Categorie<>(inschrijvingBuilder.build(), null, new Lo3Historie(null, null, null), LO3_HERKOMST)));
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(lo3Persoonslijst1);
        builder.inschrijvingStapel(lo3InschrijvingStapel);

        final TussenPersoonslijst tussenPersoonslijst1 = lo3InhoudNaarBrpConversieStap.converteer(builder.build());
        assertNotNull(tussenPersoonslijst1);
        assertNotNull(tussenPersoonslijst1.getBijhoudingStapel());
        assertEquals(BrpBijhoudingsaardCode.INGEZETENE, tussenPersoonslijst1.getBijhoudingStapel().get(0).getInhoud().getBijhoudingsaardCode());
        assertEquals(
                BrpNadereBijhoudingsaardCode.RECHTSTREEKS_NIET_INGEZETENE,
                tussenPersoonslijst1.getBijhoudingStapel().get(0).getInhoud().getNadereBijhoudingsaardCode());
    }

    private Lo3Persoonslijst maakEenvoudigeLo3Persoonslijst1() {
        final Lo3Categorie<Lo3NationaliteitInhoud> lo3Nationaliteit =
                Lo3Builder.createLo3Nationaliteit(NATIONALITEIT_CODE, REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE, null, null, null, null, DATE, DATE2);
        final Lo3Stapel<Lo3NationaliteitInhoud> lo3NationaliteitStapel = new Lo3Stapel<>(Collections.singletonList(lo3Nationaliteit));
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon =
                Lo3Builder.createLo3Persoon(
                        A_NUMMER,
                        null,
                        VOORNAMEN,
                        ADELIJKE_TITEL_PREDIKAAT,
                        VOORVOEGSEL,
                        GESLACHTSNAAM,
                        GEBOORTEDATUM,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        GESLACHTSAANDUIDING_MAN,
                        null,
                        null,
                        AANDUIDING_NAAMGEBRUIK,
                        null,
                        DATE,
                        DATE2,
                        0);
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel = new Lo3Stapel<>(Collections.singletonList(lo3Persoon));

        final Lo3InschrijvingInhoud.Builder inschrijvingBuilder = new Lo3InschrijvingInhoud.Builder();
        inschrijvingBuilder.setDatumEersteInschrijving(new Lo3Datum(GEBOORTEDATUM));
        inschrijvingBuilder.setVersienummer(new Lo3Integer(1));
        inschrijvingBuilder.setDatumtijdstempel(new Lo3Datumtijdstempel(20070401000000000L));
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<>(
                        Collections.singletonList(new Lo3Categorie<>(inschrijvingBuilder.build(), null, new Lo3Historie(null, null, null), LO3_HERKOMST)));
        final Lo3Stapel<Lo3OuderInhoud> ouder1 = VerplichteStapel.createOuder1Stapel();
        final Lo3Stapel<Lo3OuderInhoud> ouder2 = VerplichteStapel.createOuder2Stapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();

        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel)
                .ouder1Stapel(ouder1)
                .ouder2Stapel(ouder2)
                .nationaliteitStapel(lo3NationaliteitStapel)
                .inschrijvingStapel(lo3InschrijvingStapel)
                .verblijfplaatsStapel(verblijfplaats)
                .build();
    }

    private Lo3Persoonslijst maakEenvoudigeLo3Persoonslijst2() {
        final Lo3Categorie<Lo3NationaliteitInhoud> lo3Nationaliteit =
                Lo3Builder.createLo3Nationaliteit(NATIONALITEIT_CODE, REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE, null, null, null, null, DATE, DATE2);
        final Lo3Stapel<Lo3NationaliteitInhoud> lo3NationaliteitStapel = new Lo3Stapel<>(Collections.singletonList(lo3Nationaliteit));
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon1 =
                Lo3Builder.createLo3Persoon(
                        A_NUMMER,
                        BSN_NUMMER,
                        VOORNAMEN,
                        ADELIJKE_TITEL_PREDIKAAT,
                        VOORVOEGSEL,
                        GESLACHTSNAAM,
                        GEBOORTEDATUM,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        GESLACHTSAANDUIDING_VROUW,
                        null,
                        null,
                        AANDUIDING_NAAMGEBRUIK,
                        INDICATIE_ONJUIST,
                        DATE,
                        DATE2,
                        1);
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon2 =
                Lo3Builder.createLo3Persoon(
                        A_NUMMER,
                        BSN_NUMMER,
                        VOORNAMEN2,
                        ADELIJKE_TITEL_PREDIKAAT,
                        VOORVOEGSEL,
                        GESLACHTSNAAM,
                        GEBOORTEDATUM,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        GESLACHTSAANDUIDING_MAN,
                        null,
                        null,
                        AANDUIDING_NAAMGEBRUIK,
                        null,
                        DATE,
                        DATE2,
                        0);
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel = new Lo3Stapel<>(Arrays.asList(lo3Persoon1, lo3Persoon2));
        final Lo3InschrijvingInhoud.Builder inschrijvingBuilder = new Lo3InschrijvingInhoud.Builder();
        inschrijvingBuilder.setDatumEersteInschrijving(new Lo3Datum(GEBOORTEDATUM));
        inschrijvingBuilder.setVersienummer(new Lo3Integer(1));
        inschrijvingBuilder.setDatumtijdstempel(new Lo3Datumtijdstempel(20070401000000000L));
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<>(
                        Collections.singletonList(new Lo3Categorie<>(inschrijvingBuilder.build(), null, new Lo3Historie(null, null, null), LO3_HERKOMST)));
        final Lo3Stapel<Lo3OuderInhoud> ouder1 = VerplichteStapel.createOuder1Stapel();
        final Lo3Stapel<Lo3OuderInhoud> ouder2 = VerplichteStapel.createOuder2Stapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel)
                .ouder1Stapel(ouder1)
                .ouder2Stapel(ouder2)
                .nationaliteitStapel(lo3NationaliteitStapel)
                .inschrijvingStapel(lo3InschrijvingStapel)
                .verblijfplaatsStapel(verblijfplaats)
                .build();
    }

    private Lo3Persoonslijst maakLo3PersoonslijstMetComplexeAnummerHistorie() {
        final Lo3Categorie<Lo3NationaliteitInhoud> lo3Nationaliteit =
                Lo3Builder.createLo3Nationaliteit(NATIONALITEIT_CODE, REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE, null, null, null, null, DATE, DATE2);
        final Lo3Stapel<Lo3NationaliteitInhoud> lo3NationaliteitStapel = new Lo3Stapel<>(Collections.singletonList(lo3Nationaliteit));
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon1 =
                Lo3Builder.createLo3Persoon(
                        "6276180257",
                        BSN_NUMMER,
                        VOORNAMEN,
                        ADELIJKE_TITEL_PREDIKAAT,
                        VOORVOEGSEL,
                        GESLACHTSNAAM,
                        GEBOORTEDATUM,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        GESLACHTSAANDUIDING_VROUW,
                        null,
                        null,
                        AANDUIDING_NAAMGEBRUIK,
                        null,
                        19920808,
                        19940930,
                        2);
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon2 =
                Lo3Builder.createLo3Persoon(
                        "1401323649",
                        BSN_NUMMER,
                        VOORNAMEN2,
                        ADELIJKE_TITEL_PREDIKAAT,
                        VOORVOEGSEL,
                        GESLACHTSNAAM,
                        GEBOORTEDATUM,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        GESLACHTSAANDUIDING_MAN,
                        null,
                        null,
                        AANDUIDING_NAAMGEBRUIK,
                        INDICATIE_ONJUIST,
                        20100101,
                        20100102,
                        1);
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon3 =
                Lo3Builder.createLo3Persoon(
                        "8049804065",
                        BSN_NUMMER,
                        VOORNAMEN2,
                        ADELIJKE_TITEL_PREDIKAAT,
                        VOORVOEGSEL,
                        GESLACHTSNAAM,
                        GEBOORTEDATUM,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        GESLACHTSAANDUIDING_MAN,
                        null,
                        null,
                        AANDUIDING_NAAMGEBRUIK,
                        null,
                        20100101,
                        20130102,
                        0);
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel = new Lo3Stapel<>(Arrays.asList(lo3Persoon1, lo3Persoon2, lo3Persoon3));
        final Lo3InschrijvingInhoud.Builder inschrijvingBuilder = new Lo3InschrijvingInhoud.Builder();
        inschrijvingBuilder.setDatumEersteInschrijving(new Lo3Datum(GEBOORTEDATUM));
        inschrijvingBuilder.setVersienummer(new Lo3Integer(1));
        inschrijvingBuilder.setDatumtijdstempel(new Lo3Datumtijdstempel(20070401000000000L));

        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<>(
                        Collections.singletonList(new Lo3Categorie<>(inschrijvingBuilder.build(), null, new Lo3Historie(null, null, null), LO3_HERKOMST)));
        final Lo3Stapel<Lo3OuderInhoud> ouder1 = VerplichteStapel.createOuder1Stapel();
        final Lo3Stapel<Lo3OuderInhoud> ouder2 = VerplichteStapel.createOuder2Stapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel)
                .ouder1Stapel(ouder1)
                .ouder2Stapel(ouder2)
                .nationaliteitStapel(lo3NationaliteitStapel)
                .inschrijvingStapel(lo3InschrijvingStapel)
                .verblijfplaatsStapel(verblijfplaats)
                .build();
    }
}
