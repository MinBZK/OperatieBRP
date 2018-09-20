/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatserial;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatsubject;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PubliekeSleutel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Authenticatiemiddel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import org.junit.Test;


public class AuthenticatieMiddelRepositoryTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Inject
    private AuthenticatiemiddelRepository authenticatiemiddelRepository;

    @Test
    public void testZoekAuthMiddelenVoorPartijMetCertificaat() {
        final Partij partij = em.find(Partij.class, (short) 4);
        List<Authenticatiemiddel> authenticatieMiddelen =
            authenticatiemiddelRepository.zoekAuthMiddelenVoorPartijMetCertificaat(partij.getID(),
                new Certificaatsubject("CN=Test"), new Certificaatserial("1323167436"),
                new PubliekeSleutel(
                    "54150f1230a8068661cf321b1ad1d61c430b0bff1076c6197ba5b0a0e49cd7496b0c99e34085c1c"
                        + "37fe30646c0de6e4ea1a07e329093bf5f6f77c50341bcbfba0f79b5d24651ffc27c404d"
                        + "49a30c3f5ae67fee82e9dc5bbe7cacb9fa15e8186c703120ed768d6eacf7d75fb5cfec1"
                        + "7a2c10e4381529a367021e8208f9742453c"));

        for (Authenticatiemiddel authenticatieMiddel : authenticatieMiddelen) {
            Assert.assertEquals(StatusHistorie.A,
                authenticatieMiddel.getAuthenticatiemiddelStatusHis());
            Assert.assertEquals(partij, authenticatieMiddel.getPartij());
            Assert.assertEquals("CN=Test",
                authenticatieMiddel.getCertificaatTbvOndertekening().getSubject().getWaarde());
            Assert.assertEquals("1323167436",
                authenticatieMiddel.getCertificaatTbvOndertekening().getSerial().getWaarde());
            Assert.assertEquals(
                "54150f1230a8068661cf321b1ad1d61c430b0bff1076c6197ba5b0a0e49cd7496b0c99e34085c1c37fe306"
                    + "46c0de6e4ea1a07e329093bf5f6f77c50341bcbfba0f79b5d24651ffc27c404d49a30c3f5ae67fe"
                    + "e82e9dc5bbe7cacb9fa15e8186c703120ed768d6eacf7d75fb5cfec17a2c10e4381529a367021e8"
                    + "208f9742453c",
                authenticatieMiddel.getCertificaatTbvOndertekening().getSignature().getWaarde());
        }
    }

}
