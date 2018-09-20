/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieVoorvoegsel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import org.junit.Assert;
import org.junit.Test;
import support.ReflectionUtils;

/**
 * Test voor {@link VoorvoegselConversietabel}.
 */
public class VoorvoegselConversietabelTest {

    private static final String X1 = "X1";
    private static final String X2 = "X2";
    private static final String X3 = "X3";
    private static final String A = "A";
    private static final String B = "B";
    private static final String Y = "Y";
    private static final String Z = "Z";

    @Test
    public void test() throws ReflectiveOperationException {
        final List<ConversieVoorvoegsel> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X1, Y, Z));
        list.add(maakConversietabelRegel(X2, A, null));
        list.add(maakConversietabelRegel(X3, null, B));

        final VoorvoegselConversietabel subject = new VoorvoegselConversietabel(list);

        Assert.assertEquals(X1, subject.converteerNaarLo3(new VoorvoegselScheidingstekenPaar(new BrpString(Y, null), new BrpCharacter('Z', null)))
                                         .getWaarde());
        Assert.assertEquals(X2, subject.converteerNaarLo3(new VoorvoegselScheidingstekenPaar(new BrpString(A, null), null)).getWaarde());
        Assert.assertEquals(X3, subject.converteerNaarLo3(new VoorvoegselScheidingstekenPaar(null, new BrpCharacter('B', null))).getWaarde());
    }

    private ConversieVoorvoegsel maakConversietabelRegel(final String lo3, final String brpVoorvoegsel, final String brpScheidingsteken)
        throws ReflectiveOperationException
    {
        final NaamEnumeratiewaardeAttribuut naamEnumeratiewaardeAttribuut;
        if (brpVoorvoegsel == null) {
            naamEnumeratiewaardeAttribuut = null;
        } else {
            naamEnumeratiewaardeAttribuut = new NaamEnumeratiewaardeAttribuut(brpVoorvoegsel);
        }

        final ScheidingstekenAttribuut scheidingstekenAttribuut;
        if (brpScheidingsteken == null) {
            scheidingstekenAttribuut = null;
        } else {
            scheidingstekenAttribuut = new ScheidingstekenAttribuut(brpScheidingsteken);
        }

        return ReflectionUtils.instantiate(
            ConversieVoorvoegsel.class,
            new LO3VoorvoegselAttribuut(lo3),
            naamEnumeratiewaardeAttribuut,
            scheidingstekenAttribuut);
    }
}
