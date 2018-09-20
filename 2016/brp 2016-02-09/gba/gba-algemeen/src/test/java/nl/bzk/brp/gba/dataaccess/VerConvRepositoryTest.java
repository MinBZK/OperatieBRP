/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import nl.bzk.brp.model.logisch.verconv.LO3Voorkomen;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

public class VerConvRepositoryTest extends AbstractIntegratieTest {

    @Autowired
    private VerConvRepository verConvRepository;

    @Test
    public final void zoekLo3VoorkomenVoorActie() {
        final ActieModel actie = new ActieModel(null, null, null, null, null, null, null);
        ReflectionTestUtils.setField(actie, "iD", 4563565L);

        final LO3Voorkomen result = verConvRepository.zoekLo3VoorkomenVoorActie(actie);
        Assert.assertNull(result);
    }
}
