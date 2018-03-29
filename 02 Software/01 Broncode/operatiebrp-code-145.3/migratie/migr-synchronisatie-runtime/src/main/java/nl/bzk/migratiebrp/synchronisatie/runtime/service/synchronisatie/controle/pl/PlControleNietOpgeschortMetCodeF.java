/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

import org.springframework.stereotype.Component;

/**
 * Controle dat persoonslijst niet is opgeschort met reden 'F' (Fout).
 */
@Component(value = "plControleNietOpgeschortMetCodeF")
public final class PlControleNietOpgeschortMetCodeF implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_PERSOONSLIJST_IS_NIET_OPGESCHORT_MET_REDEN_F);

        final Lo3RedenOpschortingBijhoudingCode redenOpschorting = context.getLo3Persoonslijst().getInschrijvingStapel().get(0).getInhoud()
                .getRedenOpschortingBijhoudingCode();

        logging.logAangebodenWaarden(redenOpschorting == null ? null : redenOpschorting.getWaarde());

        final boolean result = redenOpschorting == null || !redenOpschorting.isFout();
        logging.logResultaat(result);

        return result;
    }

}
