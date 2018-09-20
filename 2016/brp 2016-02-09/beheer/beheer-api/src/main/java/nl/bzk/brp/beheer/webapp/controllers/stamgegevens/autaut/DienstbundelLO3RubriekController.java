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
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.ReferenceValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelLO3RubriekRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.LeveringsautorisatieRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.conv.ConversieLO3RubriekRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.beheer.autaut.DienstbundelLO3Rubriek;
import nl.bzk.brp.model.beheer.autaut.HisDienstbundelLO3Rubriek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AbonnementLO3Rubriek controller voor beheer AbonnementLO3Rubrieken.
 */
@RestController
@RequestMapping(value = ControllerConstants.LO3_FILTER_RUBRIEK)
public final class DienstbundelLO3RubriekController extends AbstractReadWriteController<DienstbundelLO3Rubriek, Integer> {

    /** Parameter abonnement. */
    public static final String PARAMETER_FILTER_ABONNEMENT = "abonnement";

    private static final String ABONNEMENT = PARAMETER_FILTER_ABONNEMENT;

    private static final List<String> SORTERINGEN = Arrays.asList(ABONNEMENT);

    private final LeveringsautorisatieRepository leveringsautorisatieRepository;
    private final ConversieLO3RubriekRepository rubriekRepository;

    /**
     * Constructor voor AbonnementLO3Rubriekcontroller.
     *
     * @param repository
     *            AbonnementLO3Rubriek repository
     * @param leveringsautorisatieRepository
     *            abonnement repository;
     * @param rubriekRepository
     *            conversie LO3 rubriek repository;
     */
    @Autowired
    public DienstbundelLO3RubriekController(
        final DienstbundelLO3RubriekRepository repository,
        final LeveringsautorisatieRepository leveringsautorisatieRepository,
        final ConversieLO3RubriekRepository rubriekRepository)
    {
        super(repository,
              Arrays.<Filter<?>>asList(new Filter<>(
                  ABONNEMENT,
                  new ReferenceValueAdapter<>(new IntegerValueAdapter(), leveringsautorisatieRepository),
                  new EqualPredicateBuilderFactory(ABONNEMENT))),
              Arrays.<HistorieVerwerker<DienstbundelLO3Rubriek, ?>>asList(new HisDienstbundelLO3RubriekVerwerker()),
              SORTERINGEN);
        this.leveringsautorisatieRepository = leveringsautorisatieRepository;
        this.rubriekRepository = rubriekRepository;
    }

    @Override
    protected void wijzigObjectVoorOpslag(final DienstbundelLO3Rubriek item) throws NotFoundException {
        // Abonnement niet updaten via de AbonnementLO3Rubriek (laden via id)
        if (item.getDienstbundel().getLeveringsautorisatie() != null && item.getDienstbundel().getLeveringsautorisatie().getID() != null) {
            item.getDienstbundel().setLeveringsautorisatie(leveringsautorisatieRepository.getOne(item.getDienstbundel().getLeveringsautorisatie().getID()));
        }

        // Conversie LO3 rubriek niet updaten via de AbonnementLO3Rubriek (laden via id)
        if (item.getRubriek() != null && item.getRubriek().getID() != null) {
            item.setRubriek(rubriekRepository.getOne(item.getRubriek().getID()));
        }
    }

    /**
     * AbonnementLO3Rubriek historie verwerker.
     */
    public static final class HisDienstbundelLO3RubriekVerwerker extends AbstractHistorieVerwerker<DienstbundelLO3Rubriek, HisDienstbundelLO3Rubriek> {

        @Override
        public HisDienstbundelLO3Rubriek maakHistorie(final DienstbundelLO3Rubriek item) {
            // Controle inhoudelijk leeg

            final HisDienstbundelLO3Rubriek hisAbonnementLO3Rubriek = new HisDienstbundelLO3Rubriek();

            // A-laag
            hisAbonnementLO3Rubriek.setDienstbundelLO3Rubriek(item);

            // Historie
            hisAbonnementLO3Rubriek.setDatumTijdRegistratie(new DatumTijdAttribuut(new Date()));

            return hisAbonnementLO3Rubriek;
        }

        @Override
        public boolean isHistorieInhoudelijkGelijk(final HisDienstbundelLO3Rubriek nieuweHistorie, final HisDienstbundelLO3Rubriek actueleRecord) {
            return true;
        }

        @Override
        public Set<HisDienstbundelLO3Rubriek> geefHistorie(final DienstbundelLO3Rubriek item) {
            return item.getHisDienstbundelLO3RubriekLijst();
        }

        @Override
        public void kopieerHistorie(final DienstbundelLO3Rubriek item, final DienstbundelLO3Rubriek managedItem) {
            managedItem.setDienstbundel(item.getDienstbundel());
            managedItem.setRubriek(item.getRubriek());
        }
    }
}
