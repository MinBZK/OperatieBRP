/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor;

import java.util.logging.Logger;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.phase.Phase;


/**
 * CXF Interceptor die inkomende berichten archiveert via de archivering service.
 *
 * @see nl.bzk.brp.bevraging.business.service.ArchiveringService
 */
public class ArchiveringInInterceptor extends LoggingInInterceptor {

    /**
     * Constante om het BRP bericht id terug te kunnen vinden in CXF Messages.
     */
    public static final String BRP_BERICHT_ID = "BRP_BERICHT_ID";

    private final Logger logger;

    /**
     * Constructor die de fase zet waarin de interceptor dient te worden aangeroepen en tevens configureert
     * welke eventueel andere interceptoren reeds vooraf uitgevoerd dienen te worden.
     * @param logger De logger die gebruikt dient te worden.
     */
    public ArchiveringInInterceptor(final Logger logger) {
        super(Phase.USER_STREAM);
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
