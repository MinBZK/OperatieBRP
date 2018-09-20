/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.isc.jbpm.jsf.FoutafhandelingPaden;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer de variabelen voor het algemene foutafhandeling proces.
 */
@Component("foutafhandelingControleerInputAction")
public final class ControleerInputAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final List<String> TOEGESTANE_PARAMETERS = Arrays.asList(FoutafhandelingConstants.FOUT,
            FoutafhandelingConstants.FOUTMELDING, FoutafhandelingConstants.STAP,
            FoutafhandelingConstants.INDICATIE_BEHEERDER, FoutafhandelingConstants.PADEN,
            FoutafhandelingConstants.RESTART, FoutafhandelingConstants.BERICHT_LO3,
            FoutafhandelingConstants.BERICHT_BRP, FoutafhandelingConstants.BERICHT_BLOKKERING,
            FoutafhandelingConstants.REGISTRATIE_ID, FoutafhandelingConstants.BRON_GEMEENTE,
            FoutafhandelingConstants.DOEL_GEMEENTE);

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final List<String> parameterNames = new ArrayList<String>(parameters.keySet());
        parameterNames.removeAll(TOEGESTANE_PARAMETERS);
        if (!parameterNames.isEmpty()) {
            throw new IllegalArgumentException("Ongeldige parameters opgegeven aan foutafhandeling: "
                    + parameterNames);
        }

        // if (!parameters.containsKey(FoutafhandelingConstants.BRON_GEMEENTE)) {
        // throw new IllegalArgumentException(
        // "Bron gemeente moet verplicht worden meegegeven aan de foutafhandeling.");
        // }

        final Map<String, Object> result = new HashMap<String, Object>();

        final boolean indBeheerder = controleerIndicatieBeheerder(parameters, result);

        if (indBeheerder) {
            result.put(FoutafhandelingConstants.RESTART, null);
        } else {
            final String restart = (String) parameters.get(FoutafhandelingConstants.RESTART);
            if (restart == null || "".equals(restart)) {
                result.put(FoutafhandelingConstants.INDICATIE_PF, false);
                result.put(FoutafhandelingConstants.INDICATIE_DEBLOKKERING, false);
                result.put(FoutafhandelingConstants.INDICATIE_ANTWOORD, false);
            } else {
                final FoutafhandelingPaden paden = (FoutafhandelingPaden) parameters.get("foutafhandelingPaden");
                if (paden == null) {
                    throw new IllegalArgumentException("Geen foutafhandelingspaden gedefinieerd");
                }

                result.put(FoutafhandelingConstants.INDICATIE_PF, paden.getPf(restart));
                result.put(FoutafhandelingConstants.INDICATIE_DEBLOKKERING, paden.getDeblokkeren(restart));
                result.put(FoutafhandelingConstants.INDICATIE_ANTWOORD, paden.getAntwoord(restart));
            }
        }

        return result;
    }

    private boolean controleerIndicatieBeheerder(
            final Map<String, Object> parameters,
            final Map<String, Object> result) {
        // Indicatie beheerder (default: false)
        if (!parameters.containsKey(FoutafhandelingConstants.INDICATIE_BEHEERDER)
                || !(parameters.get(FoutafhandelingConstants.INDICATIE_BEHEERDER) instanceof Boolean)) {
            result.put(FoutafhandelingConstants.INDICATIE_BEHEERDER, Boolean.FALSE);
            return false;
        } else {
            return (Boolean) parameters.get(FoutafhandelingConstants.INDICATIE_BEHEERDER);
        }
    }
}
