/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerantwoordelijkeCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsverantwoordelijkheidInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonBijhoudingsverantwoordelijkheidHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verantwoordelijke;

import org.junit.Test;

public class BrpBijhoudingsverantwoordelijkheidMapperTest extends BrpAbstractTest {

    @Inject
    private BrpBijhoudingsverantwoordelijkheidMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonBijhoudingsverantwoordelijkheidHistorie historie =
                new PersoonBijhoudingsverantwoordelijkheidHistorie();
        historie.setVerantwoordelijke(Verantwoordelijke.C);

        final BrpBijhoudingsverantwoordelijkheidInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(BrpVerantwoordelijkeCode.COLLEGE_B_EN_W, result.getVerantwoordelijkeCode());
        Assert.assertNull(result.getDatumBijhoudingsverantwoordelijkheid());
    }
}
