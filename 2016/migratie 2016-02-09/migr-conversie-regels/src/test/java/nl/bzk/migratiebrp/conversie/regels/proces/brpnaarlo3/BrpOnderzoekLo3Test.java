/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3;

import java.util.Arrays;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Assert;
import org.junit.Test;

public class BrpOnderzoekLo3Test {

    private static final String DATUM = "20121212";
    private static final String GEG_IN_ONDERZ_2 = "60820";
    private static final String GEG_IN_ONDERZ = "60800";
    private static final String GEM_CODE = "1234";
    private final BrpOnderzoekLo3 brpOnderzoekLo3 = new BrpOnderzoekLo3();

    @Test
    public void testGeenOnderzoek() {
        final Lo3Persoonslijst in =
                new Lo3PersoonslijstBuilder().overlijdenStapel(
                    new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3OverlijdenInhoud(
                        new Lo3Datum(DATUM, null),
                        new Lo3GemeenteCode(GEM_CODE, null),
                        new Lo3LandCode(GEM_CODE, null)), maakDocumentatie(), Lo3Historie.NULL_HISTORIE, null)))).build();
        final Lo3Persoonslijst out = brpOnderzoekLo3.converteer(in);

        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden = out.getOverlijdenStapel().get(0);

        Assert.assertNull(overlijden.getOnderzoek());
    }

    @Test
    public void test1OnderzoekOp1Element() {
        final Lo3Onderzoek onderzoek1 = new Lo3Onderzoek(new Lo3Integer(GEG_IN_ONDERZ_2, null), new Lo3Datum(20131212), null);
        final Lo3Persoonslijst in =
                new Lo3PersoonslijstBuilder().overlijdenStapel(
                    new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3OverlijdenInhoud(new Lo3Datum(DATUM, null), new Lo3GemeenteCode(
                        GEM_CODE,
                        onderzoek1), new Lo3LandCode(GEM_CODE, null)), maakDocumentatie(), Lo3Historie.NULL_HISTORIE, null)))).build();
        final Lo3Persoonslijst out = brpOnderzoekLo3.converteer(in);

        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden = out.getOverlijdenStapel().get(0);

        Assert.assertNotNull(overlijden.getOnderzoek());
        Assert.assertSame(onderzoek1, overlijden.getOnderzoek());
    }

    @Test
    public void test1OnderzoekOp3Elementen() {
        final Lo3Onderzoek onderzoek1 = new Lo3Onderzoek(new Lo3Integer(GEG_IN_ONDERZ, null), new Lo3Datum(20131212), null);
        final Lo3Persoonslijst in =
                new Lo3PersoonslijstBuilder().overlijdenStapel(
                    new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3OverlijdenInhoud(new Lo3Datum(DATUM, onderzoek1), new Lo3GemeenteCode(
                        GEM_CODE,
                        onderzoek1), new Lo3LandCode(GEM_CODE, onderzoek1)), maakDocumentatie(), Lo3Historie.NULL_HISTORIE, null)))).build();
        final Lo3Persoonslijst out = brpOnderzoekLo3.converteer(in);

        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden = out.getOverlijdenStapel().get(0);

        Assert.assertNotNull(overlijden.getOnderzoek());
        Assert.assertSame(onderzoek1, overlijden.getOnderzoek());
    }

    @Test
    public void test2OnderzoekenOp2Elementen() {
        final Lo3Onderzoek onderzoek1 = new Lo3Onderzoek(new Lo3Integer(GEG_IN_ONDERZ_2, null), new Lo3Datum(20131212), null);
        final Lo3Onderzoek onderzoek2 = new Lo3Onderzoek(new Lo3Integer("60830", null), new Lo3Datum(20131111), null);
        final Lo3Persoonslijst in =
                new Lo3PersoonslijstBuilder().overlijdenStapel(
                    new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3OverlijdenInhoud(new Lo3Datum(DATUM, null), new Lo3GemeenteCode(
                        GEM_CODE,
                        onderzoek1), new Lo3LandCode(GEM_CODE, onderzoek2)), maakDocumentatie(), Lo3Historie.NULL_HISTORIE, null)))).build();
        final Lo3Persoonslijst out = brpOnderzoekLo3.converteer(in);

        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden = out.getOverlijdenStapel().get(0);

        Assert.assertNotNull(overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek1, overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek2, overlijden.getOnderzoek());
        Assert.assertEquals(new Lo3Onderzoek(new Lo3Integer(GEG_IN_ONDERZ, null), new Lo3Datum(20131212), null), overlijden.getOnderzoek());
    }

    @Test
    public void test1OnderzoekOp1ElementEnCategorieHeeftAlOnderzoek() {
        final Lo3Onderzoek onderzoek1 = new Lo3Onderzoek(new Lo3Integer(GEG_IN_ONDERZ_2, null), new Lo3Datum(20131212), null);
        final Lo3Onderzoek onderzoekCategorie = new Lo3Onderzoek(new Lo3Integer("68610", null), new Lo3Datum(20131111), null);
        final Lo3Persoonslijst in =
                new Lo3PersoonslijstBuilder().overlijdenStapel(
                    new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3OverlijdenInhoud(new Lo3Datum(DATUM, null), new Lo3GemeenteCode(
                        GEM_CODE,
                        onderzoek1), new Lo3LandCode(GEM_CODE, null)), maakDocumentatie(), onderzoekCategorie, Lo3Historie.NULL_HISTORIE, null)))).build();
        final Lo3Persoonslijst out = brpOnderzoekLo3.converteer(in);

        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden = out.getOverlijdenStapel().get(0);

        Assert.assertNotNull(overlijden.getOnderzoek());
        Assert.assertSame(onderzoekCategorie, overlijden.getOnderzoek());
    }

    @Test
    public void test1BrpOnderzoekOp1Element() {
        final Lo3Onderzoek onderzoek1 = new Lo3Onderzoek(new Lo3Integer("0", null), new Lo3Datum(20131212), null);
        final Lo3Persoonslijst in =
                new Lo3PersoonslijstBuilder().overlijdenStapel(
                    new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3OverlijdenInhoud(new Lo3Datum(DATUM, null), new Lo3GemeenteCode(
                        GEM_CODE,
                        onderzoek1), new Lo3LandCode(GEM_CODE, null)), maakDocumentatie(), Lo3Historie.NULL_HISTORIE, null)))).build();
        final Lo3Persoonslijst out = brpOnderzoekLo3.converteer(in);

        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden = out.getOverlijdenStapel().get(0);

        Assert.assertNotNull(overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek1, overlijden.getOnderzoek());
        Assert.assertEquals(new Lo3Onderzoek(new Lo3Integer(GEG_IN_ONDERZ_2, null), new Lo3Datum(20131212), null), overlijden.getOnderzoek());
    }

    @Test
    public void test1BrpOnderzoekOp3Elementen() {
        final Lo3Onderzoek onderzoek1 = new Lo3Onderzoek(new Lo3Integer("0", null), new Lo3Datum(20131212), null);
        final Lo3Persoonslijst in =
                new Lo3PersoonslijstBuilder().overlijdenStapel(
                    new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3OverlijdenInhoud(new Lo3Datum(DATUM, onderzoek1), new Lo3GemeenteCode(
                        GEM_CODE,
                        onderzoek1), new Lo3LandCode(GEM_CODE, onderzoek1)), maakDocumentatie(), Lo3Historie.NULL_HISTORIE, null)))).build();
        final Lo3Persoonslijst out = brpOnderzoekLo3.converteer(in);

        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden = out.getOverlijdenStapel().get(0);

        Assert.assertNotNull(overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek1, overlijden.getOnderzoek());
        Assert.assertEquals(new Lo3Onderzoek(new Lo3Integer(GEG_IN_ONDERZ, null), new Lo3Datum(20131212), null), overlijden.getOnderzoek());
    }

    @Test
    public void test2BrpOnderzoekenOp2Elementen() {
        final Lo3Onderzoek onderzoek1 = new Lo3Onderzoek(new Lo3Integer("0", null), new Lo3Datum(20131212), null);
        final Lo3Onderzoek onderzoek2 = new Lo3Onderzoek(new Lo3Integer("0", null), new Lo3Datum(20131111), null);
        final Lo3Persoonslijst in =
                new Lo3PersoonslijstBuilder().overlijdenStapel(
                    new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3OverlijdenInhoud(new Lo3Datum(DATUM, null), new Lo3GemeenteCode(
                        GEM_CODE,
                        onderzoek1), new Lo3LandCode(GEM_CODE, onderzoek2)), maakDocumentatie(), Lo3Historie.NULL_HISTORIE, null)))).build();
        final Lo3Persoonslijst out = brpOnderzoekLo3.converteer(in);

        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden = out.getOverlijdenStapel().get(0);

        Assert.assertNotNull(overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek1, overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek2, overlijden.getOnderzoek());
        Assert.assertEquals(new Lo3Onderzoek(new Lo3Integer(GEG_IN_ONDERZ, null), new Lo3Datum(20131212), null), overlijden.getOnderzoek());
    }

    @Test
    public void test2BrpOnderzoekenOp2ElementenWaarvan1Afgesloten() {
        final Lo3Onderzoek onderzoek1 = new Lo3Onderzoek(new Lo3Integer("0", null), new Lo3Datum(20131212), null);
        final Lo3Onderzoek onderzoek2 = new Lo3Onderzoek(new Lo3Integer("0", null), new Lo3Datum(20131111), new Lo3Datum(20131210));
        final Lo3Persoonslijst in =
                new Lo3PersoonslijstBuilder().overlijdenStapel(
                    new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3OverlijdenInhoud(new Lo3Datum(DATUM, null), new Lo3GemeenteCode(
                        GEM_CODE,
                        onderzoek1), new Lo3LandCode(GEM_CODE, onderzoek2)), maakDocumentatie(), Lo3Historie.NULL_HISTORIE, null)))).build();
        final Lo3Persoonslijst out = brpOnderzoekLo3.converteer(in);

        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden = out.getOverlijdenStapel().get(0);

        Assert.assertNotNull(overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek1, overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek2, overlijden.getOnderzoek());
        Assert.assertEquals(new Lo3Onderzoek(new Lo3Integer(GEG_IN_ONDERZ_2, null), new Lo3Datum(20131212), null), overlijden.getOnderzoek());
    }

    @Test
    public void test2BrpOnderzoekenOp2ElementenBeideAfgesloten() {
        final Lo3Onderzoek onderzoek1 = new Lo3Onderzoek(new Lo3Integer("0", null), new Lo3Datum(20131212), new Lo3Datum(20131219));
        final Lo3Onderzoek onderzoek2 = new Lo3Onderzoek(new Lo3Integer("0", null), new Lo3Datum(20131111), new Lo3Datum(20131222));
        final Lo3Persoonslijst in =
                new Lo3PersoonslijstBuilder().overlijdenStapel(
                    new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3OverlijdenInhoud(new Lo3Datum(DATUM, null), new Lo3GemeenteCode(
                        GEM_CODE,
                        onderzoek1), new Lo3LandCode(GEM_CODE, onderzoek2)), maakDocumentatie(), Lo3Historie.NULL_HISTORIE, null)))).build();
        final Lo3Persoonslijst out = brpOnderzoekLo3.converteer(in);

        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden = out.getOverlijdenStapel().get(0);

        Assert.assertNotNull(overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek1, overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek2, overlijden.getOnderzoek());
        Assert.assertEquals(
            new Lo3Onderzoek(new Lo3Integer(GEG_IN_ONDERZ, null), new Lo3Datum(20131111), new Lo3Datum(20131222)),
            overlijden.getOnderzoek());
    }

    @Test
    public void test2BrpOnderzoekenOp2ElementenInVerschillendeGroepen() {
        final Lo3Onderzoek onderzoek1 = new Lo3Onderzoek(new Lo3Integer("0", null), new Lo3Datum(20131212), null);
        final Lo3Onderzoek onderzoek2 = new Lo3Onderzoek(new Lo3Integer("0", null), new Lo3Datum(20131111), null);
        final Lo3Persoonslijst in =
                new Lo3PersoonslijstBuilder().overlijdenStapel(
                    new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3OverlijdenInhoud(new Lo3Datum(DATUM, null), new Lo3GemeenteCode(
                        GEM_CODE,
                        onderzoek1), new Lo3LandCode(GEM_CODE, null)), maakDocumentatie(), new Lo3Historie(null, new Lo3Datum(DATUM, null), new Lo3Datum(
                        "20121214",
                        onderzoek2)), null)))).build();
        final Lo3Persoonslijst out = brpOnderzoekLo3.converteer(in);

        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden = out.getOverlijdenStapel().get(0);

        Assert.assertNotNull(overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek1, overlijden.getOnderzoek());
        Assert.assertNotSame(onderzoek2, overlijden.getOnderzoek());
        Assert.assertEquals(new Lo3Onderzoek(new Lo3Integer("60000", null), new Lo3Datum(20131212), null), overlijden.getOnderzoek());
    }

    private Lo3Documentatie maakDocumentatie() {
        return new Lo3Documentatie(0, null, null, null, null, null, null, null);
    }
}
