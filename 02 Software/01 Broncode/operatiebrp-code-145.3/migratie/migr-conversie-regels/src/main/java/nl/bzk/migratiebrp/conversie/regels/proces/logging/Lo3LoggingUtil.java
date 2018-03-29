/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.logging;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

/**
 * Util class om in de logging te controleren of een bepaalde preconditie bestaat.
 */
public final class Lo3LoggingUtil {

    private Lo3LoggingUtil() {
    }

    /**
     * Controle of er een logregel voor de gegeven herkomst is waar de gegeven preconditie in gelogd is.
     * @param soortMeldingCode de preconditie waarop gecontroleerd moet worden
     * @param herkomst de herkomst waarvoor de gegeven preconditie al voor gelogd is
     * @return true als de preconditie in de logregel staat en de gelogde herkomst volledig overeenkomt met de gegeven herkomst.
     */
    public static boolean bevatLogPreconditie(final SoortMeldingCode soortMeldingCode, final Lo3Herkomst herkomst) {
        boolean result = false;
        final Set<LogRegel> logRegels = getLogRegels(soortMeldingCode);
        for (final LogRegel logRegel : logRegels) {
            if (herkomst.equals(logRegel.getLo3Herkomst())) {
                result = true;
                break;
            }
        }
        return result;

    }

    /**
     * Geeft de logregels terug waarin de precondities voorkomt.
     * @param soortMeldingCodes de precondities waarop gecontroleerd moet worden
     * @return de logregels die de opgegeven precondities bevatten
     */
    public static Set<LogRegel> getLogRegels(final SoortMeldingCode... soortMeldingCodes) {
        final Set<LogRegel> gevondenRegels = new HashSet<>();
        final Set<LogRegel> regels = Logging.getLogging().getRegels();
        for (final LogRegel regel : regels) {
            filterPrecondities(gevondenRegels, regel, soortMeldingCodes);
        }
        return gevondenRegels;
    }

    private static void filterPrecondities(final Set<LogRegel> gevondenRegels, final LogRegel regel, final SoortMeldingCode[] soortMeldingCodes) {
        if (regel.getSoortMeldingCode().isPreconditie()) {
            for (final SoortMeldingCode soortMeldingCode : soortMeldingCodes) {
                if (soortMeldingCode.name().equals(regel.getSoortMeldingCode().name())) {
                    gevondenRegels.add(regel);
                }
            }
        }
    }
}
