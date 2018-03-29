/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import java.io.Serializable;
import java.util.Date;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * Action listener voor process instances filter tag.
 */
public final class ProcessInstancesFilterActionListener implements ActionListener, Serializable {
    private static final long serialVersionUID = 1L;

    private final ValueExpression targetExpression;
    private final ValueExpression keyExpression;
    private final ValueExpression startDateExpression;
    private final ValueExpression runningExpression;
    private final ValueExpression suspendedExpression;
    private final ValueExpression endedExpression;
    private final ValueExpression anummerExpression;
    private final ValueExpression partijExpression;
    private final ValueExpression ahIdExpression;
    private final ValueExpression processInstanceIdExpression;
    private final ValueExpression processDefinitionExpression;
    private final ValueExpression rootExpression;

    /**
     * Constructor.
     * @param targetExpression expression voor target
     * @param keyExpression expression voor key (filter waarde)
     * @param startDateExpression expression voor start datum (filter waarde)
     * @param runningExpression expression voor indicatie running (filter waarde)
     * @param suspendedExpression expression voor indicatie suspended (filter waarde)
     * @param endedExpression expression voor indicatie ended (filter waarde)
     * @param anummerExpression expression voor anummer (filter waarde)
     * @param partijExpression expression voor partij (filter waarde)
     * @param ahIdExpression expression voor ah-id (filter waarde)
     * @param processInstanceIdExpression expression voor processInstanceId (filter waarde)
     * @param processDefinitionExpression expression voor processDefinition (filter waarde)
     * @param rootExpression expression voor indicatie root proces (filter waarde)
     */
    public ProcessInstancesFilterActionListener(
            final ValueExpression targetExpression,
            final ValueExpression keyExpression,
            final ValueExpression startDateExpression,
            final ValueExpression runningExpression,
            final ValueExpression suspendedExpression,
            final ValueExpression endedExpression,
            final ValueExpression anummerExpression,
            final ValueExpression partijExpression,
            final ValueExpression ahIdExpression,
            final ValueExpression processInstanceIdExpression,
            final ValueExpression processDefinitionExpression,
            final ValueExpression rootExpression) {
        this.targetExpression = targetExpression;
        this.keyExpression = keyExpression;
        this.startDateExpression = startDateExpression;
        this.runningExpression = runningExpression;
        this.suspendedExpression = suspendedExpression;
        this.endedExpression = endedExpression;
        this.anummerExpression = anummerExpression;
        this.partijExpression = partijExpression;
        this.ahIdExpression = ahIdExpression;
        this.processInstanceIdExpression = processInstanceIdExpression;
        this.processDefinitionExpression = processDefinitionExpression;
        this.rootExpression = rootExpression;
    }

    @Override
    public void processAction(final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final String key = ExpressionHelper.getString(keyExpression, elContext);
        final Date startDate = ExpressionHelper.getDate(startDateExpression, elContext);
        final Boolean running = ExpressionHelper.getBoolean(runningExpression, elContext);
        final Boolean suspended = ExpressionHelper.getBoolean(suspendedExpression, elContext);
        final Boolean ended = ExpressionHelper.getBoolean(endedExpression, elContext);
        final String anummer = ExpressionHelper.getString(anummerExpression, elContext);
        final String partij = ExpressionHelper.getString(partijExpression, elContext);
        final String ahId = ExpressionHelper.getString(ahIdExpression, elContext);
        final String processInstanceId = ExpressionHelper.getString(processInstanceIdExpression, elContext);
        final String processDefinition = ExpressionHelper.getString(processDefinitionExpression, elContext);
        final Boolean root = ExpressionHelper.getBoolean(rootExpression, elContext);

        targetExpression.setValue(
                elContext,
                new ProcessInstancesFilter(key, startDate, running, suspended, ended, anummer, partij, ahId, processInstanceId, processDefinition, root));
    }

}
