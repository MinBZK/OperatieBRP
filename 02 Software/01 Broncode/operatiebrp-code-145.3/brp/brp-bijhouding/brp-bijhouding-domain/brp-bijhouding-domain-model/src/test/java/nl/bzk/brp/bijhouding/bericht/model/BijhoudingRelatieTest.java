/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Test;

/**
 * Testen voor {@link BijhoudingRelatie}.
 */
public class BijhoudingRelatieTest {

    @Test
    public void zoekRelatieHistorieVoorVoorkomenSleutel() {
        //setup
        final Relatie relatieEntiteit = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie relatieHistorie1 = new RelatieHistorie(relatieEntiteit);
        relatieHistorie1.setId(1L);
        final RelatieHistorie relatieHistorie2 = new RelatieHistorie(relatieEntiteit);
        relatieHistorie2.setId(2L);
        relatieEntiteit.addRelatieHistorie(relatieHistorie1);
        relatieEntiteit.addRelatieHistorie(relatieHistorie2);
        final BijhoudingRelatie bijhoudingRelatie = BijhoudingRelatie.decorate(relatieEntiteit);
        //execute & valideer
        assertSame(relatieHistorie1, bijhoudingRelatie.zoekRelatieHistorieVoorVoorkomenSleutel("1"));
        assertSame(relatieHistorie2, bijhoudingRelatie.zoekRelatieHistorieVoorVoorkomenSleutel("2"));
        //setup
        final RelatieHistorie relatieHistorie1Kopie = new RelatieHistorie(relatieEntiteit);
        bijhoudingRelatie.registreerKopieHistorie(1L, relatieHistorie1Kopie);
        assertNotSame(relatieHistorie1, bijhoudingRelatie.zoekRelatieHistorieVoorVoorkomenSleutel("1"));
        assertSame(relatieHistorie1Kopie, bijhoudingRelatie.zoekRelatieHistorieVoorVoorkomenSleutel("1"));
    }
}
