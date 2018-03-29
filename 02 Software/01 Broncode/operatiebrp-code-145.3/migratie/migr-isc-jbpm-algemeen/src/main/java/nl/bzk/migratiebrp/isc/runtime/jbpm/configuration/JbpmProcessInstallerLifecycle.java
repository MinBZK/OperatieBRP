/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.configuration;

import java.util.Map;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

import org.jbpm.JbpmConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;

/**
 * Starten van de JBPM configuratie op een eerdere dan de default phase (MAX_INT) zodat de processen eerder worden
 * geinstalleerd dan de listeners worden gestart.
 */
public final class JbpmProcessInstallerLifecycle implements SmartLifecycle {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private Map<String, JbpmProcessInstaller> installers;
    @Autowired
    private JbpmConfiguration jbpmConfiguration;

    @Override
    public void start() {
        // Install process definitions
        LOG.info("Installing process instances");
        for (final Map.Entry<String, JbpmProcessInstaller> installer : installers.entrySet()) {
            LOG.info("Installing {}", installer.getKey());
            installer.getValue().deployJbpmProcesses(jbpmConfiguration);
        }
        LOG.info("Process instances installed");
    }

    @Override
    public void stop() {
        // Niets
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 0;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(final Runnable callback) {
        // Niets
    }
}
