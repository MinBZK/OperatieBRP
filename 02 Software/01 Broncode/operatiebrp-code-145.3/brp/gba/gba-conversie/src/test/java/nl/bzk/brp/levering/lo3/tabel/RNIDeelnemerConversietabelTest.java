/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RNIDeelnemer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link SoortRegisterSoortDocumentConversietabel}.
 */
public class RNIDeelnemerConversietabelTest {

    @Test
    public void test() {
        final List<RNIDeelnemer> list = new ArrayList<>();
        list.add(maakConversietabelRegel("0012", "566444"));

        final RNIDeelnemerConversietabel subject = new RNIDeelnemerConversietabel(list);

        Assert.assertEquals("0012", subject.converteerNaarLo3(new BrpPartijCode("566444")).getWaarde());
    }

    private RNIDeelnemer maakConversietabelRegel(final String lo3, final String partijCode) {
        return new RNIDeelnemer(lo3, new Partij("Naam", partijCode));
    }
}
