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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Land;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonOverlijdenHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Plaats;

import org.junit.Test;

public class BrpOverlijdenMapperTest extends BrpAbstractTest {

    @Inject
    private BrpOverlijdenMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonOverlijdenHistorie historie = new PersoonOverlijdenHistorie();
        historie.setDatumOverlijden(new BigDecimal("19880306"));
        historie.setPartij(new Partij());
        historie.getPartij().setGemeentecode(new BigDecimal("0517"));
        historie.setPlaats(new Plaats());
        historie.getPlaats().setNaam("Leiderdorp");
        historie.setBuitenlandsePlaatsOverlijden("Net over de grens");
        historie.setBuitenlandseRegioOverlijden("Wallonie");
        historie.setLand(new Land());
        historie.getLand().setLandcode(new BigDecimal("0032"));
        historie.setOmschrijvingLocatieOverlijden("Omschrijving");

        final BrpOverlijdenInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpDatum(19880306), result.getDatum());
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0517")), result.getGemeenteCode());
        Assert.assertEquals(new BrpPlaatsCode("Leiderdorp"), result.getPlaatsCode());
        Assert.assertEquals("Net over de grens", result.getBuitenlandsePlaats());
        Assert.assertEquals("Wallonie", result.getBuitenlandseRegio());
        Assert.assertEquals(new BrpLandCode(Integer.valueOf("0032")), result.getLandCode());
        Assert.assertEquals("Omschrijving", result.getOmschrijvingLocatie());
    }
}
