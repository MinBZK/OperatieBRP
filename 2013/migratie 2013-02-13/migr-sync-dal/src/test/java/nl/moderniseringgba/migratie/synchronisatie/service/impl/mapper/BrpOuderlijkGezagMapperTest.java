/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BetrokkenheidOuderlijkGezagHistorie;

import org.junit.Test;

public class BrpOuderlijkGezagMapperTest extends BrpAbstractTest {

    @Inject
    private BrpOuderlijkGezagMapper mapper;

    @Test
    public void testMapInhoud() {
        final BetrokkenheidOuderlijkGezagHistorie historie = new BetrokkenheidOuderlijkGezagHistorie();
        historie.setIndicatieOuderHeeftGezag(Boolean.FALSE);

        final BrpOuderlijkGezagInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(Boolean.FALSE, result.getOuderHeeftGezag());
    }
}
