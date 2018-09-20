/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import org.junit.Assert;
import org.junit.Test;

public class KeepAliveTest {

    @Test
    public void ok() throws InterruptedException {
        final KeepAlive keepAlive = new KeepAlive();

        final Thread keepAliveThread = new Thread(keepAlive);
        keepAliveThread.start();

        Assert.assertTrue(keepAliveThread.isAlive());

        Thread.sleep(1000);
        keepAlive.stop();
        Thread.sleep(1000);

        Assert.assertFalse(keepAliveThread.isAlive());
    }

    @Test
    public void interrupted() throws InterruptedException {
        final KeepAlive keepAlive = new KeepAlive();

        final Thread keepAliveThread = new Thread(keepAlive);
        keepAliveThread.start();

        Thread.sleep(1000);
        keepAliveThread.interrupt();
        Thread.sleep(1000);

        Assert.assertFalse(keepAliveThread.isAlive());
    }
}
