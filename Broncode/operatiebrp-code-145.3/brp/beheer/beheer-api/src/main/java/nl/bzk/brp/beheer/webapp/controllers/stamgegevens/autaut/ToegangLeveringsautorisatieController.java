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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsautorisatieHistorie;
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
@RequestMapping(value = ControllerConstants.TOEGANGABONNEMENT_URI)
public final class ToegangLeveringsautorisatieController extends AbstractReadWriteController<ToegangLeveringsAutorisatie, Integer> {

    /**
     * Parameter: filter op abonnement.
     */
    static final String PARAMETER_FILTER_LEVERINGSAUTORISATIE = "leveringsautorisatie";

    private static final String LEVERINGSAUTORISATIE = PARAMETER_FILTER_LEVERINGSAUTORISATIE;
    private static final String PARTIJ = "geautoriseerde";
    private static final String DATUM_INGANG = "datumIngang";
    private static final String DATUM_EINDE = "datumEinde";
    private static final List<String> SORTERINGEN = Arrays.asList(PARTIJ, DATUM_INGANG, DATUM_EINDE);

    private final ReadonlyRepository<Leveringsautorisatie, Integer> leveringsautorisatieRepository;

    private final ReadonlyRepository<Partij, Short> partijRepository;
    private final PartijRolRepository partijRolRepository;

    /**
     * Constructor voor LeveringsautoriteitController.
     * @param repository repo
     * @param leveringsautorisatieRepository repo voor leveringsautorisatie
     * @param partijRepository repo voor partij
     * @param partijRolRepository repo voor partijrol
     */
    @Inject
    public ToegangLeveringsautorisatieController(
            final ReadWriteRepository<ToegangLeveringsAutorisatie, Integer> repository,
            final ReadonlyRepository<Leveringsautorisatie, Integer> leveringsautorisatieRepository, final ReadonlyRepository<Partij, Short> partijRepository,
            final PartijRolRepository partijRolRepository) {
        super(repository,
                Collections.singletonList(
                        new Filter<>(LEVERINGSAUTORISATIE, new IntegerValueAdapter(), new EqualPredicateBuilderFactory(LEVERINGSAUTORISATIE))),
                Collections.singletonList(new ToegangLeveringsautorisatieHistorieVerwerker()),
                SORTERINGEN);
        this.leveringsautorisatieRepository = leveringsautorisatieRepository;
        this.partijRepository = partijRepository;
        this.partijRolRepository = partijRolRepository;
    }

    @Override
    protected void wijzigObjectVoorOpslag(final ToegangLeveringsAutorisatie item) throws NotFoundException {
        getReadonlyTransactionTemplate().execute(status -> {
            // Abonnement niet updaten via het toegangabonnement (laden via id)
            item.setLeveringsautorisatie(leveringsautorisatieRepository.getOne(item.getLeveringsautorisatie().getId()));

            // Geautoriseerde niet updaten via het toegangabonnement (laden via id)
            item.setGeautoriseerde(partijRolRepository.getOne(item.getGeautoriseerde().getId()));

            // Ondertekenaar niet updaten via het toegangabonnement (laden via id)
            if (item.getOndertekenaar() != null && item.getOndertekenaar().getId() != null) {
                item.setOndertekenaar(partijRepository.getOne(item.getOndertekenaar().getId()));
            }

            // Transporteur niet updaten via het toegangabonnement (laden via id)
            if (item.getTransporteur() != null && item.getTransporteur().getId() != null) {
                item.setTransporteur(partijRepository.getOne(item.getTransporteur().getId()));
            }
            return null;
        });
    }

    /**
     * Dienst historie verwerker.
     */
    static final class ToegangLeveringsautorisatieHistorieVerwerker
            extends AbstractHistorieVerwerker<ToegangLeveringsAutorisatie, ToegangLeveringsautorisatieHistorie> {
        @Override
        public Class<ToegangLeveringsautorisatieHistorie> historieClass() {
            return ToegangLeveringsautorisatieHistorie.class;
        }

        @Override
        public Class<ToegangLeveringsAutorisatie> entiteitClass() {
            return ToegangLeveringsAutorisatie.class;
        }
    }
}
