/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AdellijkeTitel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Predikaat;

import org.junit.Test;

public class BrpGeslachtsnaamcomponentMapperTest extends BrpAbstractTest {

    @Inject
    private BrpGeslachtsnaamcomponentMapper.BrpGeslachtsnaamcomponentInhoudMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonGeslachtsnaamcomponentHistorie historie = new PersoonGeslachtsnaamcomponentHistorie();
        historie.setVoorvoegsel("voor");
        historie.setScheidingsteken('x');
        historie.setNaam("naam");
        historie.setPredikaat(Predikaat.J);
        historie.setAdellijkeTitel(AdellijkeTitel.M);
        historie.setPersoonGeslachtsnaamcomponent(new PersoonGeslachtsnaamcomponent());
        historie.getPersoonGeslachtsnaamcomponent().setVolgnummer(1234);

        final BrpGeslachtsnaamcomponentInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals("voor", result.getVoorvoegsel());
        Assert.assertEquals(Character.valueOf('x'), result.getScheidingsteken());
        Assert.assertEquals("naam", result.getNaam());
        Assert.assertEquals(new BrpPredikaatCode("J"), result.getPredikaatCode());
        Assert.assertEquals(new BrpAdellijkeTitelCode("M"), result.getAdellijkeTitelCode());
        Assert.assertEquals(1234, result.getVolgnummer());
    }
}
