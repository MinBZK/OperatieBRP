/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.groep;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.stapel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnverwerktDocumentAanwezigIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingHuisnummerEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieDocumentEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor de terug conversie van BrpAdres/Migratie/Bijhouding naar Lo3Verblijfplaats.
 */
@RunWith(MockitoJUnitRunner.class)
public class BrpVerblijfplaatsConverteerderTest {
    // Groep 09
    private static final Lo3GemeenteCode LO3_GEMEENTE_CODE = new Lo3GemeenteCode("1904");
    private static final BrpPartijCode BIJHOUDINGSPARTIJ_CODE = new BrpPartijCode("190401");
    private static final Lo3Datum LO3_DATUM_INSCHRIJVING = new Lo3Datum(2000_01_01);
    private static final BrpDatum BRP_DATUM_INSCHRIJVING = new BrpDatum(2000_01_01, null);
    // Groep 10
    private static final Lo3FunctieAdres LO3_FUNCTIE_ADRES_B = Lo3FunctieAdresEnum.BRIEFADRES.asElement();
    private static final BrpSoortAdresCode BRP_SOORT_ADRES_CODE_B = BrpSoortAdresCode.B;
    private static final Lo3String GEMEENTE_DEEL = Lo3String.wrap("linkerkant");
    private static final Lo3Datum LO3_DATUM_AANVANG_ADRESHOUDING = new Lo3Datum(2000_01_02);
    private static final BrpDatum BRP_DATUM_AANVANG_ADRESHOUDING = new BrpDatum(2000_01_02, null);
    // Groep 11
    private static final Lo3String STRAATNAAM_PUNT = Lo3String.wrap(".");
    private static final Lo3String STRAATNAAM = Lo3String.wrap("straat");
    private static final Lo3String NAAM_OPENBARE_RUIMTE = Lo3String.wrap("ergens maar niet overal");
    private static final BrpInteger HUISNUMMER = BrpInteger.wrap(12, null);
    private static final Lo3Huisnummer LO3_HUISNUMMER = new Lo3Huisnummer(BrpInteger.unwrap(HUISNUMMER));
    private static final Lo3Character HUISLETTER = Lo3Character.wrap('a');
    private static final Lo3String HUISNUMMER_TOEVOEGING = Lo3String.wrap("-z");
    private static final Lo3AanduidingHuisnummer LO3_AANDUIDING_HUISNUMMER = Lo3AanduidingHuisnummerEnum.BY.asElement();
    private static final BrpAanduidingBijHuisnummerCode BRP_AANDUIDING_HUISNUMMER = new BrpAanduidingBijHuisnummerCode(
            BrpAanduidingBijHuisnummerCode.CODE_BY);
    private static final Lo3String POSTCODE = Lo3String.wrap("1233WE");
    private static final Lo3String WOONPLAATSNAAM = Lo3String.wrap("Appingedam");
    private static final Lo3String IDENTCODE_ADRESSEERBAAR_OBJECT = Lo3String.wrap("2349_82_349832WER");
    private static final Lo3String IDENTCODE_NUMMERAANDUIDING = Lo3String.wrap("0023_02_34939342w");
    // Groep 12
    private static final Lo3String LOCATIE_OMSCHRIJVING = Lo3String.wrap("locatie omschrijving");
    // Groep 13
    private static final Lo3LandCode LO3_LAND_CODE_EMIGRATIE = new Lo3LandCode("1234");
    private static final BrpLandOfGebiedCode BRP_LAND_OF_GEBIED_CODE_EMIGRATIE = new BrpLandOfGebiedCode("1234");
    private static final Lo3Datum LO3_DATUM_VERTREK_UIT_NL = new Lo3Datum(2000_01_03);
    private static final BrpDatum BRP_DATUM_VERTREK_UIT_NL = new BrpDatum(2000_01_03, null);
    private static final Lo3String ADRES_BUITENLAND_1 = Lo3String.wrap("adres buitenland 1");
    private static final Lo3String ADRES_BUITENLAND_2 = Lo3String.wrap("adres buitenland 2");
    private static final Lo3String ADRES_BUITENLAND_3 = Lo3String.wrap("adres buitenland 3");
    // Groep 14
    private static final Lo3LandCode LO3_LAND_CODE_IMMIGRATIE = new Lo3LandCode("4321");
    private static final BrpLandOfGebiedCode BRP_LAND_OF_GEBIED_CODE_IMMIGRATIE = new BrpLandOfGebiedCode("4321");
    private static final Lo3Datum LO3_DATUM_VESTIGING_IN_NL = new Lo3Datum(2000_01_04);
    private static final BrpDatum BRP_DATUM_VESTIGING_IN_NL = new BrpDatum(2000_01_04, null);
    // Groep 72
    private static final Lo3AangifteAdreshouding LO3_AANGIFTE_ADRESHOUDING_INGESCHREVENE =
            Lo3AangifteAdreshoudingEnum.INGESCHREVENE.asElement();
    private static final BrpAangeverCode BRP_AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE = new BrpAangeverCode('I');
    private static final BrpRedenWijzigingVerblijfCode BRP_REDEN_WIJZIGING_ADRES_PERSOON = new BrpRedenWijzigingVerblijfCode('P');
    // Groep 75
    private static final Lo3IndicatieDocument LO3_INDICATIE_DOCUMENT = Lo3IndicatieDocumentEnum.INDICATIE.asElement();
    private static final BrpLandOfGebiedCode BRP_LAND_OF_GEBIED_CODE_NL = new BrpLandOfGebiedCode("6030");

    private static final int DATUM_AANVANG_GELDIGHEID = 1994_01_01;
    private static final boolean ALLEEN_BUITENLANDS_ADRES_REGEL_1 = true;
    private static final boolean ALLE_BUITENLANDS_ADRES_REGELS = false;
    private static final boolean IS_IMMIGRATIE = true;
    private static final boolean IS_EMIGRATIE = false;
    @Mock
    private BrpAttribuutConverteerder attribuutConverteerder;
    private BrpVerblijfplaatsConverteerder converteerder;

    @Before
    public void setUp() {
        converteerder = new BrpVerblijfplaatsConverteerder(attribuutConverteerder);

        // Adres
        when(attribuutConverteerder.converteerAangifteAdreshouding(BRP_REDEN_WIJZIGING_ADRES_PERSOON, BRP_AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE))
                .thenReturn(LO3_AANGIFTE_ADRESHOUDING_INGESCHREVENE);
        when(attribuutConverteerder.converteerDatum(BRP_DATUM_AANVANG_ADRESHOUDING)).thenReturn(LO3_DATUM_AANVANG_ADRESHOUDING);
        when(attribuutConverteerder.converteerFunctieAdres(BRP_SOORT_ADRES_CODE_B)).thenReturn(LO3_FUNCTIE_ADRES_B);
        when(attribuutConverteerder.converteerString(toBrpString(IDENTCODE_ADRESSEERBAAR_OBJECT))).thenReturn(IDENTCODE_ADRESSEERBAAR_OBJECT);
        when(attribuutConverteerder.converteerString(toBrpString(IDENTCODE_NUMMERAANDUIDING))).thenReturn(IDENTCODE_NUMMERAANDUIDING);
        when(attribuutConverteerder.converteerString(toBrpString(NAAM_OPENBARE_RUIMTE))).thenReturn(NAAM_OPENBARE_RUIMTE);
        when(attribuutConverteerder.converteerString(toBrpString(WOONPLAATSNAAM))).thenReturn(WOONPLAATSNAAM);
        when(attribuutConverteerder.converteerString(toBrpString(STRAATNAAM))).thenReturn(STRAATNAAM);
        when(attribuutConverteerder.converteerString(toBrpString(GEMEENTE_DEEL))).thenReturn(GEMEENTE_DEEL);
        when(attribuutConverteerder.converteerHuisnummer(HUISNUMMER)).thenReturn(LO3_HUISNUMMER);
        when(attribuutConverteerder.converteerCharacter(new BrpCharacter(HUISLETTER.getCharacterWaarde()))).thenReturn(HUISLETTER);
        when(attribuutConverteerder.converteerString(toBrpString(HUISNUMMER_TOEVOEGING))).thenReturn(HUISNUMMER_TOEVOEGING);
        when(attribuutConverteerder.converteerString(toBrpString(POSTCODE))).thenReturn(POSTCODE);
        when(attribuutConverteerder.converteerAanduidingHuisnummer(BRP_AANDUIDING_HUISNUMMER)).thenReturn(LO3_AANDUIDING_HUISNUMMER);
        when(attribuutConverteerder.converteerString(toBrpString(LOCATIE_OMSCHRIJVING))).thenReturn(LOCATIE_OMSCHRIJVING);

        // Buitenlands adres
        when(attribuutConverteerder.converteerString(toBrpString(ADRES_BUITENLAND_1))).thenReturn(ADRES_BUITENLAND_1);
        when(attribuutConverteerder.converteerString(toBrpString(ADRES_BUITENLAND_2))).thenReturn(ADRES_BUITENLAND_2);
        when(attribuutConverteerder.converteerString(toBrpString(ADRES_BUITENLAND_3))).thenReturn(ADRES_BUITENLAND_3);

        // Onverwerkt document aanwezig
        when(attribuutConverteerder.converteerIndicatieDocument(new BrpBoolean(true))).thenReturn(LO3_INDICATIE_DOCUMENT);

        // Bijhouding
        when(attribuutConverteerder.converteerGemeenteCode(BIJHOUDINGSPARTIJ_CODE)).thenReturn(LO3_GEMEENTE_CODE);

        // Migratie
        when(attribuutConverteerder.converteerLandCode(BRP_LAND_OF_GEBIED_CODE_EMIGRATIE)).thenReturn(LO3_LAND_CODE_EMIGRATIE);
        when(attribuutConverteerder.converteerLandCode(BRP_LAND_OF_GEBIED_CODE_IMMIGRATIE)).thenReturn(LO3_LAND_CODE_IMMIGRATIE);
    }

    private BrpString toBrpString(final Lo3String lo3String) {
        return new BrpString(lo3String.getWaarde());
    }

    @Test
    @Definitie(Definities.DEF024)
    @Requirement({Requirements.CCA08_BL01, Requirements.CCA08_BL02})
    public void testNederlandsAdres() {
        final BrpAdresInhoud.Builder builder = maakNederlandsAdresBuilder(false);
        final BrpStapel<BrpAdresInhoud> adresStapel = maakNederlandsAdresStapel(builder);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.converteer(adresStapel);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertNederlandsAdres(voorkomen.getInhoud(), false);
        assertTrue(lo3Inhoud.isNederlandsAdres());
        assertFalse(lo3Inhoud.isImmigratie());
        assertFalse(lo3Inhoud.isEmigratie());
    }

    @Test
    @Definitie(Definities.DEF024)
    @Requirement({Requirements.CCA08_BL01, Requirements.CCA08_BL02})
    public void testNederlandsAdresWoonplaatsnaamNull() {
        final BrpAdresInhoud.Builder builder = maakNederlandsAdresBuilder(false);
        builder.woonplaatsnaam(null);
        final BrpStapel<BrpAdresInhoud> adresStapel = maakNederlandsAdresStapel(builder);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.converteer(adresStapel);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertNederlandsAdres(voorkomen.getInhoud(), true);
        assertTrue(lo3Inhoud.isNederlandsAdres());
        assertFalse(lo3Inhoud.isImmigratie());
        assertFalse(lo3Inhoud.isEmigratie());
    }

    @Test
    @Definitie(Definities.DEF023)
    @Requirement({Requirements.CCA08_BL01, Requirements.CCA08_BL02})
    public void testNederlandsPuntAdres() {
        final BrpAdresInhoud.Builder builder = maakNederlandsAdresBuilder(true);
        final BrpStapel<BrpAdresInhoud> adresStapel = maakNederlandsAdresStapel(builder);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.converteer(adresStapel);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertEquals(STRAATNAAM_PUNT, lo3Inhoud.getStraatnaam());
        assertNull(lo3Inhoud.getIdentificatiecodeVerblijfplaats());
        assertNull(lo3Inhoud.getIdentificatiecodeNummeraanduiding());
        assertNull(lo3Inhoud.getGemeenteInschrijving());
        assertNull(lo3Inhoud.getNaamOpenbareRuimte());
        assertNull(lo3Inhoud.getGemeenteDeel());
        assertNull(lo3Inhoud.getHuisnummer());
        assertNull(lo3Inhoud.getHuisletter());
        assertNull(lo3Inhoud.getHuisnummertoevoeging());
        assertNull(lo3Inhoud.getPostcode());
        assertNull(lo3Inhoud.getWoonplaatsnaam());
        assertNull(lo3Inhoud.getAanduidingHuisnummer());
        assertNull(lo3Inhoud.getLocatieBeschrijving());

        assertTrue(lo3Inhoud.isPuntAdres());
        assertTrue(lo3Inhoud.isNederlandsAdres());
        assertFalse(lo3Inhoud.isImmigratie());
        assertFalse(lo3Inhoud.isEmigratie());
    }

    @Test
    @Definitie({Definities.DEF045, Definities.DEF074})
    @Requirement(Requirements.CCA08_BL03)
    public void testEmigratieAlleenRegel1Gevuld() {
        final BrpStapel<BrpAdresInhoud> adresStapel = maakBuitenlandsAdresStapel(ALLEEN_BUITENLANDS_ADRES_REGEL_1);
        final BrpMigratieInhoud.Builder builder = maakMigratieBuilder(false, ALLEEN_BUITENLANDS_ADRES_REGEL_1);
        final BrpStapel<BrpMigratieInhoud> migratieStapel = maakMigratieStapel(false, builder);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> tussenResult = converteerder.converteer(adresStapel, migratieStapel);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.nabewerking(tussenResult, migratieStapel, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertBuitenlandsAdres(lo3Inhoud, ALLEEN_BUITENLANDS_ADRES_REGEL_1);
        assertEquals(LO3_LAND_CODE_EMIGRATIE, lo3Inhoud.getLandAdresBuitenland());
        assertEquals(LO3_DATUM_VERTREK_UIT_NL, lo3Inhoud.getDatumVertrekUitNederland());
        assertFalse(lo3Inhoud.isNederlandsAdres());
        assertFalse(lo3Inhoud.isImmigratie());
        assertTrue(lo3Inhoud.isEmigratie());
    }

    @Test
    @Definitie({Definities.DEF046, Definities.DEF075})
    @Requirement(Requirements.CCA08_BL03)
    public void testEmigratieAlleRegelsGevuld() {
        final BrpStapel<BrpAdresInhoud> adresStapel = maakBuitenlandsAdresStapel(ALLE_BUITENLANDS_ADRES_REGELS);
        final BrpMigratieInhoud.Builder builder = maakMigratieBuilder(IS_EMIGRATIE, ALLE_BUITENLANDS_ADRES_REGELS);
        final BrpStapel<BrpMigratieInhoud> migratieStapel = maakMigratieStapel(IS_EMIGRATIE, builder);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> tussenResult = converteerder.converteer(adresStapel, migratieStapel);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.nabewerking(tussenResult, migratieStapel, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertBuitenlandsAdres(lo3Inhoud, ALLE_BUITENLANDS_ADRES_REGELS);
        assertEquals(LO3_LAND_CODE_EMIGRATIE, lo3Inhoud.getLandAdresBuitenland());
        assertEquals(LO3_DATUM_VERTREK_UIT_NL, lo3Inhoud.getDatumVertrekUitNederland());
        assertFalse(lo3Inhoud.isNederlandsAdres());
        assertFalse(lo3Inhoud.isImmigratie());
        assertTrue(lo3Inhoud.isEmigratie());
    }

    @Definitie({Definities.DEF046, Definities.DEF075})
    @Requirement(Requirements.CCA08_BL03)
    @Preconditie(SoortMeldingCode.PRE095)
    @Test
    public void testEmigratieRegels4en5en6gevuld() {
        try {
            final BrpMigratieInhoud.Builder builder = maakMigratieBuilder(IS_EMIGRATIE, ALLE_BUITENLANDS_ADRES_REGELS);
            builder.buitenlandsAdresRegel4(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_1)));
            builder.buitenlandsAdresRegel5(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_1)));
            builder.buitenlandsAdresRegel6(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_1)));
            final BrpStapel<BrpMigratieInhoud> migratieStapel = maakMigratieStapel(IS_EMIGRATIE, builder);
            migratieStapel.valideer();
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE095.name(), pe.getPreconditieNaam());
            assertFalse(pe.getGroepen().isEmpty());
            assertTrue(pe.getGroepen().size() == 1);
            assertEquals(BrpMigratieInhoud.class.getName(), pe.getGroepen().iterator().next());
        }
    }

    @Definitie({Definities.DEF046, Definities.DEF075})
    @Requirement(Requirements.CCA08_BL03)
    @Preconditie(SoortMeldingCode.PRE096)
    @Test
    public void testEmigratieRegel1TeLang() {
        try {
            final BrpMigratieInhoud.Builder builder = maakMigratieBuilder(IS_EMIGRATIE, ALLE_BUITENLANDS_ADRES_REGELS);
            builder.buitenlandsAdresRegel1(
                    new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_1) + Lo3String.unwrap(ADRES_BUITENLAND_2) + Lo3String.unwrap(ADRES_BUITENLAND_3)));
            final BrpStapel<BrpMigratieInhoud> migratieStapel = maakMigratieStapel(IS_EMIGRATIE, builder);
            migratieStapel.valideer();
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE096.name(), pe.getPreconditieNaam());
            assertFalse(pe.getGroepen().isEmpty());
            assertTrue(pe.getGroepen().size() == 1);
            assertEquals(BrpMigratieInhoud.class.getName(), pe.getGroepen().iterator().next());
        }
    }

    @Test
    @Definitie(Definities.DEF076)
    @Requirement(Requirements.CCA08_BL04)
    public void testImmigratie() {
        final BrpAdresInhoud.Builder adresBuilder = maakNederlandsAdresBuilder(false);
        final BrpStapel<BrpAdresInhoud> adresStapel = maakNederlandsAdresStapel(adresBuilder);

        final BrpMigratieInhoud.Builder builder = maakMigratieBuilder(IS_IMMIGRATIE, null);
        final BrpStapel<BrpMigratieInhoud> migratieStapel = maakMigratieStapel(IS_IMMIGRATIE, builder);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> tussenResult = converteerder.converteer(adresStapel, migratieStapel);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.nabewerking(tussenResult, migratieStapel, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertNederlandsAdres(lo3Inhoud, false);
        assertEquals(LO3_LAND_CODE_IMMIGRATIE, lo3Inhoud.getLandVanwaarIngeschreven());
        assertEquals(LO3_DATUM_VESTIGING_IN_NL, lo3Inhoud.getVestigingInNederland());
        assertTrue(lo3Inhoud.isNederlandsAdres());
        assertTrue(lo3Inhoud.isImmigratie());
        assertFalse(lo3Inhoud.isEmigratie());
    }

    @Test
    @Requirement(Requirements.CCA08_BL05)
    public void testBijhoudingsgemeente() {
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel = maakBijhoudingStapel();

        // converteer en nabewerking
        Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.converteer(bijhoudingStapel);
        result = converteerder.nabewerking(result, null, bijhoudingStapel);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertEquals(LO3_GEMEENTE_CODE, lo3Inhoud.getGemeenteInschrijving());
        assertEquals(LO3_DATUM_INSCHRIJVING, lo3Inhoud.getDatumInschrijving());
    }

    @Test
    public void testOnverwerktDocumentAanwezigIndicatie() {
        final BrpStapel<BrpOnverwerktDocumentAanwezigIndicatieInhoud> onverwerktDocumentAanwezigIndicatieStapel =
                maakOnverwerktDocumentAanwezigIndicatieStapel();

        Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.converteer(onverwerktDocumentAanwezigIndicatieStapel);
        result = converteerder.nabewerking(result, null, null);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertEquals(LO3_DATUM_INSCHRIJVING, lo3Inhoud.getDatumInschrijving());
        assertEquals(LO3_INDICATIE_DOCUMENT, lo3Inhoud.getIndicatieDocument());
    }

    private void assertBuitenlandsAdres(final Lo3VerblijfplaatsInhoud lo3Inhoud, final boolean alleenBuitenlandsAdresRegel1) {
        // Controle of groep 11 leeg is
        assertNull(lo3Inhoud.getStraatnaam());
        if (alleenBuitenlandsAdresRegel1) {
            assertNull(lo3Inhoud.getAdresBuitenland1());
            assertNull(lo3Inhoud.getAdresBuitenland3());
            assertEquals(ADRES_BUITENLAND_2, lo3Inhoud.getAdresBuitenland2());
        } else {
            assertEquals(ADRES_BUITENLAND_1, lo3Inhoud.getAdresBuitenland1());
            assertEquals(ADRES_BUITENLAND_2, lo3Inhoud.getAdresBuitenland2());
            assertEquals(ADRES_BUITENLAND_3, lo3Inhoud.getAdresBuitenland3());
        }
    }

    private void assertNederlandsAdres(final Lo3VerblijfplaatsInhoud lo3Inhoud, final boolean isWoonplaatsnaamNull) {
        assertAdreshouding(lo3Inhoud);
        assertNull(lo3Inhoud.getGemeenteInschrijving());
        assertEquals(IDENTCODE_ADRESSEERBAAR_OBJECT, lo3Inhoud.getIdentificatiecodeVerblijfplaats());
        assertEquals(IDENTCODE_NUMMERAANDUIDING, lo3Inhoud.getIdentificatiecodeNummeraanduiding());
        assertEquals(NAAM_OPENBARE_RUIMTE, lo3Inhoud.getNaamOpenbareRuimte());
        assertEquals(STRAATNAAM, lo3Inhoud.getStraatnaam());
        assertEquals(GEMEENTE_DEEL, lo3Inhoud.getGemeenteDeel());
        assertEquals(LO3_HUISNUMMER, lo3Inhoud.getHuisnummer());
        assertEquals(HUISLETTER, lo3Inhoud.getHuisletter());
        assertEquals(HUISNUMMER_TOEVOEGING, lo3Inhoud.getHuisnummertoevoeging());
        assertEquals(POSTCODE, lo3Inhoud.getPostcode());
        if (isWoonplaatsnaamNull) {
            assertEquals(Lo3String.wrap("."), lo3Inhoud.getWoonplaatsnaam());
        } else {
            assertEquals(WOONPLAATSNAAM, lo3Inhoud.getWoonplaatsnaam());
        }
        assertEquals(LO3_AANDUIDING_HUISNUMMER, lo3Inhoud.getAanduidingHuisnummer());
        assertEquals(LOCATIE_OMSCHRIJVING, lo3Inhoud.getLocatieBeschrijving());
    }

    private void assertAdreshouding(final Lo3VerblijfplaatsInhoud voorkomen) {
        assertEquals(LO3_FUNCTIE_ADRES_B, voorkomen.getFunctieAdres());
        assertEquals(LO3_AANGIFTE_ADRESHOUDING_INGESCHREVENE, voorkomen.getAangifteAdreshouding());
        assertEquals(LO3_DATUM_AANVANG_ADRESHOUDING, voorkomen.getAanvangAdreshouding());
    }

    private BrpStapel<BrpMigratieInhoud> maakMigratieStapel(final boolean immigratie, final BrpMigratieInhoud.Builder builder) {
        final BrpDatum hisDatum;
        if (immigratie) {
            hisDatum = BRP_DATUM_VESTIGING_IN_NL;
        } else {
            hisDatum = BRP_DATUM_VERTREK_UIT_NL;
        }
        return stapel(groep(builder.build(), his(hisDatum.getWaarde()), act(9, 1994_01_02)));
    }

    private BrpMigratieInhoud.Builder maakMigratieBuilder(final boolean immigratie, final Boolean alleenBuitenlandsAdresRegel1) {
        final BrpMigratieInhoud.Builder builder;
        if (immigratie) {
            builder = new BrpMigratieInhoud.Builder(BrpSoortMigratieCode.IMMIGRATIE, BRP_LAND_OF_GEBIED_CODE_IMMIGRATIE);
        } else {
            builder = new BrpMigratieInhoud.Builder(BrpSoortMigratieCode.EMIGRATIE, BRP_LAND_OF_GEBIED_CODE_EMIGRATIE);
            if (alleenBuitenlandsAdresRegel1) {
                builder.buitenlandsAdresRegel1(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_2)));
            } else {
                builder.buitenlandsAdresRegel1(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_1)));
                builder.buitenlandsAdresRegel2(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_2)));
                builder.buitenlandsAdresRegel3(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_3)));
            }
        }
        return builder;
    }

    private BrpAdresInhoud.Builder maakNederlandsAdresBuilder(final boolean puntAdres) {
        final BrpAdresInhoud.Builder builder = new BrpAdresInhoud.Builder(BrpSoortAdresCode.B);
        builder.gemeenteCode(new BrpGemeenteCode("0518"));
        builder.aangeverAdreshoudingCode(BRP_AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE);
        builder.redenWijzigingAdresCode(BRP_REDEN_WIJZIGING_ADRES_PERSOON);
        builder.datumAanvangAdreshouding(BRP_DATUM_AANVANG_ADRESHOUDING);
        builder.landOfGebiedCode(BRP_LAND_OF_GEBIED_CODE_NL);
        if (!puntAdres) {
            builder.gemeentedeel(new BrpString(Lo3String.unwrap(GEMEENTE_DEEL)));
            builder.afgekorteNaamOpenbareRuimte(new BrpString(Lo3String.unwrap(STRAATNAAM)));
            builder.naamOpenbareRuimte(new BrpString(Lo3String.unwrap(NAAM_OPENBARE_RUIMTE)));
            builder.huisnummer(HUISNUMMER);
            builder.huisletter(new BrpCharacter(Lo3Character.unwrap(HUISLETTER)));
            builder.huisnummertoevoeging(new BrpString(Lo3String.unwrap(HUISNUMMER_TOEVOEGING)));
            builder.locatieTovAdres(BRP_AANDUIDING_HUISNUMMER);
            builder.postcode(new BrpString(Lo3String.unwrap(POSTCODE)));
            builder.woonplaatsnaam(new BrpString(Lo3String.unwrap(WOONPLAATSNAAM)));
            builder.identificatiecodeAdresseerbaarObject(new BrpString(Lo3String.unwrap(IDENTCODE_ADRESSEERBAAR_OBJECT)));
            builder.identificatiecodeNummeraanduiding(new BrpString(Lo3String.unwrap(IDENTCODE_NUMMERAANDUIDING)));
            builder.locatieOmschrijving(new BrpString(Lo3String.unwrap(LOCATIE_OMSCHRIJVING)));
        }
        return builder;
    }

    private BrpStapel<BrpAdresInhoud> maakNederlandsAdresStapel(final BrpAdresInhoud.Builder builder) {
        return stapel(groep(builder.build(), his(DATUM_AANVANG_GELDIGHEID), act(9, 1994_01_02)));
    }

    private BrpStapel<BrpAdresInhoud> maakBuitenlandsAdresStapel(final boolean alleenBuitenlandsAdresRegel1) {
        final BrpAdresInhoud.Builder builder = new BrpAdresInhoud.Builder(BrpSoortAdresCode.W);
        builder.aangeverAdreshoudingCode(BRP_AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE);
        builder.redenWijzigingAdresCode(BRP_REDEN_WIJZIGING_ADRES_PERSOON);
        builder.landOfGebiedCode(BRP_LAND_OF_GEBIED_CODE_EMIGRATIE);
        if (alleenBuitenlandsAdresRegel1) {
            builder.buitenlandsAdresRegel1(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_2)));
        } else {
            builder.buitenlandsAdresRegel1(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_1)));
            builder.buitenlandsAdresRegel2(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_2)));
            builder.buitenlandsAdresRegel3(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_3)));
        }
        return stapel(groep(builder.build(), his(BRP_DATUM_VERTREK_UIT_NL.getWaarde()), act(9, 1994_01_02)));
    }

    private BrpStapel<BrpBijhoudingInhoud> maakBijhoudingStapel() {
        final BrpBijhoudingInhoud bijhoudingInhoud =
                new BrpBijhoudingInhoud(
                        BIJHOUDINGSPARTIJ_CODE,
                        BrpBijhoudingsaardCode.INGEZETENE,
                        BrpNadereBijhoudingsaardCode.ACTUEEL);
        return stapel(groep(bijhoudingInhoud, his(BRP_DATUM_INSCHRIJVING.getWaarde()), act(10, BRP_DATUM_INSCHRIJVING.getWaarde())));
    }

    private BrpStapel<BrpOnverwerktDocumentAanwezigIndicatieInhoud> maakOnverwerktDocumentAanwezigIndicatieStapel() {
        final BrpOnverwerktDocumentAanwezigIndicatieInhoud inhoud = new BrpOnverwerktDocumentAanwezigIndicatieInhoud(new BrpBoolean(true), null, null);
        return stapel(groep(inhoud, his(BRP_DATUM_INSCHRIJVING.getWaarde()), act(10, BRP_DATUM_INSCHRIJVING.getWaarde())));
    }
}
