/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.event.AbortProcessingException;

/**
 * JSF Expressie helper.
 */
public final class ExpressionHelper {

    private ExpressionHelper() {
    }

    /**
     * Geef een boolean waarde.
     *
     * @param expression
     *            expressie die een boolean retourneert
     * @param elContext
     *            el context
     * @return boolean waarde
     */
    public static Boolean getBoolean(final ValueExpression expression, final ELContext elContext) {
        final Boolean result;
        if (expression == null) {
            result = null;
        } else {
            final Object value = expression.getValue(elContext);

            if (value == null || "".equals(value)) {
                result = null;
            } else if (value instanceof Boolean) {
                result = (Boolean) value;
            } else {
                result = Boolean.valueOf(value.toString());
            }
        }

        return result;
    }

    /**
     * Geef een string waarde.
     *
     * @param expression
     *            expressie die een boolean retourneert
     * @param elContext
     *            el context
     * @return string waarde
     */
    public static String getString(final ValueExpression expression, final ELContext elContext) {
        final String result;
        if (expression == null) {
            result = null;
        } else {
            final Object value = expression.getValue(elContext);

            if (value == null) {
                result = null;
            } else if (value instanceof String) {
                result = (String) value;
            } else {
                result = value.toString();
            }
        }

        return result;
    }

    /**
     * Geef een date waarde.
     *
     * @param expression
     *            expressie die een boolean retourneert
     * @param elContext
     *            el context
     * @return date waarde
     */
    public static Date getDate(final ValueExpression expression, final ELContext elContext) {
        final Date result;
        if (expression == null) {
            result = null;
        } else {
            final Object value = expression.getValue(elContext);

            if (value == null || "".equals(value)) {
                result = null;
            } else if (value instanceof Date) {
                result = (Date) value;
            } else {
                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    result = format.parse(value.toString());
                } catch (final ParseException e) {
                    throw new AbortProcessingException("Ongeldige datum: " + value, e);
                }
            }
        }

        return result;
    }
}
