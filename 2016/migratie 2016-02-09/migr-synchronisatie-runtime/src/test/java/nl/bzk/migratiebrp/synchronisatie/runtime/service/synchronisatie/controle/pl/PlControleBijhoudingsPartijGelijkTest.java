/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlControleBijhoudingsPartijGelijkTest {

    private final PlControleBijhoudingsPartijGelijk subject = new PlControleBijhoudingsPartijGelijk();

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void test() {
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, maakPl(516010, false)), maakPl(516010, false)));
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, maakPl(516010, false)), maakPl(599010, false)));
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, maakPl(516010, true)), maakPl(599010, false)));
    }

    private BrpPersoonslijst maakPl(final int bijhoudingspartijCode, final boolean legeBijhoudingStapel) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        if (!legeBijhoudingStapel) {
            builder.bijhoudingStapel(BrpStapelHelper.stapel(BrpStapelHelper.groep(
                BrpStapelHelper.bijhouding(bijhoudingspartijCode, null, null, new BrpBoolean(false, null)),
                BrpStapelHelper.his(20000101),
                BrpStapelHelper.act(1, 20000101))));
        }
        return builder.build();
    }
}
