/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlControleBevatDatumIngangBlokkeringTest {
    private final PlControleBevatDatumIngangBlokkering subject = new PlControleBevatDatumIngangBlokkering();

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void test() {
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, maakPl(20010101), null), null));
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, maakPl(null), null), null));
    }

    private Lo3Persoonslijst maakPl(final Integer datumIngangBlokkering) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Inschrijving(datumIngangBlokkering, null, null, 19770101, "0626", 0, 1, 19770101L, true),
                Lo3CategorieEnum.CATEGORIE_07)));
        return builder.build();
    }
}
