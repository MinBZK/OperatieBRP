/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingInhoudingVermissingReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import org.junit.Assert;
import org.junit.Test;
import support.ReflectionUtils;

/**
 * Test voor {@link AanduidingInhoudingVermissingReisdocumentConversietabel}.
 */
public class AanduidingInhoudingVermissingReisdocumentConversietabelTest {

    private static final String X = "X";
    private static final String Z = "Z";

    @Test
    public void test() throws ReflectiveOperationException {
        final List<ConversieAanduidingInhoudingVermissingReisdocument> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X, Z));

        final AanduidingInhoudingVermissingReisdocumentConversietabel subject = new AanduidingInhoudingVermissingReisdocumentConversietabel(list);

        Assert.assertEquals(X, subject.converteerNaarLo3(new BrpAanduidingInhoudingOfVermissingReisdocumentCode('Z')).getWaarde());
    }

    private ConversieAanduidingInhoudingVermissingReisdocument maakConversietabelRegel(final String lo3, final String brp)
        throws ReflectiveOperationException
    {
        return ReflectionUtils.instantiate(
            ConversieAanduidingInhoudingVermissingReisdocument.class,
            new LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut(lo3),
            new AanduidingInhoudingVermissingReisdocument(new AanduidingInhoudingVermissingReisdocumentCodeAttribuut(brp), null));
    }
}
