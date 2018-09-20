/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.Identifier;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * Zoek persoonslijsten op actueel a-nummer en niet foutief ('F' of 'W') opgeschort obv actueel vorig a-nummer.
 */
@Component(value = "plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer")
public final class PlZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer implements PlZoeker {

    @Inject
    private PlService plService;

    @Override
    public List<BrpPersoonslijst> zoek(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.ZOEKER_NIET_FOUTIEF_OBV_VORIG);

        final Long aNummer = context.getVorigAnummer();
        logging.logAangebodenWaarden(aNummer);

        final List<BrpPersoonslijst> resultaat;
        final Identifier resultaatIdentifier = maakResultaatIdentifier(aNummer);
        if (context.bevatResultaatVoor(resultaatIdentifier)) {
            resultaat = context.geefResultaatVoor(resultaatIdentifier);
        } else {
            if (aNummer == null) {
                resultaat = new ArrayList<>();
            } else {
                final BrpPersoonslijst dbPersoonslijst = plService.zoekNietFoutievePersoonslijstOpActueelAnummer(aNummer);

                if (dbPersoonslijst == null) {
                    resultaat = Collections.emptyList();
                } else {
                    resultaat = Arrays.asList(dbPersoonslijst);
                }
            }
            context.bewaarResultaatVoor(resultaatIdentifier, resultaat);
            context.kandidatenToevoegen(resultaat);
        }

        logging.addMelding("Aantal gevonden persoonslijsten: " + resultaat.size());
        logging.logResultaat(true);
        return resultaat;
    }

    private Identifier maakResultaatIdentifier(final Long aNummer) {
        return new Identifier("plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer", aNummer);
    }
}
