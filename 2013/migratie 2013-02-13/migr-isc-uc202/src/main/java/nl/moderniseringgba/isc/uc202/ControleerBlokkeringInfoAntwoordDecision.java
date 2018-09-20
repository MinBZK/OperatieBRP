/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer blokkering info antwoord.
 */
@Component("uc202ControleerBlokkeringInfoAntwoordDecision")
public final class ControleerBlokkeringInfoAntwoordDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord =
                (BlokkeringInfoAntwoordBericht) parameters.get("blokkeringInfoAntwoordBericht");

        final String result;
        if (blokkeringInfoAntwoord.getStatus() == StatusType.OK
                || blokkeringInfoAntwoord.getStatus() == StatusType.GEBLOKKEERD) {
            final PersoonsaanduidingType persoonsaanduiding = blokkeringInfoAntwoord.getPersoonsaanduiding();
            if (blokkeringInfoAntwoord.getStatus() == StatusType.OK && persoonsaanduiding == null) {
                result = null;
            } else if (persoonsaanduiding == PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA
                    || persoonsaanduiding == PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_RNI) {
                // TODO: Controleren dat BRP vind dat de bijhoudingsverantwoordelijkheid in BRP ligt.
                result = null;
            } else {
                result = "7c. Blokkeringssituatie fout";

            }
        } else {
            result = "7b. Fout";
        }

        return result;
    }
}
