/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import nl.bzk.brp.business.stappen.support.BrpStapContext;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link nl.bzk.brp.business.stappen.support.BrpStapContext} class.
 */
public class StappenContextTest extends AbstractStappenTestBasis {

    @Test
    public void zouContextBasisGegevensMoetenVullen() {
        final BrpStapContext context = bouwStapContext();

        Assert.assertNotNull(context.getReferentieId());
        Assert.assertNotNull(context.getResultaatId());
    }

    @Test
    public void zouStappenInterfaceMethodesMoetenImplementeren() {
        final BrpStapContext context = bouwStapContext();
        Assert.assertEquals(Long.valueOf(123L), context.getReferentieId());
        Assert.assertEquals(Long.valueOf(654L), context.getResultaatId());

    }

}
