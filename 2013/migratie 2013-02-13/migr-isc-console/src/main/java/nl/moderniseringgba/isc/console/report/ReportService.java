/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.console.report;

import java.util.Map;

/**
 * Rapport service.
 */
public interface ReportService {

    /**
     * Voer een rapport uit.
     * 
     * @param report
     *            rapport
     * @param parameters
     *            parameters
     * @return rapport filenaam
     * @throws ReportException
     *             bij fouten
     */
    String genereerReport(String report, Map<String, Object> parameters) throws ReportException;
}
