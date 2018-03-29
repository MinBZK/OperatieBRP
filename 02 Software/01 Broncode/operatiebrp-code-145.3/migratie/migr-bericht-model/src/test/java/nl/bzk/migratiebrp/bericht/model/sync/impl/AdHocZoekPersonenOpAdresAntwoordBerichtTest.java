/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersonenOpAdresAntwoordType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor AdHocZoekPersoonAntwoordBericht.
 */
public class AdHocZoekPersonenOpAdresAntwoordBerichtTest extends AbstractSyncBerichtTestBasis {

    @Test
    public void testFoutreden() throws Exception {
        final AdHocZoekPersonenOpAdresAntwoordBericht bericht = new AdHocZoekPersonenOpAdresAntwoordBericht();
        bericht.setFoutreden(AdHocZoekAntwoordFoutReden.H);

        Assert.assertEquals("foutreden moet overeenkomen", AdHocZoekAntwoordFoutReden.H, bericht.getFoutreden());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);
    }

    @Test
    public void testTypeFoutreden() throws Exception {
        final AdHocZoekPersonenOpAdresAntwoordType type = new AdHocZoekPersonenOpAdresAntwoordType();
        type.setFoutreden(AdHocZoekAntwoordFoutReden.H);

        final AdHocZoekPersonenOpAdresAntwoordBericht bericht = new AdHocZoekPersonenOpAdresAntwoordBericht(type);
        Assert.assertEquals("foutreden moet overeenkomen", AdHocZoekAntwoordFoutReden.H, bericht.getFoutreden());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);
    }

    @Test
    public void testInhoud() throws Exception {
        final AdHocZoekPersonenOpAdresAntwoordBericht bericht = new AdHocZoekPersonenOpAdresAntwoordBericht();
        bericht.setInhoud("persoonslijst");

        Assert.assertEquals("persoon moet overeenkomen", "persoonslijst", bericht.getInhoud());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);
    }
}
