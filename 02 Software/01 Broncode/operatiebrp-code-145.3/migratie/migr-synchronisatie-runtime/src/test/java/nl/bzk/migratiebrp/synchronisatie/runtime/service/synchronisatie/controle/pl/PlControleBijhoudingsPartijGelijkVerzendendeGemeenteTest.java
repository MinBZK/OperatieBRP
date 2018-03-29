/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * test controle.
 */
public class PlControleBijhoudingsPartijGelijkVerzendendeGemeenteTest {

    private PlControleBijhoudingsPartijGelijkVerzendendeGemeente subject;

    @Before
    public void setup() {
        SynchronisatieLogging.init();
        subject = new PlControleBijhoudingsPartijGelijkVerzendendeGemeente();
    }

    @Test
    public void testGelijk() {
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, maakPl("516010", false), new BrpPartijCode("516010")), null));
    }

    @Test
    public void testOngelijk() {
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, maakPl("516010", false), new BrpPartijCode("599010")), null));
    }

    private BrpPersoonslijst maakPl(final String bijhoudingspartijCode, final boolean legeBijhoudingStapel) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        if (!legeBijhoudingStapel) {
            builder.bijhoudingStapel(
                    BrpStapelHelper.stapel(
                            BrpStapelHelper.groep(
                                    BrpStapelHelper.bijhouding(bijhoudingspartijCode, null, null),
                                    BrpStapelHelper.his(20000101),
                                    BrpStapelHelper.act(1, 20000101))));
        }
        return builder.build();
    }
}
