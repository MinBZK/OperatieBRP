/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.isc.migratie.service.GemeenteService;
import nl.moderniseringgba.isc.migratie.service.Stelsel;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer de bijhoudings gemeente.
 */
@Component("uc306ControleerBijhoudingsGemeenteDecision")
public final class ControleerBijhoudingsGemeenteDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private GemeenteService gemeenteService;

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final GeboorteVerzoekBericht geboorteBericht = (GeboorteVerzoekBericht) parameters.get("input");

        final Integer brpGemeenteCode = geboorteBericht.getBrpGemeente().getCode().intValue();
        final Stelsel stelselBrpGemeente = gemeenteService.geefStelselVoorGemeente(brpGemeenteCode);

        if (!Stelsel.BRP.equals(stelselBrpGemeente)) {
            return "3b. BRP-gemeente";
        }

        return null;
    }
}
