/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.isc;

import javax.inject.Inject;
import javax.naming.NamingException;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.operatie.Herhaal;

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

    public void setWachtOpJboss(final boolean wachtOpJboss) {
        this.wachtOpJboss = wachtOpJboss;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!wachtOpJboss) {
            return;
        }

        LOG.info("Controle op initialisatie Jboss gestart");

        final Herhaal herhaling = new Herhaal(2000, 10, Herhaal.Strategie.LINEAIR);
        herhaling.herhaal(new Runnable() {
            @Override
            public void run() {
                try {
                    jndiTemplate.lookup("ConnectionFactory");
                } catch (final NamingException e) {
                    LOG.info("Jboss niet gestart");
                    throw new RuntimeException("Jboss niet gestart.");
                }
            }
        });
        LOG.info("Jboss gestart");
    }
}
