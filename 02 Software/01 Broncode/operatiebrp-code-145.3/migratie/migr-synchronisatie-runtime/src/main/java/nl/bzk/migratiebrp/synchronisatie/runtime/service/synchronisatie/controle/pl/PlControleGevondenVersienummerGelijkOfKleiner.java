/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

/**
 * Controleer dat het versienummer van de gevonden persoonslijst gelijk of kleiner is dan het versienummer van de
 * aangeboden persoonslijst.
 */
public final class PlControleGevondenVersienummerGelijkOfKleiner implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_GEVONDEN_VERSIENUMMER_GELIJK_OF_KLEINER);

        // Aangeboden waarden
        final long brpVersienummer = getVersienummer(brpPersoonslijst);
        logging.logAangebodenWaarden(brpVersienummer);

        // Gevonden waarden
        final long dbVersienummer = getVersienummer(dbPersoonslijst);
        logging.logGevondenWaarden(dbVersienummer);

        // Resultaat
        final boolean result = dbVersienummer <= brpVersienummer;
        logging.logResultaat(result);

        return result;
    }

    private long getVersienummer(final BrpPersoonslijst persoonslijst) {
        return BrpLong.unwrap(persoonslijst.getInschrijvingStapel().getActueel().getInhoud().getVersienummer());
    }

}
