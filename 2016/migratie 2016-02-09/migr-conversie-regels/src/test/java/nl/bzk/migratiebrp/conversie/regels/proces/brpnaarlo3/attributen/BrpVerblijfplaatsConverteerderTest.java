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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractVerblijfplaatsTest;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor de terug conversie van BrpAdres/Migratie/Bijhouding naar Lo3Verblijfplaats.
 */
public class BrpVerblijfplaatsConverteerderTest extends AbstractVerblijfplaatsTest {

    private static final boolean ALLEEN_BUITENLANDS_ADRES_REGEL_1 = true;
    private static final boolean ALLE_BUITENLANDS_ADRES_REGELS = false;
    private static final boolean IS_IMMIGRATIE = true;
    private static final boolean IS_EMIGRATIE = false;
    @Inject
    private BrpVerblijfplaatsConverteerder converteerder;

    @Test
    @Definitie(Definities.DEF024)
    @Requirement({Requirements.CCA08_BL01, Requirements.CCA08_BL02 })
    public void testNederlandsAdres() {
        final BrpAdresInhoud.Builder builder = maakNederlandsAdresBuilder(false);
        final BrpStapel<BrpAdresInhoud> adresStapel = maakNederlandsAdresStapel(builder);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.converteer(adresStapel);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        Assert.assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertNederlandsAdres(voorkomen.getInhoud(), false);
        Assert.assertTrue(lo3Inhoud.isNederlandsAdres());
        Assert.assertFalse(lo3Inhoud.isImmigratie());
        Assert.assertFalse(lo3Inhoud.isEmigratie());
    }

    @Test
    @Definitie(Definities.DEF024)
    @Requirement({Requirements.CCA08_BL01, Requirements.CCA08_BL02 })
    public void testNederlandsAdresWoonplaatsnaamNull() {
        final BrpAdresInhoud.Builder builder = maakNederlandsAdresBuilder(false);
        builder.woonplaatsnaam(null);
        final BrpStapel<BrpAdresInhoud> adresStapel = maakNederlandsAdresStapel(builder);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.converteer(adresStapel);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        Assert.assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertNederlandsAdres(voorkomen.getInhoud(), true);
        Assert.assertTrue(lo3Inhoud.isNederlandsAdres());
        Assert.assertFalse(lo3Inhoud.isImmigratie());
        Assert.assertFalse(lo3Inhoud.isEmigratie());
    }

    @Test
    @Definitie(Definities.DEF023)
    @Requirement({Requirements.CCA08_BL01, Requirements.CCA08_BL02 })
    public void testNederlandsPuntAdres() {
        final BrpAdresInhoud.Builder builder = maakNederlandsAdresBuilder(true);
        final BrpStapel<BrpAdresInhoud> adresStapel = maakNederlandsAdresStapel(builder);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.converteer(adresStapel);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        Assert.assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        Assert.assertEquals(STRAATNAAM_PUNT, lo3Inhoud.getStraatnaam());
        Assert.assertNull(lo3Inhoud.getIdentificatiecodeVerblijfplaats());
        Assert.assertNull(lo3Inhoud.getIdentificatiecodeNummeraanduiding());
        Assert.assertNull(lo3Inhoud.getGemeenteInschrijving());
        Assert.assertNull(lo3Inhoud.getNaamOpenbareRuimte());
        Assert.assertNull(lo3Inhoud.getGemeenteDeel());
        Assert.assertNull(lo3Inhoud.getHuisnummer());
        Assert.assertNull(lo3Inhoud.getHuisletter());
        Assert.assertNull(lo3Inhoud.getHuisnummertoevoeging());
        Assert.assertNull(lo3Inhoud.getPostcode());
        Assert.assertNull(lo3Inhoud.getWoonplaatsnaam());
        Assert.assertNull(lo3Inhoud.getAanduidingHuisnummer());
        Assert.assertNull(lo3Inhoud.getLocatieBeschrijving());

        Assert.assertTrue(lo3Inhoud.isPuntAdres());
        Assert.assertTrue(lo3Inhoud.isNederlandsAdres());
        Assert.assertFalse(lo3Inhoud.isImmigratie());
        Assert.assertFalse(lo3Inhoud.isEmigratie());
    }

    @Test
    @Definitie({Definities.DEF045, Definities.DEF074 })
    @Requirement(Requirements.CCA08_BL03)
    public void testEmigratieAlleenRegel1Gevuld() {
        final BrpStapel<BrpAdresInhoud> adresStapel = maakBuitenlandsAdresStapel(ALLEEN_BUITENLANDS_ADRES_REGEL_1);
        final BrpMigratieInhoud.Builder builder = maakMigratieBuilder(false, ALLEEN_BUITENLANDS_ADRES_REGEL_1);
        final BrpStapel<BrpMigratieInhoud> migratieStapel = maakMigratieStapel(false, builder);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> tussenResult = converteerder.converteer(adresStapel, migratieStapel);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.nabewerking(tussenResult, migratieStapel, null);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        Assert.assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertBuitenlandsAdres(lo3Inhoud, ALLEEN_BUITENLANDS_ADRES_REGEL_1);
        Assert.assertEquals(LO3_LAND_CODE_EMIGRATIE, lo3Inhoud.getLandAdresBuitenland());
        Assert.assertEquals(LO3_DATUM_VERTREK_UIT_NL, lo3Inhoud.getDatumVertrekUitNederland());
        Assert.assertFalse(lo3Inhoud.isNederlandsAdres());
        Assert.assertFalse(lo3Inhoud.isImmigratie());
        Assert.assertTrue(lo3Inhoud.isEmigratie());
    }

    @Test
    @Definitie({Definities.DEF046, Definities.DEF075 })
    @Requirement(Requirements.CCA08_BL03)
    public void testEmigratieAlleRegelsGevuld() {
        final BrpStapel<BrpAdresInhoud> adresStapel = maakBuitenlandsAdresStapel(ALLE_BUITENLANDS_ADRES_REGELS);
        final BrpMigratieInhoud.Builder builder = maakMigratieBuilder(IS_EMIGRATIE, ALLE_BUITENLANDS_ADRES_REGELS);
        final BrpStapel<BrpMigratieInhoud> migratieStapel = maakMigratieStapel(IS_EMIGRATIE, builder);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> tussenResult = converteerder.converteer(adresStapel, migratieStapel);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.nabewerking(tussenResult, migratieStapel, null);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        Assert.assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertBuitenlandsAdres(lo3Inhoud, ALLE_BUITENLANDS_ADRES_REGELS);
        Assert.assertEquals(LO3_LAND_CODE_EMIGRATIE, lo3Inhoud.getLandAdresBuitenland());
        Assert.assertEquals(LO3_DATUM_VERTREK_UIT_NL, lo3Inhoud.getDatumVertrekUitNederland());
        Assert.assertFalse(lo3Inhoud.isNederlandsAdres());
        Assert.assertFalse(lo3Inhoud.isImmigratie());
        Assert.assertTrue(lo3Inhoud.isEmigratie());
    }

    @Definitie({Definities.DEF046, Definities.DEF075 })
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
            Assert.assertEquals(SoortMeldingCode.PRE095.name(), pe.getPreconditieNaam());
            Assert.assertFalse(pe.getGroepen().isEmpty());
            Assert.assertTrue(pe.getGroepen().size() == 1);
            Assert.assertEquals(BrpMigratieInhoud.class.getName(), pe.getGroepen().iterator().next());
        }
    }

    @Definitie({Definities.DEF046, Definities.DEF075 })
    @Requirement(Requirements.CCA08_BL03)
    @Preconditie(SoortMeldingCode.PRE096)
    @Test
    public void testEmigratieRegel1TeLang() {
        try {
            final BrpMigratieInhoud.Builder builder = maakMigratieBuilder(IS_EMIGRATIE, ALLE_BUITENLANDS_ADRES_REGELS);
            builder.buitenlandsAdresRegel1(new BrpString(Lo3String.unwrap(ADRES_BUITENLAND_1)
                                                         + Lo3String.unwrap(ADRES_BUITENLAND_2)
                                                         + Lo3String.unwrap(ADRES_BUITENLAND_3)));
            final BrpStapel<BrpMigratieInhoud> migratieStapel = maakMigratieStapel(IS_EMIGRATIE, builder);
            migratieStapel.valideer();
        } catch (final PreconditieException pe) {
            Assert.assertEquals(SoortMeldingCode.PRE096.name(), pe.getPreconditieNaam());
            assertFalse(pe.getGroepen().isEmpty());
            assertTrue(pe.getGroepen().size() == 1);
            Assert.assertEquals(BrpMigratieInhoud.class.getName(), pe.getGroepen().iterator().next());
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

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        Assert.assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        assertNederlandsAdres(lo3Inhoud, false);
        Assert.assertEquals(LO3_LAND_CODE_IMMIGRATIE, lo3Inhoud.getLandVanwaarIngeschreven());
        Assert.assertEquals(LO3_DATUM_VESTIGING_IN_NL, lo3Inhoud.getVestigingInNederland());
        Assert.assertTrue(lo3Inhoud.isNederlandsAdres());
        Assert.assertTrue(lo3Inhoud.isImmigratie());
        Assert.assertFalse(lo3Inhoud.isEmigratie());
    }

    @Test
    @Requirement(Requirements.CCA08_BL05)
    public void testBijhoudingsgemeente() {
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel = maakBijhoudingStapel();

        // converteer en nabewerking
        Lo3Stapel<Lo3VerblijfplaatsInhoud> result = converteerder.converteer(bijhoudingStapel);
        result = converteerder.nabewerking(result, null, bijhoudingStapel);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen = result.get(0);
        Assert.assertNotNull(voorkomen);

        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        Assert.assertEquals(LO3_GEMEENTE_CODE, lo3Inhoud.getGemeenteInschrijving());
        Assert.assertEquals(LO3_DATUM_INSCHRIJVING, lo3Inhoud.getDatumInschrijving());
        Assert.assertEquals(LO3_INDICATIE_DOCUMENT, lo3Inhoud.getIndicatieDocument());
    }

    private void assertBuitenlandsAdres(final Lo3VerblijfplaatsInhoud lo3Inhoud, final boolean alleenBuitenlandsAdresRegel1) {
        // Controle of groep 11 leeg is
        Assert.assertNull(lo3Inhoud.getStraatnaam());
        if (alleenBuitenlandsAdresRegel1) {
            Assert.assertNull(lo3Inhoud.getAdresBuitenland1());
            Assert.assertNull(lo3Inhoud.getAdresBuitenland3());
            Assert.assertEquals(ADRES_BUITENLAND_2, lo3Inhoud.getAdresBuitenland2());
        } else {
            Assert.assertEquals(ADRES_BUITENLAND_1, lo3Inhoud.getAdresBuitenland1());
            Assert.assertEquals(ADRES_BUITENLAND_2, lo3Inhoud.getAdresBuitenland2());
            Assert.assertEquals(ADRES_BUITENLAND_3, lo3Inhoud.getAdresBuitenland3());
        }
    }

    private void assertNederlandsAdres(final Lo3VerblijfplaatsInhoud lo3Inhoud, final boolean isWoonplaatsnaamNull) {
        assertAdreshouding(lo3Inhoud);
        Assert.assertNull(lo3Inhoud.getGemeenteInschrijving());
        Assert.assertEquals(IDENTCODE_ADRESSEERBAAR_OBJECT, lo3Inhoud.getIdentificatiecodeVerblijfplaats());
        Assert.assertEquals(IDENTCODE_NUMMERAANDUIDING, lo3Inhoud.getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals(NAAM_OPENBARE_RUIMTE, lo3Inhoud.getNaamOpenbareRuimte());
        Assert.assertEquals(STRAATNAAM, lo3Inhoud.getStraatnaam());
        Assert.assertEquals(GEMEENTE_DEEL, lo3Inhoud.getGemeenteDeel());
        Assert.assertEquals(LO3_HUISNUMMER, lo3Inhoud.getHuisnummer());
        Assert.assertEquals(HUISLETTER, lo3Inhoud.getHuisletter());
        Assert.assertEquals(HUISNUMMER_TOEVOEGING, lo3Inhoud.getHuisnummertoevoeging());
        Assert.assertEquals(POSTCODE, lo3Inhoud.getPostcode());
        if (isWoonplaatsnaamNull) {
            Assert.assertEquals(Lo3String.wrap("."), lo3Inhoud.getWoonplaatsnaam());
        } else {
            Assert.assertEquals(WOONPLAATSNAAM, lo3Inhoud.getWoonplaatsnaam());
        }
        Assert.assertEquals(LO3_AANDUIDING_HUISNUMMER, lo3Inhoud.getAanduidingHuisnummer());
        Assert.assertEquals(LOCATIE_OMSCHRIJVING, lo3Inhoud.getLocatieBeschrijving());
    }

    private void assertAdreshouding(final Lo3VerblijfplaatsInhoud voorkomen) {
        Assert.assertEquals(LO3_FUNCTIE_ADRES_B, voorkomen.getFunctieAdres());
        Assert.assertEquals(LO3_AANGIFTE_ADRESHOUDING_INGESCHREVENE, voorkomen.getAangifteAdreshouding());
        Assert.assertEquals(LO3_DATUM_AANVANG_ADRESHOUDING, voorkomen.getAanvangAdreshouding());
    }

    private BrpStapel<BrpMigratieInhoud> maakMigratieStapel(final boolean immigratie, final BrpMigratieInhoud.Builder builder) {
        final BrpDatum hisDatum;
        if (immigratie) {
            hisDatum = BRP_DATUM_VESTIGING_IN_NL;
        } else {
            hisDatum = BRP_DATUM_VERTREK_UIT_NL;
        }
        return stapel(groep(builder.build(), his(hisDatum.getWaarde()), act(9, 19940102)));
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
        builder.gemeenteCode(new BrpGemeenteCode((short)518));
        builder.aangeverAdreshoudingCode(BRP_AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE);
        builder.redenWijzigingAdresCode(BRP_REDEN_WIJZIGING_ADRES_PERSOON);
        builder.datumAanvangAdreshouding(BRP_DATUM_AANVANG_ADRESHOUDING);
        builder.landOfGebiedCode(BRP_LAND_OF_GEBIED_CODE_NL);
        if (puntAdres) {
            builder.afgekorteNaamOpenbareRuimte(new BrpString(Lo3String.unwrap(STRAATNAAM_PUNT)));
        } else {
            builder.gemeentedeel(new BrpString(Lo3String.unwrap(GEMEENTE_DEEL)));
            builder.afgekorteNaamOpenbareRuimte(new BrpString(Lo3String.unwrap(STRAATNAAM)));
            builder.naamOpenbareRuimte(new BrpString(Lo3String.unwrap(NAAM_OPENBARE_RUIMTE)));
            builder.huisnummer(new BrpInteger(Lo3Integer.unwrap(HUISNUMMER)));
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
        return stapel(groep(builder.build(), his(DATUM_AANVANG_GELDIGHEID), act(9, 19940102)));
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
        return stapel(groep(builder.build(), his(BRP_DATUM_VERTREK_UIT_NL.getWaarde()), act(9, 19940102)));
    }

    private BrpStapel<BrpBijhoudingInhoud> maakBijhoudingStapel() {
        final BrpBijhoudingInhoud bijhoudingInhoud =
                new BrpBijhoudingInhoud(
                    new BrpPartijCode(BRP_GEMEENTE_CODE.getWaarde() * 100 + 1),
                    BrpBijhoudingsaardCode.INGEZETENE,
                    BrpNadereBijhoudingsaardCode.ACTUEEL,
                    new BrpBoolean(true));
        return stapel(groep(bijhoudingInhoud, his(BRP_DATUM_INSCHRIJVING.getWaarde()), act(10, BRP_DATUM_INSCHRIJVING.getWaarde())));
    }
}
