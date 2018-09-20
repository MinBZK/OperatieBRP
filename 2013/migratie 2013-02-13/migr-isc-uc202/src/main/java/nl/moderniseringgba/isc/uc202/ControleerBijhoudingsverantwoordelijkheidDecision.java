/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.mvi.impl.PlSyncBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.isc.migratie.service.GemeenteService;
import nl.moderniseringgba.isc.migratie.service.Stelsel;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer blokkering info antwoord.
 */
@Component("uc202ControleerBijhoudingsverwantwoordelijkheidDecision")
public final class ControleerBijhoudingsverantwoordelijkheidDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    /* ************************************************************************************************************* */

    @Inject
    private GemeenteService gemeenteService;

    public void setGemeenteService(final GemeenteService gemeenteService) {
        this.gemeenteService = gemeenteService;
    }

    /* ************************************************************************************************************* */

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);
        final PlSyncBericht plSync = (PlSyncBericht) parameters.get("input");

        final Stelsel stelselUitPlSync =
                gemeenteService.geefStelselVoorGemeente(geefBijhoudingsgemeente(plSync.getLo3Persoonslijst()));

        if (stelselUitPlSync == Stelsel.GBA) {
            // bijhoudingsverantwoordelijkheid wel bij GBA
            return null;
        } else {
            // bijhoudingsverantwoordelijkheid niet bij GBA
            return "5a. Bijhoudingsverantwoordelijkheid niet bij LO3";
        }
    }

    private int geefBijhoudingsgemeente(final Lo3Persoonslijst pl) {
        final String gemeenteCode =
                pl.getVerblijfplaatsStapel().iterator().next().getInhoud().getGemeenteInschrijving().getCode();

        return Integer.valueOf(gemeenteCode);
    }

}
