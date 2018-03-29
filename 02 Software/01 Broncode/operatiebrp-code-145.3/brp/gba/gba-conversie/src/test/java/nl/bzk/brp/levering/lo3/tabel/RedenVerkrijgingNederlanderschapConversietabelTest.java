/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link RedenVerkrijgingNederlanderschapConversietabel}.
 */
public class RedenVerkrijgingNederlanderschapConversietabelTest {

    @Test
    public void test() throws ReflectiveOperationException {
        final List<RedenVerkrijgingNLNationaliteit> list = new ArrayList<>();
        list.add(maakConversietabelRegel("001"));

        final RedenVerkrijgingNederlanderschapConversietabel subject = new RedenVerkrijgingNederlanderschapConversietabel(list);

        Assert.assertEquals("001", subject.converteerNaarLo3(new BrpRedenVerkrijgingNederlandschapCode("001")).getWaarde());
    }

    private RedenVerkrijgingNLNationaliteit maakConversietabelRegel(final String waarde) throws ReflectiveOperationException {
        return new RedenVerkrijgingNLNationaliteit(waarde, "Omschrijving");
    }
}
