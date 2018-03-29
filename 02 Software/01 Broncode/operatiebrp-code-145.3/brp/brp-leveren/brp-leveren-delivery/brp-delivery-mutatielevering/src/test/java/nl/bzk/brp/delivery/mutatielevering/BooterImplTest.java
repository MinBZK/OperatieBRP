/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.mutatielevering;

import java.util.concurrent.TimeUnit;
import nl.bzk.brp.delivery.mutatielevering.boot.BooterImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class BooterImplTest {

    @Test(timeout = 10000)
    public void test() throws InterruptedException {

        final Thread mainWaitThread = new Thread(
                () -> new BooterImpl().springboot("classpath:lege-context.xml")
        );
        mainWaitThread.start();

        TimeUnit.SECONDS.sleep(5);

        Assert.assertTrue(mainWaitThread.isDaemon());
        Assert.assertTrue(mainWaitThread.isAlive());
        Assert.assertFalse(mainWaitThread.isInterrupted());

        mainWaitThread.interrupt();

        Assert.assertTrue(mainWaitThread.isInterrupted());
        mainWaitThread.join();
        Assert.assertFalse(mainWaitThread.isAlive());
    }
}
