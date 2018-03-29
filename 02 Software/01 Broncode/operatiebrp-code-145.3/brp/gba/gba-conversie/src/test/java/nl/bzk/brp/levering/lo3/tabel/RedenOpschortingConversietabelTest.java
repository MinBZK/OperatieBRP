/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpschorting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link RedenOpschortingConversietabel}.
 */
public class RedenOpschortingConversietabelTest {

    @Test
    public void test() {
        final List<RedenOpschorting> list = new ArrayList<>();

        list.add(maakConversietabelRegel('X', NadereBijhoudingsaard.OVERLEDEN));

        final RedenOpschortingConversietabel subject = new RedenOpschortingConversietabel(list);

        Assert.assertEquals("X", subject.converteerNaarLo3(new BrpNadereBijhoudingsaardCode(NadereBijhoudingsaard.OVERLEDEN.getCode())).getWaarde());
    }

    private RedenOpschorting maakConversietabelRegel(final char lo3, final NadereBijhoudingsaard brp) {
        return new RedenOpschorting(lo3, brp);
    }
}
