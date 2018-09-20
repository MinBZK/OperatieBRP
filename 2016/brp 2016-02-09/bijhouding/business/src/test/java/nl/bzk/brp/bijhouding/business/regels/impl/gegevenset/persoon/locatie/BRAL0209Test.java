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

public class BRAL0209Test extends AbstractLocatieRegelTest {

    private BRAL0209 bral0209;

    @Before
    public void init() {
        bral0209 = new BRAL0209();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRAL0209, bral0209.getRegel());
    }

    @Test
    public void testRegel0209() {
        PersoonBericht persoon = getPersoonGeboorteNederland(null, getWoonplaats().getNaam());
        List<BerichtEntiteit> berichtEntiteiten = bral0209.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());

        persoon = getPersoonGeboorteNederland(getGemeente(), getWoonplaats().getNaam());
        berichtEntiteiten = bral0209.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());

        persoon = getPersoonGeboorteNederland(null, null);
        berichtEntiteiten = bral0209.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }


}
