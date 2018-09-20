/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.VergelijkingUtil;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.ReferenceValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.beheer.autaut.HisToegangLeveringsautorisatie;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.beheer.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.beheer.kern.Partij;
import nl.bzk.brp.model.beheer.kern.PartijRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ToegangAbonnementController voor beheer toegangabonnement.
 */
@RestController
@RequestMapping(value = ControllerConstants.TOEGANGABONNEMENT_URI)
public final class ToegangLeveringsautorisatieController extends AbstractReadWriteController<ToegangLeveringsautorisatie, Integer> {

    /**
     * Parameter: filter op abonnement.
     */
    public static final String PARAMETER_FILTER_LEVERINGSAUTORISATIE = "leveringsautorisatie";

    private static final String LEVERINGSAUTORISATIE = PARAMETER_FILTER_LEVERINGSAUTORISATIE;
    private static final String PARTIJ = "partij";
    private static final List<String> SORTERINGEN = Arrays.asList(PARTIJ);

    private final ReadonlyRepository<Leveringsautorisatie, Integer> leveringsautorisatieRepository;

    @Autowired
    private ReadonlyRepository<Partij, Short> partijRepository;

    @Autowired
    private ReadonlyRepository<PartijRol, Integer> partijRolRepository;

    /**
     * Constructor voor LeveringsautoriteitController.
     *
     * @param repository repo
     * @param leveringsautorisatieRepository repo voor leveringsautorisatie
     */
    @Autowired
    public ToegangLeveringsautorisatieController(
            final ReadWriteRepository<ToegangLeveringsautorisatie, Integer> repository,
            final ReadonlyRepository<Leveringsautorisatie, Integer> leveringsautorisatieRepository)
    {
        super(repository,
                Arrays.<Filter<?>>asList(new Filter<>(
                        LEVERINGSAUTORISATIE,
                        new ReferenceValueAdapter<>(new IntegerValueAdapter(), leveringsautorisatieRepository),
                        new EqualPredicateBuilderFactory(LEVERINGSAUTORISATIE))),
                Arrays.<HistorieVerwerker<ToegangLeveringsautorisatie, ?>>asList(new HisToegangLeveringsautorisatieVerwerker()),
                SORTERINGEN);
        this.leveringsautorisatieRepository = leveringsautorisatieRepository;
    }

    @Override
    protected void wijzigObjectVoorOpslag(final ToegangLeveringsautorisatie item) throws NotFoundException {
        // Abonnement niet updaten via het toegangabonnement (laden via id)
        if (item.getLeveringsautorisatie() != null && item.getLeveringsautorisatie().getID() != null) {
            item.setLeveringsautorisatie(leveringsautorisatieRepository.getOne(item.getLeveringsautorisatie().getID()));
        }

        // Geautoriseerde niet updaten via het toegangabonnement (laden via id)
        if (item.getGeautoriseerde() != null && item.getGeautoriseerde().getID() != null) {
            item.setGeautoriseerde(partijRolRepository.getOne(item.getGeautoriseerde().getID()));
        }

        // Ondertekenaar niet updaten via het toegangabonnement (laden via id)
        if (item.getOndertekenaar() != null && item.getOndertekenaar().getID() != null) {
            item.setOndertekenaar(partijRepository.getOne(item.getOndertekenaar().getID()));
        }

        // Transporteur niet updaten via het toegangabonnement (laden via id)
        if (item.getTransporteur() != null && item.getTransporteur().getID() != null) {
            item.setTransporteur(partijRepository.getOne(item.getTransporteur().getID()));
        }

    }

    /**
     * Dienst historie verwerker.
     */
    public static final class HisToegangLeveringsautorisatieVerwerker
            extends AbstractHistorieVerwerker<ToegangLeveringsautorisatie, HisToegangLeveringsautorisatie>
    {

        @Override
        public HisToegangLeveringsautorisatie maakHistorie(final ToegangLeveringsautorisatie item) {
            // Controle inhoudelijk leeg
            if (item.getDatumIngang() == null && item.getDatumEinde() == null) {
                return null;
            }

            final HisToegangLeveringsautorisatie historie = new HisToegangLeveringsautorisatie();

            // A-laag
            historie.setToegangLeveringsautorisatie(item);

            // Gegevens
            historie.setDatumIngang(item.getDatumIngang());
            historie.setDatumEinde(item.getDatumEinde());

            // Historie
            historie.setDatumTijdRegistratie(new DatumTijdAttribuut(new Date()));

            return historie;
        }

        @Override
        public boolean isHistorieInhoudelijkGelijk(final HisToegangLeveringsautorisatie nieuweHistorie, final HisToegangLeveringsautorisatie actueleRecord) {
            return VergelijkingUtil.isEqual(nieuweHistorie.getDatumIngang(), actueleRecord.getDatumIngang())
                    && VergelijkingUtil.isEqual(nieuweHistorie.getDatumEinde(), actueleRecord.getDatumEinde());
        }

        @Override
        public Set<HisToegangLeveringsautorisatie> geefHistorie(final ToegangLeveringsautorisatie item) {
            return item.getHisToegangLeveringsautorisatieLijst();
        }

        @Override
        public void kopieerHistorie(final ToegangLeveringsautorisatie item, final ToegangLeveringsautorisatie managedItem) {
            managedItem.setDatumIngang(item.getDatumIngang());
            managedItem.setDatumEinde(item.getDatumEinde());
        }
    }
}

