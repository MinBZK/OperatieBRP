/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAangifteAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Aangever;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import org.junit.Assert;
import org.junit.Test;
import support.ReflectionUtils;

/**
 * Test voor {@link AangeverRedenWijzigingVerblijfConversietabel}.
 */
public class AangeverRedenWijzigingVerblijfConversietabelTest {

    private static final String X1 = "X1";
    private static final String X2 = "X2";
    private static final String X3 = "X3";

    @Test
    public void test() throws ReflectiveOperationException {
        final List<ConversieAangifteAdreshouding> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X1, "Y", "Z"));
        list.add(maakConversietabelRegel(X2, "A", null));
        list.add(maakConversietabelRegel(X3, null, "B"));

        final AangeverRedenWijzigingVerblijfConversietabel subject = new AangeverRedenWijzigingVerblijfConversietabel(list);

        Assert.assertEquals(
            X1,
            subject.converteerNaarLo3(new AangeverRedenWijzigingVerblijfPaar(new BrpAangeverCode('Y'), new BrpRedenWijzigingVerblijfCode('Z')))
                   .getWaarde());

        Assert.assertEquals(X2, subject.converteerNaarLo3(new AangeverRedenWijzigingVerblijfPaar(new BrpAangeverCode('A'), null)).getWaarde());

        Assert.assertEquals(X3, subject.converteerNaarLo3(new AangeverRedenWijzigingVerblijfPaar(null, new BrpRedenWijzigingVerblijfCode('B')))
                                         .getWaarde());
    }

    private ConversieAangifteAdreshouding maakConversietabelRegel(final String lo3, final String brpAangever, final String brpReden)
        throws ReflectiveOperationException
    {
        final Aangever aangeverAdreshouding;
        if (brpAangever == null) {
            aangeverAdreshouding = null;
        } else {
            aangeverAdreshouding = ReflectionUtils.instantiate(Aangever.class, new AangeverCodeAttribuut(brpAangever), null, null);
        }

        final RedenWijzigingVerblijf redenWijzigingAdres;
        if (brpReden == null) {
            redenWijzigingAdres = null;
        } else {
            redenWijzigingAdres = new RedenWijzigingVerblijf(new RedenWijzigingVerblijfCodeAttribuut(brpReden), null);
        }

        return ReflectionUtils.instantiate(
            ConversieAangifteAdreshouding.class,
            new LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut(lo3),
            aangeverAdreshouding,
            redenWijzigingAdres);
    }
}
