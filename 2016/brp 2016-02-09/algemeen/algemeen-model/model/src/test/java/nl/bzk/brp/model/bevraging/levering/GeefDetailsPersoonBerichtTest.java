/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bevraging.levering;

import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link GeefDetailsPersoonBericht} class waarin de
 * specifieke methodes van deze class worden getest.
 */
@Ignore
public class GeefDetailsPersoonBerichtTest {

    @Test
    public void testStandaardGettersEnSetters() {
        GeefDetailsPersoonBericht bericht = new GeefDetailsPersoonBericht();
        Assert.assertNull(bericht.getStuurgegevens());
        Assert.assertNull(bericht.getZoekcriteriaPersoon());

        BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        BerichtZoekcriteriaPersoonGroepBericht crit = new BerichtZoekcriteriaPersoonGroepBericht();
        bericht.setStuurgegevens(stuurgegevens);
        ReflectionTestUtils.setField(bericht, "zoekCriteria", crit);

        Assert.assertNotNull(crit.getMetaId());
        Assert.assertSame(stuurgegevens, bericht.getStuurgegevens());
        Assert.assertSame(bericht.getZoekcriteriaPersoon(), crit);
    }

    @Test
    public void testOphalenOngeldigePartijId() {
        GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();

        BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        // @TODO BMR28
        // stuurgegevens.setOrganisatie(new OrganisatienaamAttribuut("test"));
        geefDetailsPersoonBericht.setStuurgegevens(stuurgegevens);
        Assert.assertNull(geefDetailsPersoonBericht.getAdministratieveHandeling());
        // en daarmee dus ook de partij is null.
        // stuurgegevens.setOrganisatie(new OrganisatienaamAttribuut(""));
        Assert.assertNull(geefDetailsPersoonBericht.getAdministratieveHandeling());
    }
}
