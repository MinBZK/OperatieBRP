/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.util.Objects;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

/**
 * Controle actuele bsn gelijk.
 */
public final class PlControleActueelBsnGelijk implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_ACTUEEL_BSN_GELIJK);

        final String brpBsn = brpPersoonslijst.getActueelBurgerservicenummer();
        final String dbBsn = dbPersoonslijst.getActueelBurgerservicenummer();

        // Log waarden
        logging.logAangebodenWaarden(brpBsn);
        logging.logGevondenWaarden(dbBsn);

        final boolean result = Objects.equals(brpBsn, dbBsn);
        logging.logResultaat(result);
        return result;
    }
}
