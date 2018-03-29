/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link RedenVerliesNederlanderschapConversietabel}.
 */
public class RedenVerliesNederlanderschapConversietabelTest {

    @Test
    public void test() throws ReflectiveOperationException {
        final List<RedenVerliesNLNationaliteit> list = new ArrayList<>();
        list.add(maakConversietabelRegel("001"));

        final RedenVerliesNederlanderschapConversietabel subject = new RedenVerliesNederlanderschapConversietabel(list);

        Assert.assertEquals("001", subject.converteerNaarLo3(new BrpRedenVerliesNederlandschapCode("001")).getWaarde());
    }

    private RedenVerliesNLNationaliteit maakConversietabelRegel(final String waarde) throws ReflectiveOperationException {
        return new RedenVerliesNLNationaliteit(waarde, "Omschrijving");
    }
}
