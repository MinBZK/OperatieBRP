/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc308;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.BrpBijhoudingVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Valideer het ErkenningVerzoekBericht.
 */
@Component("uc308ControleerValidatieBrpBijhoudingVerzoekBerichtDecision")
public final class ControleerValidatieBrpBijhoudingVerzoekBerichtDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final BrpBijhoudingVerzoekBericht erkenningVerzoekBericht =
                (BrpBijhoudingVerzoekBericht) parameters.get(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT);

        if (valideerBrpBijhoudingVerzoekBerichtgegevens(erkenningVerzoekBericht)) {
            return null;
        } else {
            return UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT;
        }
    }

    /**
     * Controleert of de inhoud alle benodigde informatie bevat om verder in het proces een Tb02 bericht op te kunnen
     * stellen. Benodigd zijn: 1) Aktenummer 2) Registratiegemeente 3) Ingangsdatum geldigheid - BrpPersoonslijst
     * (indien er voor het vullen hiervan gegevens ontbreken zal deze null zijn en hier worden afgevangen.
     * 
     * @param brpBijhoudingVerzoekBericht
     *            Het ontvangen BrpBijhoudingVerzoekBericht.
     * @return True indien de benodigde gegevens aanwezig zijn, false in alle andere gevallen.
     */
    private boolean valideerBrpBijhoudingVerzoekBerichtgegevens(
            final BrpBijhoudingVerzoekBericht brpBijhoudingVerzoekBericht) {

        boolean gevalideerd = false;

        if (brpBijhoudingVerzoekBericht != null && brpBijhoudingVerzoekBericht.getBrpGemeente() != null
                && brpBijhoudingVerzoekBericht.getLo3Gemeente() != null) {

            try {
                if (brpBijhoudingVerzoekBericht.getAktenummer() != null
                        && brpBijhoudingVerzoekBericht.getIngangsdatumGeldigheid() != null
                        && brpBijhoudingVerzoekBericht.getBrpPersoonslijst() != null) {

                    gevalideerd = true;
                } else {
                    gevalideerd = false;
                }
            } catch (final NullPointerException exception) {
                gevalideerd = false;
            }
        }

        return gevalideerd;
    }
}
