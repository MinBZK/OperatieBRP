/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Test;

/**
 * Unittest voor {@link BijhoudingPersoonNationaliteit}
 */
public class BijhoudingPersoonNationaliteitTest {

    @Test
    public void testDecorator() {
        assertNull(BijhoudingPersoonNationaliteit.decorate(null));
        final PersoonNationaliteit nat = new PersoonNationaliteit(new Persoon(SoortPersoon.INGESCHREVENE), new Nationaliteit("Nat", "0001"));
        assertNotNull(BijhoudingPersoonNationaliteit.decorate(nat));
    }

}
