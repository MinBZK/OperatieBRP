/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselConversie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link VoorvoegselConversietabel}.
 */
public class VoorvoegselConversietabelTest {

    private static final String X1 = "X1";
    private static final String Y = "Y";
    private static final char Z = 'Z';

    @Test
    public void test() throws ReflectiveOperationException {
        final List<VoorvoegselConversie> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X1, Y, Z));

        final VoorvoegselConversietabel subject = new VoorvoegselConversietabel(list);

        Assert.assertEquals(
            X1,
            subject.converteerNaarLo3(new VoorvoegselScheidingstekenPaar(new BrpString(Y, null), new BrpCharacter('Z', null))).getWaarde());
    }

    private VoorvoegselConversie maakConversietabelRegel(final String lo3, final String brpVoorvoegsel, final char brpScheidingsteken) {
        return new VoorvoegselConversie(lo3, brpVoorvoegsel, brpScheidingsteken);
    }
}
