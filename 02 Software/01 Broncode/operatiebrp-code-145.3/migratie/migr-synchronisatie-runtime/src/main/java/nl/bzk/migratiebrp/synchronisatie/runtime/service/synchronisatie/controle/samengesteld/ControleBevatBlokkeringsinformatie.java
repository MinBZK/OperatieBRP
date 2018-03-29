/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleBevatDatumIngangBlokkering;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek.VerzoekControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek.VerzoekControleBerichtVanSoortLg01;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Samengestelde controle 'Aangeboden persoonslijst bevat blokkeringsinformatie'.
 * </p>
 * Een aangeboden persoonslijst die blokkeringsinformatie bevat kan genegeerd worden als:
 * <ol>
 * <li>de aangeboden persoonslijst een gevulde datum ingang blokkering PL bevat</li>
 * </ol>
 */
@Component(value = "controleBevatBlokkeringsinformatie")
public final class ControleBevatBlokkeringsinformatie implements Controle {

    private final VerzoekControle verzoekControleBerichtVanSoortLg01;
    private final PlControle plControleBevatDatumIngangBlokkering;


    /**
     * Constructor voor deze implementatie van een {@link Controle}.
     */
    ControleBevatBlokkeringsinformatie() {
        this.verzoekControleBerichtVanSoortLg01 = new VerzoekControleBerichtVanSoortLg01();
        this.plControleBevatDatumIngangBlokkering = new PlControleBevatDatumIngangBlokkering();
    }

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_PL_IS_GEBLOKKEERD);

        boolean result = verzoekControleBerichtVanSoortLg01.controleer(context.getVerzoek());
        result = result && plControleBevatDatumIngangBlokkering.controleer(context, null);

        logging.logResultaat(result, true);
        return result;
    }
}
