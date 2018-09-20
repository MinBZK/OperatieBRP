/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.business.bepalers.PopulatieBepaler;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.stereotype.Service;


/**
 * De implementatie van de interface PersoonPopulatieBepalingService.
 */
@Service
public class PersoonPopulatieBepalingServiceImpl implements PersoonPopulatieBepalingService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private AdministratieveHandelingSoortService administratieveHandelingSoortService;

    @Inject
    @Named("populatieTransitieBepaler")
    private PopulatieBepaler populatieTransitieBepaler;

    @Inject
    @Named("populatieBinnenBuitenBepaler")
    private PopulatieBepaler populatieBinnenBuitenBepaler;

    @Inject
    private ExpressieService expressieService;


    @Override
    public final Map<Integer, Populatie> geefPersoonPopulatieCorrelatie(
        final AdministratieveHandelingModel administratieveHandeling, final List<PersoonHisVolledig> personen,
        final Leveringinformatie leveringinformatie) throws ExpressieExceptie
    {
        final Expressie populatieBeperking = expressieService.geefPopulatiebeperking(leveringinformatie.getToegangLeveringsautorisatie()
            .getNaderePopulatiebeperkingExpressieString(), leveringinformatie.getDienst().getDienstbundel().getNaderePopulatiebeperkingExpressieString()
            , leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie().getPopulatiebeperkingExpressieString());
        LOGGER.info("Bepaal populatie voor toegang leveringsautorisatie '{}' (naam leveringsautorisatie: {})", leveringinformatie
            .getToegangLeveringsautorisatie().getID(), leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie().getNaam());
        final Map<Integer, Populatie> resultaat = new HashMap<>(personen.size());
        for (final PersoonHisVolledig persoon : personen) {
            final Populatie persoonMoetGeleverdWorden =
                persoonMoetGeleverdWorden(administratieveHandeling, persoon, leveringinformatie, populatieBeperking);
            resultaat.put(persoon.getID(), persoonMoetGeleverdWorden);
            LOGGER.debug("Populatie bepaling voor persoon {} is '{}'", persoon.getID(),
                persoonMoetGeleverdWorden.getOmschrijving());
        }

        return resultaat;
    }

    /**
     * Controleert of een persoon geleverd moet worden.
     *
     * @param administratieveHandeling De administratieve handeling.
     * @param leveringAutorisatie      De levering autorisatie
     * @param populatieBeperking       De epressie voor de populatie
     * @param persoon                  De persoon.
     * @return Boolean true als de persoon geleverd moet worden, anders false.
     */
    private Populatie persoonMoetGeleverdWorden(final AdministratieveHandelingModel administratieveHandeling,
        final PersoonHisVolledig persoon, final Leveringinformatie leveringAutorisatie, final Expressie populatieBeperking)
    {
        // Bij Admhnd plaatsen/verwijderen afnemerindicatie is positie eenvoudig
        final Populatie populatieVoorPlaatsenOfVerwijderenAfnemerindicatie =
            getPopulatieVoorPlaatsenOfVerwijderenAfnemerindicatie(administratieveHandeling);
        if (populatieVoorPlaatsenOfVerwijderenAfnemerindicatie != null) {
            return populatieVoorPlaatsenOfVerwijderenAfnemerindicatie;
        }
        final PopulatieBepaler populatieBepaler = kiesPopulatieBepaler(leveringAutorisatie.getSoortDienst());
        final Leveringsautorisatie la = leveringAutorisatie.getToegangLeveringsautorisatie().getLeveringsautorisatie();
        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(persoon, administratieveHandeling, populatieBeperking, la);
        return populatie;
    }

    /**
     * Selecteert de beste {@link nl.bzk.brp.levering.business.bepalers.PopulatieBepaler} voor de gegeven soortDienst. Soms kan het met eenvoudige
     * logica.
     *
     * @param soortDienst de soortDienst
     * @return de beste bepaler
     */
    private PopulatieBepaler kiesPopulatieBepaler(final SoortDienst soortDienst) {
        if (soortDienst == SoortDienst.ATTENDERING) {
            return populatieBinnenBuitenBepaler;
        } else {
            return populatieTransitieBepaler;
        }
    }

    /**
     * Geeft de populatie voor het toevoegen of verwijderen van een afnemerindicatie. Als het niet om een van beide handelingen gaat, wordt null
     * gereturned.
     *
     * @param administratieveHandeling De administartieve handeling.
     * @return De populatie,of null als het om een andere administratieve handeling gaat.
     */
    private Populatie getPopulatieVoorPlaatsenOfVerwijderenAfnemerindicatie(
        final AdministratieveHandeling administratieveHandeling)
    {
        Populatie resultaat = null;
        if (administratieveHandelingSoortService.isPlaatsingAfnemerIndicatie(administratieveHandeling)) {
            resultaat = Populatie.BETREEDT;
        } else if (administratieveHandelingSoortService.isVerwijderingAfnemerIndicatie(administratieveHandeling)) {
            resultaat = Populatie.BUITEN;
        }
        return resultaat;
    }
}
