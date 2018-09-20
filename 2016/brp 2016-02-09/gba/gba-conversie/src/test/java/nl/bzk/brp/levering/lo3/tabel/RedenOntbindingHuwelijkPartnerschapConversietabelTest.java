/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import org.junit.Assert;
import org.junit.Test;
import support.ReflectionUtils;

/**
 * Test voor {@link RedenOntbindingHuwelijkPartnerschapConversietabel}.
 */
public class RedenOntbindingHuwelijkPartnerschapConversietabelTest {

    private static final String X = "X";

    @Test
    public void test() throws ReflectiveOperationException {
        final List<ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X, "Z"));

        final RedenOntbindingHuwelijkPartnerschapConversietabel subject = new RedenOntbindingHuwelijkPartnerschapConversietabel(list);

        Assert.assertEquals(X, subject.converteerNaarLo3(new BrpRedenEindeRelatieCode('Z')).getWaarde());
    }

    private ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap maakConversietabelRegel(final String lo3, final String brp)
        throws ReflectiveOperationException
    {
        return ReflectionUtils.instantiate(
            ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap.class,
            new LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut(lo3),
            ReflectionUtils.instantiate(RedenEindeRelatie.class, new RedenEindeRelatieCodeAttribuut(brp), null));
    }
}
