/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import javax.inject.Inject;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jndi.JndiTemplate;

/**
 * Stiekeme bean om te kunnen wachten totdat een Jboss installatie is geinitialiseerd.
 *
 * Maakt gebruikt van afterPropertiesSet in de InitializingBean om de controle te doen voordat de JndiFactoryBean de
 * lookup probeert te doen via de FactoryBean interface.
 */
public class JbossInitializationWaiter implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private JndiTemplate jndiTemplate;

    private boolean wachtOpJboss;
    private boolean controleerQueues = true;
    private boolean controleerBerichtCache = true;

    /**
     * Zet de waarde van wacht op jboss.
     *
     * @param wachtOpJboss
     *            wacht op jboss
     */
    public void setWachtOpJboss(final boolean wachtOpJboss) {
        this.wachtOpJboss = wachtOpJboss;
    }

    /**
     * Zet de waarde van controleer queues.
     *
     * @param controleerQueues
     *            controleer queues
     */
    public void setControleerQueues(final boolean controleerQueues) {
        this.controleerQueues = controleerQueues;
    }

    /**
     * Zet de waarde van controleer bericht cache.
     *
     * @param controleerBerichtCache
     *            controleer bericht cache
     */
    public void setControleerBerichtCache(final boolean controleerBerichtCache) {
        this.controleerBerichtCache = controleerBerichtCache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!wachtOpJboss) {
            return;
        }

        LOG.info("Controle op initialisatie Jboss gestart");

        final Herhaal herhaling = new Herhaal(2000, 20, Herhaal.Strategie.LINEAIR);
        herhaling.herhaal(new Runnable() {
            @Override
            public void run() {
                try {
                    if (controleerQueues) {
                        jndiTemplate.lookup("ConnectionFactory");
                    }

                    throw new UnsupportedOperationException("TODO: Moet geimplementeerd worden op activemq topic");
                    //
                    // if (controleerBerichtCache) {
                    // final ObjectName objectName = new ObjectName("nl.bzk.migratiebrp.isc:service=EsbBerichtCache");
                    // final RMIAdaptor server = jndiTemplate.lookup("jmx/invoker/RMIAdaptor", RMIAdaptor.class);
                    //
                    // // Get the MBeanInfo for the JNDIView MBean
                    // final MBeanInfo info = server.getMBeanInfo(objectName);
                    // }
                } catch (final Exception e) {
                    LOG.info("Jboss niet gestart: " + e);
                    throw new RuntimeException("Jboss niet gestart.");
                }
            }
        });
        LOG.info("Jboss gestart");
    }
}
