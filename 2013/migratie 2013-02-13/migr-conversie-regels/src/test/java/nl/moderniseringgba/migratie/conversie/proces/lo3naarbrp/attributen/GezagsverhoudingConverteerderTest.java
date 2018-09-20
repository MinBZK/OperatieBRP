/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor {@link GezagsverhoudingConverteerder}.
 * 
 */
public class GezagsverhoudingConverteerderTest extends AbstractComponentTest {

    @Inject
    private GezagsverhoudingConverteerder gezagsverhoudingConverteerder;

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

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3GezagsverhoudingInhoud> buildDerdeHeeftGezag() {
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status1 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(
                        Lo3IndicatieGezagMinderjarigeEnum.OUDERS_1_EN_2.asElement(), null), null, new Lo3Historie(
                        null, new Lo3Datum(20010101), new Lo3Datum(20010101)), null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status2 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(
                        Lo3IndicatieGezagMinderjarigeEnum.OUDER_1_EN_DERDE.asElement(), null), null, new Lo3Historie(
                        null, new Lo3Datum(20020101), new Lo3Datum(20020101)), null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status3 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(
                        Lo3IndicatieGezagMinderjarigeEnum.DERDE.asElement(), null), null, new Lo3Historie(null,
                        new Lo3Datum(20030101), new Lo3Datum(20030101)), null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status4 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(
                        Lo3IndicatieGezagMinderjarigeEnum.OUDER_2.asElement(), null), null, new Lo3Historie(null,
                        new Lo3Datum(20040101), new Lo3Datum(20040101)), null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status5 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(
                        Lo3IndicatieGezagMinderjarigeEnum.OUDER_2_EN_DERDE.asElement(), null), null, new Lo3Historie(
                        null, new Lo3Datum(20050101), new Lo3Datum(20050101)), null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status6 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(null, null), null,
                        new Lo3Historie(null, new Lo3Datum(20060101), new Lo3Datum(20060101)), null);

        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(Arrays.asList(status1, status2, status3, status4, status5,
                status6));
    }

    @Test
    public void testConversieDerdeHeeftGezag() throws Exception {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel = buildDerdeHeeftGezag();
        final MigratiePersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();

        // Run
        gezagsverhoudingConverteerder.converteer(gezagsverhoudingStapel, builder);
        final MigratiePersoonslijst migratiePersoonslijst = builder.build();
        final MigratieStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel =
                migratiePersoonslijst.getDerdeHeeftGezagIndicatieStapel();
        final MigratieStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel =
                migratiePersoonslijst.getOnderCurateleIndicatieStapel();

        // Test - migratie stapel (redundante regels *niet* verwijderd)
        Assert.assertNotNull(onderCurateleIndicatieStapel);
        Assert.assertEquals(6, onderCurateleIndicatieStapel.size());
        Assert.assertNull(onderCurateleIndicatieStapel.get(0).getInhoud().getOnderCuratele());
        Assert.assertNull(onderCurateleIndicatieStapel.get(1).getInhoud().getOnderCuratele());
        Assert.assertNull(onderCurateleIndicatieStapel.get(2).getInhoud().getOnderCuratele());
        Assert.assertNull(onderCurateleIndicatieStapel.get(3).getInhoud().getOnderCuratele());
        Assert.assertNull(onderCurateleIndicatieStapel.get(4).getInhoud().getOnderCuratele());
        Assert.assertNull(onderCurateleIndicatieStapel.get(5).getInhoud().getOnderCuratele());

        Assert.assertNotNull(derdeHeeftGezagIndicatieStapel);
        Assert.assertEquals(6, derdeHeeftGezagIndicatieStapel.size());
        final MigratieGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus1 =
                derdeHeeftGezagIndicatieStapel.get(0);
        Assert.assertNotNull(derdeheeftGezagStatus1);
        final MigratieGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus2 =
                derdeHeeftGezagIndicatieStapel.get(1);
        Assert.assertNotNull(derdeheeftGezagStatus2);
        final MigratieGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus3 =
                derdeHeeftGezagIndicatieStapel.get(2);
        Assert.assertNotNull(derdeheeftGezagStatus3);
        final MigratieGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus4 =
                derdeHeeftGezagIndicatieStapel.get(3);
        Assert.assertNotNull(derdeheeftGezagStatus4);
        final MigratieGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus5 =
                derdeHeeftGezagIndicatieStapel.get(4);
        Assert.assertNotNull(derdeheeftGezagStatus5);
        final MigratieGroep<BrpDerdeHeeftGezagIndicatieInhoud> derdeheeftGezagStatus6 =
                derdeHeeftGezagIndicatieStapel.get(5);
        Assert.assertNotNull(derdeheeftGezagStatus6);

        Assert.assertNull(derdeheeftGezagStatus1.getInhoud().getDerdeHeeftGezag());
        Assert.assertEquals(new Lo3Datum(20010101), derdeheeftGezagStatus1.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, derdeheeftGezagStatus2.getInhoud().getDerdeHeeftGezag());
        Assert.assertEquals(new Lo3Datum(20020101), derdeheeftGezagStatus2.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, derdeheeftGezagStatus3.getInhoud().getDerdeHeeftGezag());
        Assert.assertEquals(new Lo3Datum(20030101), derdeheeftGezagStatus3.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertNull(derdeheeftGezagStatus4.getInhoud().getDerdeHeeftGezag());
        Assert.assertEquals(new Lo3Datum(20040101), derdeheeftGezagStatus4.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, derdeheeftGezagStatus5.getInhoud().getDerdeHeeftGezag());
        Assert.assertEquals(new Lo3Datum(20050101), derdeheeftGezagStatus5.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertNull(derdeheeftGezagStatus6.getInhoud().getDerdeHeeftGezag());
        Assert.assertEquals(new Lo3Datum(20060101), derdeheeftGezagStatus6.getHistorie().getIngangsdatumGeldigheid());

    }

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3GezagsverhoudingInhoud> buildOnderCuratele() {
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status1 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(
                        Lo3IndicatieGezagMinderjarigeEnum.OUDER_1_EN_DERDE.asElement(), null), null, new Lo3Historie(
                        null, new Lo3Datum(20010101), new Lo3Datum(20010101)), null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status2 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(null,
                        Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement()), null, new Lo3Historie(null,
                        new Lo3Datum(20020101), new Lo3Datum(20020101)), null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status3 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(null, null), null,
                        new Lo3Historie(null, new Lo3Datum(20030101), new Lo3Datum(20030101)), null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status4 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(null,
                        Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement()), null, new Lo3Historie(null,
                        new Lo3Datum(20040101), new Lo3Datum(20040101)), null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status5 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(null,
                        Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement()), null, new Lo3Historie(null,
                        new Lo3Datum(20050101), new Lo3Datum(20050101)), null);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> status6 =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(null, null), null,
                        new Lo3Historie(null, new Lo3Datum(20060101), new Lo3Datum(20060101)), null);

        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(Arrays.asList(status1, status2, status3, status4, status5,
                status6));
    }

    @Test
    public void testConversieOnderCuratele() throws Exception {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel = buildOnderCuratele();
        final MigratiePersoonslijstBuilder builder = buildMigratiePersoonslijstBuilder();

        // Run
        gezagsverhoudingConverteerder.converteer(gezagsverhoudingStapel, builder);
        final MigratiePersoonslijst migratiePersoonslijst = builder.build();
        final MigratieStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel =
                migratiePersoonslijst.getDerdeHeeftGezagIndicatieStapel();
        final MigratieStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel =
                migratiePersoonslijst.getOnderCurateleIndicatieStapel();

        Assert.assertNotNull(derdeHeeftGezagIndicatieStapel);
        Assert.assertEquals(6, derdeHeeftGezagIndicatieStapel.size());

        Assert.assertNotNull(onderCurateleIndicatieStapel);
        Assert.assertEquals(6, onderCurateleIndicatieStapel.size());
        final MigratieGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus1 =
                onderCurateleIndicatieStapel.get(0);
        Assert.assertNotNull(onderCurateleStatus1);
        final MigratieGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus2 =
                onderCurateleIndicatieStapel.get(1);
        Assert.assertNotNull(onderCurateleStatus2);
        final MigratieGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus3 =
                onderCurateleIndicatieStapel.get(2);
        Assert.assertNotNull(onderCurateleStatus3);
        final MigratieGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus4 =
                onderCurateleIndicatieStapel.get(3);
        Assert.assertNotNull(onderCurateleStatus4);
        final MigratieGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus5 =
                onderCurateleIndicatieStapel.get(4);
        Assert.assertNotNull(onderCurateleStatus5);
        final MigratieGroep<BrpOnderCurateleIndicatieInhoud> onderCurateleStatus6 =
                onderCurateleIndicatieStapel.get(5);
        Assert.assertNotNull(onderCurateleStatus6);

        Assert.assertNull(onderCurateleStatus1.getInhoud().getOnderCuratele());
        Assert.assertEquals(new Lo3Datum(20010101), onderCurateleStatus1.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, onderCurateleStatus2.getInhoud().getOnderCuratele());
        Assert.assertEquals(new Lo3Datum(20020101), onderCurateleStatus2.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertNull(onderCurateleStatus3.getInhoud().getOnderCuratele());
        Assert.assertEquals(new Lo3Datum(20030101), onderCurateleStatus3.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, onderCurateleStatus4.getInhoud().getOnderCuratele());
        Assert.assertEquals(new Lo3Datum(20040101), onderCurateleStatus4.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertEquals(Boolean.TRUE, onderCurateleStatus5.getInhoud().getOnderCuratele());
        Assert.assertEquals(new Lo3Datum(20050101), onderCurateleStatus5.getHistorie().getIngangsdatumGeldigheid());

        Assert.assertNull(onderCurateleStatus6.getInhoud().getOnderCuratele());
        Assert.assertEquals(new Lo3Datum(20060101), onderCurateleStatus6.getHistorie().getIngangsdatumGeldigheid());

    }

}
