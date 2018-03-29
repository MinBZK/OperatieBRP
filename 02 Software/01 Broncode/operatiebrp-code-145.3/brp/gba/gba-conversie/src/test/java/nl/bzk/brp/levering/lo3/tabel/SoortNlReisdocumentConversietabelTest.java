/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNlReisdocument;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link SoortNlReisdocumentConversietabel}.
 */
public class SoortNlReisdocumentConversietabelTest {

    private static final String X = "X";
    private static final String Z = "Z";

    @Test
    public void test() {
        final List<SoortNlReisdocument> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X, Z));

        final SoortNlReisdocumentConversietabel subject = new SoortNlReisdocumentConversietabel(list);

        Assert.assertEquals(X, subject.converteerNaarLo3(new BrpSoortNederlandsReisdocumentCode(Z)).getWaarde());
    }

    private SoortNlReisdocument maakConversietabelRegel(final String lo3, final String brp) {
        return new SoortNlReisdocument(lo3, new SoortNederlandsReisdocument(brp, brp));
    }
}
