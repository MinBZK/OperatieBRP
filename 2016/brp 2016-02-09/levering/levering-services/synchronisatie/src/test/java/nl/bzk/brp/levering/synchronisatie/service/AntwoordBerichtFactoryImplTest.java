/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.service;

import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import org.junit.Assert;
import org.junit.Test;

public class AntwoordBerichtFactoryImplTest {

    private AntwoordBerichtFactoryImpl antwoordBerichtFactory = new AntwoordBerichtFactoryImpl();

    @Test
    public final void testMaakInitieelBerichtResultaatGroepBericht() {
        final BerichtResultaatGroepBericht resultaatGroepBericht =
            antwoordBerichtFactory.maakInitieelBerichtResultaatGroepBericht(null, null);

        Assert.assertNotNull(resultaatGroepBericht);
    }

    @Test
    public final void testMaakInitieelAntwoordBerichtVoorInkomendBericht() {
        final Bericht bericht = new GeefSynchronisatiePersoonBericht();
        final AntwoordBericht antwoordBericht = antwoordBerichtFactory.maakInitieelAntwoordBerichtVoorInkomendBericht(bericht);

        Assert.assertTrue(antwoordBericht instanceof GeefSynchronisatiePersoonAntwoordBericht);
    }

    @Test(expected = IllegalStateException.class)
    public final void testMaakInitieelAntwoordBerichtVoorInkomendOnbekendBericht() {
        final Bericht bericht = new GeefDetailsPersoonBericht();
        antwoordBerichtFactory.maakInitieelAntwoordBerichtVoorInkomendBericht(bericht);
    }
}
