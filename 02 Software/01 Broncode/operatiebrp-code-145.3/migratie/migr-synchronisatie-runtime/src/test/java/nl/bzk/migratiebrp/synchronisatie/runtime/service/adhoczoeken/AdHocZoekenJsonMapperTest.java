/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.adhoczoeken;

import java.util.Arrays;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonMapper;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import nl.bzk.brp.gba.domain.bevraging.ZoekCriterium;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test de json mapper voor adhoc zoeken.
 */
public class AdHocZoekenJsonMapperTest {

    @Test
    public void testPersoonsVraag() throws Exception {
        final Persoonsvraag persoonsvraag = new Persoonsvraag();
        final ZoekCriterium criterium = new ZoekCriterium("Persoon.Identificatienummers.Administratienummer");
        criterium.setWaarde("waarde");
        final ZoekCriterium criterium1 = new ZoekCriterium("Persoon.Identificatienummers.Administratienummer");
        criterium1.setWaarde("ofWaarde");
        criterium.setOf(criterium1);

        persoonsvraag.getZoekCriteria().add(criterium);
        persoonsvraag.setPartijCode("034101");
        persoonsvraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "01.01.20"));
        persoonsvraag.setZoekRubrieken(Arrays.asList("01.02.30", "01.02.40", "01.04.10"));

        Assert.assertEquals(
                "{\"gevraagdeRubrieken\":[\"01.01.10\",\"01.01.20\"],\"partijCode\":\"034101\","
                        + "\"zoekCriteria\":[{\"naam\":\"Persoon.Identificatienummers.Administratienummer\","
                        + "\"of\":{\"naam\":\"Persoon.Identificatienummers.Administratienummer\","
                        + "\"waarde\":\"ofWaarde\"},\"waarde\":\"waarde\"}],"
                        + "\"zoekRubrieken\":[\"01.02.30\",\"01.02.40\",\"01.04.10\"]}",
                JsonMapper.writer().writeValueAsString(persoonsvraag));
    }
}
