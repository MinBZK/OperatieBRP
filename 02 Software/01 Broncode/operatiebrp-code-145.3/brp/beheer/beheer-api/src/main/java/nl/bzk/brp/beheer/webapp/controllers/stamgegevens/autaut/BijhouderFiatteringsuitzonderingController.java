/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzondering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzonderingHistorie;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.BijhouderFiatteringsuitzonderingRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRolRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BijhouderFiatteringsuitzonderingController voor beheer.
 */
@RestController
@RequestMapping(value = ControllerConstants.BIJHOUDER_FIATTERINGSUITZONDERING_URI)
public class BijhouderFiatteringsuitzonderingController extends AbstractReadWriteController<BijhouderFiatteringsuitzondering, Integer> {

    private static final List<String> SORTERINGEN = Arrays.asList("bijhouder.partij.naam", "bijhouderBijhoudingsvoorstel.partij.naam");

    private final PartijRolRepository partijRolRepository;

    /**
     * Constructor voor BijhouderFiatteringsuitzonderingController.
     * @param repository repo
     * @param partijRolRepository repo partij rol
     */
    @Inject
    public BijhouderFiatteringsuitzonderingController(final BijhouderFiatteringsuitzonderingRepository repository,
                                                      final PartijRolRepository partijRolRepository) {
        super(repository,
                Arrays.asList(
                        BijhouderFiatteringsuitzonderingFilterFactory.getFilterDatumIngang(),
                        BijhouderFiatteringsuitzonderingFilterFactory.getFilterDatumEinde(),
                        BijhouderFiatteringsuitzonderingFilterFactory.getFilterBijhouder(),
                        BijhouderFiatteringsuitzonderingFilterFactory.getFilterBijhouderBijhoudingsvoorstel(),
                        BijhouderFiatteringsuitzonderingFilterFactory.getFilterSoortDocument(),
                        BijhouderFiatteringsuitzonderingFilterFactory.getFilterSoortAdministratieveHandeling(),
                        BijhouderFiatteringsuitzonderingFilterFactory.getFilterGeblokkeerd()),
                Collections.singletonList(new BijhouderFiatteringsuitzonderingHistorieVerwerker()),
                SORTERINGEN);
        this.partijRolRepository = partijRolRepository;
    }

    @Override
    protected final void wijzigObjectVoorOpslag(final BijhouderFiatteringsuitzondering item) throws NotFoundException {
        getReadonlyTransactionTemplate().execute(status -> {

            // Bijhouder niet updaten via de fiatteringsuitzondering (laden via id)
            item.setBijhouder(partijRolRepository.getOne(item.getBijhouder().getId()));

            if (item.getBijhouderBijhoudingsvoorstel() != null) {
                // Bijhouder bijhoudingsvoorstel niet updaten via de fiatteringsuitzondering (laden via id)
                item.setBijhouderBijhoudingsvoorstel(partijRolRepository.getOne(item.getBijhouderBijhoudingsvoorstel().getId()));
            }
            return null;
        });
    }

    /**
     * Bijhoudingsautorisatie historie verwerker.
     */
    static class BijhouderFiatteringsuitzonderingHistorieVerwerker
            extends AbstractHistorieVerwerker<BijhouderFiatteringsuitzondering, BijhouderFiatteringsuitzonderingHistorie> {
        @Override
        public Class<BijhouderFiatteringsuitzonderingHistorie> historieClass() {
            return BijhouderFiatteringsuitzonderingHistorie.class;
        }

        @Override
        public Class<BijhouderFiatteringsuitzondering> entiteitClass() {
            return BijhouderFiatteringsuitzondering.class;
        }
    }
}
