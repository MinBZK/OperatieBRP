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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Land;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeboorteHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Plaats;

import org.junit.Test;

public class BrpGeboorteMapperTest extends BrpAbstractTest {

    @Inject
    private BrpGeboorteMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonGeboorteHistorie historie = new PersoonGeboorteHistorie();
        historie.setDatumGeboorte(new BigDecimal("14322402"));
        historie.setPartij(new Partij());
        historie.getPartij().setGemeentecode(new BigDecimal("0518"));
        historie.setPlaats(new Plaats());
        historie.getPlaats().setNaam("Harlingen");
        historie.setBuitenlandseGeboorteplaats("Kuna kuna");
        historie.setBuitenlandseRegioGeboorte("West van de Onderwindse eilanden");
        historie.setLand(new Land());
        historie.getLand().setLandcode(new BigDecimal("0023"));
        historie.setOmschrijvingGeboortelocatie("Op de boot");

        final BrpGeboorteInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpDatum(14322402), result.getGeboortedatum());
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0518")), result.getGemeenteCode());
        Assert.assertEquals(new BrpPlaatsCode("Harlingen"), result.getPlaatsCode());
        Assert.assertEquals("Kuna kuna", result.getBuitenlandseGeboorteplaats());
        Assert.assertEquals("West van de Onderwindse eilanden", result.getBuitenlandseRegioGeboorte());
        Assert.assertEquals(new BrpLandCode(Integer.valueOf("0023")), result.getLandCode());
        Assert.assertEquals("Op de boot", result.getOmschrijvingGeboortelocatie());

    }
}
