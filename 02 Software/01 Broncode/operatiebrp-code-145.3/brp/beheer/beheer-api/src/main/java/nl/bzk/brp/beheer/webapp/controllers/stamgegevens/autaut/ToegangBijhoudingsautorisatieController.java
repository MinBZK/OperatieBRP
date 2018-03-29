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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatieHistorie;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRolRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ToegangAbonnementController voor beheer toegangabonnement.
 */
@RestController
@RequestMapping(value = ControllerConstants.TOEGANGBIJHOUDINGAUTORISATIE_URI)
public final class ToegangBijhoudingsautorisatieController extends AbstractReadWriteController<ToegangBijhoudingsautorisatie, Integer> {

    /**
     * Parameter: filter op bijhoudingsautorisatie.
     */
    static final String PARAMETER_FILTER_BIJHOUDINGSAUTORISATIE = "bijhoudingsautorisatie";

    private static final String BIJHOUDINGSAUTORISATIE = PARAMETER_FILTER_BIJHOUDINGSAUTORISATIE;
    private static final String PARTIJ = "geautoriseerde";
    private static final String DATUM_INGANG = "datumIngang";
    private static final String DATUM_EINDE = "datumEinde";
    private static final List<String> SORTERINGEN = Arrays.asList(PARTIJ, DATUM_INGANG, DATUM_EINDE);

    private final ReadonlyRepository<Bijhoudingsautorisatie, Integer> bijhoudingautorisatieRepository;

    private final ReadonlyRepository<Partij, Short> partijRepository;
    private final PartijRolRepository partijRolRepository;

    /**
     * Constructor voor BijhoudingssautorisatieController.
     * @param repository repo
     * @param bijhoudingautorisatieRepository repo voor bijhoudingautorisatie
     * @param partijRepository repo voor partij
     * @param partijRolRepository repo voor partijrol
     */
    @Inject
    public ToegangBijhoudingsautorisatieController(
            final ReadWriteRepository<ToegangBijhoudingsautorisatie, Integer> repository,
            final ReadonlyRepository<Bijhoudingsautorisatie, Integer> bijhoudingautorisatieRepository, final ReadonlyRepository<Partij, Short> partijRepository,
            final PartijRolRepository partijRolRepository) {
        super(repository,
                Collections.singletonList(
                        new Filter<>(BIJHOUDINGSAUTORISATIE, new IntegerValueAdapter(), new EqualPredicateBuilderFactory(BIJHOUDINGSAUTORISATIE))),
                Collections.singletonList(new ToegangBijhoudingsautorisatieHistorieVerwerker()),
                SORTERINGEN);
        this.bijhoudingautorisatieRepository = bijhoudingautorisatieRepository;
        this.partijRepository = partijRepository;
        this.partijRolRepository = partijRolRepository;
    }

    @Override
    protected void wijzigObjectVoorOpslag(final ToegangBijhoudingsautorisatie item) throws NotFoundException {
        getReadonlyTransactionTemplate().execute(status -> {
            // Bijhoudingsautorisatie niet updaten via de toegang (laden via id)
            item.setBijhoudingsautorisatie(bijhoudingautorisatieRepository.getOne(item.getBijhoudingsautorisatie().getId()));

            // Geautoriseerde niet updaten via de toegang (laden via id)
            item.setGeautoriseerde(partijRolRepository.getOne(item.getGeautoriseerde().getId()));

            // Ondertekenaar niet updaten via de toegang (laden via id)
            if (item.getOndertekenaar() != null && item.getOndertekenaar().getId() != null) {
                item.setOndertekenaar(partijRepository.getOne(item.getOndertekenaar().getId()));
            }

            // Transporteur niet updaten via de toegang (laden via id)
            if (item.getTransporteur() != null && item.getTransporteur().getId() != null) {
                item.setTransporteur(partijRepository.getOne(item.getTransporteur().getId()));
            }
            return null;
        });
    }

    /**
     * Toegangbijhoudingsautorisatie historie verwerker.
     */
    static final class ToegangBijhoudingsautorisatieHistorieVerwerker
            extends AbstractHistorieVerwerker<ToegangBijhoudingsautorisatie, ToegangBijhoudingsautorisatieHistorie> {
        @Override
        public Class<ToegangBijhoudingsautorisatieHistorie> historieClass() {
            return ToegangBijhoudingsautorisatieHistorie.class;
        }

        @Override
        public Class<ToegangBijhoudingsautorisatie> entiteitClass() {
            return ToegangBijhoudingsautorisatie.class;
        }

    }
}
