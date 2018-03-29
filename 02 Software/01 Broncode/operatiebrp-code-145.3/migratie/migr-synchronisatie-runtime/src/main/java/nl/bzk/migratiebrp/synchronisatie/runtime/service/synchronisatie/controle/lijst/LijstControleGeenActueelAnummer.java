/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;

/**
 * Controleert of een lijst geen personen bevat.
 */
public final class LijstControleGeenActueelAnummer implements LijstControle {

    @Override
    public boolean controleer(final List<BrpPersoonslijst> lijst) {
        final ControleLogging logging = new ControleLogging(ControleMelding.LIJST_CONTROLE_GEEN_ACTUEEL_ANR);

        final boolean result = lijst == null || lijst.isEmpty();
        logging.logResultaat(result);

        return result;
    }

}
