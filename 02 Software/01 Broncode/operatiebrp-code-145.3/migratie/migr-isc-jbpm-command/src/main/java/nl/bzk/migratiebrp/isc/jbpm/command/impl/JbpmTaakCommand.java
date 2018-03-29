/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import nl.bzk.migratiebrp.isc.jbpm.command.Command;
import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Klasse voor JBPM taak commando's.
 */
public final class JbpmTaakCommand implements Command<Void> {

    private static final long serialVersionUID = 1L;

    private final Long taakId;

    private final Set<String> taakVariabelenVerwijderd;

    private final Map<String, Serializable> taakVariabelenGewijzigd;

    private final String commentaar;

    private final String transitieNaam;

    /**
     * Default constructor.
     * @param taakId Het taak Id van het foutafhandelingsproces.
     * @param taakVariabelenVerwijderd De map met verwijderde taak variabelen.
     * @param taakVariabelenGewijzigd De map met gewijzigde taak variabelen.
     * @param commentaar Het ingevoerde commentaar.
     * @param transitieNaam De gekozen transitie voor de taak.
     */
    public JbpmTaakCommand(final Long taakId, final Set<String> taakVariabelenVerwijderd, final Map<String, Serializable> taakVariabelenGewijzigd,
                           final String commentaar, final String transitieNaam) {
        this.taakId = taakId;
        this.taakVariabelenVerwijderd = taakVariabelenVerwijderd;
        this.taakVariabelenGewijzigd = taakVariabelenGewijzigd;
        this.commentaar = commentaar;
        this.transitieNaam = transitieNaam;
    }

    @Override
    public Void doInContext(final JbpmContext jbpmContext) {

        final TaskInstance taskInstance = jbpmContext.getTaskInstanceForUpdate(taakId);

        pasTaakVariabelenAan(taskInstance);

        if (commentaar != null && !"".equals(commentaar)) {
            taskInstance.addComment(commentaar);
        }

        if (transitieNaam != null) {
            if ("".equals(transitieNaam)) {
                taskInstance.end();
            } else {
                taskInstance.end(transitieNaam);
            }
        }

        return null;
    }

    /**
     * Past de variabelen aan voor de meegegeven taak.
     * @param taskInstance De taak waarvan de variabelen worden aangepast.
     */
    private void pasTaakVariabelenAan(final TaskInstance taskInstance) {
        if (taakVariabelenVerwijderd != null) {
            for (final String huidigeVariabele : taakVariabelenVerwijderd) {
                taskInstance.deleteVariable(huidigeVariabele);
            }
        }

        if (taakVariabelenGewijzigd != null) {
            for (final Map.Entry<String, Serializable> variabele : taakVariabelenGewijzigd.entrySet()) {
                taskInstance.setVariable(variabele.getKey(), variabele.getValue());
            }
        }
    }
}
