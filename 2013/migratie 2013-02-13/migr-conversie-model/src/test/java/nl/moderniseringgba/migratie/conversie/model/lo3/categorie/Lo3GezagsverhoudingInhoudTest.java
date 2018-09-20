/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;

import org.junit.Test;

/**
 * Test de Lo3Gezagsverhouding categorie.
 */
public class Lo3GezagsverhoudingInhoudTest {

    @Test
    public void testValid() {
        Assert.assertNotNull(new Lo3GezagsverhoudingInhoud(null, null));
        Assert.assertNotNull(new Lo3GezagsverhoudingInhoud(Lo3IndicatieGezagMinderjarigeEnum.OUDER_2.asElement(),
                null));
        Assert.assertNotNull(new Lo3GezagsverhoudingInhoud(null, Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD
                .asElement()));
    }
}
