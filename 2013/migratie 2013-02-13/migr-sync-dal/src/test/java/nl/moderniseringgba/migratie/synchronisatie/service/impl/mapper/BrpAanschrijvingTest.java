/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAanschrijvingHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Predikaat;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.WijzeGebruikGeslachtsnaam;

import org.junit.Test;

public class BrpAanschrijvingTest extends BrpAbstractTest {

    @Inject
    private BrpAanschrijvingMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonAanschrijvingHistorie historie = new PersoonAanschrijvingHistorie();
        historie.setWijzeGebruikGeslachtsnaam(WijzeGebruikGeslachtsnaam.EIGEN);
        historie.setIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten(Boolean.FALSE);
        historie.setIndicatieAanschrijvingAlgoritmischAfgeleid(Boolean.TRUE);
        historie.setPredikaat(Predikaat.K);
        // historie.setAdellijkeTitel
        historie.setVoornamenAanschrijving("Voor nam en");
        historie.setVoorvoegselAanschrijving("van der den a la");
        historie.setScheidingstekenAanschrijving('x');
        historie.setGeslachtsnaamAanschrijving("Janssen-van der Dingenmans");

        final BrpAanschrijvingInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(BrpWijzeGebruikGeslachtsnaamCode.E, result.getWijzeGebruikGeslachtsnaamCode());
        Assert.assertEquals(Boolean.FALSE, result.getIndicatieAanschrijvenMetTitels());
        Assert.assertEquals(Boolean.TRUE, result.getIndicatieAfgeleid());
        Assert.assertEquals(new BrpPredikaatCode("K"), result.getPredikaatCode());
        // Assert adellijke titel
        Assert.assertEquals("Voor nam en", result.getVoornamen());
        Assert.assertEquals("van der den a la", result.getVoorvoegsel());
        Assert.assertEquals(new Character('x'), result.getScheidingsteken());
        Assert.assertEquals("Janssen-van der Dingenmans", result.getGeslachtsnaam());
    }
}
