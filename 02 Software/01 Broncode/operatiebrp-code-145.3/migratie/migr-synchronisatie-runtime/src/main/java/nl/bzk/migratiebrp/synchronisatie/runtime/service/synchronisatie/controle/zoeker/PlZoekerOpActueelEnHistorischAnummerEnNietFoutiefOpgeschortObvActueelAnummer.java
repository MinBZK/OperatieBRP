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
 * Zoek persoonslijsten op actueel of historisch a-nummer obv actueel a-nummer.
 */
public final class PlZoekerOpActueelEnHistorischAnummerEnNietFoutiefOpgeschortObvActueelAnummer implements PlZoeker {

    private final PlService plService;

    /**
     * Constructor voor deze implementatie van de {@link PlZoeker}.
     * @param plService implementatie van de {@link PlService}
     */
    public PlZoekerOpActueelEnHistorischAnummerEnNietFoutiefOpgeschortObvActueelAnummer(final PlService plService) {
        this.plService = plService;
    }

    @Override
    public List<BrpPersoonslijst> zoek(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.ZOEKER_ALLE_OBV_ACTUEEL);
        final String administratieNummer = context.getAnummer();
        logging.logAangebodenWaarden(administratieNummer);

        final List<BrpPersoonslijst> resultaat;
        final Identifier resultaatIdentifier = maakResultaatIdentifier(administratieNummer);
        if (context.bevatResultaatVoor(resultaatIdentifier)) {
            resultaat = context.geefResultaatVoor(resultaatIdentifier);
        } else {
            final BrpPersoonslijst actueel = plService.zoekNietFoutievePersoonslijstOpActueelAnummer(administratieNummer);
            final List<BrpPersoonslijst> historisch = plService.zoekNietFoutievePersoonslijstenOpHistorischAnummer(administratieNummer);

            resultaat = new ArrayList<>();
            if(actueel != null) {
                resultaat.add(actueel);
            }
            resultaat.addAll(historisch);

            context.bewaarResultaatVoor(resultaatIdentifier, resultaat);
            context.kandidatenToevoegen(resultaat);
        }

        logging.addMelding("Aantal gevonden persoonslijsten: " + resultaat.size());
        logging.logResultaat(true);
        return resultaat;
    }

    private Identifier maakResultaatIdentifier(final String aNummer) {
        return new Identifier("PlZoekerOpActueelEnHistorischAnummerEnNietFoutiefOpgeschortObvActueelAnummer", aNummer);
    }
}
