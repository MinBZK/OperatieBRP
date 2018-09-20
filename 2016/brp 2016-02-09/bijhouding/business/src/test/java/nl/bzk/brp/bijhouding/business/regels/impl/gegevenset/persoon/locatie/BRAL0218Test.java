/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie;

import static org.junit.Assert.assertEquals;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import org.junit.Before;
import org.junit.Test;

public class BRAL0218Test extends AbstractLocatieRegelTest {

    private BRAL0218 bral0218;

    @Before
    public void init() {
        bral0218 = new BRAL0218();
    }

    @Test
    public void testGetRegel() {
        assertEquals(Regel.BRAL0218, bral0218.getRegel());
    }

    @Test
    public void testRegel0218() {
        PersoonBericht persoon = getPersoonGeboorteBuitenland(null, null, null);
        List<BerichtEntiteit> berichtEntiteiten = bral0218.voerRegelUit(null, persoon, null, null);
        assertEquals(1, berichtEntiteiten.size());

        persoon = getPersoonGeboorteBuitenland(getBuitenlandsePlaats(), null, null);
        berichtEntiteiten = bral0218.voerRegelUit(null, persoon, null, null);
        assertEquals(0, berichtEntiteiten.size());

        persoon = getPersoonGeboorteBuitenland(null, null, getLocatieOmschrijving());
        berichtEntiteiten = bral0218.voerRegelUit(null, persoon, null, null);
        assertEquals(0, berichtEntiteiten.size());

        persoon = getPersoonGeboorteBuitenland(getBuitenlandsePlaats(), null, getLocatieOmschrijving());
        berichtEntiteiten = bral0218.voerRegelUit(null, persoon, null, null);
        assertEquals(1, berichtEntiteiten.size());

        persoon = getPersoonGeboorteNederland(null, null);
        berichtEntiteiten = bral0218.voerRegelUit(null, persoon, null, null);
        assertEquals(0, berichtEntiteiten.size());

        persoon = getPersoonGeboorte(getNederland(), null, null, null, null, getLocatieOmschrijving());
        berichtEntiteiten = bral0218.voerRegelUit(null, persoon, null, null);
        assertEquals(1, berichtEntiteiten.size());
    }

}
