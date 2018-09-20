/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek;

import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerzoekControleOudANummerIsGevuldTest {

    @InjectMocks
    private VerzoekControleOudANummerIsGevuld subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void test() {
        Assert.assertFalse(subject.controleer(maakVerzoek(null)));
        Assert.assertTrue(subject.controleer(maakVerzoek(true)));
        Assert.assertFalse(subject.controleer(maakVerzoek(false)));
    }

    private SynchroniseerNaarBrpVerzoekBericht maakVerzoek(final Boolean isAnummerWijziging) {
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setAnummerWijziging(isAnummerWijziging);
        return verzoek;

    }
}
