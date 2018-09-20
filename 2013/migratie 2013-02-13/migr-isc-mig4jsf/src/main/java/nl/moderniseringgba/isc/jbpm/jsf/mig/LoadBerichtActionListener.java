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

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jbpm.JbpmContext;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

/**
 *
 */
public final class LoadBerichtActionListener implements JbpmActionListener {

    private static final String FIND_BY_ID_SQL =
            "select id, tijdstip, kanaal, richting, message_id, correlation_id, bericht, naam, process_instance_id "
                    + "from mig_berichten where id = ?";

    private static final BerichtMapper BERICHT_MAPPER = new BerichtMapper();

    private final ValueExpression idExpression;
    private final ValueExpression targetExpression;

    public LoadBerichtActionListener(final ValueExpression idExpression, final ValueExpression targetExpression) {
        this.idExpression = idExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public String getName() {
        return "loadBericht";
    }

    @Override
    public void handleAction(final JbpmJsfContext context, final ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();

            final Object idValue = idExpression.getValue(elContext);
            if (idValue == null) {
                context.setError("Error loading process", "The ID value is null");
                return;
            }
            final long id;
            if (idValue instanceof Long) {
                id = ((Long) idValue).longValue();
            } else {
                id = Long.valueOf(idValue.toString()).longValue();
            }

            final Bericht bericht = getBericht(context.getJbpmContext(), id);
            targetExpression.setValue(elContext, bericht);
            context.selectOutcome("success");
        } catch (final Exception ex) {
            context.setError("Error loading berichten list", ex);
            return;
        }
    }

    private Bericht getBericht(final JbpmContext jbpmContext, final long berichtId) {
        try {
            @SuppressWarnings("deprecation")
            final Connection connection = jbpmContext.getSession().connection();
            final PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL);
            statement.setLong(1, berichtId);

            statement.execute();
            final ResultSet rs = statement.getResultSet();

            if (rs.next()) {
                return BERICHT_MAPPER.map(rs);
            }
            statement.close();

            return null;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
