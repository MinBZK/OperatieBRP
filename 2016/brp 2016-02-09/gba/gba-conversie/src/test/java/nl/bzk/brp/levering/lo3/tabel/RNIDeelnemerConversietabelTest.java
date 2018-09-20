/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RNIDeelnemerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRNIDeelnemer;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijAttribuut;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import org.junit.Assert;
import org.junit.Test;
import support.ReflectionUtils;

/**
 * Test voor {@link SoortRegisterSoortDocumentConversietabel}.
 */
public class RNIDeelnemerConversietabelTest {

    @Test
    public void test() throws ReflectiveOperationException {
        final List<ConversieRNIDeelnemer> list = new ArrayList<>();
        list.add(maakConversietabelRegel((short) 12, 5664445));

        final RNIDeelnemerConversietabel subject = new RNIDeelnemerConversietabel(list);

        Assert.assertEquals("0012", subject.converteerNaarLo3(new BrpPartijCode(5664445)).getWaarde());
    }

    private ConversieRNIDeelnemer maakConversietabelRegel(final Short lo3, final Integer brp) throws ReflectiveOperationException {
        return ReflectionUtils.instantiate(ConversieRNIDeelnemer.class, new LO3RNIDeelnemerAttribuut(lo3), new Partij(new NaamEnumeratiewaardeAttribuut(
            "partij" + brp), new SoortPartijAttribuut(SoortPartij.GEMEENTE), new PartijCodeAttribuut(brp), null, null, null, null, null, null));
    }
}
