/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek;

import nl.bzk.migratiebrp.bericht.model.sync.generated.TypeSynchronisatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;

/**
 * Controle of bericht van soort Lg01 is.
 */
public final class VerzoekControleBerichtVanSoortLg01 implements VerzoekControle {

    @Override
    public boolean controleer(final SynchroniseerNaarBrpVerzoekBericht verzoek) {
        final ControleLogging logging = new ControleLogging(ControleMelding.VERZOEK_CONTROLE_BERICHT_VAN_SOORT_LG01);

        final boolean resultaat = TypeSynchronisatieBericht.LG_01 == verzoek.getTypeBericht();
        logging.logResultaat(resultaat);

        return resultaat;
    }
}
