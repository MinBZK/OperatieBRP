/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;

import javax.inject.Inject;

import org.junit.Test;

import org.junit.Assert;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;

public class BrpGeboorteMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    private static final String WOONPLAATS_NAAM = "Harlingen";
    private static final String WOONPLAATS_CODE = "0518";
    @Inject
    private BrpGeboorteMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonGeboorteHistorie historie =
                new PersoonGeboorteHistorie(new Persoon(SoortPersoon.INGESCHREVENE), 14322402, new LandOfGebied("0023", "naam"));
        historie.setGemeente(new Gemeente(Short.valueOf(WOONPLAATS_CODE), WOONPLAATS_NAAM, WOONPLAATS_CODE, new Partij(WOONPLAATS_NAAM.toLowerCase(), "051801")));
        historie.setWoonplaatsnaamGeboorte(WOONPLAATS_NAAM);
        historie.setBuitenlandsePlaatsGeboorte("Kuna kuna");
        historie.setBuitenlandseRegioGeboorte("West van de Onderwindse eilanden");
        historie.setOmschrijvingGeboortelocatie("Op de boot");

        final BrpGeboorteInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpDatum(14322402, null), result.getGeboortedatum());
        Assert.assertEquals(new BrpGemeenteCode("0518"), result.getGemeenteCode());
        Assert.assertEquals("Harlingen", result.getWoonplaatsnaamGeboorte().getWaarde());
        Assert.assertEquals("Kuna kuna", result.getBuitenlandsePlaatsGeboorte().getWaarde());
        Assert.assertEquals("West van de Onderwindse eilanden", result.getBuitenlandseRegioGeboorte().getWaarde());
        Assert.assertEquals(new BrpLandOfGebiedCode("0023"), result.getLandOfGebiedCode());
        Assert.assertEquals("Op de boot", result.getOmschrijvingGeboortelocatie().getWaarde());

    }
}
