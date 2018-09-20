/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.util;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Historievorm;
import org.junit.Assert;
import org.junit.Test;

public class BevragingUtilTest {

    @Test
    public final void testGetDefaultFormeelPeilmomentVoorBevraging() throws Exception {
        Assert.assertTrue(DatumTijdAttribuut.nu().getWaarde().getTime()
                                  - BevragingUtil.getDefaultFormeelPeilmomentVoorBevraging().getWaarde().getTime() < 1000);
    }

    @Test
    public final void testGetDefaultMaterieelPeilmomentVoorBevraging() throws Exception {
        Assert.assertEquals(DatumAttribuut.vandaag(), BevragingUtil.getDefaultMaterieelPeilmomentVoorBevraging());
    }

    @Test
    public final void testGetDefaultHistorieVormVoorBevraging() throws Exception {
        Assert.assertEquals(Historievorm.GEEN, BevragingUtil.getDefaultHistorieVormVoorBevraging().getWaarde());
    }
}
