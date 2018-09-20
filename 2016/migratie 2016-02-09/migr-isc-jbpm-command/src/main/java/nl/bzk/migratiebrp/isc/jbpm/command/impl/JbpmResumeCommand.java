/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.impl;

import java.util.List;
import nl.bzk.migratiebrp.isc.jbpm.command.Command;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.logging.log.MessageLog;

/**
 * Klasse voor JBPM Signal commando's.
 */
public final class JbpmResumeCommand implements Command<Void> {

    private static final long serialVersionUID = 1L;

    private final Long processInstanceId;

    /**
     * Default constructor.
     *
     * @param processInstanceId
     *            Het proces instantie Id.
     */
    public JbpmResumeCommand(final Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public Void doInContext(final JbpmContext jbpmContext) {
        final ProcessInstance processInstance = jbpmContext.getProcessInstanceForUpdate(processInstanceId);
        processInstance.getLoggingInstance().addLog(new MessageLog("Beheerder heeft 'hervatten' uitgevoerd."));

        resumeProcessInstance(processInstance);
        return null;
    }

    private void resumeProcessInstance(final ProcessInstance processInstance) {

        @SuppressWarnings("unchecked")
        final List<Token> tokens = processInstance.findAllTokens();
        for (final Token token : tokens) {
            final ProcessInstance subProcessInstance = token.getSubProcessInstance();
            if (subProcessInstance != null) {
                resumeProcessInstance(subProcessInstance);
            }
        }

        processInstance.resume();
    }

}
