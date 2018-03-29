/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * Controleer dat de datumtijdstempel van de gevonden persoonslijst kleiner is dan het datumtijdstempel van de
 * aangeboden persoonslijst.
 */
@Component(value = "plControleGevondenDatumtijdstempelGelijkOfOuder")
public final class PlControleGevondenDatumtijdstempelGelijkOfOuder implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_GEVONDEN_DATUMTIJDSTEMPEL_GELIJK_OF_OUDER);

        // Aangeboden waarden
        final long brpTijdstempel = getDatumTijdstempel(brpPersoonslijst);
        logging.logAangebodenWaarden(brpTijdstempel);

        // Gevonden waarden
        final long dbTijdstempel = getDatumTijdstempel(dbPersoonslijst);
        logging.logGevondenWaarden(dbTijdstempel);

        // Resultaat
        final boolean result = dbTijdstempel <= brpTijdstempel;
        logging.logResultaat(result);

        return result;
    }

    private long getDatumTijdstempel(final BrpPersoonslijst persoonslijst) {
        return BrpDatumTijd.unwrap(persoonslijst.getInschrijvingStapel().getActueel().getInhoud().getDatumtijdstempel());
    }

}
