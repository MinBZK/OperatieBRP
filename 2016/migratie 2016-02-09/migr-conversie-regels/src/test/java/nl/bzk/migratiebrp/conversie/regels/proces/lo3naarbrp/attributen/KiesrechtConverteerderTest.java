/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.Arrays;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor KiesrechtConverteerder.
 */
public class KiesrechtConverteerderTest extends AbstractComponentTest {

    @Inject
    private KiesrechtConverteerder kiesrechtConverteerder;

    private TussenPersoonslijstBuilder buildMigratiePersoonslijstBuilder() {
        final TussenStapel<BrpIdentificatienummersInhoud> identificatienummerStapel =
                new TussenStapel<>(Arrays.asList(new TussenGroep<>(new BrpIdentificatienummersInhoud(
                    new BrpLong(1234567890L, null),
                    new BrpInteger(123456789, null)), Lo3Historie.NULL_HISTORIE, null, null)));
        final TussenStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                new TussenStapel<>(Arrays.asList(new TussenGroep<>(
                    new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN),
                    Lo3Historie.NULL_HISTORIE,
                    null,
                    null)));

        final TussenPersoonslijstBuilder tussenPersoonslijstBuilder = new TussenPersoonslijstBuilder();
        tussenPersoonslijstBuilder.identificatienummerStapel(identificatienummerStapel);
        tussenPersoonslijstBuilder.geslachtsaanduidingStapel(geslachtsaanduidingStapel);
        tussenPersoonslijstBuilder.geboorteStapel(new TussenStapel<>(
            Arrays.asList(new TussenGroep<>(new BrpGeboorteInhoud(
                new BrpDatum(19770101, null),
                null,
                null,
                null,
                null,
                new BrpLandOfGebiedCode(Short.parseShort("0000")),
                null), Lo3Historie.NULL_HISTORIE, null, null))));
        // tussenPersoonslijstBuilder
        // .geslachtsnaamcomponentStapel(new TussenStapel<BrpGeslachtsnaamcomponentInhoud>(Arrays
        // .asList(new TussenGroep<BrpGeslachtsnaamcomponentInhoud>(
        // new BrpGeslachtsnaamcomponentInhoud(null, null, "Jansen", null, null, 1),
        // Lo3Historie.NULL_HISTORIE, null))));
        tussenPersoonslijstBuilder.naamgebruikStapel(new TussenStapel<>(Arrays.asList(new TussenGroep<>(new BrpNaamgebruikInhoud(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null), Lo3Historie.NULL_HISTORIE, null, null))));
        tussenPersoonslijstBuilder.inschrijvingStapel(new TussenStapel<>(Arrays.asList(new TussenGroep<>(new BrpInschrijvingInhoud(
            new BrpDatum(19560312, null),
            new BrpLong(0L, null),
            BrpDatumTijd.fromDatum(19560312, null)), Lo3Historie.NULL_HISTORIE, null, null))));
        tussenPersoonslijstBuilder.adresStapel(new TussenStapel<>(Arrays.asList(new TussenGroep<>(new BrpAdresInhoud(
            null,
            new BrpRedenWijzigingVerblijfCode('O'),
            new BrpAangeverCode('A'),
            new BrpDatum(20010101, null),
            null,
            null,
            new BrpGemeenteCode(Short.parseShort("0518")),

            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            new BrpLandOfGebiedCode(Short.parseShort("6030")),
            null), Lo3Historie.NULL_HISTORIE, null, null))));

        return tussenPersoonslijstBuilder;
    }

    @Test(expected = NullPointerException.class)
    public void testNullInputs1() {
        kiesrechtConverteerder.converteer(null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullInputs2() {
        final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel = new Lo3Stapel<>(Arrays.asList(buildUitsluitingKiesrecht()));

        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = new Lo3Stapel<>(Arrays.asList(buildInschrijving()));

        kiesrechtConverteerder.converteer(kiesrechtStapel, inschrijvingStapel, null);
    }

    @Test
    public void testEmpty() {
        // Setup
        final TussenPersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = new Lo3Stapel<>(Arrays.asList(buildInschrijving()));

        // Run
        kiesrechtConverteerder.converteer(null, inschrijvingStapel, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();
        final TussenStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel = tussenPersoonslijst.getUitsluitingKiesrechtStapel();
        final TussenStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingenStapel =
                tussenPersoonslijst.getDeelnameEuVerkiezingenStapel();

        // Check
        Assert.assertNull(uitsluitingKiesrechtStapel);
        Assert.assertNull(deelnameEuVerkiezingenStapel);
    }

    private Lo3Categorie<Lo3InschrijvingInhoud> buildInschrijving() {

        return new Lo3Categorie<>(new Lo3InschrijvingInhoud(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            new Lo3Integer(1),
            new Lo3Datumtijdstempel(20120405121500000L),
            Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement()), null, Lo3Historie.NULL_HISTORIE, null);
    }

    private Lo3Categorie<Lo3KiesrechtInhoud> buildUitsluitingKiesrecht() {
        return new Lo3Categorie<>(new Lo3KiesrechtInhoud(null, // aanduiding deelnameEuVerkiezingen kiesrecht
            null, // datum deelnameEuVerkiezingen kiesrecht
            null, // einddatum uitsluiting deelnameEuVerkiezingen kiesrecht
            Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.asElement(), // aanduiding
                                                                                     // uitgesloten
            // kiesrecht
            new Lo3Datum(20140405)), // einddatum uitsluiting kiesrecht
            null,
            Lo3Historie.NULL_HISTORIE,
            null);
    }

    @Test
    public void testUitsluitingKiesrecht() {
        // Setup
        final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel = new Lo3Stapel<>(Arrays.asList(buildUitsluitingKiesrecht()));
        final TussenPersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = new Lo3Stapel<>(Arrays.asList(buildInschrijving()));

        // Run
        kiesrechtConverteerder.converteer(kiesrechtStapel, inschrijvingStapel, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();
        final TussenStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel = tussenPersoonslijst.getUitsluitingKiesrechtStapel();
        final TussenStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingenStapel =
                tussenPersoonslijst.getDeelnameEuVerkiezingenStapel();

        // Check
        Assert.assertNotNull(uitsluitingKiesrechtStapel);
        Assert.assertEquals(1, uitsluitingKiesrechtStapel.size());
        final TussenGroep<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrecht = uitsluitingKiesrechtStapel.get(0);
        Assert.assertNotNull(uitsluitingKiesrecht);
        Assert.assertEquals(Boolean.TRUE, BrpBoolean.unwrap(uitsluitingKiesrecht.getInhoud().getIndicatieUitsluitingKiesrecht()));
        Assert.assertEquals(new BrpDatum(20140405, null), uitsluitingKiesrecht.getInhoud().getDatumVoorzienEindeUitsluitingKiesrecht());

        Assert.assertNull(deelnameEuVerkiezingenStapel);
    }

    private Lo3Categorie<Lo3KiesrechtInhoud> buildUitsluitingEuropeesKiesrecht() {
        return new Lo3Categorie<>(new Lo3KiesrechtInhoud(Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement(), // aanduiding
            // deelnameEuVerkiezingen
            // kiesrecht
            new Lo3Datum(20000000), // datum deelnameEuVerkiezingen
                                    // kiesrecht
            new Lo3Datum(20160203), // einddatum uitsluiting
                                    // deelnameEuVerkiezingen kiesrecht
            null, // aanduiding uitgesloten kiesrecht
            null), // einddatum uitsluiting kiesrecht
            null,
            Lo3Historie.NULL_HISTORIE,
            null);
    }

    @Test
    public void testUitsluitingEuropeesKiesrecht() {
        // Setup
        final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel = new Lo3Stapel<>(Arrays.asList(buildUitsluitingEuropeesKiesrecht()));
        final TussenPersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = new Lo3Stapel<>(Arrays.asList(buildInschrijving()));

        // Run
        kiesrechtConverteerder.converteer(kiesrechtStapel, inschrijvingStapel, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();
        final TussenStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel = tussenPersoonslijst.getUitsluitingKiesrechtStapel();
        final TussenStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingenStapel =
                tussenPersoonslijst.getDeelnameEuVerkiezingenStapel();

        // Check
        Assert.assertNull(uitsluitingKiesrechtStapel);
        Assert.assertNotNull(deelnameEuVerkiezingenStapel);
        Assert.assertEquals(1, deelnameEuVerkiezingenStapel.size());

        final TussenGroep<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingen = deelnameEuVerkiezingenStapel.get(0);
        Assert.assertNotNull(deelnameEuVerkiezingen);
        final BrpDeelnameEuVerkiezingenInhoud inhoud = deelnameEuVerkiezingen.getInhoud();
        Assert.assertEquals(Boolean.FALSE, BrpBoolean.unwrap(inhoud.getIndicatieDeelnameEuVerkiezingen()));
        Assert.assertEquals(new BrpDatum(20000000, null), inhoud.getDatumAanleidingAanpassingDeelnameEuVerkiezingen());
        Assert.assertEquals(new BrpDatum(20160203, null), inhoud.getDatumVoorzienEindeUitsluitingEuVerkiezingen());
    }

    private Lo3Categorie<Lo3KiesrechtInhoud> buildUitsluitingBeide() {
        return new Lo3Categorie<>(new Lo3KiesrechtInhoud(Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement(), // aanduiding
            // deelnameEuVerkiezingen
            // kiesrecht
            new Lo3Datum(20000000), // datum deelnameEuVerkiezingen
                                    // kiesrecht
            new Lo3Datum(20160203), // einddatum uitsluiting
                                    // deelnameEuVerkiezingen kiesrecht
            Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.asElement(), // aanduiding
                                                                                     // uitgesloten
            // kiesrecht
            new Lo3Datum(20140405)), // einddatum uitsluiting kiesrecht
            null,
            Lo3Historie.NULL_HISTORIE,
            null);
    }

    @Test
    public void testUitsluitingBeide() {
        // Setup
        final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel = new Lo3Stapel<>(Arrays.asList(buildUitsluitingBeide()));
        final TussenPersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = new Lo3Stapel<>(Arrays.asList(buildInschrijving()));

        // Run
        kiesrechtConverteerder.converteer(kiesrechtStapel, inschrijvingStapel, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();
        final TussenStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel = tussenPersoonslijst.getUitsluitingKiesrechtStapel();
        final TussenStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingenStapel =
                tussenPersoonslijst.getDeelnameEuVerkiezingenStapel();

        // Check
        Assert.assertNotNull(uitsluitingKiesrechtStapel);
        Assert.assertEquals(1, uitsluitingKiesrechtStapel.size());
        // Inhoud check in andere test

        Assert.assertNotNull(deelnameEuVerkiezingenStapel);
        Assert.assertEquals(1, deelnameEuVerkiezingenStapel.size());
        // Inhoud check in andere test
    }
}
