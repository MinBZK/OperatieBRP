/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek;

import nl.bzk.migratiebrp.bericht.model.sync.generated.TypeSynchronisatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test controle.
 */
public class VerzoekControleBerichtVanSoortLg01Test {

    private SynchroniseerNaarBrpVerzoekBericht verzoek;

    private VerzoekControleBerichtVanSoortLg01 subject;

    @Before
    public void setup() {
        SynchronisatieLogging.init();
        subject = new VerzoekControleBerichtVanSoortLg01();

    }

    @Test
    public void testControleerTrue() {
        verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setTypeBericht(TypeSynchronisatieBericht.LG_01);

        Assert.assertTrue("Bericht is van type LG_01", subject.controleer(verzoek));
    }

    @Test
    public void testControleerFalse() {
        verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setTypeBericht(TypeSynchronisatieBericht.LA_01);

        Assert.assertFalse("Bericht is van type LA_01", subject.controleer(verzoek));
    }

    @Test
    public void testControleerGeenType() {
        verzoek = new SynchroniseerNaarBrpVerzoekBericht();

        Assert.assertFalse("Type bericht is leeg", subject.controleer(verzoek));
    }
}
