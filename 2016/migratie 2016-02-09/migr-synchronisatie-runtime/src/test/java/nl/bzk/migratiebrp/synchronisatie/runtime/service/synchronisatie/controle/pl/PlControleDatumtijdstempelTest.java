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

public class PlControleDatumtijdstempelTest {

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void testGelijk() {
        final PlControle subject = new PlControleDatumtijdstempelGelijk();
        Assert.assertFalse(subject.controleer(maakContext(19990101), maakPl(20000101)));
        Assert.assertTrue(subject.controleer(maakContext(20000101), maakPl(20000101)));
        Assert.assertFalse(subject.controleer(maakContext(20010101), maakPl(20000101)));
    }

    @Test
    public void testGelijkOfOuder() {
        final PlControle subject = new PlControleGevondenDatumtijdstempelGelijkOfOuder();
        Assert.assertFalse(subject.controleer(maakContext(19990101), maakPl(20000101)));
        Assert.assertTrue(subject.controleer(maakContext(20000101), maakPl(20000101)));
        Assert.assertTrue(subject.controleer(maakContext(20010101), maakPl(20000101)));
    }

    @Test
    public void testNieuwer() {
        final PlControle subject = new PlControleGevondenDatumtijdstempelNieuwer();
        Assert.assertTrue(subject.controleer(maakContext(19990101), maakPl(20000101)));
        Assert.assertFalse(subject.controleer(maakContext(20000101), maakPl(20000101)));
        Assert.assertFalse(subject.controleer(maakContext(20010101), maakPl(20000101)));
    }

    @Test
    public void testOuder() {
        final PlControle subject = new PlControleGevondenDatumtijdstempelOuder();
        Assert.assertFalse(subject.controleer(maakContext(19990101), maakPl(20000101)));
        Assert.assertFalse(subject.controleer(maakContext(20000101), maakPl(20000101)));
        Assert.assertTrue(subject.controleer(maakContext(20010101), maakPl(20000101)));
    }

    private VerwerkingsContext maakContext(final int datumtijdstempel) {
        return new VerwerkingsContext(null, null, null, maakPl(datumtijdstempel));
    }

    private BrpPersoonslijst maakPl(final int datumtijdstempel) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        builder.inschrijvingStapel(BrpStapelHelper.stapel(BrpStapelHelper.groep(
            BrpStapelHelper.inschrijving(19770101, 1, datumtijdstempel * 1000000L),
            BrpStapelHelper.his(20000101),
            BrpStapelHelper.act(1, 20000101))));
        return builder.build();
    }
}
