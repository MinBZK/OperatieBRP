/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlControleVersienummerTest {

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void testGelijk() {
        final PlControle subject = new PlControleVersienummerGelijk();
        Assert.assertFalse(subject.controleer(maakContext(1), maakPl(2)));
        Assert.assertTrue(subject.controleer(maakContext(2), maakPl(2)));
        Assert.assertFalse(subject.controleer(maakContext(3), maakPl(2)));
    }

    @Test
    public void testGelijkOfGroter() {
        final PlControle subject = new PlControleGevondenVersienummerGelijkOfGroter();
        Assert.assertTrue(subject.controleer(maakContext(1), maakPl(2)));
        Assert.assertTrue(subject.controleer(maakContext(2), maakPl(2)));
        Assert.assertFalse(subject.controleer(maakContext(3), maakPl(2)));
    }

    @Test
    public void testGelijkOfKleiner() {
        final PlControle subject = new PlControleGevondenVersienummerGelijkOfKleiner();
        Assert.assertFalse(subject.controleer(maakContext(1), maakPl(2)));
        Assert.assertTrue(subject.controleer(maakContext(2), maakPl(2)));
        Assert.assertTrue(subject.controleer(maakContext(3), maakPl(2)));
    }

    @Test
    public void testGroter() {
        final PlControle subject = new PlControleGevondenVersienummerGroter();
        Assert.assertTrue(subject.controleer(maakContext(1), maakPl(2)));
        Assert.assertFalse(subject.controleer(maakContext(2), maakPl(2)));
        Assert.assertFalse(subject.controleer(maakContext(3), maakPl(2)));
    }

    @Test
    public void testKleiner() {
        final PlControle subject = new PlControleGevondenVersienummerKleiner();
        Assert.assertFalse(subject.controleer(maakContext(1), maakPl(2)));
        Assert.assertFalse(subject.controleer(maakContext(2), maakPl(2)));
        Assert.assertTrue(subject.controleer(maakContext(3), maakPl(2)));
    }

    private VerwerkingsContext maakContext(final int versienummer) {
        return new VerwerkingsContext(null, null, null, maakPl(versienummer));
    }

    private BrpPersoonslijst maakPl(final int versienummer) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        builder.inschrijvingStapel(BrpStapelHelper.stapel(BrpStapelHelper.groep(
                BrpStapelHelper.inschrijving(19770101, versienummer, 20000101000000L),
                BrpStapelHelper.his(20000101),
                BrpStapelHelper.act(1, 20000101))));
        return builder.build();
    }
}
