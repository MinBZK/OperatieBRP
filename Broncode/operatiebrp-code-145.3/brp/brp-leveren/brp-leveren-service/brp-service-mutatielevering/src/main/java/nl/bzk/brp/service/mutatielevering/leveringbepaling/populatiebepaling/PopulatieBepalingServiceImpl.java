/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.populatiebepaling;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import org.springframework.stereotype.Service;


/**
 * De implementatie van de interface PersoonPopulatieBepalingService.
 */
@Service
final class PopulatieBepalingServiceImpl implements PopulatieBepalingService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named("PopulatieTransitieBepaler")
    private PopulatieBepaler populatieTransitieBepaler;
    @Inject
    @Named("PopulatieBinnenBuitenBepaler")
    private PopulatieBepaler populatiePopulatieBinnenBuitenBepaler;
    @Inject
    private ExpressieService expressieService;

    private PopulatieBepalingServiceImpl() {

    }

    @Override
    public Map<Persoonslijst, Populatie> bepaalPersoonPopulatieCorrelatie(final Mutatiehandeling mutatiehandeling,
                                                                          final Autorisatiebundel autorisatiebundel) throws ExpressieException {
        final Expressie populatieBeperking = expressieService.geefPopulatiebeperking(autorisatiebundel);
        LOGGER.debug("Bepaal populatie voor toegang leveringsautorisatie '{}' (naam leveringsautorisatie: {})", autorisatiebundel
                .getToegangLeveringsautorisatie().getId(), autorisatiebundel.getLeveringsautorisatie().getNaam());
        final Map<Persoonslijst, Populatie> resultaat = new HashMap<>(mutatiehandeling.getPersoonsgegevensMap().size());
        for (final Persoonslijst persoon : mutatiehandeling.getPersonen()) {
            final Populatie populatie =
                    persoonMoetGeleverdWorden(persoon.getNuNuBeeld().getAdministratieveHandeling(),
                            persoon, autorisatiebundel, populatieBeperking);
            resultaat.put(persoon.getNuNuBeeld(), populatie);
        }

        return resultaat;
    }

    /**
     * Controleert of een persoon geleverd moet worden.
     * @param administratieveHandeling De administratieve handeling.
     * @param autorisatiebundel De levering autorisatie
     * @param populatieBeperking De epressie voor de populatie
     * @param mutatiehandelingPersoon De persoon.
     * @return Boolean true als de persoon geleverd moet worden, anders false.
     */
    private Populatie persoonMoetGeleverdWorden(final AdministratieveHandeling administratieveHandeling,
                                                final Persoonslijst mutatiehandelingPersoon, final Autorisatiebundel autorisatiebundel, final Expressie
                                                        populatieBeperking) {
        // Bij Admhnd plaatsen/verwijderen afnemerindicatie is positie eenvoudig
        final Populatie populatieVoorPlaatsenOfVerwijderenAfnemerindicatie =
                getPopulatieVoorPlaatsenOfVerwijderenAfnemerindicatie(administratieveHandeling);
        if (populatieVoorPlaatsenOfVerwijderenAfnemerindicatie != null) {
            return populatieVoorPlaatsenOfVerwijderenAfnemerindicatie;
        }
        final PopulatieBepaler populatieBepaler = kiesPopulatieBepaler(autorisatiebundel.getSoortDienst());
        final Leveringsautorisatie la = autorisatiebundel.getLeveringsautorisatie();
        return populatieBepaler.bepaalInUitPopulatie(mutatiehandelingPersoon, populatieBeperking, la);
    }

    /**
     * Selecteert de beste {@link PopulatieBepaler} voor de gegeven soortDienst. Soms kan het met eenvoudige logica.
     * @param soortDienst de soortDienst
     * @return de beste bepaler
     */
    private PopulatieBepaler kiesPopulatieBepaler(final SoortDienst soortDienst) {
        if (soortDienst == SoortDienst.ATTENDERING) {
            return populatiePopulatieBinnenBuitenBepaler;
        } else {
            return populatieTransitieBepaler;
        }
    }

    /**
     * Geeft de populatie voor het toevoegen of verwijderen van een afnemerindicatie. Als het niet om een van beide handelingen gaat, wordt null
     * gereturned.
     * @param administratieveHandeling De administartieve handeling.
     * @return De populatie,of null als het om een andere administratieve handeling gaat.
     */
    private Populatie getPopulatieVoorPlaatsenOfVerwijderenAfnemerindicatie(
            final AdministratieveHandeling administratieveHandeling) {
        final Populatie resultaat;
        switch (administratieveHandeling.getSoort()) {
            case PLAATSING_AFNEMERINDICATIE:
                resultaat = Populatie.BETREEDT;
                break;
            case VERWIJDERING_AFNEMERINDICATIE:
                resultaat = Populatie.BUITEN;
                break;
            default:
                resultaat = null;
        }
        return resultaat;
    }
}
