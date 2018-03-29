/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import org.hibernate.Criteria;

/**
 * Filter om process instances te filteren.
 */
public final class BerichtenFilter implements Filter, Serializable {

    private static final long serialVersionUID = 1L;

    private final String kanaal;
    private final String richting;
    private final String bron;
    private final String doel;
    private final String type;
    private final String berichtId;
    private final String processInstanceId;
    private final String correlatieId;

    /**
     * Constructor.
     * @param waarden Map met benodigde waarden.
     */
    public BerichtenFilter(final Map<FilterEnum, String> waarden) {
        super();
        this.kanaal = waarden.get(FilterEnum.KANAAL);
        this.richting = waarden.get(FilterEnum.RICHTING);
        this.bron = waarden.get(FilterEnum.BRON);
        this.doel = waarden.get(FilterEnum.DOEL);
        this.type = waarden.get(FilterEnum.TYPE);
        this.berichtId = waarden.get(FilterEnum.BERICHT_ID);
        this.processInstanceId = waarden.get(FilterEnum.PROCESS_INSTANCE_ID);
        this.correlatieId = waarden.get(FilterEnum.CORRELATIE_ID);
    }

    /**
     * Geef de waarde van kanaal.
     * @return kanaal
     */
    public String getKanaal() {
        return kanaal;
    }

    /**
     * Geef de waarde van richting.
     * @return richting
     */
    public String getRichting() {
        return richting;
    }

    /**
     * Geef de waarde van bron.
     * @return bron
     */
    public String getBron() {
        return bron;
    }

    /**
     * Geef de waarde van doel.
     * @return doel
     */
    public String getDoel() {
        return doel;
    }

    /**
     * Geef de waarde van type.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Geef de waarde van bericht id.
     * @return bericht id
     */
    public String getBerichtId() {
        return berichtId;
    }

    /**
     * Geef de waarde van process instance id.
     * @return process instance id
     */
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * Geef de waarde van correlatie id.
     * @return correlatie id
     */
    public String getCorrelatieId() {
        return correlatieId;
    }

    @Override
    public void applyFilter(final Criteria criteria) {
        throw new UnsupportedOperationException("BerichtenFilter niet geimplementeerd voor hibernate criteria.");
    }

    @Override
    public String getWhereClause(final StartType startType) {
        final StringBuilder result = new StringBuilder();
        addAndClause(result, kanaal, "kanaal");
        addAndClause(result, richting, "richting");
        addAndClause(result, bron, "verzendende_partij");
        addAndClause(result, doel, "ontvangende_partij");
        addAndClause(result, type, "naam");
        addAndClause(result, berichtId, "message_id");
        addAndClause(result, processInstanceId, "process_instance_id");
        addAndClause(result, correlatieId, "correlation_id");

        if (result.length() > 0) {
            switch (startType) {
                case WHERE:
                    result.insert(0, "where ");
                    break;
                case AND:
                    result.insert(0, "and ");
                    break;
                default:
                    // Nothing
                    break;

            }
        }

        return result.toString();
    }

    private void addAndClause(final StringBuilder result, final String value, final String columnName) {
        if (isNotEmpty(value)) {
            if (result.length() > 0) {
                result.append(" and ");
            }

            result.append(columnName).append(" = ?");
        }
    }

    @Override
    public void setWhereClause(final PreparedStatement statement, final int startingIndex) throws SQLException {
        int index = startingIndex;
        if (isNotEmpty(kanaal)) {
            statement.setString(index, kanaal);
            index++;
        }
        if (isNotEmpty(richting)) {
            statement.setString(index, richting);
            index++;
        }
        if (isNotEmpty(bron)) {
            statement.setString(index, bron);
            index++;
        }
        if (isNotEmpty(doel)) {
            statement.setString(index, doel);
            index++;
        }
        if (isNotEmpty(type)) {
            statement.setString(index, type);
            index++;
        }
        if (isNotEmpty(berichtId)) {
            statement.setString(index, berichtId);
            index++;
        }
        if (isNotEmpty(processInstanceId)) {
            statement.setLong(index, Long.valueOf(processInstanceId));
            index++;
        }
        if (isNotEmpty(correlatieId)) {
            statement.setString(index, correlatieId);
        }
    }

    private boolean isNotEmpty(final String value) {
        return value != null && !"".equals(value);
    }
}
