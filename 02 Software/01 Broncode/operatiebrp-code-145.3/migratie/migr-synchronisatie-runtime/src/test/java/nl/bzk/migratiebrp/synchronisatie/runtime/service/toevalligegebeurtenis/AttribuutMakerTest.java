/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.AttribuutMaker;
import org.junit.Test;

public class AttribuutMakerTest {

    AttribuutMaker subject = new AttribuutMaker();

    @Test
    public void testMaakDatumMetOnzekerheid() {
        Assert.assertNull(subject.maakDatumMetOnzekerheid(null));
        Assert.assertEquals("2016-01-01", subject.maakDatumMetOnzekerheid(new BrpDatum(20160101, null)).getValue());
        Assert.assertEquals("2016-01", subject.maakDatumMetOnzekerheid(new BrpDatum(20160100, null)).getValue());
        Assert.assertEquals("2016", subject.maakDatumMetOnzekerheid(new BrpDatum(20160000, null)).getValue());

    }
}
