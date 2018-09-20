/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3InhoudNaarBrpConversieStap;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Assert;
import org.junit.Test;

/**
 * Deze testcase test de Conversie Service.
 * 
 */
public class ConversieServiceTest extends AbstractComponentTest {

    private static final String GESLACHTSAANDUIDING_MAN = "M";
    private static final String GESLACHTSAANDUIDING_VROUW = "V";
    private static final long BSN_NUMMER = 987654321L;
    private static final Long A_NUMMER = 1234567890L;
    private static final int DATE2 = 20110102;
    private static final int DATE = 20110101;
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
    private ConversieService conversieService;

    @Test
    public void testConverteerLo3Persoonslijst() throws InputValidationException {
        final BrpPersoonslijst persoonslijst =
                conversieService.converteerLo3Persoonslijst(maakEenvoudigeLo3Persoonslijst2());
        Assert.assertNotNull(persoonslijst);
    }

    @Test
    public void testPersoonConversieNaarBrp() throws FileNotFoundException, InputValidationException {
        final Lo3Persoonslijst lo3Persoonslijst1 = maakEenvoudigeLo3Persoonslijst1();
        final Lo3Persoonslijst lo3Persoonslijst2 = maakEenvoudigeLo3Persoonslijst2();

        /* Persoon 1 */
        final MigratiePersoonslijst migratiePersoonslijst1 =
                lo3InhoudNaarBrpConversieStap.converteer(lo3Persoonslijst1);
        assertEquals(1, migratiePersoonslijst1.getIdentificatienummerStapel().size());
        assertEquals(BrpGeslachtsaanduidingCode.MAN, migratiePersoonslijst1.getGeslachtsaanduidingStapel().get(0)
                .getInhoud().getGeslachtsaanduiding());
        assertNull(migratiePersoonslijst1.getIdentificatienummerStapel().get(0).getInhoud().getBurgerservicenummer());
        assertEquals(A_NUMMER, migratiePersoonslijst1.getIdentificatienummerStapel().get(0).getInhoud()
                .getAdministratienummer());
        // assertEquals(2, migratiePersoonslijst1.getVoornaamStapels().size());
        // assertEquals(VOORNAMEN.split(" ")[0], migratiePersoonslijst1.getVoornaamStapels().get(0).get(0).getInhoud()
        // .getVoornaam());
        // assertEquals(VOORNAMEN.split(" ")[1], migratiePersoonslijst1.getVoornaamStapels().get(1).get(0).getInhoud()
        // .getVoornaam());

        /* Persoon 2 */
        final MigratiePersoonslijst migratiePersoonslijst2 =
                lo3InhoudNaarBrpConversieStap.converteer(lo3Persoonslijst2);
        assertEquals(2, migratiePersoonslijst2.getIdentificatienummerStapel().size());
        assertEquals(BrpGeslachtsaanduidingCode.VROUW, migratiePersoonslijst2.getGeslachtsaanduidingStapel().get(0)
                .getInhoud().getGeslachtsaanduiding());
        assertEquals(BrpGeslachtsaanduidingCode.MAN, migratiePersoonslijst2.getGeslachtsaanduidingStapel().get(1)
                .getInhoud().getGeslachtsaanduiding());
        assertEquals(Long.valueOf(BSN_NUMMER), migratiePersoonslijst2.getIdentificatienummerStapel().get(0)
                .getInhoud().getBurgerservicenummer());
        assertEquals(Long.valueOf(BSN_NUMMER), migratiePersoonslijst2.getIdentificatienummerStapel().get(1)
                .getInhoud().getBurgerservicenummer());
        assertEquals(A_NUMMER, migratiePersoonslijst2.getIdentificatienummerStapel().get(0).getInhoud()
                .getAdministratienummer());
        assertEquals(A_NUMMER, migratiePersoonslijst2.getIdentificatienummerStapel().get(1).getInhoud()
                .getAdministratienummer());
        // assertEquals(2, migratiePersoonslijst2.getVoornaamStapels().size());
        // assertEquals(VOORNAMEN.split(" ")[0], migratiePersoonslijst2.getVoornaamStapels().get(0).get(0).getInhoud()
        // .getVoornaam());
        // assertEquals(VOORNAMEN.split(" ")[1], migratiePersoonslijst2.getVoornaamStapels().get(1).get(0).getInhoud()
        // .getVoornaam());
        // assertEquals(VOORNAMEN2.split(" ")[0], migratiePersoonslijst2.getVoornaamStapels().get(0).get(1).getInhoud()
        // .getVoornaam());
        // assertEquals(VOORNAMEN2.split(" ")[1], migratiePersoonslijst2.getVoornaamStapels().get(1).get(1).getInhoud()
        // .getVoornaam());
    }

    @Test
    public void testNationaliteitConversieNaarBrp() throws InputValidationException {
        final Lo3Persoonslijst lo3Persoonslijst1 = maakEenvoudigeLo3Persoonslijst1();
        final Lo3Persoonslijst lo3Persoonslijst2 = maakEenvoudigeLo3Persoonslijst2();

        /* Persoon 1 */
        final MigratiePersoonslijst migratiePersoonslijst1 =
                lo3InhoudNaarBrpConversieStap.converteer(lo3Persoonslijst1);
        assertNotNull(migratiePersoonslijst1);
        assertEquals(lo3Persoonslijst1.getNationaliteitStapels().size(), migratiePersoonslijst1
                .getNationaliteitStapels().size());
        assertEquals(1, migratiePersoonslijst1.getNationaliteitStapels().size());
        assertEquals(lo3Persoonslijst1.getNationaliteitStapels().get(0).size(), migratiePersoonslijst1
                .getNationaliteitStapels().get(0).size());
        assertEquals(1, migratiePersoonslijst1.getNationaliteitStapels().get(0).size());
        assertEquals(lo3Persoonslijst1.getNationaliteitStapels().get(0).get(0).getHistorie(), migratiePersoonslijst1
                .getNationaliteitStapels().get(0).get(0).getHistorie());
        final MigratieGroep<BrpNationaliteitInhoud> migratieNationaliteit1 =
                migratiePersoonslijst1.getNationaliteitStapels().get(0).get(0);
        assertEquals(new BrpNationaliteitCode(Integer.valueOf(NATIONALITEIT_CODE)), migratieNationaliteit1
                .getInhoud().getNationaliteitCode());
        assertEquals(new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal(REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE)),
                migratieNationaliteit1.getInhoud().getRedenVerkrijgingNederlandschapCode());
        assertNull(migratieNationaliteit1.getInhoud().getRedenVerliesNederlandschapCode());
        // assertTrue(migratiePersoonslijst1.getBehandeldAlsNederlanderIndicatieStapels().isEmpty());

        /* Persoon 2 */
        final MigratiePersoonslijst migratiePersoonslijst2 =
                lo3InhoudNaarBrpConversieStap.converteer(lo3Persoonslijst2);
        assertNotNull(migratiePersoonslijst2);
        final MigratieGroep<BrpNationaliteitInhoud> migratieNationaliteit2 =
                migratiePersoonslijst2.getNationaliteitStapels().get(0).get(0);
        assertEquals(new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal(REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE)),
                migratieNationaliteit2.getInhoud().getRedenVerkrijgingNederlandschapCode());
        assertNull(migratieNationaliteit2.getInhoud().getRedenVerliesNederlandschapCode());
        assertNotSame(lo3Persoonslijst2.getNationaliteitStapels().get(0).get(0).getHistorie(), migratiePersoonslijst1
                .getNationaliteitStapels().get(0).get(0).getHistorie());
        assertEquals(lo3Persoonslijst2.getNationaliteitStapels().get(0).get(0).getHistorie(), migratiePersoonslijst2
                .getNationaliteitStapels().get(0).get(0).getHistorie());
        // assertTrue(migratiePersoonslijst2.getBehandeldAlsNederlanderIndicatieStapels().isEmpty());
        // assertTrue(migratiePersoonslijst2.getVastgesteldNietNederlanderIndicatieStapels().isEmpty());
    }

    @SuppressWarnings("unchecked")
    private Lo3Persoonslijst maakEenvoudigeLo3Persoonslijst1() {
        final Lo3Categorie<Lo3NationaliteitInhoud> lo3Nationaliteit =
                Lo3Builder.createLo3Nationaliteit(NATIONALITEIT_CODE, REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE, null,
                        null, null, DATE, DATE2);
        final Lo3Stapel<Lo3NationaliteitInhoud> lo3NationaliteitStapel =
                new Lo3Stapel<Lo3NationaliteitInhoud>(Arrays.asList(lo3Nationaliteit));
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon =
                Lo3Builder.createLo3Persoon(A_NUMMER, null, VOORNAMEN, ADELIJKE_TITEL_PREDIKAAT, VOORVOEGSEL,
                        GESLACHTSNAAM, GEBOORTEDATUM, GEMEENTE_CODE, LAND_CODE, GESLACHTSAANDUIDING_MAN,
                        AANDUIDING_NAAMGEBRUIK, null, null, null, DATE, DATE2);
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel =
                new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(lo3Persoon));
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(new Lo3Categorie<Lo3InschrijvingInhoud>(
                        new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(GEBOORTEDATUM), null, null, 1,
                                new Lo3Datumtijdstempel(20070401000000000L), null), null, Lo3Historie.NULL_HISTORIE,
                        null)));
        final Lo3Stapel<Lo3OuderInhoud> ouder = VerplichteStapel.createOuderStapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel).ouder1Stapel(ouder).ouder2Stapel(ouder)
                .nationaliteitStapel(lo3NationaliteitStapel).inschrijvingStapel(lo3InschrijvingStapel)
                .verblijfplaatsStapel(verblijfplaats).build();
    }

    @SuppressWarnings("unchecked")
    private Lo3Persoonslijst maakEenvoudigeLo3Persoonslijst2() throws InputValidationException {
        final Lo3Categorie<Lo3NationaliteitInhoud> lo3Nationaliteit =
                Lo3Builder.createLo3Nationaliteit(NATIONALITEIT_CODE, REDEN_VERKRIJGEN_NEDERLANDSCHAP_CODE, null,
                        null, null, DATE, DATE2);
        final Lo3Stapel<Lo3NationaliteitInhoud> lo3NationaliteitStapel =
                new Lo3Stapel<Lo3NationaliteitInhoud>(Arrays.asList(lo3Nationaliteit));
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon1 =
                Lo3Builder.createLo3Persoon(A_NUMMER, BSN_NUMMER, VOORNAMEN, ADELIJKE_TITEL_PREDIKAAT, VOORVOEGSEL,
                        GESLACHTSNAAM, GEBOORTEDATUM, GEMEENTE_CODE, LAND_CODE, GESLACHTSAANDUIDING_VROUW,
                        AANDUIDING_NAAMGEBRUIK, null, null, INDICATIE_ONJUIST, DATE, DATE2);
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon2 =
                Lo3Builder.createLo3Persoon(A_NUMMER, BSN_NUMMER, VOORNAMEN2, ADELIJKE_TITEL_PREDIKAAT, VOORVOEGSEL,
                        GESLACHTSNAAM, GEBOORTEDATUM, GEMEENTE_CODE, LAND_CODE, GESLACHTSAANDUIDING_MAN,
                        AANDUIDING_NAAMGEBRUIK, null, null, null, DATE, DATE2);
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel =
                new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(lo3Persoon1, lo3Persoon2));
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(new Lo3Categorie<Lo3InschrijvingInhoud>(
                        new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(GEBOORTEDATUM), null, null, 1,
                                new Lo3Datumtijdstempel(20070401000000000L), null), null, Lo3Historie.NULL_HISTORIE,
                        null)));
        final Lo3Stapel<Lo3OuderInhoud> ouder = VerplichteStapel.createOuderStapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel).ouder1Stapel(ouder).ouder2Stapel(ouder)
                .nationaliteitStapel(lo3NationaliteitStapel).inschrijvingStapel(lo3InschrijvingStapel)
                .verblijfplaatsStapel(verblijfplaats).build();
    }
}
