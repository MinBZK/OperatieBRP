/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleAdresHelper.AdresData;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

/**
 * Controle dat de aangeboden persoonslijst in de BRP een adres en een historie van adressen heeft dat voorkomt in het adres en de
 * historie van adressen op de aangeboden persoonslijst.
 */
public final class PlControleAangebodenAdressenKomenVoorInGevondenAdressen implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_AANGEBODEN_ADRESSEN_IN_GEVONDEN);

        // Aangeboden waarden (inclusief actueel)
        final Map<AdresData, Long> brpAdressen = PlControleAdresHelper.getAdresHistorie(brpPersoonslijst);
        if (brpPersoonslijst.getAdresStapel() != null) {
            PlControleAdresHelper.voegAdresToeAanMap(brpAdressen, new AdresData(brpPersoonslijst.getAdresStapel().getActueel().getInhoud(), true));
        }
        logging.logAangebodenWaarden(brpAdressen);

        // Gevonden waarden (inclusief actueel)
        final Map<AdresData, Long> dbAdressen = PlControleAdresHelper.getAdresHistorie(dbPersoonslijst);
        if (dbPersoonslijst.getAdresStapel() != null) {
            PlControleAdresHelper.voegAdresToeAanMap(dbAdressen, new AdresData(dbPersoonslijst.getAdresStapel().getActueel().getInhoud(), true));
        }
        logging.logGevondenWaarden(dbAdressen);

        // Resultaat
        final boolean result = PlControleAdresHelper.volledigSetBevatSubset(dbAdressen, brpAdressen);
        logging.logResultaat(result);

        return result;

    }

}
