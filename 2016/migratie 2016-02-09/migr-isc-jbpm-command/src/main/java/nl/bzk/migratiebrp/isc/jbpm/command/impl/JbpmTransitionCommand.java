/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.impl;

import nl.bzk.migratiebrp.isc.jbpm.command.Command;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.logging.log.MessageLog;

/**
 * Transitie commando.
 */
public final class JbpmTransitionCommand implements Command<Void> {

    private static final long serialVersionUID = 1L;

    private final Long tokenId;
    private final String transitionName;

    /**
     * Constructor.
     *
     * @param tokenId
     *            token id
     * @param transitionName
     *            transitie naam
     */
    public JbpmTransitionCommand(final Long tokenId, final String transitionName) {
        this.tokenId = tokenId;
        this.transitionName = transitionName;
    }

    @Override
    public Void doInContext(final JbpmContext jbpmContext) {
        final Token token = jbpmContext.getTokenForUpdate(tokenId);
        token.getProcessInstance().getLoggingInstance().addLog(new MessageLog("Beheerder heeft 'transitie' uitgevoerd."));

        token.signal(transitionName);
        return null;
    }
}
