/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3OmschrijvingRedenOpschortingBijhoudingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRedenOpschorting;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import org.junit.Assert;
import org.junit.Test;
import support.ReflectionUtils;

/**
 * Test voor {@link RedenOpschortingConversietabel}.
 */
public class RedenOpschortingConversietabelTest {

    private static final String X = "X";

    @Test
    public void test() throws ReflectiveOperationException {
        final List<ConversieRedenOpschorting> list = new ArrayList<>();

        list.add(maakConversietabelRegel(X, NadereBijhoudingsaard.OVERLEDEN));

        final RedenOpschortingConversietabel subject = new RedenOpschortingConversietabel(list);

        Assert.assertEquals(X, subject.converteerNaarLo3(new BrpNadereBijhoudingsaardCode(NadereBijhoudingsaard.OVERLEDEN.getCode())).getWaarde());
    }

    private ConversieRedenOpschorting maakConversietabelRegel(final String lo3, final NadereBijhoudingsaard brp) throws ReflectiveOperationException {
        return ReflectionUtils.instantiate(ConversieRedenOpschorting.class, new LO3OmschrijvingRedenOpschortingBijhoudingAttribuut(lo3), brp);
    }
}
