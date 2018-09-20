/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import org.junit.Assert;
import org.junit.Test;

/** Unit test class voor de {@link nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent} class. */
public class PersoonGeslachtsnaamcomponentTest {

    @Test
    public void testEquals() {
        Persoon persoon = new Persoon();
        PersoonGeslachtsnaamcomponent component = bouwNaamComponent("Test", 1, "vv", persoon);

        Assert.assertTrue(component.equals(component));
        Assert.assertFalse(component.equals(null));
        Assert.assertFalse(component.equals(new Object()));
        Assert.assertTrue(component.equals(bouwNaamComponent("Test", 1, "vv", persoon)));

        Assert.assertTrue(
            bouwNaamComponent("Test", 1, "vv", persoon).equals(bouwNaamComponent("Test", 1, "vv", persoon)));
        Assert.assertFalse(
            bouwNaamComponent("Test1", 1, "vv", persoon).equals(bouwNaamComponent("Test2", 1, "vv", persoon)));
        Assert.assertFalse(
            bouwNaamComponent("Test", 1, "vv", persoon).equals(bouwNaamComponent("Test", 2, "vv", persoon)));
        Assert.assertFalse(
            bouwNaamComponent("Test", 1, "vv", persoon).equals(bouwNaamComponent("Test", 1, "aa", persoon)));
        Assert
            .assertFalse(
                bouwNaamComponent("Test", 1, "vv", persoon).equals(bouwNaamComponent("Test", 1, "vv", new Persoon())));
    }

    @Test
    public void testHashCode() {
        Persoon persoon = new Persoon();
        Assert.assertTrue(
            bouwNaamComponent("Test", 1, "vv", persoon).hashCode() == bouwNaamComponent("Test", 1, "vv", persoon)
                .hashCode());
        Assert.assertFalse(
            bouwNaamComponent("Test1", 1, "vv", persoon).hashCode() == bouwNaamComponent("Test2", 1, "vv", persoon)
                .hashCode());
        Assert.assertFalse(
            bouwNaamComponent("Test", 1, "vv", persoon).hashCode() == bouwNaamComponent("Test", 2, "vv", persoon)
                .hashCode());
        Assert.assertFalse(
            bouwNaamComponent("Test", 1, "vv", persoon).hashCode() == bouwNaamComponent("Test", 1, "aa", persoon)
                .hashCode());
        Assert.assertFalse(
            bouwNaamComponent("Test", 1, "vv", persoon).hashCode() == bouwNaamComponent("Test", 1, "vv", new Persoon())
                .hashCode());
    }

    @Test
    public void testCompare() {
        Assert.assertTrue(
            bouwNaamComponent("Test", 1, "vv", new Persoon())
                .compareTo(bouwNaamComponent("Test", 2, "vv", new Persoon())) < 0);
        Assert.assertTrue(
            bouwNaamComponent("Test", 2, "vv", new Persoon())
                .compareTo(bouwNaamComponent("Test", 1, "vv", new Persoon())) > 0);
        Assert.assertTrue(
            bouwNaamComponent("Test1", 1, "vv", new Persoon())
                .compareTo(bouwNaamComponent("Test2", 1, "aa", new Persoon())) == 0);
    }

    private PersoonGeslachtsnaamcomponent bouwNaamComponent(final String naam, final int volgNummer,
        final String voorvoegsel, final Persoon persoon)
    {
        PersoonGeslachtsnaamcomponent component = new PersoonGeslachtsnaamcomponent();
        component.setNaam(naam);
        component.setVolgnummer(volgNummer);
        component.setVoorvoegsel(voorvoegsel);
        component.setPersoon(persoon);
        return component;
    }

}
