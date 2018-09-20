/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BetrokkenheidOuderHistorie;

import org.junit.Test;

public class BrpOuderMapperTest extends BrpAbstractTest {

    @Inject
    private BrpOuderMapper mapper;

    @Test
    public void testMapInhoud() {
        final BetrokkenheidOuderHistorie historie = new BetrokkenheidOuderHistorie();
        historie.setIndicatieOuder(Boolean.FALSE);

        final BrpOuderInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(Boolean.FALSE, result.getHeeftIndicatie());
        Assert.assertNull(result.getDatumAanvang());
    }
}
