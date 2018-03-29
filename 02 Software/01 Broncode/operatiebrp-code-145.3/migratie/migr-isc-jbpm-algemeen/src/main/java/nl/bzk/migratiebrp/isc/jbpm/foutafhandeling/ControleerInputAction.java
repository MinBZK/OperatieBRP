/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.common.jsf.FoutafhandelingPaden;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Controleer de variabelen voor het algemene foutafhandeling proces.
 */
@Component("foutafhandelingControleerInputAction")
public final class ControleerInputAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final List<String> TOEGESTANE_PARAMETERS =
            Arrays.asList(
                    FoutafhandelingConstants.FOUT,
                    FoutafhandelingConstants.FOUTMELDING,
                    FoutafhandelingConstants.STAP,
                    FoutafhandelingConstants.INDICATIE_BEHEERDER,
                    FoutafhandelingConstants.PADEN,
                    FoutafhandelingConstants.RESTART,
                    FoutafhandelingConstants.BERICHT_LO3,
                    FoutafhandelingConstants.BERICHT_BRP,
                    FoutafhandelingConstants.BERICHT_BLOKKERING,
                    FoutafhandelingConstants.REGISTRATIE_ID,
                    FoutafhandelingConstants.BRON_PARTIJ_CODE,
                    FoutafhandelingConstants.DOEL_PARTIJ_CODE,
                    FoutafhandelingConstants.INDICATIE_CYCLUS_FOUT,
                    FoutafhandelingConstants.BERICHT_OVERIG,
                    FoutafhandelingConstants.AFHANDELING_TYPE,
                    FoutafhandelingConstants.PERSOONSLIJSTOVERZICHT);

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final List<String> parameterNames = new ArrayList<>(parameters.keySet());
        parameterNames.removeAll(TOEGESTANE_PARAMETERS);
        if (!parameterNames.isEmpty()) {
            LOG.info("Ongeldige parameters opgegeven aan foutafhandeling: " + parameterNames);
            throw new IllegalArgumentException("Ongeldige parameters opgegeven aan foutafhandeling: " + parameterNames);
        }

        final Map<String, Object> result = new HashMap<>();

        final boolean indBeheerder = controleerIndicatieBeheerder(parameters, result);

        if (indBeheerder) {
            result.put(FoutafhandelingConstants.RESTART, null);
        } else {
            final String restart = (String) parameters.get(FoutafhandelingConstants.RESTART);
            if (restart == null || "".equals(restart)) {
                result.put(FoutafhandelingConstants.INDICATIE_PF, false);
                result.put(FoutafhandelingConstants.INDICATIE_VB, false);
            } else {
                final FoutafhandelingPaden paden = (FoutafhandelingPaden) parameters.get("foutafhandelingPaden");
                if (paden == null) {
                    LOG.info("Geen foutafhandelingspaden gedefinieerd");
                    throw new IllegalArgumentException("Geen foutafhandelingspaden gedefinieerd");
                }

                result.put(FoutafhandelingConstants.INDICATIE_PF, paden.getPf(restart));
                result.put(FoutafhandelingConstants.INDICATIE_VB, paden.getVb(restart));
            }
        }

        return result;
    }

    private boolean controleerIndicatieBeheerder(final Map<String, Object> parameters, final Map<String, Object> result) {
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
