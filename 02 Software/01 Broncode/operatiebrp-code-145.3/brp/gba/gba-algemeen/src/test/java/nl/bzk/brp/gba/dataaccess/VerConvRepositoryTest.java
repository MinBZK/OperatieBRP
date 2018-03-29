/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.test.dal.DBUnit;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VerConvRepositoryTest extends AbstractIntegratieTest {

    @Autowired
    private VerConvRepository verConvRepository;

    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public final void zoekLo3VoorkomenVoorActieNietGevonden() {
        final Lo3Voorkomen result = verConvRepository.zoekLo3VoorkomenVoorActie(4563565L);
        Assert.assertNull(result);
    }

    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public final void zoekLo3VoorkomenVoorActieWelGevonden() {
        final Lo3Voorkomen result = verConvRepository.zoekLo3VoorkomenVoorActie(28L);
        Assert.assertNotNull(result);
    }
}
