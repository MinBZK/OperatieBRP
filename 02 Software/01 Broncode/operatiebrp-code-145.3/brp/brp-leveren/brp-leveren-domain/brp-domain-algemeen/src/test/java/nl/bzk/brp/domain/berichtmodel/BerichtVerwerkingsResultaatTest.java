/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import org.junit.Assert;
import org.junit.Test;

/**
 * BerichtVerwerkingsResultaatTest.
 */
public class BerichtVerwerkingsResultaatTest {

    @Test
    public void testEqualsHashCode() {
        final BerichtVerwerkingsResultaat berichtVerwerkingsResultaat1 = BerichtVerwerkingsResultaat.builder().metHoogsteMeldingsniveau("HOOG")
                .metVerwerking(VerwerkingsResultaat.FOUTIEF).build();
        final BerichtVerwerkingsResultaat berichtVerwerkingsResultaat2 = BerichtVerwerkingsResultaat.builder().metHoogsteMeldingsniveau("HOOG")
                .metVerwerking(VerwerkingsResultaat.FOUTIEF).build();

        Assert.assertTrue(berichtVerwerkingsResultaat1.equals(berichtVerwerkingsResultaat1));
        Assert.assertTrue(berichtVerwerkingsResultaat1.equals(berichtVerwerkingsResultaat2));
        Assert.assertEquals(berichtVerwerkingsResultaat1.hashCode(), berichtVerwerkingsResultaat2.hashCode());
    }
}
