/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link VerblijfsrechtConversietabel}.
 */
public class VerblijfsrechtConversietabelTest {

    @Test
    public void test() throws ReflectiveOperationException {
        final List<Verblijfsrecht> list = new ArrayList<>();
        list.add(maakConversietabelRegel("01"));

        final VerblijfsrechtConversietabel subject = new VerblijfsrechtConversietabel(list);

        Assert.assertEquals("01", subject.converteerNaarLo3(new BrpVerblijfsrechtCode("01")).getWaarde());
    }

    private Verblijfsrecht maakConversietabelRegel(final String waarde) throws ReflectiveOperationException {
        return new Verblijfsrecht(waarde, "Omschrijving");
    }
}
