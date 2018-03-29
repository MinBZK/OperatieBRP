/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import org.junit.Assert;
import org.junit.Test;

/**
 * CMutatieHandelingTest.
 */
public class MutatieHandelingTest {

    @Test
    public void testMutatieHandeling() {
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final long administratieveHandelingId = 12L;
        final Map<Long, Persoonslijst> persoonsLijstMap = new HashMap<>();
        persoonsLijstMap.put(persoonslijst.getId(), persoonslijst);
        final Mutatiehandeling mutatieHandeling = new Mutatiehandeling(administratieveHandelingId, persoonsLijstMap);

        Assert.assertEquals(administratieveHandelingId, mutatieHandeling.getAdministratieveHandelingId());
        Assert.assertEquals(persoonslijst, mutatieHandeling.getPersoonsgegevensMap().get(persoonslijst.getId()));
        Assert.assertEquals(1, mutatieHandeling.getPersonen().size());


    }
}
