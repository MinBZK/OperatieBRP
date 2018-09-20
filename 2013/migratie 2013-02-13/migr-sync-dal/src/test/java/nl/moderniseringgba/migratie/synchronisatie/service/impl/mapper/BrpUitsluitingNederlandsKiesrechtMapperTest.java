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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonUitsluitingNLKiesrechtHistorie;

import org.junit.Test;

public class BrpUitsluitingNederlandsKiesrechtMapperTest extends BrpAbstractTest {

    @Inject
    private BrpUitsluitingNederlandsKiesrechtMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonUitsluitingNLKiesrechtHistorie historie = new PersoonUitsluitingNLKiesrechtHistorie();
        historie.setIndicatieUitsluitingNLKiesrecht(Boolean.TRUE);
        historie.setDatumEindeUitsluitingNLKiesrecht(new BigDecimal("20110505"));

        final BrpUitsluitingNederlandsKiesrechtInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(Boolean.TRUE, result.getIndicatieUitsluitingNederlandsKiesrecht());
        Assert.assertEquals(new BrpDatum(20110505), result.getDatumEindeUitsluitingNederlandsKiesrecht());
    }
}
