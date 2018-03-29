/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import org.junit.Test;

/**
 * Test class voor Lo3HistorieConversieVariantLB24.
 */
public class Lo3HistorieConversieVariantLB24BijzondereSituatieTest extends AbstractLoggingTest {

    @Inject
    private Lo3HistorieConversieVariantLB24 conversie;

    @Test
    public <T extends BrpGroepInhoud> void testBijzondereSituatieLB020() {
        final TussenGroep<BrpOverlijdenInhoud> tussenGroep1 =
                new TussenGroep<>(
                        new BrpOverlijdenInhoud(
                                new BrpDatum(19830205, null),
                                new BrpGemeenteCode("1904"),
                                new BrpString("woonplaats", null),
                                null,
                                null,
                                new BrpLandOfGebiedCode("6030"),
                                new BrpString("omschrijving", null)),
                        Lo3StapelHelper.lo3His(19830205),
                        Lo3StapelHelper.lo3Doc(1L, null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_56, 0, 1));
        final TussenGroep<BrpOverlijdenInhoud> tussenGroep2 =
                new TussenGroep<>(
                        new BrpOverlijdenInhoud(null, null, null, null, null, null, null),
                        Lo3StapelHelper.lo3His(20120101),
                        Lo3StapelHelper.lo3Doc(2L, null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0));

        final List<TussenGroep<T>> lo3Groepen = new ArrayList<>();
        lo3Groepen.add((TussenGroep<T>) tussenGroep1);
        lo3Groepen.add((TussenGroep<T>) tussenGroep2);
        conversie.converteer(lo3Groepen, new HashMap<>());

        assertAantalInfos(0);
    }

    @Test
    public <T extends BrpGroepInhoud> void testGeenBijzondereSituatieLB020() {
        final TussenGroep<BrpOverlijdenInhoud> tussenGroep1 =
                new TussenGroep<>(new BrpOverlijdenInhoud(new BrpDatum(19830205, null), new BrpGemeenteCode("1904"), new BrpString(
                        "woonplaats",
                        null), null, null, new BrpLandOfGebiedCode("6030"), new BrpString("omschrijving", null)), Lo3StapelHelper.lo3His(
                        "O",
                        19830205,
                        19830205), Lo3StapelHelper.lo3Doc(1L, null, null, null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_56, 0, 1));
        final TussenGroep<BrpOverlijdenInhoud> tussenGroep2 =
                new TussenGroep<>(
                        new BrpOverlijdenInhoud(null, null, null, null, null, null, null),
                        Lo3StapelHelper.lo3His(20120101),
                        Lo3StapelHelper.lo3Doc(2L, null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0));

        final List<TussenGroep<T>> lo3Groepen = new ArrayList<>();
        lo3Groepen.add((TussenGroep<T>) tussenGroep1);
        lo3Groepen.add((TussenGroep<T>) tussenGroep2);
        conversie.converteer(lo3Groepen, new HashMap<>());

        assertAantalInfos(0);
    }
}
