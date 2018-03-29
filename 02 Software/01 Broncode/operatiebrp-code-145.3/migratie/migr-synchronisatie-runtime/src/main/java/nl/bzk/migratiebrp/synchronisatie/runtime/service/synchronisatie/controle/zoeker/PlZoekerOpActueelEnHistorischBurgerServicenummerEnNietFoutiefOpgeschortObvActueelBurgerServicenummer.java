/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.Identifier;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

/**
 * Zoek persoonslijsten op actueel a-nummer en niet foutief ('F' of 'W') opgeschort obv actueel a-nummer.
 */
public final class PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummer implements PlZoeker {

    private final PlService plService;

    /**
     * Constructor voor deze implementatie van de {@link PlZoeker}.
     * @param plService implementatie van de {@link PlService}
     */
    public PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummer(final PlService plService) {
        this.plService = plService;
    }

    @Override
    public List<BrpPersoonslijst> zoek(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.ZOEKER_NIET_FOUTIEF_OBV_BSN);

        final String burgerservicenummer = context.getBurgerServicenummer();
        logging.logAangebodenWaarden(burgerservicenummer);

        final List<BrpPersoonslijst> resultaat;
        final Identifier resultaatIdentifier = maakResultaatIdentifier(burgerservicenummer);
        if (context.bevatResultaatVoor(resultaatIdentifier)) {
            resultaat = context.geefResultaatVoor(resultaatIdentifier);
        } else {
            resultaat = new ArrayList<>();

            if(burgerservicenummer != null) {
                final BrpPersoonslijst actueel = plService.zoekNietFoutievePersoonslijstOpActueelBurgerservicenummer(burgerservicenummer);
                final List<BrpPersoonslijst> historisch = plService.zoekNietFoutievePersoonslijstenOpHistorischBurgerservicenummer(burgerservicenummer);

                if (actueel != null) {
                    resultaat.add(actueel);
                }
                resultaat.addAll(historisch);
            }

            context.bewaarResultaatVoor(resultaatIdentifier, resultaat);
            context.kandidatenToevoegen(resultaat);
        }

        logging.addMelding("Aantal gevonden persoonslijsten: " + resultaat.size());
        logging.logResultaat(true);
        return resultaat;
    }

    private Identifier maakResultaatIdentifier(final String burgerServicenummer) {
        return new Identifier("PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummer", burgerServicenummer);
    }
}
