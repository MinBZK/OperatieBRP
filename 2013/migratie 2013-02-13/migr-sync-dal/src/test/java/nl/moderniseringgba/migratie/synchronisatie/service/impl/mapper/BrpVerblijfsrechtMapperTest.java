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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVerblijfsrechtHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verblijfsrecht;

import org.junit.Test;

public class BrpVerblijfsrechtMapperTest extends BrpAbstractTest {

    @Inject
    private BrpVerblijfsrechtMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonVerblijfsrechtHistorie historie = new PersoonVerblijfsrechtHistorie();
        historie.setVerblijfsrecht(new Verblijfsrecht());
        historie.getVerblijfsrecht().setOmschrijving("Wet-123ca");
        historie.setDatumAanvangVerblijfsrecht(new BigDecimal("20000202"));
        historie.setDatumVoorzienEindeVerblijfsrecht(new BigDecimal("20140202"));
        historie.setDatumAanvangAaneensluitendVerblijfsrecht(new BigDecimal("19740607"));

        final BrpVerblijfsrechtInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpVerblijfsrechtCode("Wet-123ca"), result.getVerblijfsrechtCode());
        Assert.assertEquals(new BrpDatum(20000202), result.getAanvangVerblijfsrecht());
        Assert.assertEquals(new BrpDatum(20140202), result.getVoorzienEindeVerblijfsrecht());
        Assert.assertEquals(new BrpDatum(19740607), result.getAanvangAaneensluitendVerblijfsrecht());
    }
}
