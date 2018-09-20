/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bevraging.bijhouding;

import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


/**
 * Unit test voor de {@link ZoekPersoonBericht} class waarin de specifieke methodes
 * van deze class worden getest.
 */
@Ignore
public class ZoekPersoonBerichtTest {

    @Test
    public void testStandaardGettersEnSetters() {
        ZoekPersoonBericht bericht = new ZoekPersoonBericht();
        Assert.assertNull(bericht.getStuurgegevens());
        Assert.assertNull(bericht.getZoekcriteriaPersoon());

        BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        bericht.setStuurgegevens(stuurgegevens);

        BerichtZoekcriteriaPersoonGroepBericht crit = new BerichtZoekcriteriaPersoonGroepBericht();
        bericht.setZoekcriteriaPersoon(crit);

        Assert.assertSame(stuurgegevens, bericht.getStuurgegevens());
        Assert.assertSame(bericht.getZoekcriteriaPersoon(), crit);
    }

    @Test
    public void testOphalenOngeldigePartijId() {
        ZoekPersoonBericht zoekPersoonBericht = new ZoekPersoonBericht();

        BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();

        stuurgegevens.setZendendePartijCode("test");
        zoekPersoonBericht.setStuurgegevens(stuurgegevens);
        Assert.assertNull(zoekPersoonBericht.getAdministratieveHandeling());
        // en daarmee dus ook de partij is null.
        stuurgegevens.setZendendePartijCode("");
        Assert.assertNull(zoekPersoonBericht.getAdministratieveHandeling());
    }
}
