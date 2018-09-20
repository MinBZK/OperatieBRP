/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    private final String correlatieId;

    /**
     * Constructor.
     * 
     * @param kanaal
     *            kanaal
     * @param richting
     *            richting
     * @param bron
     *            bron
     * @param doel
     *            doel
     * @param type
     *            type
     * @param berichtId
     *            bericht id
     * @param correlatieId
     *            correlatie id
     */
    public BerichtenFilter(
        final String kanaal,
        final String richting,
        final String bron,
        final String doel,
        final String type,
        final String berichtId,
        final String correlatieId)
    {
        super();
        this.kanaal = kanaal;
        this.richting = richting;
        this.bron = bron;
        this.doel = doel;
        this.type = type;
        this.berichtId = berichtId;
        this.correlatieId = correlatieId;
    }

    /**
     * Geef de waarde van kanaal.
     *
     * @return kanaal
     */
    public String getKanaal() {
        return kanaal;
    }

    /**
     * Geef de waarde van richting.
     *
     * @return richting
     */
    public String getRichting() {
        return richting;
    }

    /**
     * Geef de waarde van bron.
     *
     * @return bron
     */
    public String getBron() {
        return bron;
    }

    /**
     * Geef de waarde van doel.
     *
     * @return doel
     */
    public String getDoel() {
        return doel;
    }

    /**
     * Geef de waarde van type.
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Geef de waarde van bericht id.
     *
     * @return bericht id
     */
    public String getBerichtId() {
        return berichtId;
    }

    /**
     * Geef de waarde van correlatie id.
     *
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
            statement.setString(index++, kanaal);
        }
        if (isNotEmpty(richting)) {
            statement.setString(index++, richting);
        }
        if (isNotEmpty(bron)) {
            statement.setString(index++, bron);
        }
        if (isNotEmpty(doel)) {
            statement.setString(index++, doel);
        }
        if (isNotEmpty(type)) {
            statement.setString(index++, type);
        }
        if (isNotEmpty(berichtId)) {
            statement.setString(index++, berichtId);
        }
        if (isNotEmpty(correlatieId)) {
            statement.setString(index++, correlatieId);
        }
    }

    private boolean isNotEmpty(final String value) {
        return value != null && !"".equals(value);
    }
}
