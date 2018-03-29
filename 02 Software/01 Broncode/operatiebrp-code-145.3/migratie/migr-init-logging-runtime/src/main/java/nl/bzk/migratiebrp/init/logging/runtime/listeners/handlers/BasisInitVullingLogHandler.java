/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBerichtType;
import nl.bzk.migratiebrp.init.logging.runtime.service.LoggingService;

/**
 * Basis methoden voor init vulling verwerking.
 */
public class BasisInitVullingLogHandler {
    /**
     * Message gebruikt wanneer een logregel niet gevonden kan worden.
     */
    protected static final String KON_GEEN_LOG_VINDEN_VOOR_MESSAGE_ID_CORRELATION_ID = "Kon geen log vinden voor messageId: {}, correlationId: {}";
    private static final Pattern CORRELATIE_ID_PATTERN = Pattern.compile(
            String.format("(%s)-([0-9]*)", Arrays.stream(SyncBerichtType.values()).map(SyncBerichtType::getType).collect(Collectors.joining("|"))),
            Pattern.CASE_INSENSITIVE);
    private static final int IDENTIFIER_MATCHER_GROUP = 2;

    private LoggingService loggingService;

    BasisInitVullingLogHandler() {
    }

    /**
     * Geeft de LoggingService.
     * @return LoggingService
     */
    final LoggingService getLoggingService() {
        return loggingService;
    }

    @Inject
    public void setLoggingService(final LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    /**
     * Haalt identifier uit het correlatieId. Formaat zou [type]-[identifier] moeten zijn.
     * @param correlationId String
     * @return Long identifier
     */
    protected final String extractIdentifier(final String correlationId) {
        return getMatcher(correlationId).group(IDENTIFIER_MATCHER_GROUP);
    }

    private Matcher getMatcher(final String correlationId) {
        if (correlationId == null) {
            throw new IllegalArgumentException("CorrelatieId is null");
        }

        Matcher matcher = CORRELATIE_ID_PATTERN.matcher(correlationId);
        if (!matcher.matches()) {
            if (!Pattern.compile("[0-9]{10}").matcher(correlationId).matches()) {
                throw new IllegalArgumentException("CorrelatieId '" + correlationId + "' voldoet niet aan de naamgeving.");
            }
            // correlationId bevat alleen een anummer, prefix toevoegen
            matcher = CORRELATIE_ID_PATTERN.matcher(SyncBerichtType.PERSOON.getType() + "-" + correlationId);
            matcher.matches();

        }
        return matcher;
    }
}
