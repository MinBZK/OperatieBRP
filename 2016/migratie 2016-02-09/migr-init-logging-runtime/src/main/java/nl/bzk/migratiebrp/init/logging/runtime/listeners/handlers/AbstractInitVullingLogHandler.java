/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBerichtType;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.runtime.service.LoggingService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Basis methoden voor init vulling verwerking.
 */
public abstract class AbstractInitVullingLogHandler {
    /** Message gebruikt wanneer een logregel niet gevonden kan worden. */
    protected static final String KON_GEEN_LOG_VINDEN_VOOR_MESSAGE_ID_CORRELATION_ID =
            "Kon geen log vinden voor messageId: {}, correlationId: {}";
    private static final Pattern CORRELATIE_ID_PATTERN = Pattern.compile("(IV.PERS|IV.IND|IV.AUT)-([0-9]*)", Pattern.CASE_INSENSITIVE);
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private LoggingService loggingService;

    /**
     * Geeft de LoggingService.
     * 
     * @return LoggingService
     */
    protected final LoggingService getLoggingService() {
        return loggingService;
    }

    /**
     * Haalt het bericht type uit het correlatieId. Formaat zou [type]-[identifier] moeten zijn.
     * 
     * @param correlationId
     *            String
     * @return SyncBerichtType bericht type
     */
    protected final SyncBerichtType extractSyncType(final String correlationId) {
        return SyncBerichtType.valueOfType(getMatcher(correlationId).group(1));
    }

    /**
     * Haalt identifier uit het correlatieId. Formaat zou [type]-[identifier] moeten zijn.
     * 
     * @param correlationId
     *            String
     * @return Long identifier
     */
    protected final Long extractIdentifier(final String correlationId) {
        return Long.valueOf(getMatcher(correlationId).group(2));
    }

    private Matcher getMatcher(final String correlationId) {
        if (correlationId == null) {
            throw new IllegalArgumentException("CorrelatieId is null");
        }

        Matcher matcher = CORRELATIE_ID_PATTERN.matcher(correlationId);
        if (!matcher.matches()) {
            // TODO: Persoon bericht ook voorzien van prefix, dan moet de onderstaande check weg
            // Kan zijn dat dit een persoon bericht is en dat deze nog niet een prefix heeft.
            if (!Pattern.compile("[0-9]{10}").matcher(correlationId).matches()) {
                throw new IllegalArgumentException("CorrelatieId '" + correlationId + "' voldoet niet aan de naamgeving.");
            }
            // correlationId bevat alleen een anummer, prefix toevoegen
            matcher = CORRELATIE_ID_PATTERN.matcher(SyncBerichtType.PERSOON.getType() + "-" + correlationId);
            matcher.matches();

        }
        return matcher;
    }

    /**
     * Vul de init vulling log.
     * 
     * @param log
     *            het log object
     * @param foutmelding
     *            foutmelding
     * @param status
     *            status
     */
    protected final void fillLog(final InitVullingLog log, final String foutmelding, final String status) {
        log.setFoutmelding(foutmelding);
        log.setConversieResultaat(status);
        log.setPreconditie(findPreconditie(foutmelding));
        log.setFoutCategorie(findCategorie(foutmelding));
    }

    /**
     * Zoek het categorienummer in de melding.
     * 
     * @param melding
     *            De melding waar de fout categorie in kan staan.
     * @return categorienummer waar de fout in zit.
     */
    private Integer findCategorie(final String melding) {
        String categorie = null;
        if (melding != null) {
            final Pattern pattern = Pattern.compile("^Categorie (\\d{2})");
            final Matcher matcher = pattern.matcher(melding);
            if (matcher.find()) {
                categorie = matcher.group(1);
            }
        }
        if (categorie != null) {
            try {
                return Integer.parseInt(categorie);
            } catch (final NumberFormatException e) {
                LOG.debug("Gevonden categorienummer is geen getal.");
            }
        }
        return null;
    }

    /**
     * Find the preconditie in the error message.
     * 
     * @param melding
     *            The melding with the possible preconditie.
     * @return preconditie or null.
     */
    private String findPreconditie(final String melding) {
        String preconditie = null;
        if (melding != null) {
            final Pattern pattern = Pattern.compile("PRE\\d{3}");
            final Matcher matcher = pattern.matcher(melding);
            if (matcher.find()) {
                preconditie = matcher.group();
            }
        }
        return preconditie;
    }
}
