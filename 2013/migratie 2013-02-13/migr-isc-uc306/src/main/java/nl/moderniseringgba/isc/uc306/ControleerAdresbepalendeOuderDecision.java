/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.Map;

import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer de adres bepalende ouder, verwacht 'M' of 'V'.
 */
@Component("uc306ControleerAdresbepalendeOuderDecision")
public final class ControleerAdresbepalendeOuderDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final String adresBepalendeOuder =
                (String) parameters.get(BepaalAdresbepalendeOuderAction.ADRES_BEPALENDE_OUDER);

        if (!BepaalAdresbepalendeOuderAction.GELDIGE_GESLACHTSAANDUIDINGEN.contains(adresBepalendeOuder)) {
            return "2b. Fout";
        }

        return null;
    }
}
