/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRAL0107Test {

    private BRAL0107 bral0107;

    @Before
    public void init() {
        bral0107 = new BRAL0107();
    }

    @Test
    public void testIsWelIngeschrevene() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        Assert.assertTrue(bral0107.isIngeschrevene(persoon));
    }

    @Test
    public void testIsNietIngeschrevene() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        Assert.assertFalse(bral0107.isIngeschrevene(persoon));
    }

    @Test
    public void testSoortIsNull() {
        final PersoonBericht persoon = new PersoonBericht();
        Assert.assertFalse(bral0107.isIngeschrevene(persoon));
    }

}
