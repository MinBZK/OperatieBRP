/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeRegel;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import org.junit.Assert;

/**
 * util voor testen.
 */
public class VoorwaardeRegelTestUtil {

    private GbaVoorwaardeRegel instance;

    /**
     * Constructor.
     * @param regel voorwaarderegel
     */
    public VoorwaardeRegelTestUtil(final GbaVoorwaardeRegel regel) {
        instance = regel;
    }

    /**
     * Test vertaling voorwaarde regel.
     * @param gbaVoorwaarde gba voorwaarde
     * @param brpExpressie brp expressie
     * @throws GbaVoorwaardeOnvertaalbaarExceptie indien het niet goed gaat.
     */
    public final void testVoorwaarde(final String gbaVoorwaarde, final String brpExpressie) throws GbaVoorwaardeOnvertaalbaarExceptie {
        Assert.assertTrue(instance.filter(gbaVoorwaarde));
        final String result = instance.getBrpExpressie(new RubriekWaarde(gbaVoorwaarde)).getBrpExpressie();
        Assert.assertEquals(brpExpressie, result);
    }

}
