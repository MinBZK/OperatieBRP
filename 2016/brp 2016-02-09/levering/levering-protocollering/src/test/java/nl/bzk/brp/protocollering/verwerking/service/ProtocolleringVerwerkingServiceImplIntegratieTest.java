/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking.service;

import static junit.framework.TestCase.assertNotNull;

import javax.inject.Inject;

import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.protocollering.AbstractIntegratieTestMetJmsEnDatabase;
import nl.bzk.brp.protocollering.TestData;
import org.junit.Test;

/**
 * Test de protocollering verwerking service.
 */
public class ProtocolleringVerwerkingServiceImplIntegratieTest extends AbstractIntegratieTestMetJmsEnDatabase {

    @Inject
    private ProtocolleringVerwerkingService protocolleringVerwerkingService;

    private ProtocolleringOpdracht protocolleringOpdracht = TestData.geefTestProtocolleringOpdracht();

    @Test
    public void testSlaProtocolleringOp() {
        protocolleringVerwerkingService.slaProtocolleringOp(protocolleringOpdracht);

        assertNotNull(protocolleringOpdracht.getLevering().getID());
    }

}
