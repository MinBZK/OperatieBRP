/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3NederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SoortNederlandsReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieSoortNLReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import org.junit.Assert;
import org.junit.Test;
import support.ReflectionUtils;

/**
 * Test voor {@link SoortNlReisdocumentConversietabel}.
 */
public class SoortNlReisdocumentConversietabelTest {

    private static final String X = "X";
    private static final String Z = "Z";

    @Test
    public void test() throws ReflectiveOperationException {
        final List<ConversieSoortNLReisdocument> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X, Z));

        final SoortNlReisdocumentConversietabel subject = new SoortNlReisdocumentConversietabel(list);

        Assert.assertEquals(X, subject.converteerNaarLo3(new BrpSoortNederlandsReisdocumentCode(Z)).getWaarde());
    }

    private ConversieSoortNLReisdocument maakConversietabelRegel(final String lo3, final String brp) throws ReflectiveOperationException {
        return ReflectionUtils.instantiate(
            ConversieSoortNLReisdocument.class,
            new LO3NederlandsReisdocumentAttribuut(lo3),
            new SoortNederlandsReisdocument(new SoortNederlandsReisdocumentCodeAttribuut(brp), null, null, null));
    }
}
