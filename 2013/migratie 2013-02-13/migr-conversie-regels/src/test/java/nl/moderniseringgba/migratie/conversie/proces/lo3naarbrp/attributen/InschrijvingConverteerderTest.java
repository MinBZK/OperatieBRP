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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerstrekkingsbeperkingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;

import org.junit.Assert;
import org.junit.Test;

public class InschrijvingConverteerderTest extends AbstractComponentTest {

    @Inject
    private PersoonConverteerder persoonConverteerder;
    @Inject
    private VerblijfplaatsConverteerder verblijfplaatsConverteerder;
    @Inject
    private InschrijvingConverteerder inschrijvingConverteerder;

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonStapel() {
        final Lo3Categorie<Lo3PersoonInhoud> persoon =
                new Lo3Categorie<Lo3PersoonInhoud>(new Lo3PersoonInhoud(1234567890L, 123456789L, "Pieter Jan Jaap",
                        null, null, "Janssen", new Lo3Datum(19770101), new Lo3GemeenteCode("0518"), new Lo3LandCode(
                                "6030"), Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                        Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement(), 1112223334L, 2223334445L),
                        null, Lo3Historie.NULL_HISTORIE, null);

        return new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(persoon));
    }

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijving() {
        final Lo3Categorie<Lo3InschrijvingInhoud> inschrijving =
                new Lo3Categorie<Lo3InschrijvingInhoud>(new Lo3InschrijvingInhoud(new Lo3Datum(19700101),
                        new Lo3Datum(19710202),
                        Lo3RedenOpschortingBijhoudingCodeEnum.MINISTERIEEL_BESLUIT.asElement(),
                        new Lo3Datum(19600303), new Lo3GemeenteCode("0518"),
                        Lo3IndicatieGeheimCodeEnum.NIET_AAN_KERKEN.asElement(), 333, new Lo3Datumtijdstempel(
                                19840303221500000L),
                        Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement()), null,
                        Lo3Historie.NULL_HISTORIE, null);

        return new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(inschrijving));
    }

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3VerblijfplaatsInhoud> buildVerblijfplaats() {
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats =
                new Lo3Categorie<Lo3VerblijfplaatsInhoud>(new Lo3VerblijfplaatsInhoud(new Lo3GemeenteCode("0518"),
                        new Lo3Datum(20000101), Lo3FunctieAdresEnum.WOONADRES.asElement(), null, new Lo3Datum(
                                20000101), ".", null, null, null, null, null, null, null, null, null, null, null,
                        null, null, null, null, null, null, Lo3AangifteAdreshoudingEnum.ONBEKEND.asElement(), null),
                        null, Lo3Historie.NULL_HISTORIE, null);

        return new Lo3Stapel<Lo3VerblijfplaatsInhoud>(Arrays.asList(verblijfplaats));
    }

    @Test
    public void testConverteerInschrijving() throws InputValidationException {
        // Setup
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel = buildPersoonStapel();
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel = buildInschrijving();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel = buildVerblijfplaats();

        final MigratiePersoonslijstBuilder builder = new MigratiePersoonslijstBuilder();
        persoonConverteerder.converteer(lo3PersoonStapel, builder);
        verblijfplaatsConverteerder.converteer(lo3VerblijfplaatsStapel, builder);

        // Run
        inschrijvingConverteerder.converteer(lo3PersoonStapel, lo3VerblijfplaatsStapel, lo3InschrijvingStapel,
                builder);
        final MigratiePersoonslijst migratiePersoonslijst = builder.build();

        // Check inschrijving
        final MigratieStapel<BrpInschrijvingInhoud> migratieInschrijvingStapel =
                migratiePersoonslijst.getInschrijvingStapel();
        Assert.assertEquals(1, migratieInschrijvingStapel.size());
        final MigratieGroep<BrpInschrijvingInhoud> migratieInschrijving = migratieInschrijvingStapel.get(0);
        Assert.assertEquals(new Long(1112223334L), migratieInschrijving.getInhoud().getVorigAdministratienummer());
        Assert.assertEquals(new Long(2223334445L), migratieInschrijving.getInhoud().getVolgendAdministratienummer());
        Assert.assertEquals(new BrpDatum(19600303), migratieInschrijving.getInhoud().getDatumInschrijving());
        Assert.assertEquals(new Integer(333), migratieInschrijving.getInhoud().getVersienummer());

        // Check opschorting
        final MigratieStapel<BrpOpschortingInhoud> migratieOpschortingStapel =
                migratiePersoonslijst.getOpschortingStapel();
        Assert.assertEquals(1, migratieOpschortingStapel.size());
        final MigratieGroep<BrpOpschortingInhoud> migratieOpschorting = migratieOpschortingStapel.get(0);
        Assert.assertEquals(new BrpDatum(19710202), migratieOpschorting.getInhoud().getDatumOpschorting());
        Assert.assertEquals(BrpRedenOpschortingBijhoudingCode.MINISTERIEEL_BESLUIT, migratieOpschorting.getInhoud()
                .getRedenOpschortingBijhoudingCode());

        // Check persoonskaart
        final MigratieStapel<BrpPersoonskaartInhoud> migratiePersoonskaartStapel =
                migratiePersoonslijst.getPersoonskaartStapel();
        Assert.assertEquals(1, migratiePersoonskaartStapel.size());
        final MigratieGroep<BrpPersoonskaartInhoud> migratiePersoonskaart = migratiePersoonskaartStapel.get(0);
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0518")), migratiePersoonskaart.getInhoud()
                .getGemeentePKCode());
        Assert.assertEquals(Boolean.TRUE, migratiePersoonskaart.getInhoud().getIndicatiePKVolledigGeconverteerd());

        // Check verstrekkingsbeperking
        final MigratieStapel<BrpVerstrekkingsbeperkingInhoud> migratieVerstrekkingsbeperkingStapel =
                migratiePersoonslijst.getVerstrekkingsbeperkingStapel();
        Assert.assertEquals(1, migratieVerstrekkingsbeperkingStapel.size());
        final MigratieGroep<BrpVerstrekkingsbeperkingInhoud> migratieVerstrekkingsbeperking =
                migratieVerstrekkingsbeperkingStapel.get(0);
        Assert.assertEquals(Boolean.TRUE, migratieVerstrekkingsbeperking.getInhoud().getHeeftIndicatie());
    }
}
