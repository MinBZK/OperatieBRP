/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.Collections;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import org.junit.Ignore;
import org.junit.Test;

public class BrpAfnemersindicatiesMapperTest {

    private final BrpAfnemersindicatiesMapper subject = new BrpAfnemersindicatiesMapper();

    @Ignore("BMR44")
    @Test
    public void test() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setAdministratienummer(8887776665L);

        final BrpAfnemersindicaties result =
                subject.mapNaarMigratie(persoon, Collections.singletonList(BrpAfnemersindicatieMapperTest.maak()));

        Assert.assertNotNull(result);
        Assert.assertEquals(Long.valueOf(8887776665L), result.getAdministratienummer());
        Assert.assertEquals(1, result.getAfnemersindicaties().size());
    }
}
