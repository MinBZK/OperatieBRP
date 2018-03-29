/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.mutatielevering.boot;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Utilklasse voor het booten van een Spring context, waarna vervolgens eindeloos gewacht wordt.
 * Het wachten kan enkel geinterrupt worden, wat normaliter pas aangeroepen wordt tijdens JVM shutdown.
 */
public class BooterImpl implements Booter {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final Object WAIT_OBJ = new Object();

    @Override
    public void springboot(final String configLocation) {
        LOGGER.info("Start applicatie");
        try (final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configLocation)) {
            LOGGER.info("Applicatie gestart");

            final Thread currentThread = Thread.currentThread();
            Runtime.getRuntime().addShutdownHook(new Thread(currentThread::interrupt));
            //gard tegen spurious wakeups
            while (!currentThread.isInterrupted()) {
                try {
                    //wait kost geen cycles
                    synchronized (WAIT_OBJ) {
                        WAIT_OBJ.wait();
                    }
                } catch (InterruptedException e) {
                    LOGGER.info("Applicatie interrupt");
                    currentThread.interrupt();
                    break;
                }
            }
        } finally {
            LOGGER.info("Applicatie gestopt");
        }
    }
}
