/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import nl.bzk.brp.tooling.apitest.util.BrpLoggingAppender;
import org.apache.logging.log4j.LogManager;

/**
 * Implementatie van {@link LogControleService}
 */
final class LogControleServiceImpl implements LogControleService {

    private static final BrpLoggingAppender BRP_LOGGING_APPENDER;

    static {
        final org.apache.logging.log4j.Logger logger = LogManager.getLogger("nl.bzk.brp");
        org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger) logger;
        org.apache.logging.log4j.core.LoggerContext context = coreLogger.getContext();
        org.apache.logging.log4j.core.config.Configuration configuration = context.getConfiguration();
        BRP_LOGGING_APPENDER = (BrpLoggingAppender) configuration.getAppenders().get("BrpLoggingAppender");
    }


    @Override
    public void reset() {
        BRP_LOGGING_APPENDER.clear();
    }

    @Override
    public boolean heeftLogEventOntvangenMetCode(final String code) {
        return BRP_LOGGING_APPENDER.heeftLogEventOntvangenMetCode(code);
    }

    @Override
    public boolean heeftLogEventOntvangenMetMessage(final String logLevel, final String messageTextRegex) {
        return BRP_LOGGING_APPENDER.heeftMessageTextOntvangen(logLevel, messageTextRegex);
    }
}
