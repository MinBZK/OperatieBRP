/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test klasse waarin de methodes in de {@link PersoonVoornaamModel} klasse worden getest.
 */
public class PersoonVoornaamTest {

    @Test
    public void testEquals() {
        final PersoonModel persoon = new PersoonModel();
        final PersoonVoornaamModel voornaam = bouwPersoonVoornaam("Test1", 1, persoon);

        // Gelijk
        Assert.assertEquals(voornaam, voornaam);
        Assert.assertEquals(voornaam, bouwPersoonVoornaam("Test1", 1, persoon));
        Assert.assertEquals(voornaam, bouwPersoonVoornaam("Totaal Anders", 1, persoon));
        Assert.assertEquals(voornaam, bouwPersoonVoornaam(null, 1, persoon));

        // Niet gelijk
        Assert.assertNotEquals(voornaam, null);
        Assert.assertNotEquals(voornaam, bouwPersoonVoornaam("Test1", 2, persoon));
        Assert.assertNotEquals(voornaam, bouwPersoonVoornaam("Test1", 3, new PersoonModel()));
    }

    @Test
    public void testHashCode() {
        final PersoonModel persoon = new PersoonModel();
        final PersoonVoornaamModel voornaam = bouwPersoonVoornaam("Test1", 1, persoon);

        // Gelijk
        Assert.assertEquals(voornaam.hashCode(), voornaam.hashCode());
        Assert.assertEquals(voornaam.hashCode(), bouwPersoonVoornaam("Test1", 1, persoon).hashCode());
        Assert.assertEquals(voornaam.hashCode(), bouwPersoonVoornaam("Totaal Anders", 1, persoon).hashCode());
        Assert.assertEquals(voornaam.hashCode(), bouwPersoonVoornaam(null, 1, persoon).hashCode());

        // Niet gelijk
        Assert.assertNotEquals(voornaam.hashCode(), bouwPersoonVoornaam("Test1", 2, persoon).hashCode());
        Assert.assertNotEquals(voornaam.hashCode(), bouwPersoonVoornaam("Test1", 3, new PersoonModel()).hashCode());
    }

    /**
     * Instantieert en retourneert een nieuwe {@link PersoonVoornaamModel} instantie met opgegeven naam en volgnummer.
     *
     * @param naam       de naam van de persoon voornaam.
     * @param volgnummer het volgnummer van de persoon voornaam.
     * @param persoon    de persoon waartoe de voornaam behoort.
     * @return een {@link PersoonVoornaamModel} instantie.
     */
    private PersoonVoornaamModel
    bouwPersoonVoornaam(final String naam, final int volgnummer, final PersoonModel persoon) {
        final PersoonVoornaamModel persoonVoornaam =
                new PersoonVoornaamModel(persoon, new VolgnummerAttribuut(volgnummer));
        if (naam != null) {
            persoonVoornaam.setStandaard(new PersoonVoornaamStandaardGroepModel(new VoornaamAttribuut(naam)));
        }
        return persoonVoornaam;
    }
}
