/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.impl;

import nl.bzk.migratiebrp.isc.jbpm.command.Command;
import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Klasse voor JBPM taak commando's.
 */
public final class JbpmStartTaakCommand implements Command<Void> {

    private static final long serialVersionUID = 1L;

    private final Long taakId;
    private final String actorId;

    /**
     * Default constructor.
     *
     * @param taakId
     *            Het taak Id van het foutafhandelingsproces.
     * @param actorId
     *            Het Id van de actor.
     */
    public JbpmStartTaakCommand(final Long taakId, final String actorId) {
        this.taakId = taakId;
        this.actorId = actorId;
    }

    @Override
    public Void doInContext(final JbpmContext jbpmContext) {
        final TaskInstance taskInstance = jbpmContext.getTaskInstanceForUpdate(taakId);

        if (actorId != null && !"".equals(actorId)) {
            taskInstance.start(actorId);
        } else {
            taskInstance.start();
        }
        return null;
    }
}
