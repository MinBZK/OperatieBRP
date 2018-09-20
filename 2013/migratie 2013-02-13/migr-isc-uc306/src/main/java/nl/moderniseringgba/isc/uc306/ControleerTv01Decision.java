/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.Map;
import java.util.Set;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer het Tv01-bericht.
 */
@Component("uc306ControleerTv01Decision")
public final class ControleerTv01Decision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Tb01Bericht verstuurdTb01Bericht = (Tb01Bericht) parameters.get("tb01Bericht");
        final Tv01Bericht ontvangenTv01Bericht = (Tv01Bericht) parameters.get("tv01Bericht");

        if (controleerVerwijsgegevens(verstuurdTb01Bericht, ontvangenTv01Bericht)) {
            return null;
        } else {
            return "7b. Verwijsgegevens incorrect";
        }
    }

    /**
     * Controleert of de verwijsgegevens overeenkomen tussen het verstuurde Tb01Bericht en het ontvangen Tv01Bericht.
     * 
     * @param tb01Bericht
     *            Het verzonden Tb01 bericht.
     * @param tv01Bericht
     *            Het ontvangen Tv01 bericht.
     * @return True indien de verwijsgegevens overeenkomen tussen beide, false in alle andere gevallen.
     */
    private boolean controleerVerwijsgegevens(final Tb01Bericht tb01Bericht, final Tv01Bericht tv01Bericht) {

        boolean verwijsGegevensCorrect = true;
        boolean berichtBevatVerwijsGegevens = true;

        if (tb01Bericht == null || tb01Bericht.getLo3Persoonslijst() == null) {
            berichtBevatVerwijsGegevens = false;
        }

        if (tv01Bericht == null || tv01Bericht.getCategorieen() == null || tv01Bericht.getCategorieen().isEmpty()) {
            berichtBevatVerwijsGegevens = false;
        }

        if (berichtBevatVerwijsGegevens) {
            final Set<String> gedetecteerdeVerschillen =
                    VerwijsGegevensVerschilAnalyse.bepaalVerschilVerwijsGegevens(tb01Bericht, tv01Bericht);

            if (gedetecteerdeVerschillen.size() > 0) {

                LOG.error("Verschillen in verwijsgegevens gedetecteerd. Details:");
                for (final String gedetecteerdVerschil : gedetecteerdeVerschillen) {
                    LOG.error(gedetecteerdVerschil);
                }

                verwijsGegevensCorrect = false;
            }
        } else {
            verwijsGegevensCorrect = false;
        }

        return verwijsGegevensCorrect;

    }
}
