/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.admhndpublicatie;

import com.google.common.collect.Sets;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class HandelingVoorPublicatieTest {

    private static final Long ADM_HND_ID = 1L;
    private static final Set<Long> bijgehoudenPersonen = Sets.newHashSet(1L, 2L, 3L);

    @Test
    public void test() {
        final HandelingVoorPublicatie handelingVoorPublicatie = maakHandelingVoorPublicatie();
        Assert.assertEquals(ADM_HND_ID, handelingVoorPublicatie.getAdmhndId());
        Assert.assertEquals(bijgehoudenPersonen, handelingVoorPublicatie.getBijgehoudenPersonen());
    }

    private HandelingVoorPublicatie maakHandelingVoorPublicatie() {
        final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
        handelingVoorPublicatie.setAdmhndId(ADM_HND_ID);
        handelingVoorPublicatie.setBijgehoudenPersonen(bijgehoudenPersonen);
        return handelingVoorPublicatie;
    }
}