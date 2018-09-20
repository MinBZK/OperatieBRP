/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRAL0210Test extends AbstractLocatieRegelTest {

    private BRAL0210 bral0210;

    @Before
    public void init() {
        bral0210 = new BRAL0210();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRAL0210, bral0210.getRegel());
    }

    @Test
    public void testRegel0210() {
        PersoonBericht persoon = getPersoonGeboorte(getBuitenland(), getGemeente(), null, null, null, null);
        List<BerichtEntiteit> berichtEntiteiten = bral0210.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());

        persoon = getPersoonGeboorte(getBuitenland(), null, null, null, null, null);
        berichtEntiteiten = bral0210.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());

        persoon = getPersoonGeboorte(getNederland(), getGemeente(), null, null, null, null);
        berichtEntiteiten = bral0210.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());

        persoon = getPersoonGeboorte(getNederland(), null, null, null, null, null);
        berichtEntiteiten = bral0210.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
    }

}
