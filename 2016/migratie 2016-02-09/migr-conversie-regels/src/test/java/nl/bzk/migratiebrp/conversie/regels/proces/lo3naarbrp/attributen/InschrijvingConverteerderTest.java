/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
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
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import org.junit.Assert;
import org.junit.Test;

public class InschrijvingConverteerderTest extends AbstractComponentTest {
    private static final Lo3Datumtijdstempel LO3_DATUMTIJDSTEMPEL = new Lo3Datumtijdstempel(19840303221500000L);
    private static final Lo3Datum LO3_DATUMTIJDSTEMPEL_AS_DATUM = BrpDatumTijd.fromLo3Datumtijdstempel(LO3_DATUMTIJDSTEMPEL).converteerNaarLo3Datum();

    @Inject
    private InschrijvingConverteerder inschrijvingConverteerder;

    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijving(
        final Lo3RedenOpschortingBijhoudingCodeEnum redenOpschorting,
        final Lo3Datum datumOpschorting)
    {
        return buildInschrijving(redenOpschorting, datumOpschorting, null, null, new Lo3RNIDeelnemerCode("0101"));
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijving(
        final Lo3RedenOpschortingBijhoudingCodeEnum redenOpschorting,
        final Lo3Datum datumOpschorting,
        final Lo3Datum datumVerificatie,
        final Lo3String omschrijvingVerificatie,
        final Lo3RNIDeelnemerCode rniDeelnemerCode)
    {
        final Lo3Categorie<Lo3InschrijvingInhoud> inschrijving =
                new Lo3Categorie<>(new Lo3InschrijvingInhoud(
                    new Lo3Datum(19700101),
                    datumOpschorting,
                    redenOpschorting != null ? redenOpschorting.asElement() : null,
                    new Lo3Datum(19600303),
                    new Lo3GemeenteCode("0518"),
                    Lo3IndicatieGeheimCodeEnum.NIET_AAN_KERKEN.asElement(),
                    datumVerificatie,
                    omschrijvingVerificatie,
                    new Lo3Integer(333),
                    LO3_DATUMTIJDSTEMPEL,
                    Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement()), new Lo3Documentatie(
                    3L,
                    null,
                    null,
                    null,
                    null,
                    null,
                    rniDeelnemerCode,
                    null), Lo3Historie.NULL_HISTORIE, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0));

        return new Lo3Stapel<>(Arrays.asList(inschrijving));
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> buildVerblijfplaats() {
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats1 =
                new Lo3Categorie<>(new Lo3VerblijfplaatsInhoud(
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
                    null), null, new Lo3Historie(null, new Lo3Datum(20100101), new Lo3Datum(20100101)), new Lo3Herkomst(
                    Lo3CategorieEnum.CATEGORIE_08,
                    0,
                    0));
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats2 =
                new Lo3Categorie<>(new Lo3VerblijfplaatsInhoud(
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
                    null), null, new Lo3Historie(null, new Lo3Datum(20050101), new Lo3Datum(20050101)), new Lo3Herkomst(
                    Lo3CategorieEnum.CATEGORIE_08,
                    0,
                    1));
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats3 =
                new Lo3Categorie<>(new Lo3VerblijfplaatsInhoud(
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
                    null), null, new Lo3Historie(null, new Lo3Datum(20000101), new Lo3Datum(20000101)), new Lo3Herkomst(
                    Lo3CategorieEnum.CATEGORIE_08,
                    0,
                    2));

        return new Lo3Stapel<>(Arrays.asList(verblijfplaats1, verblijfplaats2, verblijfplaats3));
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> buildVerblijfplaatsBuitenland() {
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats1 =
                new Lo3Categorie<>(new Lo3VerblijfplaatsInhoud(
                    new Lo3GemeenteCode("0518"),
                    new Lo3Datum(20100101),
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
                    new Lo3LandCode("4322"),
                    new Lo3Datum(20090113),
                    Lo3String.wrap("regel 1"),
                    Lo3String.wrap("regel 2"),
                    Lo3String.wrap("regel 3"),
                    null,
                    null,
                    Lo3AangifteAdreshoudingEnum.ONBEKEND.asElement(),
                    null), null, new Lo3Historie(null, new Lo3Datum(20100101), new Lo3Datum(20100101)), new Lo3Herkomst(
                    Lo3CategorieEnum.CATEGORIE_08,
                    0,
                    0));
        return new Lo3Stapel<>(Arrays.asList(verblijfplaats1));
    }

    @Test
    @Definitie(Definities.DEF040)
    @Requirement({Requirements.CCA07_LB05, Requirements.CCA08_LB05 })
    public void testConverteerBijhoudingNietOpgeschort() {
        // Setup
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel = buildInschrijving(null, null);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel = buildVerblijfplaats();

        // Run
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        inschrijvingConverteerder.converteer(lo3VerblijfplaatsStapel, lo3InschrijvingStapel, false, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();

        // Check Bijhouding
        final TussenStapel<BrpBijhoudingInhoud> migratieBijhoudingStapel = tussenPersoonslijst.getBijhoudingStapel();
        Assert.assertEquals(3, migratieBijhoudingStapel.size());
        // Cat08
        checkInhoud(
            migratieBijhoudingStapel,
            0,
            BrpBijhoudingsaardCode.INGEZETENE,
            BrpNadereBijhoudingsaardCode.ACTUEEL,
            new BrpPartijCode(51801),
            new Lo3Datum(20100101),
            new Lo3Datum(20100101));
        // Cat58-1
        checkInhoud(
            migratieBijhoudingStapel,
            1,
            BrpBijhoudingsaardCode.ONBEKEND,
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            new BrpPartijCode(59901),
            new Lo3Datum(20050101),
            new Lo3Datum(20050101));
        // Cat58-2
        checkInhoud(
            migratieBijhoudingStapel,
            2,
            BrpBijhoudingsaardCode.ONBEKEND,
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            new BrpPartijCode(59901),
            new Lo3Datum(20000101),
            new Lo3Datum(20000101));
    }

    @Test
    @Definitie(Definities.DEF078)
    @Requirement({Requirements.CCA07_LB05, Requirements.CCA08_LB05 })
    public void testConverteerBijhoudingOpgeschortEmigratie() {
        // Setup
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                buildInschrijving(Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE, new Lo3Datum(20100102));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel = buildVerblijfplaats();

        // Run
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        inschrijvingConverteerder.converteer(lo3VerblijfplaatsStapel, lo3InschrijvingStapel, false, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();

        // Check Bijhouding
        final TussenStapel<BrpBijhoudingInhoud> migratieBijhoudingStapel = tussenPersoonslijst.getBijhoudingStapel();
        Assert.assertEquals(4, migratieBijhoudingStapel.size());
        checkInhoud(
            migratieBijhoudingStapel,
            0,
            BrpBijhoudingsaardCode.ONBEKEND,
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            new BrpPartijCode(51801),
            new Lo3Datum(20100101),
            new Lo3Datum(20100101));
        checkInhoud(
            migratieBijhoudingStapel,
            1,
            BrpBijhoudingsaardCode.ONBEKEND,
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            new BrpPartijCode(59901),
            new Lo3Datum(20050101),
            new Lo3Datum(20050101));
        checkInhoud(
            migratieBijhoudingStapel,
            2,
            BrpBijhoudingsaardCode.ONBEKEND,
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            new BrpPartijCode(59901),
            new Lo3Datum(20000101),
            new Lo3Datum(20000101));
        checkInhoud(
            migratieBijhoudingStapel,
            3,
            BrpBijhoudingsaardCode.NIET_INGEZETENE,
            BrpNadereBijhoudingsaardCode.EMIGRATIE,
            new BrpPartijCode(51801),
            new Lo3Datum(20100102),
            LO3_DATUMTIJDSTEMPEL_AS_DATUM);
    }

    @Test
    @Definitie(Definities.DEF078)
    @Requirement({Requirements.CCA07_LB05, Requirements.CCA08_LB05 })
    public void testConverteerBijhoudingOpgeschortBuitenland() {
        // Setup
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                buildInschrijving(Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN, new Lo3Datum(20100102));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel = buildVerblijfplaatsBuitenland();

        // Run
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        inschrijvingConverteerder.converteer(lo3VerblijfplaatsStapel, lo3InschrijvingStapel, false, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();

        // Check Bijhouding
        final TussenStapel<BrpBijhoudingInhoud> migratieBijhoudingStapel = tussenPersoonslijst.getBijhoudingStapel();
        Assert.assertEquals(2, migratieBijhoudingStapel.size());
        checkInhoud(
            migratieBijhoudingStapel,
            0,
            BrpBijhoudingsaardCode.ONBEKEND,
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            new BrpPartijCode(51801),
            new Lo3Datum(20100101),
            new Lo3Datum(20100101));
        checkInhoud(
            migratieBijhoudingStapel,
            1,
            BrpBijhoudingsaardCode.NIET_INGEZETENE,
            BrpNadereBijhoudingsaardCode.OVERLEDEN,
            new BrpPartijCode(51801),
            new Lo3Datum(20100102),
            LO3_DATUMTIJDSTEMPEL_AS_DATUM);
    }

    @Test
    @Definitie(Definities.DEF079)
    @Requirement({Requirements.CCA07_LB05, Requirements.CCA08_LB05 })
    public void testConverteerBijhoudingOpgeschort() {
        // Setup
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                buildInschrijving(Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN, new Lo3Datum(20100102));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel = buildVerblijfplaats();

        // Run
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        inschrijvingConverteerder.converteer(lo3VerblijfplaatsStapel, lo3InschrijvingStapel, false, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();

        // Check Bijhouding
        final TussenStapel<BrpBijhoudingInhoud> migratieBijhoudingStapel = tussenPersoonslijst.getBijhoudingStapel();
        Assert.assertEquals(4, migratieBijhoudingStapel.size());
        checkInhoud(
            migratieBijhoudingStapel,
            0,
            BrpBijhoudingsaardCode.ONBEKEND,
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            new BrpPartijCode(51801),
            new Lo3Datum(20100101),
            new Lo3Datum(20100101));
        checkInhoud(
            migratieBijhoudingStapel,
            1,
            BrpBijhoudingsaardCode.ONBEKEND,
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            new BrpPartijCode(59901),
            new Lo3Datum(20050101),
            new Lo3Datum(20050101));
        checkInhoud(
            migratieBijhoudingStapel,
            2,
            BrpBijhoudingsaardCode.ONBEKEND,
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            new BrpPartijCode(59901),
            new Lo3Datum(20000101),
            new Lo3Datum(20000101));
        checkInhoud(
            migratieBijhoudingStapel,
            3,
            BrpBijhoudingsaardCode.INGEZETENE,
            BrpNadereBijhoudingsaardCode.OVERLEDEN,
            new BrpPartijCode(51801),
            new Lo3Datum(20100102),
            LO3_DATUMTIJDSTEMPEL_AS_DATUM);
    }

    @Test
    @Requirement({Requirements.CCA07_LB05, Requirements.CCA08_LB05 })
    public void testConverteerBijhoudingVerhuizingNaOpschorting() {
        // Setup
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                buildInschrijving(Lo3RedenOpschortingBijhoudingCodeEnum.FOUT, new Lo3Datum(20091231));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel = buildVerblijfplaats();

        // Run
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        inschrijvingConverteerder.converteer(lo3VerblijfplaatsStapel, lo3InschrijvingStapel, false, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();

        // Check Bijhouding
        final TussenStapel<BrpBijhoudingInhoud> migratieBijhoudingStapel = tussenPersoonslijst.getBijhoudingStapel();
        Assert.assertEquals(4, migratieBijhoudingStapel.size());
        checkInhoud(
            migratieBijhoudingStapel,
            0,
            BrpBijhoudingsaardCode.INGEZETENE,
            BrpNadereBijhoudingsaardCode.FOUT,
            new BrpPartijCode(51801),
            new Lo3Datum(20100101),
            new Lo3Datum(20100101));
        checkInhoud(
            migratieBijhoudingStapel,
            1,
            BrpBijhoudingsaardCode.ONBEKEND,
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            new BrpPartijCode(59901),
            new Lo3Datum(20050101),
            new Lo3Datum(20050101));
        checkInhoud(
            migratieBijhoudingStapel,
            2,
            BrpBijhoudingsaardCode.ONBEKEND,
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            new BrpPartijCode(59901),
            new Lo3Datum(20000101),
            new Lo3Datum(20000101));
        checkInhoud(
            migratieBijhoudingStapel,
            3,
            BrpBijhoudingsaardCode.INGEZETENE,
            BrpNadereBijhoudingsaardCode.FOUT,
            new BrpPartijCode(59901),
            new Lo3Datum(20091231),
            LO3_DATUMTIJDSTEMPEL_AS_DATUM);
    }

    @Test
    public void testConverteerVerificatie() {
        // Setup
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                buildInschrijving(null, null, new Lo3Datum(20110101), new Lo3String("testVerificatie"), new Lo3RNIDeelnemerCode("0101"));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel = buildVerblijfplaats();

        // Run
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        inschrijvingConverteerder.converteer(lo3VerblijfplaatsStapel, lo3InschrijvingStapel, false, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();

        // Check Verificatie
        final List<TussenStapel<BrpVerificatieInhoud>> verificatieStapels = tussenPersoonslijst.getVerificatieStapels();
        Assert.assertEquals(1, verificatieStapels.size());
        final TussenStapel<BrpVerificatieInhoud> verificatieStapel = verificatieStapels.get(0);
        Assert.assertEquals(1, verificatieStapel.size());
        final TussenGroep<BrpVerificatieInhoud> verificatieGroep = verificatieStapel.get(0);
        Assert.assertEquals(new BrpDatum(20110101, null), verificatieGroep.getInhoud().getDatum());
        Assert.assertEquals(new BrpString("testVerificatie", null), verificatieGroep.getInhoud().getSoort());
        Assert.assertEquals(new BrpPartijCode(250001), verificatieGroep.getInhoud().getPartij());
    }

    @Test
    public void testConverteerGeenVerificatie() {
        // Setup
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel = buildInschrijving(null, null, null, null, null);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel = buildVerblijfplaats();

        // Run
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        inschrijvingConverteerder.converteer(lo3VerblijfplaatsStapel, lo3InschrijvingStapel, false, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();

        // Check Verificatie
        final List<TussenStapel<BrpVerificatieInhoud>> verificatieStapels = tussenPersoonslijst.getVerificatieStapels();
        Assert.assertEquals(0, verificatieStapels.size());
    }

    @Test
    public void testDummyPL() {
        // Setup
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                buildInschrijving(Lo3RedenOpschortingBijhoudingCodeEnum.FOUT, LO3_DATUMTIJDSTEMPEL_AS_DATUM, null, null, null);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel = buildVerblijfplaats();

        // Run
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        inschrijvingConverteerder.converteer(lo3VerblijfplaatsStapel, lo3InschrijvingStapel, true, builder);
        final TussenPersoonslijst tussenPersoonslijst = builder.build();

        // Check Verificatie
        final List<TussenStapel<BrpVerificatieInhoud>> verificatieStapels = tussenPersoonslijst.getVerificatieStapels();
        Assert.assertNotNull(tussenPersoonslijst.getPersoonAfgeleidAdministratiefStapel());
        Assert.assertEquals(0, verificatieStapels.size());
        Assert.assertNull(tussenPersoonslijst.getPersoonskaartStapel());
        Assert.assertNull(tussenPersoonslijst.getVerstrekkingsbeperkingIndicatieStapel());
    }

    private void checkInhoud(
        final TussenStapel<BrpBijhoudingInhoud> migratieBijhoudingStapel,
        final int index,
        final BrpBijhoudingsaardCode bijhaard,
        final BrpNadereBijhoudingsaardCode nadereBijhaard,
        final BrpPartijCode partij,
        final Lo3Datum ingangsdatum,
        final Lo3Datum datumOpneming)
    {
        final BrpBijhoudingInhoud inhoud = migratieBijhoudingStapel.get(index).getInhoud();
        Assert.assertEquals(bijhaard, inhoud.getBijhoudingsaardCode());
        Assert.assertEquals(nadereBijhaard, inhoud.getNadereBijhoudingsaardCode());
        Assert.assertEquals(partij, inhoud.getBijhoudingspartijCode());
        Assert.assertEquals(ingangsdatum, migratieBijhoudingStapel.get(index).getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(datumOpneming, migratieBijhoudingStapel.get(index).getHistorie().getDatumVanOpneming());
    }
}
