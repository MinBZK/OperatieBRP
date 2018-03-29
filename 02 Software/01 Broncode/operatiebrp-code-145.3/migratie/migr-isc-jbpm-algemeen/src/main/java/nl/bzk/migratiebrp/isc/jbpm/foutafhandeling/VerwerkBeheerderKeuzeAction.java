/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.isc.jbpm.common.jsf.FoutafhandelingPaden;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Verwerk de keuze van de beheerder.
 */
@Component("foutafhandelingVerwerkBeheerderKeuzeAction")
public final class VerwerkBeheerderKeuzeAction implements SpringAction {

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        final Map<String, Object> result = new HashMap<>();

        final String restart = (String) parameters.get(FoutafhandelingConstants.RESTART);
        if (restart == null || "".equals(restart)) {
            throw new IllegalArgumentException("Restart variabele is niet gevuld na beheerderskeuze");
        }

        final FoutafhandelingPaden paden = (FoutafhandelingPaden) parameters.get("foutafhandelingPaden");
        if (paden == null) {
            throw new IllegalArgumentException("Geen foutafhandelingspaden gedefinieerd");
        }

        result.put(FoutafhandelingConstants.INDICATIE_PF, paden.getPf(restart));

        return result;
    }
}
