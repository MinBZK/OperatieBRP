/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import nl.moderniseringgba.isc.jbpm.installer.JbpmProcessInstaller;
import nl.moderniseringgba.isc.jbpm.installer.JbpmProcessInstallerImpl;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

/**
 * Action die verantwoordelijk is voor het installeren van de JBPM processen bij het starten van de ESB.
 */
public final class InstallProcessesAction implements ActionPipelineProcessor {

    private JbpmProcessInstaller installer = new JbpmProcessInstallerImpl();

    /**
     * Constructor (verplicht voor Jboss ESB).
     * 
     * @param configTree
     *            configuratie
     */
    public InstallProcessesAction(final ConfigTree configTree) {
        // Geen actie
    }

    public void setJbpmProcessInstaller(final JbpmProcessInstaller installer) {
        this.installer = installer;
    }

    /**
     * Installeer JBPM processen bij starten ESB.
     */
    @Override
    public void initialise() {
        installer.deployJbpmProcesses();
    }

    /**
     * Installeer JBPM processen bij uitvoeren action.
     * 
     * @param message
     *            message
     * @return message
     */
    @Override
    public Message process(final Message message) {
        installer.deployJbpmProcesses();
        return message;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public void destroy() throws ActionLifecycleException {
        // Geen actie
    }

    @Override
    public void processException(final Message arg0, final Throwable arg1) {
        // Geen actie
    }

    @Override
    public void processSuccess(final Message arg0) {
        // Geen actie
    }
}
