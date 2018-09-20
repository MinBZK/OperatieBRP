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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeboorteHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Test;

public class BrpGeboorteMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    public static final String WOONPLAATS_NAAM = "Harlingen";
    public static final short WOONPLAATS_CODE = (short) 518;
    @Inject
    private BrpGeboorteMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonGeboorteHistorie historie =
                new PersoonGeboorteHistorie(new Persoon(SoortPersoon.INGESCHREVENE), 14322402, new LandOfGebied((short) 23, "naam"));
        historie.setGemeente(new Gemeente(WOONPLAATS_CODE, WOONPLAATS_NAAM, WOONPLAATS_CODE, new Partij(WOONPLAATS_NAAM.toLowerCase(), 518)));
        historie.setWoonplaatsnaamGeboorte(WOONPLAATS_NAAM);
        historie.setBuitenlandsePlaatsGeboorte("Kuna kuna");
        historie.setBuitenlandseRegioGeboorte("West van de Onderwindse eilanden");
        historie.setOmschrijvingGeboortelocatie("Op de boot");

        final BrpGeboorteInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpDatum(14322402, null), result.getGeboortedatum());
        Assert.assertEquals(new BrpGemeenteCode(Short.parseShort("0518")), result.getGemeenteCode());
        Assert.assertEquals("Harlingen", result.getWoonplaatsnaamGeboorte().getWaarde());
        Assert.assertEquals("Kuna kuna", result.getBuitenlandsePlaatsGeboorte().getWaarde());
        Assert.assertEquals("West van de Onderwindse eilanden", result.getBuitenlandseRegioGeboorte().getWaarde());
        Assert.assertEquals(new BrpLandOfGebiedCode(Short.parseShort("0023")), result.getLandOfGebiedCode());
        Assert.assertEquals("Op de boot", result.getOmschrijvingGeboortelocatie().getWaarde());

    }
}
