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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Land;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Plaats;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RelatieHistorie;

import org.junit.Test;

public class BrpRelatieMapperTest extends BrpAbstractTest {

    @Inject
    private BrpRelatieMapper.BrpRelatieInhoudMapper mapper;

    @Test
    public void testMapInhoud() {
        final RelatieHistorie historie = new RelatieHistorie();
        historie.setDatumAanvang(new BigDecimal("20120101"));
        historie.setGemeenteAanvang(new Partij());
        historie.getGemeenteAanvang().setGemeentecode(new BigDecimal("0244"));
        historie.setWoonplaatsAanvang(new Plaats());
        historie.getWoonplaatsAanvang().setNaam("Leiden");
        historie.setBuitenlandsePlaatsAanvang("Liverpool");
        historie.setBuitenlandseRegioAanvang("Het Noorden");
        historie.setLandAanvang(new Land());
        historie.getLandAanvang().setLandcode(new BigDecimal("6030"));
        historie.setOmschrijvingLocatieAanvang("Net buiten het stadion");
        historie.setRedenBeeindigingRelatie(new RedenBeeindigingRelatie());
        historie.getRedenBeeindigingRelatie().setCode("S");
        historie.setDatumEinde(new BigDecimal("20150304"));
        historie.setGemeenteEinde(new Partij());
        historie.getGemeenteEinde().setGemeentecode(new BigDecimal("0377"));
        historie.setWoonplaatsEinde(new Plaats());
        historie.getWoonplaatsEinde().setNaam("'s-Gravendeel");
        historie.setBuitenlandsePlaatsEinde("Bainsbury");
        historie.setBuitenlandseRegioEinde("Zuidelijk");
        historie.setLandEinde(new Land());
        historie.getLandEinde().setLandcode(new BigDecimal("2033"));
        historie.setOmschrijvingLocatieEinde("Bij het gemeentehuis");

        final BrpRelatieInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpDatum(20120101), result.getDatumAanvang());
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0244")), result.getGemeenteCodeAanvang());
        Assert.assertEquals(new BrpPlaatsCode("Leiden"), result.getPlaatsCodeAanvang());
        Assert.assertEquals("Liverpool", result.getBuitenlandsePlaatsAanvang());
        Assert.assertEquals("Het Noorden", result.getBuitenlandseRegioAanvang());
        Assert.assertEquals(new BrpLandCode(Integer.valueOf("6030")), result.getLandCodeAanvang());
        Assert.assertEquals("Net buiten het stadion", result.getOmschrijvingLocatieAanvang());
        Assert.assertEquals(new BrpRedenEindeRelatieCode("S"), result.getRedenEinde());
        Assert.assertEquals(new BrpDatum(20150304), result.getDatumEinde());
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0377")), result.getGemeenteCodeEinde());
        Assert.assertEquals(new BrpPlaatsCode("'s-Gravendeel"), result.getPlaatsCodeEinde());
        Assert.assertEquals("Bainsbury", result.getBuitenlandsePlaatsEinde());
        Assert.assertEquals("Zuidelijk", result.getBuitenlandseRegioEinde());
        Assert.assertEquals(new BrpLandCode(Integer.valueOf("2033")), result.getLandCodeEinde());
        Assert.assertEquals("Bij het gemeentehuis", result.getOmschrijvingLocatieEinde());
    }
}
