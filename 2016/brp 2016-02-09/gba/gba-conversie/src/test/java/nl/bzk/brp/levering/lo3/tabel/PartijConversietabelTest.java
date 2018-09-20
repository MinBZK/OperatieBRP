/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link PartijConversietabel}.
 */
public class PartijConversietabelTest {

    @Test
    public void test() throws ReflectiveOperationException {
        final List<Gemeente> list = new ArrayList<>();
        list.add(maakConversietabelRegel(517, 517010));

        final PartijConversietabel subject = new PartijConversietabel(list);

        Assert.assertEquals("0517", subject.converteerNaarLo3(new BrpPartijCode(517010)).getWaarde());
    }

    private Gemeente maakConversietabelRegel(final Integer gemeenteCode, final Integer partijCode) throws ReflectiveOperationException {
        return new Gemeente(null, new GemeenteCodeAttribuut(gemeenteCode.shortValue()), new Partij(
            null,
            null,
            new PartijCodeAttribuut(partijCode),
            null, null,
            null,
            null, null, null), null, null, null);
    }
}
