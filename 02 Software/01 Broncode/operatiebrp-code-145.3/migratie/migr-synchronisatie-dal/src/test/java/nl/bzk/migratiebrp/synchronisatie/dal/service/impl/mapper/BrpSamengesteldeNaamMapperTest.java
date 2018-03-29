/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;

import org.junit.Assert;
import org.junit.Test;

public class BrpSamengesteldeNaamMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Inject
    private BrpSamengesteldeNaamMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonSamengesteldeNaamHistorie historie =
                new PersoonSamengesteldeNaamHistorie(new Persoon(SoortPersoon.INGESCHREVENE), "Achter", false, false);
        historie.setPredicaat(Predicaat.K);
        historie.setVoornamen("Voor naampjes");
        historie.setVoorvoegsel("a la");
        historie.setScheidingsteken(' ');
        historie.setAdellijkeTitel(AdellijkeTitel.R);

        final BrpSamengesteldeNaamInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpPredicaatCode("K"), result.getPredicaatCode());
        Assert.assertEquals("Voor naampjes", BrpString.unwrap(result.getVoornamen()));
        Assert.assertEquals("a la", BrpString.unwrap(result.getVoorvoegsel()));
        Assert.assertEquals(Character.valueOf(' '), BrpCharacter.unwrap(result.getScheidingsteken()));
        Assert.assertEquals(new BrpAdellijkeTitelCode("R"), result.getAdellijkeTitelCode());
        Assert.assertEquals("Achter", BrpString.unwrap(result.getGeslachtsnaamstam()));
        Assert.assertEquals(Boolean.FALSE, BrpBoolean.unwrap(result.getIndicatieAfgeleid()));
        Assert.assertEquals(Boolean.FALSE, BrpBoolean.unwrap(result.getIndicatieNamenreeks()));
    }
}
