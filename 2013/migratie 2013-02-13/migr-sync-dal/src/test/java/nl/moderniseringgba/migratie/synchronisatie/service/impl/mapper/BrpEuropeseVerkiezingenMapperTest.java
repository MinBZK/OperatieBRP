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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonEUVerkiezingenHistorie;

import org.junit.Test;

public class BrpEuropeseVerkiezingenMapperTest extends BrpAbstractTest {

    @Inject
    private BrpEuropeseVerkiezingenMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonEUVerkiezingenHistorie historie = new PersoonEUVerkiezingenHistorie();
        historie.setIndicatieDeelnameEUVerkiezingen(Boolean.FALSE);
        historie.setDatumAanleidingAanpassingDeelnameEUVerkiezing(new BigDecimal("18801212"));
        historie.setDatumEindeUitsluitingEUKiesrecht(new BigDecimal("20680101"));
        final BrpEuropeseVerkiezingenInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(Boolean.FALSE, result.getDeelnameEuropeseVerkiezingen());
        Assert.assertEquals(new BrpDatum(18801212), result.getDatumAanleidingAanpassingDeelnameEuropeseVerkiezingen());
        Assert.assertEquals(new BrpDatum(20680101), result.getDatumEindeUitsluitingEuropeesKiesrecht());
    }
}
