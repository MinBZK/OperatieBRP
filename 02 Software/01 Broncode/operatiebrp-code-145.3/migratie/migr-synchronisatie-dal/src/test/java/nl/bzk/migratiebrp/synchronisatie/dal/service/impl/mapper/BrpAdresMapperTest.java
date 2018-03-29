/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Assert;
import org.junit.Test;

public class BrpAdresMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<>());

    @Inject
    private BrpAdresMapper.BrpAdresInhoudMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonAdresHistorie historie =
                new PersoonAdresHistorie(new PersoonAdres(new Persoon(SoortPersoon.INGESCHREVENE)), SoortAdres.WOONADRES, new LandOfGebied(
                        "0344", "LandOfGebied"), new RedenWijzigingVerblijf('R', "RWA"));
        historie.setAangeverAdreshouding(new Aangever('A', "A", "A"));
        historie.setDatumAanvangAdreshouding(19020304);
        historie.setIdentificatiecodeAdresseerbaarObject("Object-adres");
        historie.setIdentificatiecodeNummeraanduiding("Nummer-ident");
        historie.setGemeente(new Gemeente((short) 599, "Alkmaar", "0599", new Partij("Alkmaar", "000599")));
        historie.setNaamOpenbareRuimte("Openbare-Ruimte");
        historie.setAfgekorteNaamOpenbareRuimte("Op-Rmte");
        historie.setGemeentedeel("Links");
        historie.setHuisnummer(55);
        historie.setHuisletter('A');
        historie.setHuisnummertoevoeging("2e verdieping");
        historie.setPostcode("1234RE");
        historie.setWoonplaatsnaam("Alkmaar");
        historie.setLocatietovAdres("to");
        historie.setLocatieOmschrijving("Links achter het plein");
        historie.setBuitenlandsAdresRegel1("Buitenland-1");
        historie.setBuitenlandsAdresRegel2("Buitenland-2");
        historie.setBuitenlandsAdresRegel3("Buitenland-3");
        historie.setBuitenlandsAdresRegel4("Buitenland-4");
        historie.setBuitenlandsAdresRegel5("Buitenland-5");
        historie.setBuitenlandsAdresRegel6("Buitenland-6");

        final BrpAdresInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(BrpSoortAdresCode.W, result.getSoortAdresCode());
        Assert.assertEquals(new BrpRedenWijzigingVerblijfCode('R'), result.getRedenWijzigingAdresCode());
        Assert.assertEquals(new BrpAangeverCode('A'), result.getAangeverAdreshoudingCode());
        Assert.assertEquals(new BrpDatum(19020304, null), result.getDatumAanvangAdreshouding());
        Assert.assertEquals("Object-adres", BrpString.unwrap(result.getIdentificatiecodeAdresseerbaarObject()));
        Assert.assertEquals("Nummer-ident", BrpString.unwrap(result.getIdentificatiecodeNummeraanduiding()));
        Assert.assertEquals(new BrpGemeenteCode("0599"), result.getGemeenteCode());
        Assert.assertEquals("Openbare-Ruimte", BrpString.unwrap(result.getNaamOpenbareRuimte()));
        Assert.assertEquals("Op-Rmte", BrpString.unwrap(result.getAfgekorteNaamOpenbareRuimte()));
        Assert.assertEquals("Links", BrpString.unwrap(result.getGemeentedeel()));
        Assert.assertEquals(Integer.valueOf(55), BrpInteger.unwrap(result.getHuisnummer()));
        Assert.assertEquals(Character.valueOf('A'), BrpCharacter.unwrap(result.getHuisletter()));
        Assert.assertEquals("2e verdieping", BrpString.unwrap(result.getHuisnummertoevoeging()));
        Assert.assertEquals("1234RE", BrpString.unwrap(result.getPostcode()));
        Assert.assertEquals("Alkmaar", BrpString.unwrap(result.getWoonplaatsnaam()));
        Assert.assertEquals(new BrpAanduidingBijHuisnummerCode("to"), result.getLocatieTovAdres());
        Assert.assertEquals("Links achter het plein", BrpString.unwrap(result.getLocatieOmschrijving()));
        Assert.assertEquals("Buitenland-1", BrpString.unwrap(result.getBuitenlandsAdresRegel1()));
        Assert.assertEquals("Buitenland-2", BrpString.unwrap(result.getBuitenlandsAdresRegel2()));
        Assert.assertEquals("Buitenland-3", BrpString.unwrap(result.getBuitenlandsAdresRegel3()));
        Assert.assertEquals("Buitenland-4", BrpString.unwrap(result.getBuitenlandsAdresRegel4()));
        Assert.assertEquals("Buitenland-5", BrpString.unwrap(result.getBuitenlandsAdresRegel5()));
        Assert.assertEquals("Buitenland-6", BrpString.unwrap(result.getBuitenlandsAdresRegel6()));
        Assert.assertEquals(new BrpLandOfGebiedCode("0344"), result.getLandOfGebiedCode());
    }
}
