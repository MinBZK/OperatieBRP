/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;

import org.junit.Test;

public class Lo3HistorieConversieLoggingTest extends AbstractLoggingTest {

    public static final Lo3Herkomst LO_3_HERKOMST_0 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
    public static final Lo3Herkomst LO_3_HERKOMST_1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0);
    public static final Lo3Herkomst LO_3_HERKOMST_2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_56, 0, 1);
    public static final Lo3Herkomst LO_3_HERKOMST_3 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
    public static final Lo3Herkomst LO_3_HERKOMST_4 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 1, 0);

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB027)
    public void testLogNietGeconverteerdeVoorkomensEnkelvoudigeStapel1() {
        final TussenPersoonslijst tussenPersoonslijst = maakMigratiePersoonslijst1();

        Lo3HistorieConversieLogging.logNietGeconverteerdeVoorkomens(tussenPersoonslijst, new HashMap<Long, BrpActie>());

        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB027, 2);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB027)
    public void testLogNietGeconverteerdeVoorkomensEnkelvoudigeStapel2() {
        final TussenPersoonslijst tussenPersoonslijst = maakMigratiePersoonslijst1();

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        actieCache.put(1L, new BrpActie.Builder().id(1L).partijCode(BrpPartijCode.MIGRATIEVOORZIENING).lo3Herkomst(LO_3_HERKOMST_0).build());
        Lo3HistorieConversieLogging.logNietGeconverteerdeVoorkomens(tussenPersoonslijst, actieCache);

        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB027, 2);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB027)
    public void testLogNietGeconverteerdeVoorkomensEnkelvoudigeStapel3() {
        final TussenPersoonslijst tussenPersoonslijst = maakMigratiePersoonslijst1();

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        actieCache.put(2L, new BrpActie.Builder().id(2L).partijCode(BrpPartijCode.MIGRATIEVOORZIENING).lo3Herkomst(LO_3_HERKOMST_1).build());
        Lo3HistorieConversieLogging.logNietGeconverteerdeVoorkomens(tussenPersoonslijst, actieCache);

        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB027, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB027)
    public void testLogNietGeconverteerdeVoorkomensEnkelvoudigeStapel4() {
        final TussenPersoonslijst tussenPersoonslijst = maakMigratiePersoonslijst1();

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        actieCache.put(3L, new BrpActie.Builder().id(3L).partijCode(BrpPartijCode.MIGRATIEVOORZIENING).lo3Herkomst(LO_3_HERKOMST_1).build());
        actieCache.put(4L, new BrpActie.Builder().id(4L).partijCode(BrpPartijCode.MIGRATIEVOORZIENING).lo3Herkomst(LO_3_HERKOMST_2).build());
        Lo3HistorieConversieLogging.logNietGeconverteerdeVoorkomens(tussenPersoonslijst, actieCache);

        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB027, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB027)
    public void testLogNietGeconverteerdeVoorkomensMeervoudigeStapel1() {
        final TussenPersoonslijst tussenPersoonslijst = maakMigratiePersoonslijst2();

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        actieCache.put(5L, new BrpActie.Builder().id(5L).partijCode(BrpPartijCode.MIGRATIEVOORZIENING).lo3Herkomst(LO_3_HERKOMST_0).build());
        Lo3HistorieConversieLogging.logNietGeconverteerdeVoorkomens(tussenPersoonslijst, actieCache);

        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB027, 2);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB027)
    public void testLogNietGeconverteerdeVoorkomensMeervoudigeStapel2() {
        final TussenPersoonslijst tussenPersoonslijst = maakMigratiePersoonslijst2();

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        actieCache.put(6L, new BrpActie.Builder().id(6L).partijCode(BrpPartijCode.MIGRATIEVOORZIENING).lo3Herkomst(LO_3_HERKOMST_3).build());
        actieCache.put(7L, new BrpActie.Builder().id(7L).partijCode(BrpPartijCode.MIGRATIEVOORZIENING).lo3Herkomst(LO_3_HERKOMST_4).build());
        Lo3HistorieConversieLogging.logNietGeconverteerdeVoorkomens(tussenPersoonslijst, actieCache);

        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB027, 0);
    }

    private TussenPersoonslijst maakMigratiePersoonslijst1() {
        final TussenGroep<BrpOverlijdenInhoud> tussenGroep1 =
                new TussenGroep<>(
                    new BrpOverlijdenInhoud(
                        new BrpDatum(19830205, null),
                        new BrpGemeenteCode((short) 1904),
                        new BrpString("woonplaats", null),
                        null,
                        null,
                        new BrpLandOfGebiedCode((short) 6030),
                        new BrpString("omschrijving", null)),
                    Lo3StapelHelper.lo3His(19830205),
                    Lo3StapelHelper.lo3Doc(1L, null, null, null),
                    LO_3_HERKOMST_2);
        final TussenGroep<BrpOverlijdenInhoud> tussenGroep2 =
                new TussenGroep<>(
                    new BrpOverlijdenInhoud(null, null, null, null, null, null, null),
                    Lo3StapelHelper.lo3His(20120101),
                    Lo3StapelHelper.lo3Doc(2L, null, null, null),
                    LO_3_HERKOMST_1);

        final List<TussenGroep<BrpOverlijdenInhoud>> lo3Groepen = new ArrayList<>();
        lo3Groepen.add(tussenGroep1);
        lo3Groepen.add(tussenGroep2);
        final TussenStapel<BrpOverlijdenInhoud> overlijdenStapel = new TussenStapel<>(lo3Groepen);
        return new TussenPersoonslijstBuilder().overlijdenStapel(overlijdenStapel).build();
    }

    private TussenPersoonslijst maakMigratiePersoonslijst2() {
        final TussenGroep<BrpNationaliteitInhoud> tussenGroep1 =
                new TussenGroep<>(
                    new BrpNationaliteitInhoud(BrpNationaliteitCode.NEDERLANDS, null, null, null, null, null, null),
                    Lo3StapelHelper.lo3His(19830205),
                    Lo3StapelHelper.lo3Doc(1L, null, null, null),
                    LO_3_HERKOMST_3);
        final TussenGroep<BrpNationaliteitInhoud> tussenGroep2 =
                new TussenGroep<>(
                    new BrpNationaliteitInhoud(new BrpNationaliteitCode((short) 55), null, null, null, null, null, null),
                    Lo3StapelHelper.lo3His(20120101),
                    Lo3StapelHelper.lo3Doc(2L, null, null, null),
                    LO_3_HERKOMST_4);

        final List<TussenGroep<BrpNationaliteitInhoud>> lo3Groepen = new ArrayList<>();
        lo3Groepen.add(tussenGroep1);
        final TussenStapel<BrpNationaliteitInhoud> nationaliteitStapel1 = new TussenStapel<>(lo3Groepen);

        lo3Groepen.clear();
        lo3Groepen.add(tussenGroep2);
        final TussenStapel<BrpNationaliteitInhoud> nationaliteitStapel2 = new TussenStapel<>(lo3Groepen);
        return new TussenPersoonslijstBuilder().nationaliteitStapel(nationaliteitStapel1)
                                               .nationaliteitStapel(nationaliteitStapel2)
                                               .reisdocumentStapels(null)
                                               .build();
    }
}
