/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;

import org.junit.Assert;
import org.junit.Test;

/**
 * 

 */
public class KiesrechtConverteerderTest extends AbstractComponentTest {

    @Inject
    private KiesrechtConverteerder kiesrechtConverteerder;

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
        migratiePersoonslijstBuilder.adresStapel(new MigratieStapel<BrpAdresInhoud>(Arrays
                .asList(new MigratieGroep<BrpAdresInhoud>(new BrpAdresInhoud(null,
                        new BrpRedenWijzigingAdresCode("O"), new BrpAangeverAdreshoudingCode("A"), new BrpDatum(
                                20010101), null, null, new BrpGemeenteCode(new BigDecimal("0518")), null, null, null,
                        null, null, null, null, null, null, null, null, null, null, null, null, null,
                        new BrpLandCode(Integer.valueOf("6030")), null), Lo3Historie.NULL_HISTORIE, null, null))));

        return migratiePersoonslijstBuilder;
    }

    @Test
    public void testNullInputs() {
        try {
            kiesrechtConverteerder.converteer(null, null, null);
            Assert.fail();
        } catch (final NullPointerException npe) {
        }

        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel =
                new Lo3Stapel<Lo3KiesrechtInhoud>(Arrays.asList(buildUitsluitingNederlandsKiesrecht()));

        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel =
                new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(buildInschrijving()));

        try {
            kiesrechtConverteerder.converteer(kiesrechtStapel, inschrijvingStapel, null);
            Assert.fail();
        } catch (final NullPointerException npe) {
        }
    }

    @Test
    public void testEmpty() throws Exception {
        // Setup
        final MigratiePersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel =
                new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(buildInschrijving()));

        // Run
        kiesrechtConverteerder.converteer(null, inschrijvingStapel, builder);
        final MigratiePersoonslijst migratiePersoonslijst = builder.build();
        final MigratieStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel =
                migratiePersoonslijst.getUitsluitingNederlandsKiesrechtStapel();
        final MigratieStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel =
                migratiePersoonslijst.getEuropeseVerkiezingenStapel();

        // Check
        Assert.assertNull(uitsluitingNederlandsKiesrechtStapel);
        Assert.assertNull(europeseVerkiezingenStapel);
    }

    private Lo3Categorie<Lo3InschrijvingInhoud> buildInschrijving() {

        return new Lo3Categorie<Lo3InschrijvingInhoud>(new Lo3InschrijvingInhoud(null, null, null, null, null, null,
                1, new Lo3Datumtijdstempel(20120405121500000L),
                Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement()), null,
                Lo3Historie.NULL_HISTORIE, null);
    }

    private Lo3Categorie<Lo3KiesrechtInhoud> buildUitsluitingNederlandsKiesrecht() {
        return new Lo3Categorie<Lo3KiesrechtInhoud>(new Lo3KiesrechtInhoud(null, // aanduiding europees kiesrecht
                null, // datum europees kiesrecht
                null, // einddatum uitsluiting europees kiesrecht
                Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.asElement(), // aanduiding uitgesloten
                                                                                         // kiesrecht
                new Lo3Datum(20140405)), // einddatum uitsluiting kiesrecht
                null, Lo3Historie.NULL_HISTORIE, null);
    }

    @Test
    public void testUitsluitingNederlandsKiesrecht() throws Exception {
        // Setup
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel =
                new Lo3Stapel<Lo3KiesrechtInhoud>(Arrays.asList(buildUitsluitingNederlandsKiesrecht()));
        final MigratiePersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel =
                new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(buildInschrijving()));

        // Run
        kiesrechtConverteerder.converteer(kiesrechtStapel, inschrijvingStapel, builder);
        final MigratiePersoonslijst migratiePersoonslijst = builder.build();
        final MigratieStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel =
                migratiePersoonslijst.getUitsluitingNederlandsKiesrechtStapel();
        final MigratieStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel =
                migratiePersoonslijst.getEuropeseVerkiezingenStapel();

        // Check
        Assert.assertNotNull(uitsluitingNederlandsKiesrechtStapel);
        Assert.assertEquals(1, uitsluitingNederlandsKiesrechtStapel.size());
        final MigratieGroep<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrecht =
                uitsluitingNederlandsKiesrechtStapel.get(0);
        Assert.assertNotNull(uitsluitingNederlandsKiesrecht);
        Assert.assertEquals(Boolean.TRUE, uitsluitingNederlandsKiesrecht.getInhoud()
                .getIndicatieUitsluitingNederlandsKiesrecht());
        Assert.assertEquals(new BrpDatum(20140405), uitsluitingNederlandsKiesrecht.getInhoud()
                .getDatumEindeUitsluitingNederlandsKiesrecht());

        Assert.assertNull(europeseVerkiezingenStapel);
    }

    private Lo3Categorie<Lo3KiesrechtInhoud> buildUitsluitingEuropeesKiesrecht() {
        return new Lo3Categorie<Lo3KiesrechtInhoud>(new Lo3KiesrechtInhoud(
                Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement(), // aanduiding europees kiesrecht
                new Lo3Datum(20000000), // datum europees kiesrecht
                new Lo3Datum(20160203), // einddatum uitsluiting europees kiesrecht
                null, // aanduiding uitgesloten kiesrecht
                null), // einddatum uitsluiting kiesrecht
                null, Lo3Historie.NULL_HISTORIE, null);
    }

    @Test
    public void testUitsluitingEuropeesKiesrecht() throws Exception {
        // Setup
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel =
                new Lo3Stapel<Lo3KiesrechtInhoud>(Arrays.asList(buildUitsluitingEuropeesKiesrecht()));
        final MigratiePersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel =
                new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(buildInschrijving()));

        // Run
        kiesrechtConverteerder.converteer(kiesrechtStapel, inschrijvingStapel, builder);
        final MigratiePersoonslijst migratiePersoonslijst = builder.build();
        final MigratieStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel =
                migratiePersoonslijst.getUitsluitingNederlandsKiesrechtStapel();
        final MigratieStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel =
                migratiePersoonslijst.getEuropeseVerkiezingenStapel();

        // Check
        Assert.assertNull(uitsluitingNederlandsKiesrechtStapel);

        Assert.assertNotNull(europeseVerkiezingenStapel);
        Assert.assertEquals(1, europeseVerkiezingenStapel.size());
        final MigratieGroep<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingen = europeseVerkiezingenStapel.get(0);
        Assert.assertNotNull(europeseVerkiezingen);
        Assert.assertEquals(Boolean.FALSE, europeseVerkiezingen.getInhoud().getDeelnameEuropeseVerkiezingen());
        Assert.assertEquals(new BrpDatum(20000000), europeseVerkiezingen.getInhoud()
                .getDatumAanleidingAanpassingDeelnameEuropeseVerkiezingen());
        Assert.assertEquals(new BrpDatum(20160203), europeseVerkiezingen.getInhoud()
                .getDatumEindeUitsluitingEuropeesKiesrecht());
    }

    private Lo3Categorie<Lo3KiesrechtInhoud> buildUitsluitingBeide() {
        return new Lo3Categorie<Lo3KiesrechtInhoud>(new Lo3KiesrechtInhoud(
                Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement(), // aanduiding europees kiesrecht
                new Lo3Datum(20000000), // datum europees kiesrecht
                new Lo3Datum(20160203), // einddatum uitsluiting europees kiesrecht
                Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.asElement(), // aanduiding uitgesloten
                                                                                         // kiesrecht
                new Lo3Datum(20140405)), // einddatum uitsluiting kiesrecht
                null, Lo3Historie.NULL_HISTORIE, null);
    }

    @Test
    public void testUitsluitingBeide() throws Exception {
        // Setup
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel =
                new Lo3Stapel<Lo3KiesrechtInhoud>(Arrays.asList(buildUitsluitingBeide()));
        final MigratiePersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel =
                new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(buildInschrijving()));

        // Run
        kiesrechtConverteerder.converteer(kiesrechtStapel, inschrijvingStapel, builder);
        final MigratiePersoonslijst migratiePersoonslijst = builder.build();
        final MigratieStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingNederlandsKiesrechtStapel =
                migratiePersoonslijst.getUitsluitingNederlandsKiesrechtStapel();
        final MigratieStapel<BrpEuropeseVerkiezingenInhoud> europeseVerkiezingenStapel =
                migratiePersoonslijst.getEuropeseVerkiezingenStapel();

        // Check
        Assert.assertNotNull(uitsluitingNederlandsKiesrechtStapel);
        Assert.assertEquals(1, uitsluitingNederlandsKiesrechtStapel.size());
        // Inhoud check in andere test

        Assert.assertNotNull(europeseVerkiezingenStapel);
        Assert.assertEquals(1, europeseVerkiezingenStapel.size());
        // Inhoud check in andere test
    }

}
