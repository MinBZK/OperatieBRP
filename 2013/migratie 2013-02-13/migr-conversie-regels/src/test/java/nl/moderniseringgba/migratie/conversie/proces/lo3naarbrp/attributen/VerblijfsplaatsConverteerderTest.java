/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpImmigratieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieDocumentEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor {@link VerblijfplaatsConverteerder}.
 * 
 */
public class VerblijfsplaatsConverteerderTest extends AbstractComponentTest {

    @Inject
    private VerblijfplaatsConverteerder verblijfplaatsConverteerder;

    @SuppressWarnings("unchecked")
    private MigratiePersoonslijstBuilder buildMigratiePersoonslijstBuilder() {
        final MigratieStapel<BrpIdentificatienummersInhoud> identificatienummerStapel =
                new MigratieStapel<BrpIdentificatienummersInhoud>(
                        Arrays.asList(new MigratieGroep<BrpIdentificatienummersInhoud>(
                                new BrpIdentificatienummersInhoud(1234567890L, 1234567891L),
                                Lo3Historie.NULL_HISTORIE, null, null)));
        final MigratieStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                new MigratieStapel<BrpGeslachtsaanduidingInhoud>(
                        Arrays.asList(new MigratieGroep<BrpGeslachtsaanduidingInhoud>(
                                new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN),
                                Lo3Historie.NULL_HISTORIE, null, null)));

        final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder = new MigratiePersoonslijstBuilder();
        migratiePersoonslijstBuilder.identificatienummerStapel(identificatienummerStapel);
        migratiePersoonslijstBuilder.geslachtsaanduidingStapel(geslachtsaanduidingStapel);

        migratiePersoonslijstBuilder.geboorteStapel(new MigratieStapel<BrpGeboorteInhoud>(Arrays
                .asList(new MigratieGroep<BrpGeboorteInhoud>(new BrpGeboorteInhoud(new BrpDatum(19770101), null,
                        null, null, null, new BrpLandCode(Integer.valueOf("0000")), null), Lo3Historie.NULL_HISTORIE,
                        null, null))));
        // migratiePersoonslijstBuilder
        // .geslachtsnaamcomponentStapel(new MigratieStapel<BrpGeslachtsnaamcomponentInhoud>(Arrays
        // .asList(new MigratieGroep<BrpGeslachtsnaamcomponentInhoud>(
        // new BrpGeslachtsnaamcomponentInhoud(null, null, "Jansen", null, null, 1),
        // Lo3Historie.NULL_HISTORIE, null))));
        migratiePersoonslijstBuilder.aanschrijvingStapel(new MigratieStapel<BrpAanschrijvingInhoud>(Arrays
                .asList(new MigratieGroep<BrpAanschrijvingInhoud>(new BrpAanschrijvingInhoud(null, null, null, null,
                        null, null, null, null, null), Lo3Historie.NULL_HISTORIE, null, null))));
        migratiePersoonslijstBuilder.inschrijvingStapel(new MigratieStapel<BrpInschrijvingInhoud>(Arrays
                .asList(new MigratieGroep<BrpInschrijvingInhoud>(new BrpInschrijvingInhoud(null, null, new BrpDatum(
                        19560312), 0), Lo3Historie.NULL_HISTORIE, null, null))));

        return migratiePersoonslijstBuilder;
    }

    @Test
    public void testNullInputs() {
        try {
            verblijfplaatsConverteerder.converteer(null, null);
            Assert.fail();
        } catch (final NullPointerException npe) {
        }

        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats = buildPuntVerblijfplaats();
        @SuppressWarnings("unchecked")
        final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> verblijfplaatsen = Arrays.asList(verblijfplaats);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel =
                new Lo3Stapel<Lo3VerblijfplaatsInhoud>(verblijfplaatsen);

        try {
            verblijfplaatsConverteerder.converteer(verblijfplaatsStapel, null);
            Assert.fail();
        } catch (final NullPointerException npe) {
        }

        final MigratiePersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();
        try {
            verblijfplaatsConverteerder.converteer(null, builder);
            Assert.fail();
        } catch (final NullPointerException npe) {
        }
    }

    private Lo3Categorie<Lo3VerblijfplaatsInhoud> buildPuntVerblijfplaats() {
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats =
                new Lo3Categorie<Lo3VerblijfplaatsInhoud>(new Lo3VerblijfplaatsInhoud(new Lo3GemeenteCode("0518") // gemeente
                        // inschrijving
                        , new Lo3Datum(20000101) // datum inschrijving
                        , Lo3FunctieAdresEnum.WOONADRES.asElement() // functie adres
                        , null // gemeentedeel
                        , new Lo3Datum(20000101) // aanvang adres houding
                        , "." // straatnaam
                        , null // naam openbare ruimte
                        , null // huisnummer
                        , null // huisletter
                        , null // huisnummertoevoeging
                        , null // aanduiding huisnummer
                        , null // postcode
                        , null // woonplaatsnaam
                        , null // identificatiecode verblijfplaats
                        , null // identificatiecode nummeraanduiding
                        , null // locatie beschrijving
                        , null // land waarnaar vertrokken
                        , null // datum vertrek uit nederland
                        , null // adres regel 1 buitenland
                        , null // adres regel 2 buitenland
                        , null // adres regel 3 buitenland
                        , null // land vanwaar ingeschreven
                        , null // datum vestiging in Nederland
                        , Lo3AangifteAdreshoudingEnum.ONBEKEND.asElement()// aangifte adreshouding
                        , null // indicatie document
                        ), null, Lo3Historie.NULL_HISTORIE, null);

        Assert.assertTrue(verblijfplaats.getInhoud().isPuntAdres());

        return verblijfplaats;
    }

    @Test
    public void testPuntAdres() throws InputValidationException {
        // Setup
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel =
                new Lo3Stapel<Lo3VerblijfplaatsInhoud>(Arrays.asList(buildPuntVerblijfplaats()));
        final MigratiePersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();

        // Run
        verblijfplaatsConverteerder.converteer(verblijfplaatsStapel, builder);
        final MigratiePersoonslijst migratiePersoonslijst = builder.build();
        final MigratieStapel<BrpAdresInhoud> adresStapel = migratiePersoonslijst.getAdresStapel();
        final MigratieStapel<BrpImmigratieInhoud> immigratieStapel = migratiePersoonslijst.getImmigratieStapel();
        final MigratieStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel =
                migratiePersoonslijst.getBijhoudingsgemeenteStapel();

        // Check
        Assert.assertEquals(1, adresStapel.size());
        final MigratieGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertNotNull(adres);
        Assert.assertFalse(adres.isInhoudelijkLeeg());

        // Controleer dat de punt niet is overgenomen vanuit de straatnaam
        Assert.assertNull(adres.getInhoud().getAfgekorteNaamOpenbareRuimte());

        // Controleer dat de gemeente is overgenomen uit de gemeente van inschrijving
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0518")), adres.getInhoud().getGemeenteCode());

        // Controleer dat Nederland als default is opgenomen
        Assert.assertEquals(new BrpLandCode(Integer.valueOf("6030")), adres.getInhoud().getLandCode());

        Assert.assertNull(immigratieStapel);
        // Assert.assertEquals(1, immigratieStapel.size());
        // Assert.assertTrue(immigratieStapel.get(0).isInhoudelijkLeeg());

        Assert.assertEquals(1, bijhoudingsgemeenteStapel.size());
        Assert.assertFalse(bijhoudingsgemeenteStapel.get(0).isInhoudelijkLeeg());
    }

    private Lo3Categorie<Lo3VerblijfplaatsInhoud> buildImmigratieNaarNederlandsAdres() {
        return new Lo3Categorie<Lo3VerblijfplaatsInhoud>(new Lo3VerblijfplaatsInhoud(new Lo3GemeenteCode("0518") // gemeente
                                                                                                                 // inschrijving
                , new Lo3Datum(20000101) // datum inschrijving
                , Lo3FunctieAdresEnum.WOONADRES.asElement() // functie adres
                , "linkerkant" // gemeentedeel
                , new Lo3Datum(19980101) // aanvang adres houding
                , "straat" // straatnaam
                , "ergens maar niet overal" // naam openbare ruimte
                , new Lo3Huisnummer(12) // huisnummer
                , 'a' // huisletter
                , "-z" // huisnummertoevoeging
                , null // aanduiding huisnummer
                , "1233WE" // postcode
                , "Dimmerdam" // woonplaatsnaam
                , "234982349832WER" // identificatiecode verblijfplaats
                , "00230234939342w" // identificatiecode nummeraanduiding
                , null // locatie beschrijving
                , null // land waarnaar vertrokken
                , null // datum vertrek uit nederland
                , null // adres regel 1 buitenland
                , null // adres regel 2 buitenland
                , null // adres regel 3 buitenland
                , new Lo3LandCode("1234") // land vanwaar ingeschreven
                , new Lo3Datum(19430102) // datum vestiging in Nederland
                , Lo3AangifteAdreshoudingEnum.INGESCHREVENE.asElement() // aangifte adreshouding
                , Lo3IndicatieDocumentEnum.INDICATIE.asElement() // indicatie document
                ), null, Lo3Historie.NULL_HISTORIE, null);
    }

    @Test
    public void testAdreshoudingNederlandsAdresImmigratieEnBijhoudingsgemeente() throws InputValidationException {
        // Setup
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel =
                new Lo3Stapel<Lo3VerblijfplaatsInhoud>(Arrays.asList(buildImmigratieNaarNederlandsAdres()));
        final MigratiePersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();

        // Run
        verblijfplaatsConverteerder.converteer(verblijfplaatsStapel, builder);
        final MigratiePersoonslijst migratiePersoonslijst = builder.build();
        final MigratieStapel<BrpAdresInhoud> adresStapel = migratiePersoonslijst.getAdresStapel();
        final MigratieStapel<BrpImmigratieInhoud> immigratieStapel = migratiePersoonslijst.getImmigratieStapel();
        final MigratieStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel =
                migratiePersoonslijst.getBijhoudingsgemeenteStapel();

        // Check
        Assert.assertEquals(1, adresStapel.size());
        final MigratieGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertFalse(adres.isInhoudelijkLeeg());
        Assert.assertEquals(BrpFunctieAdresCode.W, adres.getInhoud().getFunctieAdresCode());
        Assert.assertEquals(new BrpRedenWijzigingAdresCode("P"), adres.getInhoud().getRedenWijzigingAdresCode());
        Assert.assertEquals(new BrpAangeverAdreshoudingCode("I"), adres.getInhoud().getAangeverAdreshoudingCode());
        Assert.assertEquals(new BrpDatum(19980101), adres.getInhoud().getDatumAanvangAdreshouding());
        Assert.assertEquals("234982349832WER", adres.getInhoud().getAdresseerbaarObject());
        Assert.assertEquals("00230234939342w", adres.getInhoud().getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0518")), adres.getInhoud().getGemeenteCode());
        Assert.assertEquals("ergens maar niet overal", adres.getInhoud().getNaamOpenbareRuimte());
        Assert.assertEquals("straat", adres.getInhoud().getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("linkerkant", adres.getInhoud().getGemeentedeel());
        Assert.assertEquals(new Integer(12), adres.getInhoud().getHuisnummer());
        Assert.assertEquals(new Character('a'), adres.getInhoud().getHuisletter());
        Assert.assertEquals("-z", adres.getInhoud().getHuisnummertoevoeging());
        Assert.assertEquals("1233WE", adres.getInhoud().getPostcode());
        Assert.assertEquals(new BrpPlaatsCode("Dimmerdam"), adres.getInhoud().getPlaatsCode());
        Assert.assertNull(adres.getInhoud().getLocatieTovAdres());
        Assert.assertNull(adres.getInhoud().getLocatieOmschrijving());
        Assert.assertEquals(new BrpLandCode(Integer.valueOf("6030")), adres.getInhoud().getLandCode()); // Controleer
                                                                                                        // dat
                                                                                                        // Nederland als
        // default is
        // opgenomen

        Assert.assertEquals(1, immigratieStapel.size());
        final MigratieGroep<BrpImmigratieInhoud> immigratie = immigratieStapel.get(0);
        Assert.assertFalse(immigratie.isInhoudelijkLeeg());
        Assert.assertEquals(new BrpLandCode(Integer.valueOf("1234")), immigratie.getInhoud()
                .getLandVanwaarIngeschreven());
        Assert.assertEquals(new BrpDatum(19430102), immigratie.getInhoud().getDatumVestigingInNederland());

        Assert.assertEquals(1, bijhoudingsgemeenteStapel.size());
        final MigratieGroep<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeente = bijhoudingsgemeenteStapel.get(0);
        Assert.assertFalse(bijhoudingsgemeente.isInhoudelijkLeeg());
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0518")), bijhoudingsgemeente.getInhoud()
                .getBijhoudingsgemeenteCode());
        Assert.assertEquals(new BrpDatum(20000101), bijhoudingsgemeente.getInhoud().getDatumInschrijvingInGemeente());
        Assert.assertEquals(Boolean.TRUE, bijhoudingsgemeente.getInhoud().getOnverwerktDocumentAanwezig());
    }

    private Lo3Categorie<Lo3VerblijfplaatsInhoud> buildNederlandsLocatie() {
        return new Lo3Categorie<Lo3VerblijfplaatsInhoud>(new Lo3VerblijfplaatsInhoud(new Lo3GemeenteCode("0518") // gemeente
                                                                                                                 // inschrijving
                , new Lo3Datum(20000101) // datum inschrijving
                , Lo3FunctieAdresEnum.WOONADRES.asElement() // functie adres
                , null // gemeentedeel
                , new Lo3Datum(19980101) // aanvang adres houding
                , null // straatnaam
                , null // naam openbare ruimte
                , null // huisnummer
                , null // huisletter
                , null // huisnummertoevoeging
                , null // aanduiding huisnummer
                , null // postcode
                , null // woonplaatsnaam
                , null // identificatiecode verblijfplaats
                , null // identificatiecode nummeraanduiding
                , "Links achter de tweede hoek" // locatie beschrijving
                , null // land waarnaar vertrokken
                , null // datum vertrek uit nederland
                , null // adres regel 1 buitenland
                , null // adres regel 2 buitenland
                , null // adres regel 3 buitenland
                , null // land vanwaar ingeschreven
                , null // datum vestiging in Nederland
                , Lo3AangifteAdreshoudingEnum.ONBEKEND.asElement() // aangifte adreshouding
                , Lo3IndicatieDocumentEnum.INDICATIE.asElement() // indicatie document
                ), null, Lo3Historie.NULL_HISTORIE, null);
    }

    @Test
    public void testNederlandsLocatie() throws InputValidationException {
        // Setup
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel =
                new Lo3Stapel<Lo3VerblijfplaatsInhoud>(Arrays.asList(buildNederlandsLocatie()));
        final MigratiePersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();

        // Run
        verblijfplaatsConverteerder.converteer(verblijfplaatsStapel, builder);
        final MigratiePersoonslijst migratiePersoonslijst = builder.build();
        final MigratieStapel<BrpAdresInhoud> adresStapel = migratiePersoonslijst.getAdresStapel();
        final MigratieStapel<BrpImmigratieInhoud> immigratieStapel = migratiePersoonslijst.getImmigratieStapel();
        final MigratieStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel =
                migratiePersoonslijst.getBijhoudingsgemeenteStapel();

        // Check
        Assert.assertEquals(1, adresStapel.size());
        final MigratieGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertFalse(adres.isInhoudelijkLeeg());
        Assert.assertNull(adres.getInhoud().getAdresseerbaarObject());
        Assert.assertNull(adres.getInhoud().getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0518")), adres.getInhoud().getGemeenteCode());
        Assert.assertNull(adres.getInhoud().getNaamOpenbareRuimte());
        Assert.assertNull(adres.getInhoud().getAfgekorteNaamOpenbareRuimte());
        Assert.assertNull(adres.getInhoud().getGemeentedeel());
        Assert.assertNull(adres.getInhoud().getHuisnummer());
        Assert.assertNull(adres.getInhoud().getHuisletter());
        Assert.assertNull(adres.getInhoud().getHuisnummertoevoeging());
        Assert.assertNull(adres.getInhoud().getPostcode());
        Assert.assertNull(adres.getInhoud().getPlaatsCode());
        Assert.assertNull(adres.getInhoud().getLocatieTovAdres());
        Assert.assertEquals("Links achter de tweede hoek", adres.getInhoud().getLocatieOmschrijving());
        Assert.assertEquals(new BrpLandCode(Integer.valueOf("6030")), adres.getInhoud().getLandCode()); // Controleer
                                                                                                        // dat
                                                                                                        // Nederland als
        // default is
        // opgenomen

        Assert.assertNull(immigratieStapel);
        // Assert.assertEquals(1, immigratieStapel.size());
        // final MigratieImmigratie immigratie = immigratieStapel.get(0);
        // Assert.assertTrue(immigratie.isInhoudelijkLeeg());

        Assert.assertEquals(1, bijhoudingsgemeenteStapel.size());
        final MigratieGroep<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeente = bijhoudingsgemeenteStapel.get(0);
        Assert.assertFalse(bijhoudingsgemeente.isInhoudelijkLeeg());
    }

    private Lo3Categorie<Lo3VerblijfplaatsInhoud> buildEmigratie() {
        return new Lo3Categorie<Lo3VerblijfplaatsInhoud>(new Lo3VerblijfplaatsInhoud(new Lo3GemeenteCode("0518") // gemeente
                                                                                                                 // inschrijving
                , new Lo3Datum(20000101) // datum inschrijving
                , null// Lo3FunctieAdres.W // functie adres
                , null // gemeentedeel
                , null// new Lo3Datum(19980101) // aanvang adres houding
                , null // straatnaam
                , null // naam openbare ruimte
                , null // huisnummer
                , null // huisletter
                , null // huisnummertoevoeging
                , null // aanduiding huisnummer
                , null // postcode
                , null // woonplaatsnaam
                , null // identificatiecode verblijfplaats
                , null // identificatiecode nummeraanduiding
                , null // locatie beschrijving
                , new Lo3LandCode("4322") // land waarnaar vertrokken
                , new Lo3Datum(20120113) // datum vertrek uit nederland
                , "regel uno" // adres regel 1 buitenland
                , "regel duo" // adres regel 2 buitenland
                , "regel tres" // adres regel 3 buitenland
                , null // land vanwaar ingeschreven
                , null // datum vestiging in Nederland
                , Lo3AangifteAdreshoudingEnum.OUDER.asElement() // aangifte adreshouding
                , null // indicatie document
                ), null, Lo3Historie.NULL_HISTORIE, null);
    }

    @Test
    public void testEmigratie() throws InputValidationException {
        // Setup
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel =
                new Lo3Stapel<Lo3VerblijfplaatsInhoud>(Arrays.asList(buildEmigratie()));
        final MigratiePersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();

        // Run
        verblijfplaatsConverteerder.converteer(verblijfplaatsStapel, builder);
        final MigratiePersoonslijst migratiePersoonslijst = builder.build();
        final MigratieStapel<BrpAdresInhoud> adresStapel = migratiePersoonslijst.getAdresStapel();
        final MigratieStapel<BrpImmigratieInhoud> immigratieStapel = migratiePersoonslijst.getImmigratieStapel();
        final MigratieStapel<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeenteStapel =
                migratiePersoonslijst.getBijhoudingsgemeenteStapel();

        // Check
        Assert.assertEquals(1, adresStapel.size());
        final MigratieGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertFalse(adres.isInhoudelijkLeeg());

        Assert.assertNull(adres.getInhoud().getFunctieAdresCode());
        Assert.assertEquals("regel uno", adres.getInhoud().getBuitenlandsAdresRegel1());
        Assert.assertEquals("regel duo", adres.getInhoud().getBuitenlandsAdresRegel2());
        Assert.assertEquals("regel tres", adres.getInhoud().getBuitenlandsAdresRegel3());
        Assert.assertNull(adres.getInhoud().getBuitenlandsAdresRegel4());
        Assert.assertNull(adres.getInhoud().getBuitenlandsAdresRegel5());
        Assert.assertNull(adres.getInhoud().getBuitenlandsAdresRegel6());
        Assert.assertEquals(new BrpLandCode(Integer.valueOf("4322")), adres.getInhoud().getLandCode());
        Assert.assertEquals(new BrpDatum(20120113), adres.getInhoud().getDatumVertrekUitNederland());

        Assert.assertNull(immigratieStapel);

        Assert.assertEquals(1, bijhoudingsgemeenteStapel.size());
        final MigratieGroep<BrpBijhoudingsgemeenteInhoud> bijhoudingsgemeente = bijhoudingsgemeenteStapel.get(0);
        Assert.assertFalse(bijhoudingsgemeente.isInhoudelijkLeeg());
    }
}
