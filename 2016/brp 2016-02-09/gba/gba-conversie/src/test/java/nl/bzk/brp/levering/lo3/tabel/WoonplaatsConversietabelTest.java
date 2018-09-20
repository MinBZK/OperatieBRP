/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link WoonplaatsConversietabel}.
 */
public class WoonplaatsConversietabelTest {

    private static final String X = "X";

    @Test
    public void test() throws ReflectiveOperationException {
        final List<Plaats> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X));

        final WoonplaatsConversietabel subject = new WoonplaatsConversietabel(list);

        Assert.assertEquals(X, subject.converteerNaarLo3(X));
    }

    private Plaats maakConversietabelRegel(final String woonplaatsnaam) throws ReflectiveOperationException {
        return new Plaats(null, new NaamEnumeratiewaardeAttribuut(woonplaatsnaam), null, null);
    }
}
