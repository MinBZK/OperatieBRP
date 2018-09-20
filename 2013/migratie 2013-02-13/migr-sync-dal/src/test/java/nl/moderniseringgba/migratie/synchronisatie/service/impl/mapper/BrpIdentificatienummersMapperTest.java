/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIDHistorie;

import org.junit.Test;

public class BrpIdentificatienummersMapperTest extends BrpAbstractTest {

    @Inject
    private BrpIdentificatienummersMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonIDHistorie historie = new PersoonIDHistorie();
        historie.setAdministratienummer(new BigDecimal("1234567890"));
        historie.setBurgerservicenummer(new BigDecimal("987654321"));

        final BrpIdentificatienummersInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(Long.valueOf(1234567890L), result.getAdministratienummer());
        Assert.assertEquals(Long.valueOf(987654321L), result.getBurgerservicenummer());
    }
}
