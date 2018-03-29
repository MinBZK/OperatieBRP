/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.foutafhandeling.FoutafhandelingConstants;
import org.springframework.stereotype.Component;

/**
 * Verwerk de gemaakte beheerderkeuze.
 * @see MaakBeheerderskeuzesAction
 */
@Component("uc202VerwerkBeheerderkeuzeAction")
public final class VerwerkBeheerderkeuzeAction implements SpringAction {

    /**
     * JBPM Variabele waarin de beheerderkeuze wordt opgeslagen.
     */
    public static final String VARIABELE_BEHEERDER_KEUZE = "beheerderkeuze";

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String OPNIEUW_PROBEREN = "restartAtSynchronisatieNaarBrp";
    private static final String BEEINDIGEN = "end";

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);
        final String keuze = (String) parameters.get(FoutafhandelingConstants.RESTART);

        final Map<String, Object> result = new HashMap<>();
        final boolean afbreken = MaakBeheerderskeuzesAction.KEUZE_AFBREKEN.equals(keuze) || MaakBeheerderskeuzesAction.KEUZE_AFBREKEN_MET_PF03.equals(keuze);

        boolean doorgaan = MaakBeheerderskeuzesAction.KEUZE_NIEUW.equals(keuze);
        doorgaan = doorgaan || MaakBeheerderskeuzesAction.KEUZE_NEGEREN.equals(keuze);
        doorgaan = doorgaan || MaakBeheerderskeuzesAction.KEUZE_AFKEUREN.equals(keuze);
        doorgaan = doorgaan || MaakBeheerderskeuzesAction.KEUZE_OPNIEUW.equals(keuze);
        doorgaan = doorgaan || MaakBeheerderskeuzesAction.KEUZE_AUTOMATISCH_OPNIEUW.equals(keuze);
        doorgaan = doorgaan || keuze.startsWith(MaakBeheerderskeuzesAction.KEUZE_VERVANGEN_PREFIX);

        if (afbreken) {
            LOG.debug("beheerderkeuze: afbreken");
            result.put(FoutafhandelingConstants.RESTART, BEEINDIGEN);
        } else if (doorgaan) {
            LOG.debug("beheerderkeuze: nieuw, negeren, afkeuren, opnieuw of vervangen");
            result.put(VARIABELE_BEHEERDER_KEUZE, keuze);
            result.put(FoutafhandelingConstants.RESTART, OPNIEUW_PROBEREN);
        } else {
            // Geen speciale actie, deze mogelijkheid wordt gebruikt door Uc811 om een extra keuze toe te voegen
            LOG.debug("beheerderkeuze: proces specifiek: " + keuze);
        }

        LOG.debug("result: {}", result);
        return result;
    }
}
