/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.kern.SoortPersoon;

import org.junit.Test;


/**
 * Unit test voor de {@link PersoonZoekCriteriaAntwoord} class.
 */
public class PersoonZoekCriteriaAntwoordTest {

    private DomeinObjectFactory factory = new PersistentDomeinObjectFactory();

    @Test
    public void testLegeConstructor() {
        PersoonZoekCriteriaAntwoord antwoord = new PersoonZoekCriteriaAntwoord();
        assertEquals(0, antwoord.getPersonen().size());
    }

    @Test
    public void testConstructorMetNull() {
        PersoonZoekCriteriaAntwoord antwoord = new PersoonZoekCriteriaAntwoord((Persoon) null);
        assertEquals(0, antwoord.getPersonen().size());
    }

    @Test
    public void testConstructorMetEnkelPersoon() {
        Persoon persoon = factory.createPersoon();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);
        PersoonZoekCriteriaAntwoord antwoord = new PersoonZoekCriteriaAntwoord(persoon);
        assertEquals(1, antwoord.getPersonen().size());
    }

    @Test
    public void testConstructorMetPersonenCollectie() {
        Persoon persoon1 = factory.createPersoon();
        persoon1.setSoort(SoortPersoon.INGESCHREVENE);
        Persoon persoon2 = factory.createPersoon();
        persoon2.setSoort(SoortPersoon.INGESCHREVENE);
        PersoonZoekCriteriaAntwoord antwoord = new PersoonZoekCriteriaAntwoord(Arrays.asList(persoon1, persoon2));
        assertEquals(2, antwoord.getPersonen().size());
    }

}
