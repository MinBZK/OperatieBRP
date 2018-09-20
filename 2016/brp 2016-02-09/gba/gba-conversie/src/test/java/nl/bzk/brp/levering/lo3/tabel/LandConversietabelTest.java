/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link LandConversietabel}.
 */
public class LandConversietabelTest {

    @Test
    public void test() {
        final List<Short> list = new ArrayList<>();
        list.add((short) 1);
        final LandConversietabel subject = new LandConversietabel(list);

        Assert.assertTrue(subject.valideerLo3(null));
        Assert.assertTrue(subject.valideerLo3(new Lo3LandCode("0001")));
        Assert.assertFalse(subject.valideerLo3(new Lo3LandCode("0002")));

        Assert.assertTrue(subject.valideerBrp(null));
        Assert.assertTrue(subject.valideerBrp(new BrpLandOfGebiedCode((short) 1)));
        Assert.assertFalse(subject.valideerBrp(new BrpLandOfGebiedCode((short) 2)));
    }
}
