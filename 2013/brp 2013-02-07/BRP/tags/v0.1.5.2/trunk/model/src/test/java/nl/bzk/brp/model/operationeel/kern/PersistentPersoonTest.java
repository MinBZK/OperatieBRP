/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de methodes van de {@link PersistentPersoon} class die niet behoren tot de standaard getters en
 * setters.
 */
public class PersistentPersoonTest {

    @Test
    public void testInitialisatieCollecties() {
        PersistentPersoon persoon = new PersistentPersoon();

        Assert.assertNotNull(persoon.getNationaliteiten());
        Assert.assertTrue(persoon.getNationaliteiten().isEmpty());

        Assert.assertNotNull(persoon.getPersoonVoornamen());
        Assert.assertTrue(persoon.getPersoonVoornamen().isEmpty());

        Assert.assertNotNull(persoon.getAdressen());
        Assert.assertTrue(persoon.getAdressen().isEmpty());

        Assert.assertNotNull(persoon.getPersoonGeslachtsnaamcomponenten());
        Assert.assertTrue(persoon.getPersoonGeslachtsnaamcomponenten().isEmpty());

        Assert.assertNotNull(persoon.getPersoonIndicaties());
        Assert.assertTrue(persoon.getPersoonIndicaties().isEmpty());
    }

    @Test
    public void testToevoegenNationaliteit() {
        PersistentPersoon persoon = new PersistentPersoon();
        PersistentPersoonNationaliteit nationaliteitA = new PersistentPersoonNationaliteit();
        PersistentPersoonNationaliteit nationaliteitB = new PersistentPersoonNationaliteit();

        persoon.voegNationaliteitToe(nationaliteitA);
        Assert.assertEquals(1, persoon.getNationaliteiten().size());
        persoon.voegNationaliteitToe(nationaliteitB);
        Assert.assertEquals(2, persoon.getNationaliteiten().size());
    }

    @Test
    public void testToevoegenNationaliteitIndienNationaliteitenNullIs() {
        PersistentPersoon persoon = new PersistentPersoon();
        PersistentPersoonNationaliteit nationaliteitA = new PersistentPersoonNationaliteit();

        persoon.setNationaliteiten(null);
        persoon.voegNationaliteitToe(nationaliteitA);
        Assert.assertEquals(1, persoon.getNationaliteiten().size());
    }
}
