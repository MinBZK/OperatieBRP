/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import org.junit.Assert;
import org.junit.Test;

public class VariantPre050Pre056GroepenComparatorTest {

    private final VariantPre050Pre056GroepenComparator comparator = new VariantPre050Pre056GroepenComparator();
    private final BrpSamengesteldeNaamInhoud inhoud = new BrpSamengesteldeNaamInhoud(
            null,
            null,
            null,
            null,
            null,
            null,
            new BrpBoolean(false, null),
            new BrpBoolean(false, null));

    @Test
    public void testOudereJuistVoorNieuwereJuist() {
        final TussenGroep<?> groepNieuwereJuist =
                new TussenGroep<BrpGroepInhoud>(inhoud, new Lo3Historie(null, new Lo3Datum(19950202), new Lo3Datum(19950204)), null, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01,
                        0,
                        0));
        final TussenGroep<?> groepOudereJuist =
                new TussenGroep<BrpGroepInhoud>(inhoud, new Lo3Historie(null, new Lo3Datum(19920401), new Lo3Datum(19920508)), null, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01,
                        0,
                        1));
        final TussenGroep<?> groepOuderVoorkomen =
                new TussenGroep<BrpGroepInhoud>(inhoud, new Lo3Historie(null, new Lo3Datum(19920401), new Lo3Datum(19920508)), null, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01,
                        0,
                        2));

        Assert.assertTrue(comparator.compare(groepNieuwereJuist, groepOudereJuist) > 0);
        Assert.assertTrue(comparator.compare(groepOudereJuist, groepNieuwereJuist) < 0);
        Assert.assertTrue(comparator.compare(groepOudereJuist, groepOudereJuist) == 0);
        Assert.assertTrue(comparator.compare(groepNieuwereJuist, groepNieuwereJuist) == 0);

        Assert.assertTrue(comparator.compare(groepOudereJuist, groepOuderVoorkomen) > 0);
        Assert.assertTrue(comparator.compare(groepOuderVoorkomen, groepOudereJuist) < 0);

        final List<TussenGroep<?>> lo3Groepen = new ArrayList<>();
        lo3Groepen.add(groepNieuwereJuist);
        lo3Groepen.add(groepOudereJuist);
        lo3Groepen.add(groepOuderVoorkomen);
        Collections.sort(lo3Groepen, comparator);

        Assert.assertEquals(groepOuderVoorkomen, lo3Groepen.get(0));
        Assert.assertEquals(groepOudereJuist, lo3Groepen.get(1));
        Assert.assertEquals(groepNieuwereJuist, lo3Groepen.get(2));
    }
}
