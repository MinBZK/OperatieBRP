/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.configuration;

import org.jbpm.JbpmConfiguration;

/**
 * Jbpm process installer.
 */
public interface JbpmProcessInstaller {

    /**
     * Deploy jbpm processes.
     * @param jbpmConfiguration jbpm configuratie om de deployment mee te doen
     */
    void deployJbpmProcesses(JbpmConfiguration jbpmConfiguration);

}
