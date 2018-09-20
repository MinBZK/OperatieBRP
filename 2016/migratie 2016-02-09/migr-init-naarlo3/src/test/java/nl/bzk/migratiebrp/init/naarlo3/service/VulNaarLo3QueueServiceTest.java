/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3.service;

import com.mockrunner.jms.DestinationManager;
import com.mockrunner.mock.jms.MockQueue;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * Test de service voor het versturen van LO3-berichten.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:naarlo3-test-beans.xml")
public class VulNaarLo3QueueServiceTest {

    @Resource
    private DestinationManager manager;

    @Inject
    private VulNaarLo3QueueService service;

    @Test
    public void leesAlleLo3BerichtenEnVerstuur() {
        service.leesIngeschrevenenInBrpEnVulQueue();
        final MockQueue queue = manager.getQueue("/queue/init.vulling.naarlo3");

        final List<?> messages = queue.getCurrentMessageList();
        Assert.assertEquals("De queue bevat niet het aantal verwachte berichten", 20, messages.size());
        queue.clear();
    }

    @Test
    public void leesAlleLo3BerichtenEnVerstuurOpnieuw() {
        service.leesIngeschrevenenInBrpEnVulQueue();
        final MockQueue queue = manager.getQueue("/queue/init.vulling.naarlo3");

        final List<?> messages = queue.getCurrentMessageList();
        Assert.assertEquals("De queue bevat niet het aantal verwachte berichten", 20, messages.size());
        queue.clear();
    }
}
