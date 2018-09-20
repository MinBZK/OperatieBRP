/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleert of de gegevens van het Tb01-bericht valide en bruikbaar zijn.
 */
@Component("uc307ControleerTb01BerichtDecision")
public final class ControleerTb01BerichtDecision implements SpringDecision {

    /**
     * Variabele voor logging.
     */
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Tb01Bericht tb01Bericht = (Tb01Bericht) parameters.get("tb01_bericht");

        final Lo3OuderInhoud moederGegevens;

        if (tb01Bericht != null) {
            final String gezochtePersoon = tb01Bericht.getHeader(Lo3HeaderVeld.GEZOCHTE_PERSOON);

            // Binnen BRP alleen inschrijving op M (moeder)
            if ("M".equals(gezochtePersoon)) {
                moederGegevens = UC307Utils.actueleMoederGegevens(tb01Bericht.getLo3Persoonslijst());
            } else {
                moederGegevens = null;
            }
        } else {
            moederGegevens = null;
        }

        if (moederGegevens == null) {
            return UC307Constants.INSCHRIJVING_OP_MOEDER_MISLUKT;
        }

        // happy flow ;-)
        return null;
    }
}
