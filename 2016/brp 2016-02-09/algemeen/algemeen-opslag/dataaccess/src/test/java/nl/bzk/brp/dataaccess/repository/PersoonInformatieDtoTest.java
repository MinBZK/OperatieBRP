/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test klasse ten behoeve van de {@link PersoonInformatieDto} klasse.
 */
public class PersoonInformatieDtoTest {

    @Test
    public void testConstructorEnGettersEnSetters() {
        PersoonInformatieDto persoon;

        // Test met null waardes
        persoon = new PersoonInformatieDto(null, null);
        Assert.assertNull(persoon.getiD());
        Assert.assertNull(persoon.getSoort());

        // Test met niet-null waardes
        persoon = new PersoonInformatieDto(2, SoortPersoon.INGESCHREVENE);
        Assert.assertEquals(Integer.valueOf(2), persoon.getiD());
        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort());
    }

}
