/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.repository;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(inheritLocations = true, locations = { "classpath:voisc-db-cache.xml" })
public class CachedRepositoryTest extends AbstractDbTest {

    @Inject
    private MailboxRepository mailboxRepository;

    @Test
    public void test() {
        mailboxRepository.getMailboxes();
        mailboxRepository.getMailboxes();
        mailboxRepository.getMailboxes();
    }
}
