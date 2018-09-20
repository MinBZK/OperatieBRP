/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import org.junit.Test;


/**
 * Unit test voor de {@link PersoonZoekCriteriaAntwoord} class.
 */
public class PersoonZoekCriteriaAntwoordTest {

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
        PersoonZoekCriteriaAntwoord antwoord = new PersoonZoekCriteriaAntwoord(new Persoon(SoortPersoon.INGESCHREVENE));
        assertEquals(1, antwoord.getPersonen().size());
    }

    @Test
    public void testConstructorMetPersonenCollectie() {
        PersoonZoekCriteriaAntwoord antwoord =
            new PersoonZoekCriteriaAntwoord(Arrays.asList(new Persoon(SoortPersoon.INGESCHREVENE), new Persoon(
                    SoortPersoon.INGESCHREVENE)));
        assertEquals(2, antwoord.getPersonen().size());
    }

}
