/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRAL0106;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0913Test {

    private BRBY0913 brby0913;
    private BRAL0106 bral0106;

    @Before
    public void init() {
        brby0913 = new BRBY0913();
        bral0106 = Mockito.mock(BRAL0106.class);
        ReflectionTestUtils.setField(brby0913, "bral0106", bral0106);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0913, brby0913.getRegel());
    }

    @Test
    public void testIsIngezetene() {
        Mockito.when(bral0106.isIngezetene(Matchers.any(Persoon.class))).thenReturn(true);
        final List<BerichtEntiteit> resultaat = brby0913.voerRegelUit(null, new PersoonBericht(), null, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testIsNietIngezetene() {
        Mockito.when(bral0106.isIngezetene(Matchers.any(Persoon.class))).thenReturn(false);
        final List<BerichtEntiteit> resultaat = brby0913.voerRegelUit(null, new PersoonBericht(), null, null);
        Assert.assertEquals(1, resultaat.size());
    }

}
