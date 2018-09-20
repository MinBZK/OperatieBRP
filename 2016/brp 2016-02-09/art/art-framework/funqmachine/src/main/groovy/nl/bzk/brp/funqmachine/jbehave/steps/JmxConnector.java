/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import javax.management.ObjectName;
import javax.persistence.Transient;
import nl.bzk.brp.funqmachine.configuratie.Environment;
import nl.bzk.brp.funqmachine.configuratie.JmxCommand;
import nl.bzk.brp.funqmachine.jbehave.jmx.JmxAttributeDelegate;
import nl.bzk.brp.funqmachine.jbehave.jmx.JmxMethodDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java connector for jmx from groovy.
 */
public class JmxConnector implements JmxConnectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmxConnector.class);

    private final ForkJoinPool pool = new ForkJoinPool();

    /**
     * Herlaad de cache(s) via jmx.
     *
     * @throws Exception
     */
    @Override
    public final void herlaadCaches() throws Exception {
        LOGGER.info("Start cache refresh");
        final VerversAlleCaches verversAlleCaches = new VerversAlleCaches();
        final ForkJoinTask taak = pool.submit(verversAlleCaches);
        taak.join();
        LOGGER.info("Einde cache refresh");
    }

    /**
     * Purge alle queues.
     *
     * @throws Exception
     */
    @Override
    //niet meer nodig, zit in autaut script
    @Deprecated
    public final void purgeQueues() throws Exception {
        LOGGER.info("Start purge queues");
        final PurgeAllQueuesTask purgeAllQueues = new PurgeAllQueuesTask();
        final ForkJoinTask taak = pool.submit(purgeAllQueues);
        taak.join();
        LOGGER.info("Einde purge queues");
    }

    /**
     * Reset alle abonnementen-taak.
     */
    private static class PurgeAllQueuesTask extends RecursiveTask {

        @Override
        protected Object compute() {
            final List<ForkJoinTask> purgeQueueList = new LinkedList<>();

            final JmxCommand jmxQueuesCommand = Environment.instance().getApplicationBrokerObject();
            jmxQueuesCommand.setAttributeName("Queues");
            final Object result = new JmxAttributeDelegate().perform(jmxQueuesCommand);
            final ObjectName[] objectNames = (ObjectName[]) result;

            if (objectNames != null && objectNames.length > 0) {
                for (ObjectName objectName : objectNames) {
                    final JmxCommand jmxPurgeCommand = new JmxCommand(
                        jmxQueuesCommand.getJmxURL(),
                        jmxQueuesCommand.getObjectNamePrefix(),
                        objectName.getCanonicalName(), "purge"
                    );
                    final JMXTask task = new JMXTask(jmxPurgeCommand);
                    purgeQueueList.add(task.fork());
                }

                for (final ForkJoinTask taak : purgeQueueList) {
                    taak.join();
                }
            } else {
                LOGGER.error("Could not retrieve queues from {}", jmxQueuesCommand.getJMXObjectName());
            }
            return null;
        }
    }

    /**
     * Ververs alle caches-taak.
     */
    private static class VerversAlleCaches extends RecursiveTask {

        @Override
        protected Object compute() {
            final List<ForkJoinTask> verversCacheList = new LinkedList<>();
            for (final JmxCommand applicationCacheObject : Environment.instance().getApplicationCacheObjects()) {
                final JMXTask ververCacheTaak = new JMXTask(applicationCacheObject);
                verversCacheList.add(ververCacheTaak.fork());
            }
            for (final ForkJoinTask taak : verversCacheList) {
                taak.join();
            }
            return null;
        }
    }

    /**
     * Ververs cache-taak.
     */
    private static class JMXTask extends RecursiveTask {

        @Transient
        private final JmxCommand jmxCommand;

        JMXTask(final JmxCommand cacheObject) {
            this.jmxCommand = cacheObject;
        }

        @Override
        protected Object compute() {
            return new JmxMethodDelegate().perform(jmxCommand);
        }
    }
}
