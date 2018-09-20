/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

import org.springframework.stereotype.Component;

/**
 * Samengestelde controle 'Aangeboden persoonslijst bevat blokkeringsinformatie'.
 * <p/>
 * Een aangeboden persoonslijst die blokkeringsinformatie bevat kan genegeerd worden als:
 * <ol>
 * <li>de aangeboden persoonslijst een gevulde datum ingang blokkering PL bevat</li>
 * </ol>
 */
@Component(value = "controleBevatBlokkeringsinformatie")
public final class ControleBevatBlokkeringsinformatie implements Controle {

    @Inject
    @Named(value = "plControleBevatDatumIngangBlokkering")
    private PlControle plControleBevatDatumIngangBlokkering;

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_PL_IS_GEBLOKKEERD);

        if (plControleBevatDatumIngangBlokkering.controleer(context, null)) {
            logging.logResultaat(true);
            return true;
        }

        logging.logResultaat(false);
        return false;
    }
}
