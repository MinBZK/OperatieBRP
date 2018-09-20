/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.repository.jpa;

import java.net.UnknownHostException;

import javax.inject.Inject;

import nl.bzk.brp.domein.autaut.Authenticatiemiddel;
import nl.bzk.brp.domein.repository.AuthenticatiemiddelRepository;

import org.junit.Assert;
import org.junit.Test;


/**
 * Testcases voor de {@link AuthenticatiemiddelRepository} class.
 */
public class AuthenticatiemiddelRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private AuthenticatiemiddelRepository authenticatieMiddelRepository;

    /**
     * Test het ophalen van een authenticatiemiddel op basis van de id.
     *
     * @throws UnknownHostException
     */
    @Test
    public void tesZoekAuthenticatiemiddelOpId() throws UnknownHostException {
        Authenticatiemiddel authenticatieMiddel = authenticatieMiddelRepository.findOne(1L);
        Assert.assertNotNull(authenticatieMiddel);

        Assert.assertEquals("192.168.0.1", authenticatieMiddel.getIPAdres());
        Assert.assertEquals((Long) 1L, authenticatieMiddel.getCertificaatTbvSSL().getID());
    }
}
