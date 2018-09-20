/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;
import javax.inject.Inject;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonOverlijdenHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Test;

public class BrpOverlijdenMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    public static final String WOONPLAATS = "Leiderdorp";
    public static final short GEMEENTECODE = (short) 517;
    @Inject
    private BrpOverlijdenMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonOverlijdenHistorie historie =
                new PersoonOverlijdenHistorie(new Persoon(SoortPersoon.INGESCHREVENE), 19880306, new LandOfGebied((short) 32, "naam"));
        historie.setGemeente(new Gemeente(GEMEENTECODE, WOONPLAATS, GEMEENTECODE, new Partij(WOONPLAATS, 517)));
        historie.setWoonplaatsnaamOverlijden(WOONPLAATS);
        historie.setBuitenlandsePlaatsOverlijden("Net over de grens");
        historie.setBuitenlandseRegioOverlijden("Wallonie");
        historie.setOmschrijvingLocatieOverlijden("Omschrijving");

        final BrpOverlijdenInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpDatum(19880306, null), result.getDatum());
        Assert.assertEquals(new BrpGemeenteCode(Short.parseShort("0517")), result.getGemeenteCode());
        Assert.assertEquals(new BrpString("Leiderdorp", null), result.getWoonplaatsnaamOverlijden());
        Assert.assertEquals(new BrpString("Net over de grens", null), result.getBuitenlandsePlaats());
        Assert.assertEquals(new BrpString("Wallonie", null), result.getBuitenlandseRegio());
        Assert.assertEquals(new BrpLandOfGebiedCode(Short.parseShort("0032")), result.getLandOfGebiedCode());
        Assert.assertEquals(new BrpString("Omschrijving", null), result.getOmschrijvingLocatie());
    }
}
