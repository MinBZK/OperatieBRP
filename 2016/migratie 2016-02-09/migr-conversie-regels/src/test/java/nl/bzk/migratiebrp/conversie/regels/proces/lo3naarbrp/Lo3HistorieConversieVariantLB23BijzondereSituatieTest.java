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
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import org.junit.Test;

/**
 * Test klasse voor Lo3HistorieConversieVariantLB23.
 */
public class Lo3HistorieConversieVariantLB23BijzondereSituatieTest extends AbstractLoggingTest {

    @Inject
    private Lo3HistorieConversieVariantLB24 conversie;

    @Test
    public <T extends BrpGroepInhoud> void testBijzondereSituatieLB018() {
        final TussenGroep<BrpOuderlijkGezagInhoud> tussenGroep1 =
                new TussenGroep<>(new BrpOuderlijkGezagInhoud(new BrpBoolean(true, null)), Lo3StapelHelper.lo3His(19830205), Lo3StapelHelper.lo3Doc(
                    1L,
                    null,
                    null,
                    null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_61, 0, 1));
        final TussenGroep<BrpOuderlijkGezagInhoud> tussenGroep2 =
                new TussenGroep<>(
                    new BrpOuderlijkGezagInhoud(null),
                    Lo3StapelHelper.lo3His(19840204),
                    Lo3StapelHelper.lo3Doc(2L, null, null, null),
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0));

        final List<TussenGroep<T>> lo3Groepen = new ArrayList<>();
        lo3Groepen.add((TussenGroep<T>) tussenGroep1);
        lo3Groepen.add((TussenGroep<T>) tussenGroep2);
        conversie.converteer(lo3Groepen, new HashMap<Long, BrpActie>());

        assertAantalInfos(0);
    }

    @Test
    public <T extends BrpGroepInhoud> void testBijzondereSituatieLB018LegeHistorie() {
        final TussenGroep<BrpOuderlijkGezagInhoud> tussenGroep1 =
                new TussenGroep<>(new BrpOuderlijkGezagInhoud(new BrpBoolean(true, null)), Lo3StapelHelper.lo3His(19830205), Lo3StapelHelper.lo3Doc(
                    1L,
                    null,
                    null,
                    null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0));
        final TussenGroep<BrpOuderlijkGezagInhoud> tussenGroep2 =
                new TussenGroep<>(
                    new BrpOuderlijkGezagInhoud(null),
                    Lo3StapelHelper.lo3His(19840204),
                    Lo3StapelHelper.lo3Doc(2L, null, null, null),
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_61, 0, 1));

        final List<TussenGroep<T>> lo3Groepen = new ArrayList<>();
        lo3Groepen.add((TussenGroep<T>) tussenGroep1);
        lo3Groepen.add((TussenGroep<T>) tussenGroep2);
        conversie.converteer(lo3Groepen, new HashMap<Long, BrpActie>());

        assertAantalInfos(0);
    }

    @Test
    public <T extends BrpGroepInhoud> void testBijzondereSituatieLB018NietGelogd() {
        final TussenGroep<BrpOuderlijkGezagInhoud> tussenGroep2 =
                new TussenGroep<>(
                    new BrpOuderlijkGezagInhoud(null),
                    Lo3StapelHelper.lo3His(19840204),
                    Lo3StapelHelper.lo3Doc(2L, null, null, null),
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0));

        final List<TussenGroep<T>> lo3Groepen = new ArrayList<>();
        lo3Groepen.add((TussenGroep<T>) tussenGroep2);
        conversie.converteer(lo3Groepen, new HashMap<Long, BrpActie>());

        assertAantalInfos(0);
    }

    @Test
    public <T extends BrpGroepInhoud> void testBijzondereSituatieLB019() {
        final TussenGroep<BrpGeslachtsaanduidingInhoud> tussenGroep1 =
                new TussenGroep<>(new BrpGeslachtsaanduidingInhoud(null), Lo3StapelHelper.lo3His(null, 20100101, 20100101), new Lo3Documentatie(
                    3L,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        final TussenGroep<BrpGeslachtsaanduidingInhoud> tussenGroep2 =
                new TussenGroep<>(new BrpGeslachtsaanduidingInhoud(null), Lo3StapelHelper.lo3His(null, 20100101, 20100101), new Lo3Documentatie(
                    4L,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        final TussenGroep<BrpGeslachtsaanduidingInhoud> tussenGroep3 =
                new TussenGroep<>(new BrpGeslachtsaanduidingInhoud(null), Lo3StapelHelper.lo3His("O", 20100102, 20100102), new Lo3Documentatie(
                    1L,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 2));
        final TussenGroep<BrpGeslachtsaanduidingInhoud> tussenGroep4 =
                new TussenGroep<>(
                    new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.ONBEKEND),
                    Lo3StapelHelper.lo3His("O", 20100101, 20100101),
                    new Lo3Documentatie(2L, null, null, null, null, null, null, null),
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 3));

        final List<TussenGroep<T>> lo3Groepen = new ArrayList<>();
        lo3Groepen.add((TussenGroep<T>) tussenGroep1);
        lo3Groepen.add((TussenGroep<T>) tussenGroep2);
        lo3Groepen.add((TussenGroep<T>) tussenGroep3);
        lo3Groepen.add((TussenGroep<T>) tussenGroep4);
        conversie.converteer(lo3Groepen, new HashMap<Long, BrpActie>());

        assertAantalInfos(0);
    }
}
