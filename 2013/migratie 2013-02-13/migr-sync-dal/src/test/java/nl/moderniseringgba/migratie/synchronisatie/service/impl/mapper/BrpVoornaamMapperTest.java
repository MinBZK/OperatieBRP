/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVoornaam;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVoornaamHistorie;

import org.junit.Test;

public class BrpVoornaamMapperTest extends BrpAbstractTest {

    @Inject
    private BrpVoornaamMapper.BrpVoornaamInhoudMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonVoornaamHistorie historie = new PersoonVoornaamHistorie();
        historie.setNaam("Piet");
        historie.setPersoonVoornaam(new PersoonVoornaam());
        historie.getPersoonVoornaam().setVolgnummer(32);

        final BrpVoornaamInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals("Piet", result.getVoornaam());
        Assert.assertEquals(32, result.getVolgnummer());
    }
}
