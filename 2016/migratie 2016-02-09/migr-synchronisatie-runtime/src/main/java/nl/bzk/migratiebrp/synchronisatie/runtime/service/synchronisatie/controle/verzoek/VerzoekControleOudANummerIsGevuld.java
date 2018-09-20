/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek;

import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import org.springframework.stereotype.Component;

/**
 * Controle oud-anummer is gevuld.
 */
@Component(value = "verzoekControleOudAnummerIsGevuld")
public final class VerzoekControleOudANummerIsGevuld implements VerzoekControle {

    @Override
    public boolean controleer(final SynchroniseerNaarBrpVerzoekBericht verzoek) {
        final ControleLogging logging = new ControleLogging(ControleMelding.VERZOEK_CONTROLE_OUD_ANUMMER_IS_GEVULD);

        final boolean resultaat = verzoek.isAnummerWijziging();
        logging.logResultaat(resultaat);

        return resultaat;
    }
}
