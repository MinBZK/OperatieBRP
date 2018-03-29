/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.Arrays;
import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor {@link GezagsverhoudingConverteerder}.
 */
public class GezagsverhoudingConverteerderTest extends AbstractComponentTest {

    private GezagsverhoudingConverteerder
            gezagsverhoudingConverteerder =
            new GezagsverhoudingConverteerder(new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl()));

    private TussenPersoonslijstBuilder buildMigratiePersoonslijstBuilder() {
        final TussenStapel<BrpIdentificatienummersInhoud> identificatienummerStapel =
                new TussenStapel<>(
                        Collections.singletonList(
                                new TussenGroep<>(
                                        new BrpIdentificatienummersInhoud(new BrpString("1234567890", null),
                                                new BrpString("123456789", null)),
                                        new Lo3Historie(null, null, null),
                                        null,
                                        null)));
        final TussenStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                new TussenStapel<>(
                        Collections.singletonList(
                                new TussenGroep<>(
                                        new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN),
                                        new Lo3Historie(null, null, null),
                                        null,
                                        null)));

        final TussenPersoonslijstBuilder tussenPersoonslijstBuilder = new TussenPersoonslijstBuilder();
        tussenPersoonslijstBuilder.identificatienummerStapel(identificatienummerStapel);
        tussenPersoonslijstBuilder.geslachtsaanduidingStapel(geslachtsaanduidingStapel);
        tussenPersoonslijstBuilder.geboorteStapel(
                new TussenStapel<>(
                        Collections.singletonList(
                                new TussenGroep<>(
                                        new BrpGeboorteInhoud(
                                                new BrpDatum(19770101, null),
                                                null,
                                                null,
                                                null,
                                                null,
                                                new BrpLandOfGebiedCode("0000"),
                                                null),
                                        new Lo3Historie(null, null, null),
                                        null,
                                        null))));
        tussenPersoonslijstBuilder.naamgebruikStapel(
                new TussenStapel<>(
                        Collections.singletonList(
                                new TussenGroep<>(
                                        new BrpNaamgebruikInhoud(null, null, null, null, null, null, null, null),
                                        new Lo3Historie(null, null, null),
                                        null,
                                        null))));
        tussenPersoonslijstBuilder.inschrijvingStapel(
                new TussenStapel<>(
                        Collections.singletonList(
                                new TussenGroep<>(
                                        new BrpInschrijvingInhoud(new BrpDatum(19560312, null), new BrpLong(0L, null), BrpDatumTijd.fromDatum(19560312, null)),
                                        new Lo3Historie(null, null, null),
                                        null,
                                        null))));
        tussenPersoonslijstBuilder.adresStapel(
                new TussenStapel<>(
                        Collections.singletonList(
                                new TussenGroep<>(
                                        new BrpAdresInhoud(
                                                null,
                                                new BrpRedenWijzigingVerblijfCode('O'),
                                                new BrpAangeverCode('A'),
                                                new BrpDatum(20010101, null),
                                                null,
                                                null,
                                                new BrpGemeenteCode("0518"),

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
                                                new BrpLandOfGebiedCode("6030"),
                                                null),
                                        new Lo3Historie(null, null, null),
                                        null,
                                        null))));

        return tussenPersoonslijstBuilder;
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> buildDerdeHeeftGezag() {
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status1 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(Lo3IndicatieGezagMinderjarigeEnum.OUDERS_1_EN_2.asElement(), null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20010101), new Lo3Datum(20010101)),
                        null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status2 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(Lo3IndicatieGezagMinderjarigeEnum.OUDER_1_EN_DERDE.asElement(), null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20020101), new Lo3Datum(20020101)),
                        null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status3 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(Lo3IndicatieGezagMinderjarigeEnum.DERDE.asElement(), null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20030101), new Lo3Datum(20030101)),
                        null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status4 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(Lo3IndicatieGezagMinderjarigeEnum.OUDER_2.asElement(), null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20040101), new Lo3Datum(20040101)),
                        null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status5 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(Lo3IndicatieGezagMinderjarigeEnum.OUDER_2_EN_DERDE.asElement(), null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20050101), new Lo3Datum(20050101)),
                        null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status6 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(null, null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20060101), new Lo3Datum(20060101)),
                        null);

        return new Lo3Stapel<>(Arrays.asList(status1, status2, status3, status4, status5, status6));
    }

    @Test
    public void testConversieDerdeHeeftGezag() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel = buildDerdeHeeftGezag();
        final TussenPersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();

        // Run
        gezagsverhoudingConverteerder.converteer(gezagsverhoudingStapel, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();
        final TussenStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel = tussenPersoonslijst.getDerdeHeeftGezagIndicatieStapel();
        final TussenStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel = tussenPersoonslijst.getOnderCurateleIndicatieStapel();

        // Test - migratie stapel (redundante regels *niet* verwijderd)
        Assert.assertNotNull(onderCurateleIndicatieStapel);
        Assert.assertEquals(6, onderCurateleIndicatieStapel.size());
        Assert.assertFalse(onderCurateleIndicatieStapel.get(0).getInhoud().heeftIndicatie());
        Assert.assertFalse(onderCurateleIndicatieStapel.get(1).getInhoud().heeftIndicatie());
        Assert.assertFalse(onderCurateleIndicatieStapel.get(2).getInhoud().heeftIndicatie());
        Assert.assertFalse(onderCurateleIndicatieStapel.get(3).getInhoud().heeftIndicatie());
        Assert.assertFalse(onderCurateleIndicatieStapel.get(4).getInhoud().heeftIndicatie());
        Assert.assertFalse(onderCurateleIndicatieStapel.get(5).getInhoud().heeftIndicatie());

        Assert.assertNotNull(derdeHeeftGezagIndicatieStapel);
        Assert.assertEquals(6, derdeHeeftGezagIndicatieStapel.size());
        final TussenGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus1 = derdeHeeftGezagIndicatieStapel.get(0);
        Assert.assertNotNull(derdeheeftGezagStatus1);
        final TussenGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus2 = derdeHeeftGezagIndicatieStapel.get(1);
        Assert.assertNotNull(derdeheeftGezagStatus2);
        final TussenGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus3 = derdeHeeftGezagIndicatieStapel.get(2);
        Assert.assertNotNull(derdeheeftGezagStatus3);
        final TussenGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus4 = derdeHeeftGezagIndicatieStapel.get(3);
        Assert.assertNotNull(derdeheeftGezagStatus4);
        final TussenGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus5 = derdeHeeftGezagIndicatieStapel.get(4);
        Assert.assertNotNull(derdeheeftGezagStatus5);
        final TussenGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus6 = derdeHeeftGezagIndicatieStapel.get(5);
        Assert.assertNotNull(derdeheeftGezagStatus6);

        Assert.assertFalse(derdeheeftGezagStatus1.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20010101), derdeheeftGezagStatus1.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, derdeheeftGezagStatus2.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20020101), derdeheeftGezagStatus2.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, derdeheeftGezagStatus3.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20030101), derdeheeftGezagStatus3.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertFalse(derdeheeftGezagStatus4.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20040101), derdeheeftGezagStatus4.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, derdeheeftGezagStatus5.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20050101), derdeheeftGezagStatus5.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertFalse(derdeheeftGezagStatus6.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20060101), derdeheeftGezagStatus6.getHistorie().getIngangsdatumGeldigheid());

    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> buildOnderCuratele() {
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status1 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(Lo3IndicatieGezagMinderjarigeEnum.OUDER_1_EN_DERDE.asElement(), null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20010101), new Lo3Datum(20010101)),
                        null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status2 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(null, Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement()),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20020101), new Lo3Datum(20020101)),
                        null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status3 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(null, null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20030101), new Lo3Datum(20030101)),
                        null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status4 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(null, Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement()),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20040101), new Lo3Datum(20040101)),
                        null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status5 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(null, Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement()),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20050101), new Lo3Datum(20050101)),
                        null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status6 =
                new Lo3Categorie<>(
                        new Lo3GezagsverhoudingInhoud(null, null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20060101), new Lo3Datum(20060101)),
                        null);

        return new Lo3Stapel<>(Arrays.asList(status1, status2, status3, status4, status5, status6));
    }

    @Test
    public void testConversieOnderCuratele() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel = buildOnderCuratele();
        final TussenPersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();

        // Run
        gezagsverhoudingConverteerder.converteer(gezagsverhoudingStapel, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();
        final TussenStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel = tussenPersoonslijst.getDerdeHeeftGezagIndicatieStapel();
        final TussenStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel = tussenPersoonslijst.getOnderCurateleIndicatieStapel();

        Assert.assertNotNull(derdeHeeftGezagIndicatieStapel);
        Assert.assertEquals(6, derdeHeeftGezagIndicatieStapel.size());

        Assert.assertNotNull(onderCurateleIndicatieStapel);
        Assert.assertEquals(6, onderCurateleIndicatieStapel.size());
        final TussenGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus1 = onderCurateleIndicatieStapel.get(0);
        Assert.assertNotNull(onderCurateleStatus1);
        final TussenGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus2 = onderCurateleIndicatieStapel.get(1);
        Assert.assertNotNull(onderCurateleStatus2);
        final TussenGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus3 = onderCurateleIndicatieStapel.get(2);
        Assert.assertNotNull(onderCurateleStatus3);
        final TussenGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus4 = onderCurateleIndicatieStapel.get(3);
        Assert.assertNotNull(onderCurateleStatus4);
        final TussenGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus5 = onderCurateleIndicatieStapel.get(4);
        Assert.assertNotNull(onderCurateleStatus5);
        final TussenGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus6 = onderCurateleIndicatieStapel.get(5);
        Assert.assertNotNull(onderCurateleStatus6);

        Assert.assertFalse(onderCurateleStatus1.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20010101), onderCurateleStatus1.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, onderCurateleStatus2.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20020101), onderCurateleStatus2.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertFalse(onderCurateleStatus3.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20030101), onderCurateleStatus3.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, onderCurateleStatus4.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20040101), onderCurateleStatus4.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, onderCurateleStatus5.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20050101), onderCurateleStatus5.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertFalse(onderCurateleStatus6.getInhoud().heeftIndicatie());
        Assert.assertEquals(new Lo3Datum(20060101), onderCurateleStatus6.getHistorie().getIngangsdatumGeldigheid());

    }

}
