/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.util.BrpVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * Controle dat de inhoud van de gevonden persoonslijst overeenkomt met de inhoud van de aangeboden persoonslijst.
 */
@Component(value = "plControleVolledigGelijk")
public final class PlControleVolledigGelijk implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_VOLLEDIG_GELIJK);

        final StringBuilder vergelijkingLogging = new StringBuilder();
        final boolean result = BrpVergelijker.vergelijkPersoonslijsten(vergelijkingLogging, dbPersoonslijst, brpPersoonslijst, false, true, true);
        if (!result) {
            logging.addMelding("Verschillen: " + vergelijkingLogging.toString());
        }

        logging.logResultaat(result);
        return result;
    }

}
