/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.aut.PersistentAuthenticatieMiddel;
import org.junit.Test;


public class AuthenticatieMiddelRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private AuthenticatieMiddelRepository authenticatieMiddelRepository;

    @Inject
    private PartijRepository              partijRepository;

    @Test
    public void testZoekAuthMiddelenVoorPartijMetCertificaat() {
        final Partij partij = partijRepository.findOne(4);
        List<PersistentAuthenticatieMiddel> authenticatieMiddelen =
            authenticatieMiddelRepository
                    .zoekAuthMiddelenVoorPartijMetCertificaat(
                            partij,
                            "CN=Test",
                            new BigInteger("1323167436"),
                            "54150f1230a8068661cf321b1ad1d61c430b0bff1076c6197ba5b0a0e49cd7496b0c99e34085c1c"
                                    + "37fe30646c0de6e4ea1a07e329093bf5f6f77c50341bcbfba0f79b5d24651ffc27c404d"
                                    + "49a30c3f5ae67fee82e9dc5bbe7cacb9fa15e8186c703120ed768d6eacf7d75fb5cfec1"
                                    + "7a2c10e4381529a367021e8208f9742453c");

        for (PersistentAuthenticatieMiddel authenticatieMiddel : authenticatieMiddelen) {
            Assert.assertEquals(StatusHistorie.A, authenticatieMiddel.getStatushistorie());
            Assert.assertEquals(partij.getId(), authenticatieMiddel.getPartij().getId());
            Assert.assertEquals("CN=Test", authenticatieMiddel.getOndertekeningsCertificaat().getSubject());
            Assert.assertEquals(new BigInteger("1323167436"),
                    authenticatieMiddel.getOndertekeningsCertificaat().getSerial());
            Assert.assertEquals(
                    "54150f1230a8068661cf321b1ad1d61c430b0bff1076c6197ba5b0a0e49cd7496b0c99e34085c1c37fe306"
                            + "46c0de6e4ea1a07e329093bf5f6f77c50341bcbfba0f79b5d24651ffc27c404d49a30c3f5ae67fe"
                            + "e82e9dc5bbe7cacb9fa15e8186c703120ed768d6eacf7d75fb5cfec17a2c10e4381529a367021e8"
                            + "208f9742453c",
                    authenticatieMiddel.getOndertekeningsCertificaat().getSignature());
        }
    }
}
