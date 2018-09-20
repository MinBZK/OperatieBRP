/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AangeverAdreshouding;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.FunctieAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Land;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAdresHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Plaats;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenWijzigingAdres;

import org.junit.Test;

public class BrpAdresMapperTest extends BrpAbstractTest {

    @Inject
    private BrpAdresMapper.BrpAdresInhoudMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonAdresHistorie historie = new PersoonAdresHistorie();
        historie.setFunctieAdres(FunctieAdres.WOONADRES);
        historie.setRedenWijzigingAdres(new RedenWijzigingAdres());
        historie.getRedenWijzigingAdres().setCode("RWA");
        historie.setAangeverAdreshouding(new AangeverAdreshouding());
        historie.getAangeverAdreshouding().setCode("AANGEVER");
        historie.setDatumAanvangAdreshouding(new BigDecimal("19020304"));
        historie.setAdresseerbaarObject("Object-adres");
        historie.setIdentificatiecodeNummeraanduiding("Nummer-ident");
        historie.setPartij(new Partij());
        historie.getPartij().setGemeentecode(new BigDecimal("0599"));
        historie.setNaamOpenbareRuimte("Openbare-Ruimte");
        historie.setAfgekorteNaamOpenbareRuimte("Op-Rmte");
        historie.setGemeentedeel("Links");
        historie.setHuisnummer(new BigDecimal("55"));
        historie.setHuisletter("A");
        historie.setHuisnummertoevoeging("2e verdieping");
        historie.setPostcode("1234RE");
        historie.setPlaats(new Plaats());
        historie.getPlaats().setNaam("Alkmaar");
        historie.setLocatietovAdres("TO");
        historie.setLocatieOmschrijving("Links achter het plein");
        historie.setBuitenlandsAdresRegel1("Buitenland-1");
        historie.setBuitenlandsAdresRegel2("Buitenland-2");
        historie.setBuitenlandsAdresRegel3("Buitenland-3");
        historie.setBuitenlandsAdresRegel4("Buitenland-4");
        historie.setBuitenlandsAdresRegel5("Buitenland-5");
        historie.setBuitenlandsAdresRegel6("Buitenland-6");
        historie.setLand(new Land());
        historie.getLand().setLandcode(new BigDecimal("0344"));
        historie.setDatumVertrekUitNederland(new BigDecimal("19502103"));

        final BrpAdresInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(BrpFunctieAdresCode.W, result.getFunctieAdresCode());
        Assert.assertEquals(new BrpRedenWijzigingAdresCode("RWA"), result.getRedenWijzigingAdresCode());
        Assert.assertEquals(new BrpAangeverAdreshoudingCode("AANGEVER"), result.getAangeverAdreshoudingCode());
        Assert.assertEquals(new BrpDatum(19020304), result.getDatumAanvangAdreshouding());
        Assert.assertEquals("Object-adres", result.getAdresseerbaarObject());
        Assert.assertEquals("Nummer-ident", result.getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0599")), result.getGemeenteCode());
        Assert.assertEquals("Openbare-Ruimte", result.getNaamOpenbareRuimte());
        Assert.assertEquals("Op-Rmte", result.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("Links", result.getGemeentedeel());
        Assert.assertEquals(Integer.valueOf(55), result.getHuisnummer());
        Assert.assertEquals(Character.valueOf('A'), result.getHuisletter());
        Assert.assertEquals("2e verdieping", result.getHuisnummertoevoeging());
        Assert.assertEquals("1234RE", result.getPostcode());
        Assert.assertEquals(new BrpPlaatsCode("Alkmaar"), result.getPlaatsCode());
        Assert.assertEquals(new BrpAanduidingBijHuisnummerCode("TO"), result.getLocatieTovAdres());
        Assert.assertEquals("Links achter het plein", result.getLocatieOmschrijving());
        Assert.assertEquals("Buitenland-1", result.getBuitenlandsAdresRegel1());
        Assert.assertEquals("Buitenland-2", result.getBuitenlandsAdresRegel2());
        Assert.assertEquals("Buitenland-3", result.getBuitenlandsAdresRegel3());
        Assert.assertEquals("Buitenland-4", result.getBuitenlandsAdresRegel4());
        Assert.assertEquals("Buitenland-5", result.getBuitenlandsAdresRegel5());
        Assert.assertEquals("Buitenland-6", result.getBuitenlandsAdresRegel6());
        Assert.assertEquals(new BrpLandCode(Integer.valueOf("0344")), result.getLandCode());
        Assert.assertEquals(new BrpDatum(19502103), result.getDatumVertrekUitNederland());
    }
}
