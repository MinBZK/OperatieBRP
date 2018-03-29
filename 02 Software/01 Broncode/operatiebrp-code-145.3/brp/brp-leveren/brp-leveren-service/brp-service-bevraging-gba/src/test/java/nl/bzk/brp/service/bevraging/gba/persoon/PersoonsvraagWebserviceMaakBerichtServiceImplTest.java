/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.persoon;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor de maak bericht service Ad Hoc Zoek Persoon Webservice (GBA).
 */
public class PersoonsvraagWebserviceMaakBerichtServiceImplTest {

    final PersoonsvraagWebserviceMaakBerichtServiceImpl subject = new PersoonsvraagWebserviceMaakBerichtServiceImpl(null, null, null, null, null);

    @Test
    public void test() {
        Assert.assertNotNull(subject.getDienstSpecifiekeLoggingString());
        Assert.assertEquals("Zoek Persoon via Webservice (GBA)", subject.getDienstSpecifiekeLoggingString());
    }

}
