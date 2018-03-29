/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus;

import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verhuizing na overlijden.
 */
public class Casus49InschrijvingTest extends AbstractCasusTest {

    private static final Lo3Datumtijdstempel LO3_DATUMTIJDSTEMPEL = new Lo3Datumtijdstempel(19840303221500000L);

    @Override
    protected Lo3Stapel<Lo3InschrijvingInhoud> maakInschrijvingStapel() {
        return buildInschrijving(Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN, new Lo3Datum(20091231));
    }

    @Override
    protected Lo3Stapel<Lo3VerblijfplaatsInhoud> maakVerblijfplaatsStapel() {
        return buildVerblijfplaats();
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> buildVerblijfplaats() {
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats1 =
                new Lo3Categorie<>(
                        new Lo3VerblijfplaatsInhoud(
                                new Lo3GemeenteCode("0518"),
                                new Lo3Datum(20100101),
                                Lo3FunctieAdresEnum.WOONADRES.asElement(),
                                null,
                                new Lo3Datum(20000101),
                                Lo3String.wrap("."),
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
                                null,
                                Lo3AangifteAdreshoudingEnum.ONBEKEND.asElement(),
                                null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20100101), new Lo3Datum(20100101)),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0));
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats2 =
                new Lo3Categorie<>(
                        new Lo3VerblijfplaatsInhoud(
                                new Lo3GemeenteCode("0599"),
                                new Lo3Datum(20050101),
                                Lo3FunctieAdresEnum.WOONADRES.asElement(),
                                null,
                                new Lo3Datum(20000101),
                                Lo3String.wrap("."),
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
                                null,
                                Lo3AangifteAdreshoudingEnum.ONBEKEND.asElement(),
                                null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20050101), new Lo3Datum(20050101)),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 1));
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats3 =
                new Lo3Categorie<>(
                        new Lo3VerblijfplaatsInhoud(
                                new Lo3GemeenteCode("0599"),
                                new Lo3Datum(20000101),
                                Lo3FunctieAdresEnum.WOONADRES.asElement(),
                                null,
                                new Lo3Datum(20000101),
                                Lo3String.wrap("."),
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
                                null,
                                Lo3AangifteAdreshoudingEnum.ONBEKEND.asElement(),
                                null),
                        null,
                        new Lo3Historie(null, new Lo3Datum(20000101), new Lo3Datum(20000101)),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 2));

        return new Lo3Stapel<>(Arrays.asList(verblijfplaats1, verblijfplaats2, verblijfplaats3));
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijving(
            final Lo3RedenOpschortingBijhoudingCodeEnum redenOpschorting,
            final Lo3Datum datumOpschorting) {
        final Lo3Categorie<Lo3InschrijvingInhoud> inschrijving =
                new Lo3Categorie<>(
                        new Lo3InschrijvingInhoud(
                                null,
                                datumOpschorting,
                                redenOpschorting != null ? redenOpschorting.asElement() : null,
                                new Lo3Datum(19600303),
                                new Lo3GemeenteCode("0518"),
                                Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement(),
                                null,
                                null,
                                new Lo3Integer("0333", null),
                                LO3_DATUMTIJDSTEMPEL,
                                Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement()),
                        null,
                        new Lo3Historie(null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0));

        return new Lo3Stapel<>(Collections.singletonList(inschrijving));
    }

    @Override
    @Test
    public void testLo3NaarBrp() {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);

        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel = brpPersoonslijst.getBijhoudingStapel();
        Assert.assertEquals(4, bijhoudingStapel.size());

        // sorteren op datumAanvangGeldigheid
        final List<BrpGroep<BrpBijhoudingInhoud>> groepen = bijhoudingStapel.getGroepen();
        groepen.sort(new BrpBijhoudingComparator());
        final BrpStapel<BrpBijhoudingInhoud> sortedBijhoudingStapel = new BrpStapel<>(groepen);

        checkInhoud(
                sortedBijhoudingStapel,
                0,
                BrpBijhoudingsaardCode.INGEZETENE,
                BrpNadereBijhoudingsaardCode.OVERLEDEN,
                new BrpPartijCode("051801"),
                new BrpDatum(20100101, null),
                null);
        checkInhoud(
                sortedBijhoudingStapel,
                1,
                BrpBijhoudingsaardCode.INGEZETENE,
                BrpNadereBijhoudingsaardCode.OVERLEDEN,
                new BrpPartijCode("059901"),
                new BrpDatum(20091231, null),
                new BrpDatum(20100101, null));
        checkInhoud(
                sortedBijhoudingStapel,
                2,
                BrpBijhoudingsaardCode.INGEZETENE,
                BrpNadereBijhoudingsaardCode.ONBEKEND,
                new BrpPartijCode("059901"),
                new BrpDatum(20050101, null),
                new BrpDatum(20091231, null));
        checkInhoud(
                sortedBijhoudingStapel,
                3,
                BrpBijhoudingsaardCode.INGEZETENE,
                BrpNadereBijhoudingsaardCode.ONBEKEND,
                new BrpPartijCode("059901"),
                new BrpDatum(20000101, null),
                new BrpDatum(20050101, null));
    }

    @Override
    @Test
    public void testRondverteer() {
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
        final Lo3Stapel<Lo3InschrijvingInhoud> rondverteerdeStapel = terug.getInschrijvingStapel();
        final Lo3Stapel<Lo3InschrijvingInhoud> origineleStapel = getLo3Persoonslijst().getInschrijvingStapel();
        Assert.assertEquals(1, rondverteerdeStapel.size());
        Assert.assertEquals(origineleStapel.size(), rondverteerdeStapel.size());

        Assert.assertTrue(Lo3StapelHelper.vergelijkStapels(origineleStapel, rondverteerdeStapel));
    }

    private void checkInhoud(
            final BrpStapel<BrpBijhoudingInhoud> brpBijhoudingStapel,
            final int index,
            final BrpBijhoudingsaardCode bijhaard,
            final BrpNadereBijhoudingsaardCode nadereBijhaard,
            final BrpPartijCode partij,
            final BrpDatum datumAanvang,
            final BrpDatum datumEinde) {
        final BrpBijhoudingInhoud inhoud = brpBijhoudingStapel.get(index).getInhoud();
        Assert.assertEquals(bijhaard, inhoud.getBijhoudingsaardCode());
        Assert.assertEquals(nadereBijhaard, inhoud.getNadereBijhoudingsaardCode());
        Assert.assertEquals(partij, inhoud.getBijhoudingspartijCode());
        Assert.assertEquals(datumAanvang, brpBijhoudingStapel.get(index).getHistorie().getDatumAanvangGeldigheid());
        Assert.assertEquals(datumEinde, brpBijhoudingStapel.get(index).getHistorie().getDatumEindeGeldigheid());
    }

    /**
     * Comparator welke de BrpBijhoudingInhoud vergelijkt op basis van de datumAanvangGeldigheid.
     */
    private static class BrpBijhoudingComparator implements Comparator<BrpGroep<BrpBijhoudingInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpGroep<BrpBijhoudingInhoud> o1, final BrpGroep<BrpBijhoudingInhoud> o2) {
            final BrpDatum o1DatumAanvang = o1.getHistorie().getDatumAanvangGeldigheid();
            final BrpDatum o2DatumAanvang = o2.getHistorie().getDatumAanvangGeldigheid();
            return o2DatumAanvang.compareTo(o1DatumAanvang);
        }
    }
}
