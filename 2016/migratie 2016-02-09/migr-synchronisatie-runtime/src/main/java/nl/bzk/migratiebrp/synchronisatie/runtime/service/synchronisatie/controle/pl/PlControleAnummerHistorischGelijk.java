/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * Controleert dat de aangeboden persoonslijst geen historie van a-nummers bevat of dat alle historische a-nummers
 * gelijk zijn aan het actuele a-nummer.
 */
@Component(value = "plControleAnummerHistorischGelijk")
public final class PlControleAnummerHistorischGelijk implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_ANUMMER_HISTORISCH_GELIJK);

        final Set<Long> anummers = new HashSet<>();
        for (final BrpGroep<BrpIdentificatienummersInhoud> groep : brpPersoonslijst.getIdentificatienummerStapel()) {
            final BrpIdentificatienummersInhoud inhoud = groep.getInhoud();
            final Long aNr = BrpLong.unwrap(inhoud.getAdministratienummer());
            if (aNr != null) {
                anummers.add(aNr);
            }
        }
        logging.logAangebodenWaarden(anummers);

        final boolean result = anummers.size() == 1;
        logging.logResultaat(result);

        return result;
    }

}
