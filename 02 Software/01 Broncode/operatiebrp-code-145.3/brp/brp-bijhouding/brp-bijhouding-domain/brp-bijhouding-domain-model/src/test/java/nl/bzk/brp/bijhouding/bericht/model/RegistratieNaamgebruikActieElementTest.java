/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Test;

/**
 * Unittests voor {@link RegistratieNaamgebruikActieElement}.
 */
public class RegistratieNaamgebruikActieElementTest extends AbstractHuwelijkInNederlandTestBericht{


    @Test
    public void testVerwerk() throws OngeldigeObjectSleutelException {
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        final ActieElement actieElement = bericht.getAdministratieveHandeling().getActies().get(2);
        assertTrue(actieElement instanceof RegistratieNaamgebruikActieElement);
        final RegistratieNaamgebruikActieElement registratieNaamgebruikActieElement = (RegistratieNaamgebruikActieElement) actieElement;
        registratieNaamgebruikActieElement.getHoofdPersonen().get(0).setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        registratieNaamgebruikActieElement.verwerk(bericht, getActie().getAdministratieveHandeling());

        final BijhoudingPersoon persoonEntiteit = getIngeschrevenPersoon();
        assertNotNull(persoonEntiteit);
        assertEquals(1, persoonEntiteit.getPersoonNaamgebruikHistorieSet().size());

        final PersoonNaamgebruikHistorie naamgebruikHistorie = persoonEntiteit.getPersoonNaamgebruikHistorieSet().iterator().next();
        assertNull(naamgebruikHistorie.getAdellijkeTitel());
        assertEquals(Predicaat.H, naamgebruikHistorie.getPredicaat());
        assertEquals(new Character('\''), naamgebruikHistorie.getScheidingstekenNaamgebruik());
        assertEquals("Cornelis Jan", naamgebruikHistorie.getVoornamenNaamgebruik());
        assertEquals("s", naamgebruikHistorie.getVoorvoegselNaamgebruik());
        assertEquals("Jansen", naamgebruikHistorie.getGeslachtsnaamstamNaamgebruik());
        assertEquals(Naamgebruik.EIGEN, naamgebruikHistorie.getNaamgebruik());
        assertFalse(naamgebruikHistorie.getIndicatieNaamgebruikAfgeleid());
        assertEquals(0, persoonEntiteit.getPersoonAfgeleidAdministratiefHistorieSet().size());
    }

    @Test
    public void testVerwerkNiet() throws OngeldigeObjectSleutelException {
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        final ActieElement actieElement = bericht.getAdministratieveHandeling().getActies().get(2);
        assertTrue(actieElement instanceof RegistratieNaamgebruikActieElement);
        final RegistratieNaamgebruikActieElement registratieNaamgebruikActieElement = (RegistratieNaamgebruikActieElement) actieElement;
        registratieNaamgebruikActieElement.getHoofdPersonen().get(0).setBijhoudingSituatie(BijhoudingSituatie.OPGESCHORT);
        registratieNaamgebruikActieElement.verwerk(bericht, getActie().getAdministratieveHandeling());

        final BijhoudingPersoon persoonEntiteit = getIngeschrevenPersoon();
        assertNotNull(persoonEntiteit);
        assertEquals(0, persoonEntiteit.getPersoonNaamgebruikHistorieSet().size());
    }
}
