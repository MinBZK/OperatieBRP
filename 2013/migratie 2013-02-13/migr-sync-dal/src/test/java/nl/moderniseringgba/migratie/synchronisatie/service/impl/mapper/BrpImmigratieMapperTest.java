/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpImmigratieInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Land;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonImmigratieHistorie;

import org.junit.Test;

public class BrpImmigratieMapperTest extends BrpAbstractTest {

    @Inject
    private BrpImmigratieMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonImmigratieHistorie historie = new PersoonImmigratieHistorie();
        historie.setLandVanWaarGevestigd(new Land());
        historie.getLandVanWaarGevestigd().setLandcode(new BigDecimal("4022"));
        historie.setDatumVestigingInNederland(new BigDecimal("20120630"));

        final BrpImmigratieInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpLandCode(Integer.valueOf("4022")), result.getLandVanwaarIngeschreven());
        Assert.assertEquals(new BrpDatum(20120630), result.getDatumVestigingInNederland());
    }
}
