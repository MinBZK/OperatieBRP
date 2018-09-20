/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Verwerk de gemaakte beheerderkeuze.
 * 
 * @see MaakBeheerderskeuzesAction
 */
@Component("uc202VerwerkBeheerderkeuzeAction")
public final class VerwerkBeheerderkeuzeAction implements SpringAction {

    /**
     * JBPM Variabele waarin de indicatie (Boolean) wordt aangegeven dat de beheer heeft gekozen voor 'Opnemen als
     * nieuwe PL'.
     */
    public static final String VARIABLE_INDICATIE_NIEUW = "beheerderkeuzeNieuw";
    /**
     * JBPM Variabele waarin wordt aangegeven (Long -> anummer) dat de beheer heeft gekozen voor 'Vervang PL'.
     */
    public static final String VARIABLE_INDICATIE_VERVANG = "beheerderkeuzeVervang";

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String RESTART_VARIABELE = "restart";
    private static final String OPNIEUW_PROBEREN = "restartAtSynchronisatieNaarBrp";
    private static final String BEEINDIGEN = "end";

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);
        final String keuze = (String) parameters.get(RESTART_VARIABELE);

        final Map<String, Object> result;
        if (MaakBeheerderskeuzesAction.KEUZE_NIEUW.equals(keuze)) {
            result = maakKeuzeNieuw();
        } else if (MaakBeheerderskeuzesAction.KEUZE_NEGEREN.equals(keuze)) {
            result = maakAndereKeuze(BEEINDIGEN);
        } else if (MaakBeheerderskeuzesAction.KEUZE_AFKEUREN.equals(keuze)) {
            result = maakAndereKeuze(BEEINDIGEN);
        } else if (keuze.startsWith(MaakBeheerderskeuzesAction.PREFIX_ANUMMER)) {
            result = maakKeuzeVervang(keuze);
        } else {
            // Deze is belangrijk voor UC811
            result = maakAndereKeuze(keuze);
        }

        LOG.debug("result: {}", result);
        return result;
    }

    private Map<String, Object> maakKeuzeNieuw() {
        final Map<String, Object> result = new HashMap<>();
        result.put(VARIABLE_INDICATIE_NIEUW, Boolean.TRUE);
        result.put(VARIABLE_INDICATIE_VERVANG, null);
        result.put(RESTART_VARIABELE, OPNIEUW_PROBEREN);

        return result;
    }

    private Map<String, Object> maakKeuzeVervang(final String keuze) {
        final Map<String, Object> result = new HashMap<>();
        result.put(VARIABLE_INDICATIE_NIEUW, null);
        result.put(VARIABLE_INDICATIE_VERVANG, Long.valueOf(keuze.substring(MaakBeheerderskeuzesAction.PREFIX_ANUMMER.length())));
        result.put(RESTART_VARIABELE, OPNIEUW_PROBEREN);

        return result;
    }

    private Map<String, Object> maakAndereKeuze(final String keuze) {
        final Map<String, Object> result = new HashMap<>();
        result.put(VARIABLE_INDICATIE_NIEUW, null);
        result.put(VARIABLE_INDICATIE_VERVANG, null);
        result.put(RESTART_VARIABELE, keuze);

        return result;
    }

}
