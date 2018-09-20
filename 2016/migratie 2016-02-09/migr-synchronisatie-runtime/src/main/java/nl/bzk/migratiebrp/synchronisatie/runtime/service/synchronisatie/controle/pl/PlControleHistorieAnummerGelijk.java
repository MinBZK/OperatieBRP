/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * Controleer of de historie van a-nummers van de aangeboden en de gevonden persoonslijst overeenkomt.
 */
@Component(value = "plControleHistorieAnummerGelijk")
public final class PlControleHistorieAnummerGelijk implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_HISTORIE_ANUMMER_GELIJK);

        // Aangeboden waarden
        final Set<Long> brpAnummers = getAnummerHistorie(brpPersoonslijst);
        logging.logAangebodenWaarden(brpAnummers);

        // Gevonden waarden
        final Set<Long> dbAnummers = getAnummerHistorie(dbPersoonslijst);
        logging.logGevondenWaarden(dbAnummers);

        // Resultaat
        final boolean result = brpAnummers.equals(dbAnummers);
        logging.logResultaat(result);

        return result;
    }

    private Set<Long> getAnummerHistorie(final BrpPersoonslijst persoonslijst) {
        final BrpStapel<BrpIdentificatienummersInhoud> stapel = persoonslijst.getIdentificatienummerStapel();
        if (stapel == null) {
            return Collections.emptySet();
        }
        final SortedSet<Long> anummers = new TreeSet<>();

        for (final BrpGroep<BrpIdentificatienummersInhoud> groep : stapel) {
            if (groep.getActieVerval() != null) {
                continue;
            }
            final BrpIdentificatienummersInhoud inhoud = groep.getInhoud();
            final Long administratienummer = BrpLong.unwrap(inhoud.getAdministratienummer());
            if (administratienummer != null && !anummers.contains(administratienummer)) {
                anummers.add(administratienummer);
            }
        }
        return anummers;
    }
}
