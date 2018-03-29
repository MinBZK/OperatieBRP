/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link AanduidingInhoudingVermissingReisdocumentConversietabel}.
 */
public class AanduidingInhoudingVermissingReisdocumentConversietabelTest {

    private static final char X = 'X';
    private static final char Z = 'Z';

    @Test
    public void test() {
        final List<AanduidingInhoudingVermissingReisdocument> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X, Z));

        final AanduidingInhoudingVermissingReisdocumentConversietabel subject = new AanduidingInhoudingVermissingReisdocumentConversietabel(list);

        Assert.assertEquals("X", subject.converteerNaarLo3(new BrpAanduidingInhoudingOfVermissingReisdocumentCode('Z')).getWaarde());
    }

    private AanduidingInhoudingVermissingReisdocument maakConversietabelRegel(final char lo3, final char brp) {
        return new AanduidingInhoudingVermissingReisdocument(lo3, new AanduidingInhoudingOfVermissingReisdocument(brp, "Naam"));
    }
}
