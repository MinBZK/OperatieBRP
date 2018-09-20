/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link BRPActie} class. Niet getter en setter specifieke code uit deze class wordt in deze unit
 * test getest.
 */
public class BRPActieTest {

    /** Root objects moeten altijd aan een actie toegevoegd kunnen worden, ook als de actie nog geen rootobjecten
     * bevat. */
    @Test
    public void testToevoegenRootObject() {
        BRPActie actie = new BRPActie();
        Assert.assertNull(actie.getRootObjecten());

        actie.voegRootObjectToe(new Persoon());
        actie.voegRootObjectToe(new Relatie());
        Assert.assertNotNull(actie.getRootObjecten());
        Assert.assertEquals(2, actie.getRootObjecten().size());
    }

    /**
     * Indien <code>null</code> als rootobject wordt toegevoegd, moet deze niet worden opgenomen in de lijst van
     * rootobjecten.
     */
    @Test
    public void testToevoegenNullAlsRootObject() {
        BRPActie actie = new BRPActie();
        Assert.assertNull(actie.getRootObjecten());

        actie.voegRootObjectToe(null);
        Assert.assertNull(actie.getRootObjecten());
    }

    /**
     * Gelijke test als {@link #testToevoegenRootObject()}, maar nu via de {@link BRPActie#voegPersoonToe(Persoon)}
     * methode.
     */
    @Test
    public void testToevoegenPersoon() {
        BRPActie actie = new BRPActie();
        Assert.assertNull(actie.getRootObjecten());

        actie.voegPersoonToe(new Persoon());
        actie.voegPersoonToe(new Persoon());
        Assert.assertNotNull(actie.getRootObjecten());
        Assert.assertEquals(2, actie.getRootObjecten().size());
    }

    /**
     * Gelijke test als {@link #testToevoegenRootObject()}, maar nu via de {@link BRPActie#voegRelatieToe(Relatie)}
     * methode.
     */
    @Test
    public void testToevoegenRelatie() {
        BRPActie actie = new BRPActie();
        Assert.assertNull(actie.getRootObjecten());

        actie.voegRelatieToe(new Relatie());
        actie.voegRelatieToe(new Relatie());
        Assert.assertNotNull(actie.getRootObjecten());
        Assert.assertEquals(2, actie.getRootObjecten().size());
    }

}
