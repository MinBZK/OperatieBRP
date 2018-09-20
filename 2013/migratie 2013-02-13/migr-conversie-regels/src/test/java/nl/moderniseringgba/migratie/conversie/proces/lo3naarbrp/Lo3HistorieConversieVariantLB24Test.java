/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.proces.AbstractLoggingTest;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Test;

/**
 * Test class voor Lo3HistorieConversieVariantLB24.
 */
public class Lo3HistorieConversieVariantLB24Test extends AbstractLoggingTest {

    private final Lo3HistorieConversieVariantLB24 conversie = new Lo3HistorieConversieVariantLB24();

    @SuppressWarnings("unchecked")
    @Test
    public <T extends BrpGroepInhoud> void testBijzondereSituatieLB020() {
        final MigratieGroep<BrpOverlijdenInhoud> migratieGroep1 =
                new MigratieGroep<BrpOverlijdenInhoud>(new BrpOverlijdenInhoud(new BrpDatum(19830205),
                        new BrpGemeenteCode(new BigDecimal(1904)), new BrpPlaatsCode("TestGemeente"), null, null,
                        new BrpLandCode(6030), "omschrijving"), Lo3StapelHelper.lo3His(19830205),
                        Lo3StapelHelper.lo3Doc(1L, null, null, null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_56,
                                0, 1));
        final MigratieGroep<BrpOverlijdenInhoud> migratieGroep2 =
                new MigratieGroep<BrpOverlijdenInhoud>(new BrpOverlijdenInhoud(null, null, null, null, null, null,
                        null), Lo3StapelHelper.lo3His(20120101), Lo3StapelHelper.lo3Doc(2L, null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0));

        final List<MigratieGroep<T>> lo3Groepen = new ArrayList<MigratieGroep<T>>();
        lo3Groepen.add((MigratieGroep<T>) migratieGroep1);
        lo3Groepen.add((MigratieGroep<T>) migratieGroep2);
        conversie.converteer(lo3Groepen);

        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB020);
        assertBijzondereSituatieOmschrijving("BrpOverlijdenInhoud");

    }

    @SuppressWarnings("unchecked")
    @Test
    public <T extends BrpGroepInhoud> void testGeenBijzondereSituatieLB020() {
        final MigratieGroep<BrpOverlijdenInhoud> migratieGroep1 =
                new MigratieGroep<BrpOverlijdenInhoud>(new BrpOverlijdenInhoud(new BrpDatum(19830205),
                        new BrpGemeenteCode(new BigDecimal(1904)), new BrpPlaatsCode("TestGemeente"), null, null,
                        new BrpLandCode(6030), "omschrijving"), Lo3StapelHelper.lo3His("O", 19830205, 19830205),
                        Lo3StapelHelper.lo3Doc(1L, null, null, null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_56,
                                0, 1));
        final MigratieGroep<BrpOverlijdenInhoud> migratieGroep2 =
                new MigratieGroep<BrpOverlijdenInhoud>(new BrpOverlijdenInhoud(null, null, null, null, null, null,
                        null), Lo3StapelHelper.lo3His(20120101), Lo3StapelHelper.lo3Doc(2L, null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0));

        final List<MigratieGroep<T>> lo3Groepen = new ArrayList<MigratieGroep<T>>();
        lo3Groepen.add((MigratieGroep<T>) migratieGroep1);
        lo3Groepen.add((MigratieGroep<T>) migratieGroep2);
        conversie.converteer(lo3Groepen);

        assertAantalInfos(0);
    }
}
