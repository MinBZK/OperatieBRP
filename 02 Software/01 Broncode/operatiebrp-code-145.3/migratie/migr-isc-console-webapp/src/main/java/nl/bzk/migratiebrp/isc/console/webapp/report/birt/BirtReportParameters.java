/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.webapp.report.birt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import nl.bzk.migratiebrp.isc.console.webapp.report.ReportException;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;

/**
 * Helper class om parameters voor BIRT reports te converteren.
 */
public final class BirtReportParameters {

    private BirtReportParameters() {
        // Niet instantieerbaar
    }

    /**
     * Converteer een rapport parameter.
     * @param definition parameter definitie
     * @param parameterValue parameter waarde
     * @return rapport parameter in de vorm zoals aangegeven in data type
     * @throws ReportException bij conversie fouten
     */
    public static Object convertTo(final IScalarParameterDefn definition, final Object parameterValue) throws ReportException {
        final Object result;
        if (parameterValue instanceof String) {
            result = convertStringTo(definition, (String) parameterValue);
        } else if (parameterValue instanceof String[]) {
            final String[] parameterValues = (String[]) parameterValue;

            result = parameterValues.length == 0 ? null : convertStringTo(definition, parameterValues[0]);
        } else {
            result = parameterValue;
        }
        return result;
    }

    private static Object convertStringTo(final IScalarParameterDefn definition, final String parameterValue) throws ReportException {
        final Object result;
        switch (definition.getDataType()) {
            case IParameterDefn.TYPE_DATE:
                result = convertStringToDate(definition.getDisplayFormat(), parameterValue);
                break;
            case IParameterDefn.TYPE_INTEGER:
                result = convertStringToInteger(parameterValue);
                break;
            case IParameterDefn.TYPE_STRING:
                result = parameterValue;
                break;
            default:
                throw new ReportException("Onbekend datatype: " + definition);
        }
        return result;
    }

    private static Date convertStringToDate(final String format, final String value) throws ReportException {
        final SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return new java.sql.Date(formatter.parse(value).getTime());
        } catch (final ParseException e) {
            throw new ReportException("Datum '" + value + "' is niet volgens opmaak '" + format + "'.", e);
        }
    }

    private static Object convertStringToInteger(final String value) throws ReportException {
        try {
            return Integer.valueOf(value);
        } catch (final NumberFormatException e) {
            throw new ReportException("Nummer '" + value + "' is niet een nummer.", e);
        }
    }
}
