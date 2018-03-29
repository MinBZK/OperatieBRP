/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AangifteAdreshouding;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link AangeverRedenWijzigingVerblijfConversietabel}.
 */
public class AangeverRedenWijzigingVerblijfConversietabelTest {

    private static final char X1 = '1';
    private static final char X2 = '2';
    private static final char X3 = '3';

    @Test
    public void test() {
        final List<AangifteAdreshouding> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X1, 'Y', 'Z'));
        list.add(maakConversietabelRegel(X2, 'A', null));
        list.add(maakConversietabelRegel(X3, null, 'B'));

        final AangeverRedenWijzigingVerblijfConversietabel subject = new AangeverRedenWijzigingVerblijfConversietabel(list);

        Assert.assertEquals(
            "1",
            subject.converteerNaarLo3(new AangeverRedenWijzigingVerblijfPaar(new BrpAangeverCode('Y'), new BrpRedenWijzigingVerblijfCode('Z')))
                   .getWaarde());

        Assert.assertEquals("2", subject.converteerNaarLo3(new AangeverRedenWijzigingVerblijfPaar(new BrpAangeverCode('A'), null)).getWaarde());

        Assert.assertEquals(
            "3",
            subject.converteerNaarLo3(new AangeverRedenWijzigingVerblijfPaar(null, new BrpRedenWijzigingVerblijfCode('B'))).getWaarde());
    }

    private AangifteAdreshouding maakConversietabelRegel(final char lo3, final Character brpAangever, final Character brpReden) {
        final Aangever aangeverAdreshouding;
        if (brpAangever == null) {
            aangeverAdreshouding = null;
        } else {
            aangeverAdreshouding = new Aangever(brpAangever, "Naam", "Omschrijving");
        }

        final RedenWijzigingVerblijf redenWijzigingAdres;
        if (brpReden == null) {
            redenWijzigingAdres = null;
        } else {
            redenWijzigingAdres = new RedenWijzigingVerblijf(brpReden, "Naam");
        }

        final AangifteAdreshouding result = new AangifteAdreshouding(lo3);
        result.setAangever(aangeverAdreshouding);
        result.setRedenWijzigingVerblijf(redenWijzigingAdres);

        return result;
    }
}
