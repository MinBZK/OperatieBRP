/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
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
        list.add(maakConversietabelRegel("0517", "517010"));

        final PartijConversietabel subject = new PartijConversietabel(list);

        Assert.assertEquals("0517", subject.converteerNaarLo3(new BrpPartijCode("517010")).getWaarde());
    }

    private Gemeente maakConversietabelRegel(final String gemeenteCode, final String partijCode) throws ReflectiveOperationException {
        return new Gemeente(Short.valueOf(gemeenteCode), "Naam", gemeenteCode, new Partij(partijCode.toString(), partijCode));
    }
}
