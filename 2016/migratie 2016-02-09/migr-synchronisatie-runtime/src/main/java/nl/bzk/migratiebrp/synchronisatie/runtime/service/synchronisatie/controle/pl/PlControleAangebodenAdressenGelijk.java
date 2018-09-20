/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.util.Map;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleAdresHelper.AdresData;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

import org.springframework.stereotype.Component;

/**
 * Controle dat de aangeboden persoonslijst in de BRP een adres en een historie van adressen heeft dat gelijk is aan het
 * adres en de historie van adressen op de gevonden persoonslijst.
 */
@Component(value = "plControleAangebodenAdressenGelijk")
public final class PlControleAangebodenAdressenGelijk implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_AANGEBODEN_ADRESSEN_GELIJK);
        logging.addMelding("Controleer actueel adres");

        final BrpAdresInhoud dbAdresInhoud = getHuidigAdres(dbPersoonslijst);
        final BrpAdresInhoud brpAdresInhoud = getHuidigAdres(brpPersoonslijst);

        final AdresData dbAdres = dbAdresInhoud != null ? new PlControleAdresHelper.AdresData(dbAdresInhoud, false) : null;
        final AdresData brpAdres = brpAdresInhoud != null ? new PlControleAdresHelper.AdresData(brpAdresInhoud, false) : null;

        logging.logAangebodenWaarden(brpAdres);
        logging.logGevondenWaarden(dbAdres);

        if (dbAdres == null || !dbAdres.equals(brpAdres)) {
            logging.logResultaat(false);
            return false;
        }

        logging.addMelding("Resultaat: true, ga door met controle op historie van adressen");

        // Aangeboden waarden (exclusief actueel)
        final Map<AdresData, Long> brpAdressen = PlControleAdresHelper.getAdresHistorie(brpPersoonslijst);
        logging.logAangebodenWaarden(brpAdressen);

        // Gevonden waarden (exclusief actueel)
        final Map<AdresData, Long> dbAdressen = PlControleAdresHelper.getAdresHistorie(dbPersoonslijst);
        logging.logGevondenWaarden(dbAdressen);

        // Resultaat
        final boolean result = PlControleAdresHelper.volledigSetBevatSubset(dbAdressen, brpAdressen);
        logging.logResultaat(result);

        return result;

    }

    private BrpAdresInhoud getHuidigAdres(final BrpPersoonslijst persoonslijst) {
        final BrpStapel<BrpAdresInhoud> stapel = persoonslijst.getAdresStapel();

        if (stapel == null || stapel.getActueel() == null || stapel.getActueel().getInhoud() == null) {
            return null;
        }

        return stapel.getActueel().getInhoud();
    }

}
