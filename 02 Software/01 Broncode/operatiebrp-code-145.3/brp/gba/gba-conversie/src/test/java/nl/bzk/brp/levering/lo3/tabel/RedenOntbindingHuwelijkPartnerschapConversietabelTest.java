/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOntbindingHuwelijkPartnerschap;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link RedenOntbindingHuwelijkPartnerschapConversietabel}.
 */
public class RedenOntbindingHuwelijkPartnerschapConversietabelTest {

    @Test
    public void test() {
        final List<RedenOntbindingHuwelijkPartnerschap> list = new ArrayList<>();
        list.add(maakConversietabelRegel('X', 'Z'));

        final RedenOntbindingHuwelijkPartnerschapConversietabel subject = new RedenOntbindingHuwelijkPartnerschapConversietabel(list);

        Assert.assertEquals("X", subject.converteerNaarLo3(new BrpRedenEindeRelatieCode('Z')).getWaarde());
    }

    private RedenOntbindingHuwelijkPartnerschap maakConversietabelRegel(final char lo3, final char brp) {
        return new RedenOntbindingHuwelijkPartnerschap(lo3, new RedenBeeindigingRelatie(brp, "Omschrijving"));
    }
}
