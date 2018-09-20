/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.jsf.mig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

public class ListBerichtenForProcessInstanceActionListener implements JbpmActionListener {

    private static final String FIND_BY_PROCESS_SQL =
            "select id, tijdstip, kanaal, richting, message_id, correlation_id, bericht, naam, process_instance_id "
                    + "from mig_berichten where process_instance_id = ?";

    private static final BerichtMapper BERICHT_MAPPER = new BerichtMapper();

    private final ValueExpression targetExpression;
    private final ValueExpression processInstanceExpression;

    public ListBerichtenForProcessInstanceActionListener(
            final ValueExpression processInstanceExpression,
            final ValueExpression targetExpression) {
        this.processInstanceExpression = processInstanceExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public String getName() {
        return "listBerichtenForProcessInstance";
    }

    @Override
    public void handleAction(final JbpmJsfContext context, final ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final ProcessInstance rootProcessInstance =
                    ProcessInstanceUtil.getRootProcessInstance((ProcessInstance) processInstanceExpression
                            .getValue(elContext));

            final List<ProcessInstance> allProcessInstances =
                    ProcessInstanceUtil.getProcessInstances(context.getJbpmContext(), rootProcessInstance);

            final List<Bericht> berichten = new ArrayList<Bericht>();
            for (final ProcessInstance processInstance : allProcessInstances) {
                berichten.addAll(getBerichten(context.getJbpmContext(), processInstance.getId()));
            }

            Collections.sort(berichten, new BerichtComparator());

            targetExpression.setValue(elContext, Collections.unmodifiableCollection(berichten));
            context.selectOutcome("success");
        } catch (final Exception ex) {
            context.setError("Error loading berichten list", ex);
            return;
        }
    }

    private List<Bericht> getBerichten(final JbpmContext jbpmContext, final long processInstanceId) {
        try {
            @SuppressWarnings("deprecation")
            final Connection connection = jbpmContext.getSession().connection();
            final PreparedStatement statement = connection.prepareStatement(FIND_BY_PROCESS_SQL);
            statement.setLong(1, processInstanceId);

            statement.execute();
            final ResultSet rs = statement.getResultSet();

            final List<Bericht> berichten = new ArrayList<Bericht>();
            while (rs.next()) {
                berichten.add(BERICHT_MAPPER.map(rs));
            }
            statement.close();

            return berichten;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
