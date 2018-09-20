/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIndicatieHistorie;

import org.junit.Test;

public class BrpOnderCurateleIndicatieMapperTest extends BrpAbstractTest {

    @Inject
    private BrpOnderCurateleIndicatieMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonIndicatieHistorie historie = new PersoonIndicatieHistorie();
        historie.setWaarde(Boolean.TRUE);

        final BrpOnderCurateleIndicatieInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(Boolean.TRUE, result.getHeeftIndicatie());
    }
}
