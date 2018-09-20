/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor;

import java.util.logging.Logger;

import org.apache.cxf.interceptor.LoggingOutInterceptor;


/**
 * CXF Interceptor voor het archiveren/loggen van het uitgaande bericht. Hiervoor wordt gebruik gemaakt
 * van een specifieke logger, welke gezet wordt in de constructor.
 */
public class ArchiveringUitInterceptor extends LoggingOutInterceptor {

    private final Logger logger;

    /**
     * Constructor die de logger zet welke gebruikt wordt voor het archiveren/loggen van uitgaande berichten.
     *
     * @param logger de logger die gebruikt dient te worden.
     */
    public ArchiveringUitInterceptor(final Logger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger mag niet leeg (null) zijn.");
        }
        this.logger = logger;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
