/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import org.junit.Assert;
import org.junit.Test;

/** Unit test class voor de {@link PersoonVoornaam} class. */
public class PersoonVoornaamTest {

    @Test
    public void testEquals() {
        Persoon persoon = new Persoon();
        PersoonVoornaam voornaam = bouwPersoonVoornaam("Test", 1, persoon);

        Assert.assertTrue(voornaam.equals(voornaam));
        Assert.assertFalse(voornaam.equals(null));
        Assert.assertFalse(voornaam.equals(new Object()));
        Assert.assertTrue(voornaam.equals(bouwPersoonVoornaam("Test", 1, persoon)));

        Assert.assertTrue(bouwPersoonVoornaam("Test", 1, persoon).equals(bouwPersoonVoornaam("Test", 1, persoon)));
        Assert.assertFalse(bouwPersoonVoornaam("Test1", 1, persoon).equals(bouwPersoonVoornaam("Test2", 1, persoon)));
        Assert.assertFalse(bouwPersoonVoornaam("Test", 1, persoon).equals(bouwPersoonVoornaam("Test", 2, persoon)));
        Assert
            .assertFalse(bouwPersoonVoornaam("Test", 1, persoon).equals(bouwPersoonVoornaam("Test", 1, new Persoon())));
    }

    @Test
    public void testHashCode() {
        Persoon persoon = new Persoon();
        Assert.assertTrue(
            bouwPersoonVoornaam("Test", 1, persoon).hashCode() == bouwPersoonVoornaam("Test", 1, persoon).hashCode());
        Assert.assertFalse(
            bouwPersoonVoornaam("Test1", 1, persoon).hashCode() == bouwPersoonVoornaam("Test2", 1, persoon).hashCode());
        Assert.assertFalse(
            bouwPersoonVoornaam("Test", 1, persoon).hashCode() == bouwPersoonVoornaam("Test", 2, persoon).hashCode());
        Assert.assertFalse(
            bouwPersoonVoornaam("Test", 1, persoon).hashCode() == bouwPersoonVoornaam("Test", 1, new Persoon())
                .hashCode());
    }

    @Test
    public void testCompare() {
        Assert.assertTrue(
            bouwPersoonVoornaam("Test", 1, new Persoon()).compareTo(bouwPersoonVoornaam("Test", 2, new Persoon())) < 0);
        Assert.assertTrue(
            bouwPersoonVoornaam("Test", 2, new Persoon()).compareTo(bouwPersoonVoornaam("Test", 1, new Persoon())) > 0);
        Assert.assertTrue(
            bouwPersoonVoornaam("Test1", 1, new Persoon()).compareTo(bouwPersoonVoornaam("Test2", 1, new Persoon()))
                == 0);
    }

    private PersoonVoornaam bouwPersoonVoornaam(final String naam, final int volgNummer, final Persoon persoon) {
        PersoonVoornaam voornaam = new PersoonVoornaam();
        voornaam.setNaam(naam);
        voornaam.setVolgnummer(volgNummer);
        voornaam.setPersoon(persoon);
        return voornaam;
    }

}
