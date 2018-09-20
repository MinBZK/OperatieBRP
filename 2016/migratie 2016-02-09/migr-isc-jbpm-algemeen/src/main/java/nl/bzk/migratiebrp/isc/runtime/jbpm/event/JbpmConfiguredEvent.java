/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.event;

import org.springframework.context.ApplicationEvent;

/**
 * Event die wordt gegooid door de JbpmConfigurationFactoryBean als na het starten van de spring container de JBPM
 * Context is geconfigureerd en de JBPM processen zijn geinstalleerd.
 */
public class JbpmConfiguredEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param source
     *            source
     */
    public JbpmConfiguredEvent(final Object source) {
        super(source);
    }

}
