/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test controle.
 */
public class PlControleNietOpgeschortMetCodeFTest {

    private PlControleNietOpgeschortMetCodeF subject;

    @Before
    public void setup() {
        SynchronisatieLogging.init();
        subject = new PlControleNietOpgeschortMetCodeF();
    }

    @Test
    public void testGelijk() {
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, maakPl(Lo3RedenOpschortingBijhoudingCodeEnum.FOUT), null, new
                BrpPartijCode("599010")), null));
    }

    @Test
    public void testOngelijk() {
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, maakPl(Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN), null, new
                BrpPartijCode("599010")), null));
    }

    @Test
    public void testNull() {
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, maakPl(null), null, new
                BrpPartijCode("599010")), null));
    }

    private Lo3Persoonslijst maakPl(final Lo3RedenOpschortingBijhoudingCodeEnum opschortReden) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Inschrijving(null, null, opschortReden ==
                null ? null : opschortReden.getCode(), null, null, null, null, null, null), Lo3StapelHelper.lo3Documentatie(0L, "0599", null, null,
                null, null), Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Her(7, 0, 1))));
        return builder.build();
    }

}
