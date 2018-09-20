/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bevraging;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.junit.Assert;
import org.junit.Test;


/** Unit test klasse voor de methodes in de {@link VraagDetailsPersoonAntwoord} klasse. */
public class VraagDetailsPersoonAntwoordTest {

    @Test
    public void testConstructorTypeBerichtResultaat() {
        VraagDetailsPersoonAntwoord antwoord = new VraagDetailsPersoonAntwoord(new OpvragenPersoonResultaat(null));
        Assert.assertNull(antwoord.getPersoon());
    }

    @Test
    public void testConstructorTypeOpvragen() {
        VraagDetailsPersoonAntwoord antwoord = new VraagDetailsPersoonAntwoord(new OpvragenPersoonResultaat(null));
        Assert.assertNull(antwoord.getPersoon());
        Assert.assertFalse(antwoord.isPersoonGevonden());

        Set<PersoonModel> gevondenPersonen = new HashSet<PersoonModel>();
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        resultaat.setGevondenPersonen(gevondenPersonen);

        antwoord = new VraagDetailsPersoonAntwoord(resultaat);
        Assert.assertNull(antwoord.getPersoon());
        Assert.assertFalse(antwoord.isPersoonGevonden());

        PersoonModel persoon = new PersoonModel(new PersoonBericht());
        gevondenPersonen.add(persoon);

        antwoord = new VraagDetailsPersoonAntwoord(resultaat);
        Assert.assertEquals(persoon, antwoord.getPersoon());
        Assert.assertTrue(antwoord.isPersoonGevonden());
    }
}
