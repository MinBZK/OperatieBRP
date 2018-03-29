/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlControleVorigAnummerGelijkTest {

    private final PlControleVorigAnummerGelijk subject = new PlControleVorigAnummerGelijk();

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void test() {
        Assert.assertTrue(subject.controleer(maakContext((String) null), maakPl((String) null)));
        Assert.assertTrue(subject.controleer(maakContext("1"), maakPl((String) null)));
        Assert.assertTrue(subject.controleer(maakContext("1", "2"), maakPl("1", "3")));

        Assert.assertFalse(subject.controleer(maakContext((String) null), maakPl("1")));
        Assert.assertFalse(subject.controleer(maakContext("2"), maakPl("1")));

        Assert.assertTrue(subject.controleer(maakContext("3", "2"), maakPl("2")));
        Assert.assertTrue(subject.controleer(maakContext("3", "2", "1"), maakPl("2", "1")));

        Assert.assertFalse(subject.controleer(maakContext("2"), maakPl("3", "2")));
        Assert.assertFalse(subject.controleer(maakContext("2", "1"), maakPl("3", "2", "1")));

    }

    private VerwerkingsContext maakContext(final String... vorigAnummers) {
        return new VerwerkingsContext(null, null, null, maakPl(vorigAnummers));
    }

    private BrpPersoonslijst maakPl(final String... vorigAnummers) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        if (vorigAnummers != null) {
            final List<BrpGroep<BrpNummerverwijzingInhoud>> groepen = new ArrayList<>();

            for (int i = 0; i < vorigAnummers.length; i++) {
                final Integer datumAanvang = 20000131 - i;
                final Integer datumEinde = i == 0 ? null : 20000132 - i;
                groepen.add(BrpStapelHelper.groep(
                        BrpStapelHelper.nummerverwijzing(vorigAnummers[i], null, null, null),
                        BrpStapelHelper.his(datumAanvang, datumEinde, datumAanvang, datumEinde),
                        BrpStapelHelper.act(1, datumAanvang)));
            }

            builder.nummerverwijzingStapel(BrpStapelHelper.stapel(groepen.toArray(new BrpGroep[groepen.size()])));
        }

        return builder.build();
    }
}
