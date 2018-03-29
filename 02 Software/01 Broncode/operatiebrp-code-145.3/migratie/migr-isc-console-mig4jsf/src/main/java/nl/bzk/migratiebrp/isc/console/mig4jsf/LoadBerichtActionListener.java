/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import nl.bzk.migratiebrp.isc.console.mig4jsf.JbpmSqlHelper.Sql;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.Bericht;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.BerichtMapper;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import org.jbpm.JbpmContext;
import org.jbpm.jsf.JbpmJsfContext;

/**
 * Laad bericht.
 */
public final class LoadBerichtActionListener extends AbstractActionListener {

    private static final String FIND_BY_ID_SQL = "select " + BerichtMapper.COLUMNS + " from mig_bericht where id = ?";
    private static final BerichtMapper BERICHT_MAPPER = new BerichtMapper();

    private final ValueExpression idExpression;
    private final ValueExpression targetExpression;

    /**
     * Constructor.
     * @param idExpression id expression
     * @param targetExpression target expression
     */
    public LoadBerichtActionListener(final ValueExpression idExpression, final ValueExpression targetExpression) {
        super("loadBericht");
        this.idExpression = idExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final Object idValue = idExpression.getValue(elContext);
        if (idValue == null) {
            context.setError("Error loading process", "The ID value is null");
            return;
        }
        final long id;
        if (idValue instanceof Long) {
            id = (Long) idValue;
        } else {
            id = Long.parseLong(idValue.toString());
        }

        final Bericht bericht = getBericht(context.getJbpmContext(), id);
        targetExpression.setValue(elContext, bericht);
        context.selectOutcome("success");
    }

    private Bericht getBericht(final JbpmContext jbpmContext, final long berichtId) {
        return JbpmSqlHelper.execute(jbpmContext, new Sql<Bericht>() {
            @Override
            public Bericht execute(final Connection connection) throws SQLException {
                Bericht result;
                try (final PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
                    statement.setLong(JdbcConstants.COLUMN_1, berichtId);

                    statement.execute();
                    try (final ResultSet rs = statement.getResultSet()) {

                        result = rs.next() ? BERICHT_MAPPER.map(rs) : null;
                    }
                }

                return result;
            }
        });
    }
}
