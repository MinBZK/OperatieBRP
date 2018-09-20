/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonPersoonskaartHistorie;

import org.junit.Test;

public class BrpPersoonskaartMapperTest extends BrpAbstractTest {

    @Inject
    private BrpPersoonskaartMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonPersoonskaartHistorie historie = new PersoonPersoonskaartHistorie();
        historie.setPartij(new Partij());
        historie.getPartij().setGemeentecode(new BigDecimal("0512"));
        historie.setIndicatiePersoonskaartVolledigGeconverteerd(Boolean.FALSE);

        final BrpPersoonskaartInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0512")), result.getGemeentePKCode());
        Assert.assertEquals(Boolean.FALSE, result.getIndicatiePKVolledigGeconverteerd());
    }
}
