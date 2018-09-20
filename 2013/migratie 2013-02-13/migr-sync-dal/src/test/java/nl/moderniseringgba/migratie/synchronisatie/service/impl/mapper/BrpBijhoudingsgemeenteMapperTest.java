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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonBijhoudingsgemeenteHistorie;

import org.junit.Test;

public class BrpBijhoudingsgemeenteMapperTest extends BrpAbstractTest {

    @Inject
    private BrpBijhoudingsgemeenteMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonBijhoudingsgemeenteHistorie historie = new PersoonBijhoudingsgemeenteHistorie();
        historie.setPartij(new Partij());
        historie.getPartij().setGemeentecode(new BigDecimal("0518"));
        historie.setDatumInschrijvingInGemeente(new BigDecimal("19341203"));
        historie.setIndicatieOnverwerktDocumentAanwezig(Boolean.FALSE);

        final BrpBijhoudingsgemeenteInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0518")), result.getBijhoudingsgemeenteCode());
        Assert.assertEquals(new BrpDatum(19341203), result.getDatumInschrijvingInGemeente());
        Assert.assertEquals(Boolean.FALSE, result.getOnverwerktDocumentAanwezig());
    }
}
