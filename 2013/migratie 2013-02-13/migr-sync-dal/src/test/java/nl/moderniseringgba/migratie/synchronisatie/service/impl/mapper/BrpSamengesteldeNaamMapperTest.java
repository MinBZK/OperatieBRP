/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AdellijkeTitel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonSamengesteldeNaamHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Predikaat;

import org.junit.Assert;
import org.junit.Test;

public class BrpSamengesteldeNaamMapperTest extends BrpAbstractTest {

    @Inject
    private BrpSamengesteldeNaamMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonSamengesteldeNaamHistorie historie = new PersoonSamengesteldeNaamHistorie();
        historie.setPredikaat(Predikaat.K);
        historie.setVoornamen("Voor naampjes");
        historie.setVoorvoegsel("a la");
        historie.setScheidingsteken(' ');
        historie.setAdellijkeTitel(AdellijkeTitel.R);
        historie.setGeslachtsnaam("Achter");
        historie.setIndicatieAlgoritmischAfgeleid(Boolean.FALSE);
        historie.setIndicatieNamenreeksAlsGeslachtsnaam(Boolean.FALSE);

        final BrpSamengesteldeNaamInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpPredikaatCode("K"), result.getPredikaatCode());
        Assert.assertEquals("Voor naampjes", result.getVoornamen());
        Assert.assertEquals("a la", result.getVoorvoegsel());
        Assert.assertEquals(Character.valueOf(' '), result.getScheidingsteken());
        Assert.assertEquals(new BrpAdellijkeTitelCode("R"), result.getAdellijkeTitelCode());
        Assert.assertEquals("Achter", result.getGeslachtsnaam());
        Assert.assertEquals(Boolean.FALSE, result.getIndicatieAfgeleid());
        Assert.assertEquals(Boolean.FALSE, result.getIndicatieNamenreeks());
    }
}
