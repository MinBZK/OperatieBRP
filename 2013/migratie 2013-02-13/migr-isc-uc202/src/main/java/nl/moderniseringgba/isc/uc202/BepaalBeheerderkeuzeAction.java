/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Bepaal beheerder keuze.
 */
@Component("uc202BepaalBeheerderkeuzeAction")
public final class BepaalBeheerderkeuzeAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String VARIABELE_RESTART = "restart";
    private static final String VARIABELE_BEHEERDER_KEUZE = "beheerderKeuze";

    private static final String RESTART_VERVANGEN = "plVervangen";
    private static final String RESTART_TOEVOEGEN = "plToevoegen";

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final String restart = (String) parameters.get(VARIABELE_RESTART);

        final Map<String, Object> result = new HashMap<String, Object>();
        if (RESTART_TOEVOEGEN.equals(restart)) {
            result.put(VARIABELE_BEHEERDER_KEUZE, SearchResultaatType.TOEVOEGEN);
        } else if (RESTART_VERVANGEN.equals(restart)) {
            result.put(VARIABELE_BEHEERDER_KEUZE, SearchResultaatType.VERVANGEN);
        }

        return result;
    }
}
