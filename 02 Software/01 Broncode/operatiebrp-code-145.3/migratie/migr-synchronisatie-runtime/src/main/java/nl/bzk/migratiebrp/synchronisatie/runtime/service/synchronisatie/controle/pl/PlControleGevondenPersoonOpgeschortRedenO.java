/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

/**
 * Controle of gevonden persoon is opgeschort met reden O.
 */
public final class PlControleGevondenPersoonOpgeschortRedenO implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_GEVONDEN_PERSOONSLIJST_IS_OPGESCHORT_MET_REDEN_O);
        boolean result = dbPersoonslijst.getBijhoudingStapel() != null;
        result = result && dbPersoonslijst.getBijhoudingStapel().bevatActueel();
        final BrpNadereBijhoudingsaardCode opschortingCode = dbPersoonslijst.getBijhoudingStapel().getActueel().getInhoud().getNadereBijhoudingsaardCode();
        result = result && opschortingCode != null;
        result = result && BrpNadereBijhoudingsaardCode.OVERLEDEN.equals(opschortingCode);

        logging.logResultaat(result);
        return result;

    }

}
